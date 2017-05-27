/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.File;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author alejo
 */
public class Compositor {

    private ArrayList<Genero> generos = new ArrayList<>();
    private String[][] matriz_transicion;
    private boolean ejecutado;

    //Punto 7
    private char[] caracteres;
    private double[] probabilidades;

    /**
     * Constructor de la clase compositor, se cargan los generos al inicializar
     * una instancia de la clase
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

        for (int i = 0; i < generos.size(); i++) {
            if (generos.get(i).getNombre().equalsIgnoreCase(nombre)) {
                if (generos.get(i).cargar_configuracion()) {
                    // necesito que vea como soluciona aqui, ese ConfigurarProbabilidades tiene muchas cosas raras
                    // de preferencia necesito aqui me devuelva un properties
                    //return generos.get(i).
                }
            }
        }

        return null;
    }

    /**
     *
     * @return
     */
    public boolean guardar_configuraciones() {

        // necesitamos mirar este
        return true;
    }

    /**
     * metodo que permite cargar los generos de la carpeta de banco
     */
    private void cargar_generos() {

        // cargamos la ubicacion del directorio donde esta el banco de canciones
        File directorio = new File(Utilidades.BANCO_CANCIONES);

        // verificamos que exista
        if (directorio.exists()) {

            // obtenemos la lista de archivos en el banco
            File[] ficheros = directorio.listFiles();

            // recorremos la lista de archivos
            for (int x = 0; x < ficheros.length; x++) {

                // verificamo si el archivo es una carpeta
                if (ficheros[x].isDirectory()) {

                    // inicializamos el genero por la carpeta
                    Genero tmpGenero = new Genero(ficheros[x].getPath(), ficheros[x].getName());

                    // cargamos la configuracion del genero
                    tmpGenero.cargar_configuracion();

                    // agregamos el genero a la lista de generos
                    generos.add(tmpGenero);

                    break;
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
     * @return true si se crea la canción, falso si ocurre un error durante el
     * proceso
     */
    public boolean generar_cancion(char caracter, String genero, int tamano) {

        // validamos que el caracter especificado por el usuario sea una letra
        if (!validarCaracter(caracter)) {
            System.err.println("No es un caracter válido.");
            return false;
        }

        if (!ejecutado) {
            // leer cada genero a través de un hilo
            cargar_letras();
            ejecutado = true;
        }
        
        // calcular matriz de transición

        // generamos los vectores que almacenarán la canción y las probabilidades aleatorias
        generarVectores(caracter, tamano);

        // llenar vector con la nueva canción
        crear_cancion();

        return true;

    }

    /**
     * Crea la nueva canción, almacenandola en el arreglo de caracteres
     */
    private void crear_cancion() {

        for (int i = 0; i < probabilidades.length; i++) {
            caracteres[i + 1] = obtener_caracter(probabilidades[i], caracteres[i]);
        }
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

        char caracter_nuevo = '?';

        for (int i = 0; i < matriz_transicion[0].length; i++) {

            if (matriz_transicion[i][0].charAt(0) == letra_actual) {

                double aux;

                for (int j = 1; j < matriz_transicion.length; j++) {

                    aux = Double.parseDouble(matriz_transicion[i][j]);

                    if (aux == valor_probabilidad) {
                        return matriz_transicion[0][i].charAt(0);
                    } else if (aux < valor_probabilidad) {
                        caracter_nuevo = matriz_transicion[0][i].charAt(0);
                    }
                }

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
    private boolean validarCaracter(char caracter) {
        return caracter >= 'a' && caracter <= 'z' || caracter == 'ñ';
    }

    public void cargar_letras() {

        for (int i = 0; i < generos.size(); i++) {
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
    private void generarVectores(char caracter, int tamano) {

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

}
