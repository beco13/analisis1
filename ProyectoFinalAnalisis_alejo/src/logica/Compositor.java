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
public class Compositor extends Utilidades {

    private ArrayList<Genero> generos = new ArrayList<>();
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

    private HashMap<Character, Integer> popularidad_letras;
    private Character letra_mas_popular;

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
        matriz_transicion = super.getMoldeTabla();
        ejecutado = false;
    }

    public void setCancion_creada(CallbackCompositor cancion_creada) {
        this.cancion_creada = cancion_creada;
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

        // validamos que el caracter especificado por el usuario sea una letra
        if (!validar_caracter(caracter)) {
            System.err.println("No es un caracter válido.");
            cancion_creada.callback(false);
            return;
        }

        // inicia conteo de tiempo de ejecución de la tarea
        long tiempo_inicial = System.currentTimeMillis();

        // si ya se ejecuto una vez, omite este paso
        if (!ejecutado) {

            barra_progreso.modificarProgressBar(0, "Cargando aplicación base");

            // leer cada genero a través de un hilo
            cargar_letras(caracter, genero, tamano, tiempo_inicial);

            // cambiamos el valor de la variable para que omita este paso en la próxima ocasión
            ejecutado = true;
        } else {

            progreso = 90;
            // modificando el progressbar de que ya realizo la parte de cargar letras
            barra_progreso.modificarProgressBar(progreso, "Letras de canción cargadas.");

            terminar_cancion(caracter, genero, tamano, tiempo_inicial);
        }

    }

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

    private void terminar_cancion(char caracter, String genero, int tamano, long tiempo_inicial) {

        // calcular matriz de transición
        calcular_matriz_transicion(genero);

        progreso += 5;
        barra_progreso.modificarProgressBar(progreso, "Matriz de transición generada.");

        // validamos que el tamaño no sea 0
        if (tamano == 0) {
            tamano = obtener_promedio_caracteres_genero(genero);
        }

        // generamos los vectores que almacenarán la canción y las probabilidades aleatorias
        generar_vectores(caracter, tamano);

        System.out.println("Vector generado");

        // llenar vector con la nueva canción
        crear_cancion();

        System.out.println("Cancion creada");

        progreso += 5;
        barra_progreso.modificarProgressBar(progreso, "Canción generada.");

        // tiempo final de la tarea
        long tiempo_final = System.currentTimeMillis();

        // tiempo en segundos
        long tiempo_segundos = (tiempo_final - tiempo_inicial) / 1000;

        System.out.println("Tiempo Resta " + (tiempo_final - tiempo_inicial) + " tiempo segundos " + tiempo_segundos);

        // pasamos el tiempo a minutos:segundos
        this.tiempo = calcular_tiempo(tiempo_segundos, 0);

        System.out.println(this.tiempo);

        // indicamos que ya se creo la cancion llamando el callback
        cancion_creada.callback(true);

    }

    private boolean calcular_matriz_transicion(String genero) {

        Genero genero_trabajar = null;
        // iteramos el arraylist de géneros en busca del género especificado
        for (int i = 0; i < generos.size(); i++) {

            // verifica que el género concuerde con el nombre especificado
            if (generos.get(i).getNombre().equalsIgnoreCase(genero)) {

                genero_trabajar = generos.get(i);

                // asignamos las probabilidades que se trabajarán
                probabilidades_trabajas = generos.get(i).getConfiguracion().getPropiedades();
                break;
            }
        }

        if (genero_trabajar != null) {
            System.out.println("");
            System.out.println("AQUIIIIIIII");
            System.out.println("");
            for (int i = 0; i < generos.size(); i++) {

                System.out.println("");
                System.out.println("Matriz del genero: " + generos.get(i).getNombre());
                System.out.println("");
                for (int j = 0; j < generos.get(i).getMatriz_transicion().length; j++) {
                    for (int k = 0; k < generos.get(i).getMatriz_transicion().length; k++) {
                        System.out.print(generos.get(i).getMatriz_transicion()[j][k] + " ");
                    }
                    System.out.println("");
                }
            }
        }

        return false;
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

        popularidad_letras = new HashMap<Character, Integer>();

        // iteramos el arreglo de caracteres
        for (int i = 0; i < probabilidades.length; i++) {

            // identificamos la letra
            char tmpChart = obtener_caracter(probabilidades[i], caracteres[i]);

            // verificamos cuantas veces aparece
            int total_letras = popularidad_letras.get(tmpChart) == null ? 0 : popularidad_letras.get(tmpChart);

            // gaurdamos el valor de la cantidad de veces que ha aparecido
            popularidad_letras.put(tmpChart, total_letras + 1);

            //guarda el nuevo caracter de la canción
            caracteres[i + 1] = tmpChart;
        }

        verificar_letra_mas_repetida();
    }

    private void verificar_letra_mas_repetida() {

        char mostKey = ' ';
        int mostValue = 0;

        for (Map.Entry<Character, Integer> entry : popularidad_letras.entrySet()) {
            if (entry.getValue() > mostValue) {
                mostKey = entry.getKey();
                mostValue = entry.getValue();
            }
        }

        letra_mas_popular = mostKey;

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
        char caracter_nuevo = '?';

        // iteramos las filas de la matriz de transicion
        for (int i = 1; i < matriz_transicion.length; i++) {

            // entra cuando encuentre en la matriz (fila) la letra que viene como parametro
            if (matriz_transicion[0][i].charAt(0) == letra_actual) {

                // variable aux para castear los valores de la matriz de transicion
                double aux;

                // iteramos las columnas en busca de la probabilidad especficada como parámetro
                for (int j = 1; j < matriz_transicion.length; j++) {

                    // asignamos el valor casteado
                    aux = Double.parseDouble(matriz_transicion[i][j]);

                    // pregunta si la probabilidad en curso es igual a la de parámetro
                    if (aux == valor_probabilidad) {

                        // si es asi retorna ese caracter inmediatamente
                        return matriz_transicion[0][i].charAt(0);

                    } // pregunta si la probabilidad en curso es menor a la buscada (la más cercanada a la buscada)
                    else if (aux < valor_probabilidad) {

                        // se asigna el caracter con la probabilidad más cercana a la buscada
                        caracter_nuevo = matriz_transicion[0][i].charAt(0);
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

    public void cargar_letras(char caracter, String genero, int tamano, long tiempo_inicial) {

        for (int i = 0; i < generos.size(); i++) {

            generos.get(i).setCallBackFinalizar(
                    new CallbackGenero() {
                @Override
                public void callback(String nombre_genero) {
                    progreso += 10;
                    barra_progreso.modificarProgressBar(progreso, "Finalizo de cargar el género: " + nombre_genero);

                    if (progreso == 90) {
                        barra_progreso.modificarProgressBar(progreso, "Todos los géneros han sido cargados");
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
     *
     * @param lbl_generando
     * @param progressbar
     */
    public void inicializar_barra_progreso(JLabel lbl_generando, JProgressBar progressbar) {
        barra_progreso = new BarraProgreso(lbl_generando, progressbar);
        barra_progreso.execute();
    }

    public String getTiempo() {
        return tiempo;
    }

    public char[] getCaracteres() {
        return caracteres;
    }

    public double[] getProbabilidades() {
        return probabilidades;
    }

    public Properties getProbabilidades_trabajas() {
        return probabilidades_trabajas;
    }

    public Character getLetra_mas_popular() {
        return letra_mas_popular;
    }
}
