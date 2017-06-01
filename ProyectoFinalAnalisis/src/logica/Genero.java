/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alejo
 */
public class Genero extends Thread {

    private String nombre;
    private String ruta_carpeta;
    private ArrayList<Cancion> lista_canciones = new ArrayList<>();
    private int[][] frecuencia_acumulada;
    private double[][] matriz_transicion;
    private ConfiguracionProbabilidades configuracion;
    final String BANCO_CONFIGURACION = "banco/config";

    private CallbackGenero callBackFinalizar;
    private int promedio_caracteres;

    
    /**
     * metodo constructor
     * 
     * @param ruta_carpeta
     * @param nombre 
     */
    public Genero(String ruta_carpeta, String nombre) {
        this.ruta_carpeta = ruta_carpeta;
        this.nombre = nombre;
        frecuencia_acumulada = new int[30][30];
        matriz_transicion = new double[30][30];
    }

    
    /**
     * metodo que permite agregar un objecto callback para ser ejectucado cuando finalize la carga de las canciones
     * @param callBackFinalizar 
     */
    public void setCallBackFinalizar(CallbackGenero callBackFinalizar) {
        this.callBackFinalizar = callBackFinalizar;
    }

    /**
     * metodo que permite cargar el archivo de configuracion del genero
     *
     * @return
     */
    public boolean cargar_configuracion() {

        String ruta_archivo_config = BANCO_CONFIGURACION + "/" + nombre + ".properties";

        // cargamos la ruta del archivo de configuracion
        File archivo_configuracion = new File(ruta_archivo_config);

        // verificamos que exista el archivo
        if (archivo_configuracion.exists()) {

            // creamos la nueva instancia
            configuracion = new ConfiguracionProbabilidades(ruta_archivo_config);

            // verificamos que se puedan cargar las propiedades
            if (configuracion.leer_archivo()) {
                return true;
            }
        }

        return false;
    }

    /**
     * metodo que permite cargar las canciones del genero
     */
    private void cargar_canciones() {

        // cargamos la ubicacion del directorio donde esta el banco de canciones
        File directorio = new File(ruta_carpeta);

        // verificamos que exista
        if (directorio.exists()) {

            // obtenemos la lista de los archivos con las letras de las canciones
            File[] ficheros = directorio.listFiles();

            // recorremos la lista de archivos
            for (int x = 0; x < ficheros.length; x++) {

                // verificamo si el archivo es una carpeta
                if (ficheros[x].isFile()) {

                    // inicializamos la cancion con la ruta del archivo                    
                    Cancion tmpCancion = new Cancion(ficheros[x].getPath(), ficheros[x].getName());

                    // leemos la cancion
                    tmpCancion.leer_cancion();

                    // agregamos la lista de canciones al archivo
                    lista_canciones.add(tmpCancion);

                }
            }
        }

    }

    /**
     * metodo que permite calcular la frecuencia de letras de todas las
     * canciones
     */
    private void calcular_frecuencia_letras() {
        frecuencia_acumulada = calcular_frecuencia_letras(0, lista_canciones.size() - 1);
    }

    /**
     * metodo utilizado para sumar 2 matrices
     * 
     * @param frecuenciaA
     * @param frecuenciaB
     * @return 
     */
    private int[][] sumar_frecuencias(int[][] frecuenciaA, int[][] frecuenciaB) {

        int[][] aux = new int[30][30];

        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 30; j++) {
                aux[i][j] = frecuenciaA[i][j] + frecuenciaB[i][j];
            }
        }

        return aux;

    }

    /**
     * metodo utilizado para calcular la frecuencia de letras,
     * trabaja recursivamente aplicando el concepto de divide y venceras
     * 
     * @param inicio
     * @param fin
     * @return 
     */
    private int[][] calcular_frecuencia_letras(int inicio, int fin) {

        // calculamos el total
        int total = fin - inicio;

        // verificamos si solo hay uno (caso base)
        if (total == 1) {
            // como llegamos hasta el total de uno a este genero se le opera por los valores del genero seleccionado
            return lista_canciones.get(inicio).getFrecuencia_letras();
        }

        // hayamos la mitad
        int mitad = total / 2;

        // calculamos el valor por el lado izquierdo hasta la mitad
        int[][] frecuenciaA = calcular_frecuencia_letras(inicio, inicio + mitad);

        // calculamos el valor por el lado derecho depsues de la mitad hasta el final
        int[][] frecuenciaB = calcular_frecuencia_letras(inicio + mitad, fin);

        // y sumamos las matrices
        return sumar_frecuencias(frecuenciaA, frecuenciaB);
    }

    /**
     * Este metodo se encarga de calcular la matriz de transicion del género
     */
    private void calcular_matriz_transicion() {

        for (int i = 0; i < matriz_transicion.length; i++) {

            int cantidadVeces = 0;

            for (int j = 0; j < matriz_transicion.length; j++) {
                cantidadVeces += frecuencia_acumulada[i][j];
            }

            if (cantidadVeces != 0) {

                matriz_transicion[i][0] = ((double) frecuencia_acumulada[i][0] / (double) cantidadVeces);

                for (int j = 1; j < matriz_transicion.length; j++) {

                    matriz_transicion[i][j] = ((double) frecuencia_acumulada[i][j] / (double) cantidadVeces) + matriz_transicion[i][j - 1];
                }
            }

        }
    }

    /**
     * metodo que recorre todas las canciones pertenecientes al genero para
     * obtener el total de caracteares
     */
    public void calcular_promedio_caracteres() {

        if (lista_canciones.isEmpty()) {
            promedio_caracteres = 0;
            return;
        }

        int total_letras = 0;

        for (int i = 0; i < lista_canciones.size(); i++) {
            total_letras += lista_canciones.get(i).getTotalLetras();
        }

        promedio_caracteres = total_letras / lista_canciones.size();

        //System.out.println("Termino de calcular_promedio_caracteres " + nombre);
    }

    
    /**
     * metodo que se encarga de ser llamado durandte la ejecucion del hilo
     */
    @Override
    public void run() {

        cargar_canciones();

        calcular_frecuencia_letras();

        calcular_matriz_transicion();

        calcular_promedio_caracteres();

        //System.out.println("Retorno callback " + nombre);
        callBackFinalizar.callback(nombre);

    }

    /**
     * metodo que permite obenet el nombre del genero
     * @return 
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * metodo que permite obtener
     * @return 
     */
    public ConfiguracionProbabilidades getConfiguracion() {
        return configuracion;
    }

    /**
     * metodo que permite obtener el promedio de caracteres ya calculado 
     * @return 
     */
    public int getPromedio_caracteres() {
        return promedio_caracteres;
    }

    /**
     * metodo que permite obtener la matriz transicion calculada
     * @return 
     */
    public double[][] getMatriz_transicion() {
        return matriz_transicion;
    }
}
