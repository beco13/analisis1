/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import interfaz.BarraProgreso;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author alejo
 */
public class Compositor {

    // Generos
    private ArrayList<Genero> generos = new ArrayList<>();

    // Matriz final para la creación de la canción
    private String[][] matriz_transicion;

    // Variable para almacenar las probabilidades trabajadas de un género
    private Properties probabilidades_trabajas;

    // Variable para saber si ya se ejecuto una vez la carga de matrices
    private boolean ejecutado;

    // Vectores que almacenan los caracteres de la nueva canción y las probabilidades aleatorias
    private char[] caracteres;
    private double[] probabilidades;

    // Variable de tiempo de ejecución de la tarea
    private String tiempo;

    // Variable barra de progreso
    private BarraProgreso barra_progreso;

    // Variables de la funcion del grafo
    private HashMap<Character, Integer> popularidad_letras;
    private Character letra_mas_popular;

    // Dirección donde estan almacenados los archivos con las canciones
    final String BANCO_CANCIONES = "banco/canciones";

    // variable de progreso
    int progreso = 0;

    CallbackCompositor cancion_creada;

    /**
     * Constructor de la clase compositor, se cargan los generos al inicializar
     * una instancia de la clase e iniciamos la matriz de transición
     */
    public Compositor() {
        cargar_generos();
        ejecutado = false;
    }

    /**
     * Permite cargar las configuraciones del género especificado
     *
     * @param nombre del género a cargar
     * @return las propiedades clave-valor del género o null en caso de no
     * cargarse
     */
    public Properties cargar_configuracion(String nombre) {

        // iteramos el arraylist de géneros en busca del género especificado
        for (int i = 0; i < generos.size(); i++) {

            // verifica que el género concuerde con el nombre especificado
            if (generos.get(i).getNombre().equalsIgnoreCase(nombre)) {

                // verificamos que se hayan cargado las propiedades
                if (generos.get(i).cargar_configuracion()) {

                    return generos.get(i).getConfiguracion().getPropiedades();
                }
            }
        }

        return null;
    }

    /**
     * Permite guardar las configuraciones de un genero
     *
     * @param nombre del género
     * @param propiedades valores a guardar
     * @return true si se pueden guardar false en caso contrario
     */
    public boolean guardar_configuraciones(String nombre, Properties propiedades) {

        // arreglo donde almacenaremos los valores para poder operarlos y verificar que no existan inconsistencias
        double[] valores = new double[9];

        // valor mayor por ser su género
        double valor_mayor;

        try {
            // asignamos los valores al arreglo
            valores[0] = Double.parseDouble(propiedades.getProperty("bachata"));
            valores[1] = Double.parseDouble(propiedades.getProperty("balada"));
            valores[2] = Double.parseDouble(propiedades.getProperty("merengue"));
            valores[3] = Double.parseDouble(propiedades.getProperty("pop"));
            valores[4] = Double.parseDouble(propiedades.getProperty("ranchera"));
            valores[5] = Double.parseDouble(propiedades.getProperty("reggaeton"));
            valores[6] = Double.parseDouble(propiedades.getProperty("rock"));
            valores[7] = Double.parseDouble(propiedades.getProperty("salsa"));
            valores[8] = Double.parseDouble(propiedades.getProperty("vallenato"));

            // asignamos el valor del género seleccionado
            valor_mayor = Double.parseDouble(propiedades.getProperty(nombre));

        } catch (NumberFormatException exception) {
            exception.getStackTrace();
            return false;
        }

        // verificamos que la suma sea igual a uno
        if (verificar_suma(valores, valor_mayor)) {

            // iteramos el arraylist de géneros en busca del género especificado
            for (int i = 0; i < generos.size(); i++) {

                // verifica que el género concuerde con el nombre especificado
                if (generos.get(i).getNombre().equalsIgnoreCase(nombre)) {

                    return generos.get(i).getConfiguracion().guardar(propiedades);
                }
            }
        }

        return false;
    }

    /**
     * Se encarga de verificar que la suma de probabilidades de un genero no
     * supere o este por debajo de uno, antes de guardar
     *
     * @param valores arreglo con los valores a verificar
     * @param valor_mayor el valor que debe ser mayor a los demás por ser su
     * género
     * @return true si la suma es uno, false en caso contrario
     */
    public boolean verificar_suma(double[] valores, double valor_mayor) {

        // Variable donde almacenaremos la suma de los valores
        double suma = 0;

        for (int i = 0; i < valores.length; i++) {

            // Verificamos que el valor del género seleccionado sea mayor a los demás
            if (valor_mayor < valores[i]) {
                return false;
            }

            // Almacenamos la suma de los valores
            suma += valores[i];

            // Verificamos que la suma no pase de uno
            if (suma > 1) {
                return false;
            }
        }

        return suma > 0.99;
    }

    /**
     * metodo que permite cargar los generos de la carpeta de banco
     */
    private void cargar_generos() {

        // cargamos la ubicacion del directorio donde esta el banco de canciones
        File directorio = new File(BANCO_CANCIONES);

        // verificamos que exista
        if (directorio.exists()) {

            // obtenemos la lista de archivos en el banco
            File[] ficheros = directorio.listFiles();

            // recorremos la lista de archivos
            for (int x = 0; x < ficheros.length; x++) {

                // verificamo si el archivo es una carpeta
                if (ficheros[x].isDirectory()) {

                    // inicializamos el genero por la carpeta, y le indicamos el callback a ejecutar cuando se inicialice el hilo
                    Genero tmpGenero = new Genero(ficheros[x].getPath(), ficheros[x].getName());

                    // cargamos la configuracion del genero
                    tmpGenero.cargar_configuracion();

                    // agregamos el genero a la lista de generos
                    generos.add(tmpGenero);

                }

            }
        }
    }

    /**
     * Método que se encarga de generar la nueva canción de acuerdo al caracter
     * inicial especificado, el género seleccionado y tamaño especificado
     *
     * @param caracter primera caracter de la nueva canción
     * @param genero tipo de género de la nueva canción
     * @param tamano es la cantidad de caracteres que tendra la canción
     */
    public void generar_cancion(char caracter, String genero, int tamano) {

        // inicia conteo de tiempo de ejecución de la tarea
        long tiempo_inicial = System.currentTimeMillis();

        // validamos que el caracter especificado por el usuario sea una letra
        if (!validar_caracter(caracter)) {
            System.err.println("No es un caracter válido.");
            cancion_creada.callback(false);
            return;
        }

        // si ya se ejecuto una vez, omite este paso
        if (!ejecutado) {

            barra_progreso.modificarProgressBar(0, "Cargando aplicación base");

            // leer cada genero a través de un hilo
            cargar_letras(caracter, genero, tamano, tiempo_inicial);

            // cambiamos el valor de la variable para que omita este paso en la próxima ocasión
            ejecutado = true;
        } else {

            // modificando el progressbar de que ya realizo la parte de cargar letras
            progreso = 50;            
            barra_progreso.modificarProgressBar(progreso, "Letras de canción cargadas.");
            
            progreso = 90;
            barra_progreso.modificarProgressBar(progreso, "Ejecutaremos la parte final.");

            // terminamos el proceso de generar canción omitiendo la carga de letras
            terminar_cancion(caracter, genero, tamano, tiempo_inicial);
        }

    }

    /**
     * Permite obtener el promedio de caracteres de cada genero
     *
     * @param genero el nombre del género que se requiere obtener el promedio de
     * caracteres
     * @return el número promedio de caracteres de la canción
     */
    private int obtener_promedio_caracteres_genero(String genero) {

        // iteramos el arraylist de géneros en busca del género especificado
        for (int i = 0; i < generos.size(); i++) {

            // verifica que el género concuerde con el nombre especificado
            if (generos.get(i).getNombre().equalsIgnoreCase(genero)) {
                return generos.get(i).getPromedio_caracteres();
            }
        }

        return 0;
    }

    /**
     * Se encarga de finalizar el proceso de generar_cancion
     *
     * @param caracter el primer caracter de la nueva canción
     * @param genero nombre del genero de la nueva canción
     * @param tamano es el tamaño de la nueva canción
     * @param tiempo_inicial tiempo de inicio del proceso de generación de
     * canción
     */
    private void terminar_cancion(char caracter, String genero, int tamano, long tiempo_inicial) {

        // calcular matriz de transición
        calcular_matriz_transicion(genero);

        // modificamos el estado de la barra de progreso
        progreso += 5;
        barra_progreso.modificarProgressBar(progreso, "Matriz de transición generada.");

        // validamos que el tamaño no sea 0
        if (tamano == 0) {
            tamano = obtener_promedio_caracteres_genero(genero);
        }

        // generamos los vectores que almacenarán la canción y las probabilidades aleatorias
        generar_vectores(caracter, tamano);

        // llenar vector con la nueva canción
        crear_cancion();

        // modificamos el estado de la barra de progreso
        progreso += 5;
        barra_progreso.modificarProgressBar(progreso, "Canción generada.");

        // tiempo final de la tarea
        long tiempo_final = System.currentTimeMillis();

        // tiempo en segundos
        long tiempo_segundos = (tiempo_final - tiempo_inicial) / 1000;        

        // pasamos el tiempo a minutos:segundos
        this.tiempo = calcular_tiempo(tiempo_segundos, 0);

        System.out.println(this.tiempo);

        // indicamos que ya se creo la cancion llamando el callback
        cancion_creada.callback(true);

    }

    /**
     * metodo publico que permite calcular los valores de la matriz transicion
     *
     * @param genero nombre del genero que se esta creando la canción
     */
    public void calcular_matriz_transicion(String genero) {
        Genero genero_trabajar = null;

        // iteramos el arraylist de géneros en busca del género especificado
        for (int i = 0; i < generos.size(); i++) {

            // verifica que el género concuerde con el nombre especificado
            if (generos.get(i).getNombre().equalsIgnoreCase(genero)) {

                // guardamos el género
                genero_trabajar = generos.get(i);

                // asignamos las probabilidades que se trabajarán
                probabilidades_trabajas = generos.get(i).getConfiguracion().getPropiedades();

                break;
            }
        }

        // calculamos la matriz
        double[][] tmpMatriz = calcular_matriz_transicion(0, generos.size() - 1, genero_trabajar);

        for (int i = 0; i < 30; i++) {

            // verificamos que la última columna sea un uno
            if (tmpMatriz[i][29] < 0.99) {

                // completamos para que de uno en caso de que no lo sea
                completar_fila(i, tmpMatriz);
            }
        }

        // transformamos la matriz
        matriz_transicion = generar_tabla_matriz_transicion(tmpMatriz);
    }

    /**
     * metodo que permite transcribir los valores obtenidos para la matriz
     * transccion a forma de tabla en String
     *
     * @param matriz auxiliar
     * @return la nueva matriz generada
     */
    private String[][] generar_tabla_matriz_transicion(double[][] matriz) {
        String[][] aux = new String[31][31];

        // almacenamos los simbolos que van a interactuar en la tabla
        String simbolos = "abcdefghijklmnñopqrstuvwxyz,. ";

        // iteramos sobre la fila
        for (int i = 0; i < 30; i++) {

            // asignamos a la primera fila y a la primera columna su letra correpondiente
            aux[0][i + 1] = Character.toString(simbolos.charAt(i));
            aux[i + 1][0] = Character.toString(simbolos.charAt(i));

            // iteramos sobre la columna
            for (int j = 0; j < 30; j++) {

                // y asignamos en la casilla el valor de la matriz entrante
                aux[i + 1][j + 1] = Double.toString(matriz[i][j]);
            }
        }

        return aux;
    }

    /**
     * metodo que permite operar la matriz de cada genero con el valor
     * correspondiente al genero seleccionado por el usuario
     *
     * @param tmpGenero género que se esta iterando
     * @param generoSeleccioado la instancia del género elegido
     * @return una matriz auxiliar
     */
    private double[][] operar_probabilidad(Genero tmpGenero, Genero generoSeleccioado) {

        double[][] aux = new double[30][30];
        double[][] matriz_transicion_genero = tmpGenero.getMatriz_transicion();
        ConfiguracionProbabilidades configGenero = generoSeleccioado.getConfiguracion();

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {

                double operador = 0.0;

                switch (tmpGenero.getNombre()) {

                    case "bachata":
                        operador = configGenero.getBachata();
                        break;
                    case "balada":
                        operador = configGenero.getBalada();
                        break;
                    case "merengue":
                        operador = configGenero.getMerengue();
                        break;
                    case "pop":
                        operador = configGenero.getPop();
                        break;
                    case "ranchera":
                        operador = configGenero.getRanchera();
                        break;
                    case "reggaeton":
                        operador = configGenero.getReggaeton();
                        break;
                    case "rock":
                        operador = configGenero.getRock();
                        break;
                    case "salsa":
                        operador = configGenero.getSalsa();
                        break;
                    case "vallenato":
                        operador = configGenero.getVallenato();
                        break;
                    default:
                        operador = 1;
                        break;

                }

                aux[i][j] = matriz_transicion_genero[i][j] * operador;
            }
        }

        return aux;

    }

    /**
     * metodo que se encarga de completar las filas para que el la ultima
     * columna de 1
     *
     * @param fila que se va modificar de la matriz
     * @param aux la matriz auxiliar de trabajo
     */
    private void completar_fila(int fila, double[][] aux) {

        // obtengo el valor de la ultima columna
        double total = aux[fila][29];

        // verifico cuanto me hace falta para llegar a 1
        double resta = (double)(1 - total);

        // calculo cuando debo sumar por cada uno
        double asignar = (double)resta / (double)30;       

        // empezamos a sumar por cada columna
        for (int i = 0; i < 30; i++) {
            
            // actualizamos el valor
            aux[fila][i] += asignar;
        }
    }

    /**
     * metodo que permite sumar la matriz operada de un genero con la de otro
     * genero
     *
     * @param generoA primera matriz
     * @param generoB segunda matriz
     * @return la nueva matriz sumada entre las dos que llegan por párametro
     */
    private double[][] sumar_probabilidad(double[][] generoA, double[][] generoB) {
        double[][] aux = new double[30][30];

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                aux[i][j] = generoA[i][j] + generoB[i][j];
            }
        }

        return aux;
    }

    /**
     * metodo que permite calcular los valores de matriz transicion a manera de
     * que funcione recursivamente sobre los generos que hay sobre la lista de
     * generos
     *
     * @param inicio el rango inicial de la matriz
     * @param fin rango final de la matriz
     * @param generoSeleccioado el género de la nueva canción
     * @return una matriz auxiliar
     */
    private double[][] calcular_matriz_transicion(int inicio, int fin, Genero generoSeleccioado) {

        // calculamos el total
        int total = fin - inicio;

        // verificamos si solo hay uno (caso base)
        if (total == 1) {
            // como llegamos hasta el total de uno a este genero se le opera por los valores del genero seleccionado
            return operar_probabilidad(generos.get(inicio), generoSeleccioado);
        }

        // hayamos la mitad
        int mitad = total / 2;

        // calculamos el valor por el lado izquierdo hasta la mitad
        double[][] generoA = calcular_matriz_transicion(inicio, inicio + mitad, generoSeleccioado);

        // calculamos el valor por el lado derecho depsues de la mitad hasta el final
        double[][] generoB = calcular_matriz_transicion(inicio + mitad, fin, generoSeleccioado);

        // y sumamos las matrices
        return sumar_probabilidad(generoA, generoB);
    }

    /**
     * Transforma el tiempo a minutos y segundos apartir de los segundos
     * indicados, por parametro.
     *
     * @param tiempo el tiempo ocupado durante la generación de la canción en
     * segundos
     * @param minutos variable para ir contando los minutos que se tardo en
     * ejecucion a partir de los segundos registrados
     * @return una cadena con el tiempo en minutos y segundos 00:00 minutos
     */
    private String calcular_tiempo(long tiempo, int minutos) {

        // si el tiempo es menor a 60 segundos termina la recursividad
        if (tiempo < 60) {

            // si los segundos son menos a 10 le agregamos el 0 antes y lo retornamos
            if (tiempo < 10) {
                return minutos + ":0" + tiempo + " minutos";
            } // sino retornamos la cadena con los minutos y segundos
            else {
                return minutos + ":" + tiempo + " minutos";
            }
        } // sino sigue aumentando los minutos y descontando segundos
        else {
            return calcular_tiempo(tiempo - 60, minutos + 1);
        }
    }

    /**
     * Crea la nueva canción, almacenandola en el arreglo de caracteres
     */
    private void crear_cancion() {

        popularidad_letras = new HashMap<>();

        // iteramos el arreglo de caracteres
        for (int i = 0; i < probabilidades.length; i++) {

            // identificamos la letra
            char tmpChart = obtener_caracter(probabilidades[i], caracteres[i]);

            //guarda el nuevo caracter de la canción
            caracteres[i + 1] = tmpChart;

            // verificamos cuantas veces aparece
            int total_letras = popularidad_letras.get(tmpChart) == null ? 0 : popularidad_letras.get(tmpChart);

            // guardamos el valor de la cantidad de veces que ha aparecido
            popularidad_letras.put(tmpChart, total_letras + 1);
        }

        // buscar la letra que más pasa por el grafo
        verificar_letra_mas_repetida();
    }

    /**
     * Permite encontrar la letra que más paso en el grafo
     */
    private void verificar_letra_mas_repetida() {

        char letra_mas_repetida = ' ';
        int cantidad_ciclos = 0;

        for (Map.Entry<Character, Integer> entry : popularidad_letras.entrySet()) {

            // verificamos si el valor iterado es mayor
            if (entry.getValue() > cantidad_ciclos) {

                // asignamos los nuevos valores
                letra_mas_repetida = entry.getKey();
                cantidad_ciclos = entry.getValue();
            }
        }

        // asignamos el caracter más repetido
        letra_mas_popular = letra_mas_repetida;

    }

    /**
     * Obtiene el nuevo caracter que se asignara al arreglo de caracteres (La
     * nueva canción), apartir de la probabilidad recibida y la ultima letra del
     * arreglo de caracteres
     *
     * @param valor_probabilidad es el valor(exacto o más aproximado) que se
     * buscara dentro de la fila de la letra actual
     * @param letra_actual define la fila en la cual se buscará la probabilidad
     * indicada
     * @return el nuevo caracter que tendrá la canción
     */
    private char obtener_caracter(double valor_probabilidad, char letra_actual) {

        // caracter a retornar que tenga la probabilidad más proxima
        char caracter_nuevo = ' ';

        // iteramos las filas de la matriz de transicion
        for (int i = 1; i < matriz_transicion.length; i++) {

            // entra cuando encuentre en la matriz (fila) la letra que viene como parametro
            if (matriz_transicion[i][0].charAt(0) == letra_actual) {

                // variable aux para castear los valores de la matriz de transicion
                double aux;

                // iteramos las columnas en busca de la probabilidad especificada como parámetro
                for (int j = 1; j < matriz_transicion.length; j++) {

                    // asignamos el valor casteado
                    aux = Double.parseDouble(matriz_transicion[i][j]);

                    // pregunta si la probabilidad en curso es igual a la de parámetro
                    if (aux == valor_probabilidad) {

                        // si es asi retorna ese caracter inmediatamente
                        return matriz_transicion[0][j].charAt(0);

                    } // pregunta si la probabilidad en curso es menor a la buscada (la más cercanada a la buscada)
                    else if (aux < valor_probabilidad) {

                        // se asigna el caracter con la probabilidad más cercana a la buscada
                        caracter_nuevo = matriz_transicion[0][j].charAt(0);
                        System.out.println(caracter_nuevo);
                    }
                }

                // como ya encontro la fila necesaria rompe el ciclo
                break;
            }
        }

        return caracter_nuevo;
    }

    /**
     * Se encarga de determinar que un caracter solo sea una letra
     *
     * @param caracter que se evaluara si es letra o no
     * @return true si es letra, falso en caso contrario
     */
    private boolean validar_caracter(char caracter) {
        return caracter >= 'a' && caracter <= 'z' || caracter == 'ñ';
    }

    /**
     * Se encarga de cargar todas las canciones del banco
     *
     * @param caracter el primer caracter de la nueva canción
     * @param genero el nombre del género de la nueva canción
     * @param tamano es el tamaño de la nueva canción
     * @param tiempo_inicial tiempo de inicio del proceso de generación de la
     * canción
     */
    public void cargar_letras(char caracter, String genero, int tamano, long tiempo_inicial) {

        for (int i = 0; i < generos.size(); i++) {

            generos.get(i).setCallBackFinalizar(
                    new CallbackGenero() {
                @Override
                public void callback(String nombre_genero) {

                    // modificamos el valor de la barra de progreso, cada que un hilo termina
                    progreso += 10;
                    barra_progreso.modificarProgressBar(progreso, "Finalizo de cargar el género: " + nombre_genero);

                    // verificamos que todos los hilos hayan terminado su proceso
                    if (progreso == 90) {

                        // modificamos el valor del progreso
                        barra_progreso.modificarProgressBar(progreso, "Todos los géneros han sido cargados");

                        // terminamos el proceso de generación de la canción
                        terminar_cancion(caracter, genero, tamano, tiempo_inicial);
                    }
                }
            });

            // cargamos el genero (hilo)
            Thread tmpThreadGenero = new Thread(generos.get(i), generos.get(i).getNombre());

            // inicializamos el genero
            tmpThreadGenero.start();

        }

    }

    /**
     * Se encarga de crear dos arreglos, uno de probabilidades y otro que que
     * almacenará la nueva canción
     *
     * @param caracter primer caracter de la nueva canción, especificado por el
     * usuario
     * @param tamano cantidad de caracteres + 1 y el tamaño del vector aleatorio
     */
    private void generar_vectores(char caracter, int tamano) {

        // iniciamos los arreglos        
        probabilidades = new double[tamano];
        caracteres = new char[tamano + 1];

        // asignamos el primer caracter a la nueva canción
        caracteres[0] = caracter;

        // variable donde se almacena el valor aleatorio asignar al arreglo probabilidades
        double numAleatorio;

        // iteramos el arreglo probabilidades
        for (int i = 0; i < tamano; i++) {

            // asignamos el nuevo valor aleatorio en la variable auxiliar
            numAleatorio = Math.random() * 1 + 0;

            // guardamos el valor en el arreglo probabilidades
            probabilidades[i] = numAleatorio;
        }
    }

    /**
     * Permite inicializar una instancia de la barra de progreso y poder estar
     * modificandola en la interfaz
     *
     * @param lbl_generando label de la interfaz que muestra el estado del
     * proceso
     * @param progressbar la barra de progreso de la interfaz
     */
    public void inicializar_barra_progreso(JLabel lbl_generando, JProgressBar progressbar) {
        barra_progreso = new BarraProgreso(lbl_generando, progressbar);
        barra_progreso.execute();
    }

    /**
     * Permite obtener el tiempo que tardó en ejecutar la operación
     *
     * @return una cadena con el tiempo en 00:00 minutos:segundos
     */
    public String getTiempo() {
        return tiempo;
    }

    /**
     * Permite acceder al vector de caracteres(la canción).
     *
     * @return el arreglo con la canción
     */
    public char[] getCaracteres() {
        return caracteres;
    }

    /**
     * Permite obtener las probabilidades aleatorias usadas en la generación de
     * la canción
     *
     * @return el arreglo de probabilidades
     */
    public double[] getProbabilidades() {
        return probabilidades;
    }

    /**
     * Permite acceder a las probabilidades del género utilizado para generar la
     * nueva canción
     *
     * @return las probabilidades
     */
    public Properties getProbabilidades_trabajas() {
        return probabilidades_trabajas;
    }

    /**
     * Permite obtener la letra que más paso por el grafo
     *
     * @return la letra
     */
    public Character getLetra_mas_popular() {
        return letra_mas_popular;
    }

    /**
     * Metodo que permite asignar una nueva instancia del callback
     *
     * @param cancion_creada el nuevo callback
     */
    public void setCancion_creada(CallbackCompositor cancion_creada) {
        this.cancion_creada = cancion_creada;
    }
}
