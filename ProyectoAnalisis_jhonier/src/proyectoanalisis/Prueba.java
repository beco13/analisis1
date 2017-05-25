package proyectoanalisis;

import java.util.HashMap;

/**
 * Ejemplo de como realizar divide y venceras para el proyecto de generación
 * automatica de canciones usando cadenas de markov
 *
 * Sólo es un ejemplo que muestra la estructura general del proceso de divide y
 * venceras. Acá no se muestra niguno de los procesos probabilisticos
 *
 * @author EinerZG
 * @version 1.0 28/04/2017
 *
 */
public class Prueba {

    /**
     * Inicializa la lista de generos musical y muestra el resultado del proceso
     *
     * @param args no aplica
     */
    public static void main(String[] args) {

        String generos[] = new String[]{"rock", "reggaeton", "salsa", "bachata", "balada"};
        Object o = realizarDivideYVencerasACanciones(generos, 1, null);

        if (o != null) {
            double[][] matrizGeneral = (double[][]) o;
            printarMatriz(matrizGeneral);
        } else {
            System.out.println("Error Fatal, programe todo nuevamente!!!");
        }

    }

    /**
     * Muestra el contenido de una matriz en consola
     *
     * @param matrizGeneral
     */
    private static void printarMatriz(double[][] matrizGeneral) {
        for (int i = 0; i < matrizGeneral.length; i++) {
            for (int j = 0; j < matrizGeneral.length; j++) {
                System.out.printf("%s\t", matrizGeneral[i][j]);
            }
            System.out.println();
        }
        System.out.println("---------------------");
    }

    /**
     * Realiza todo el proceso de divide y venceras para las canciones
     *
     * @param lista puden ser lista de generos, lista de canciones o una
     * cancioón
     * @param nivel nivel del divide y venceras al que se quiere pasar
     * @param m matriz base para generar la matriz de tansicion por generos
     * @return matriz general de tansion
     */
    private static Object realizarDivideYVencerasACanciones(String[] lista, int nivel, double m[][]) {

        // matriz que contendrá el resultado final
        double[][] matrizGeneralDeTransicion = null;

        if (nivel == 1) {
            matrizGeneralDeTransicion = new double[5][5];
        }

        for (int i = 0; i < lista.length; i++) {
            if (nivel == 1) {
                // Envia cada uno de los generos
                // recibe las matrices por genero y
                // genera la matriz general de transicion
                matrizGeneralDeTransicion = generarMatrizDeTansicion(
                        realizarDivideYVencerasACanciones(lista, 2, new double[5][5]), matrizGeneralDeTransicion);
            } else if (nivel == 2) {
                // recibe lista de canciones por genero
                Object conteo = realizarDivideYVencerasACanciones(lista, 3, null);
                // generar matriz de transición por genero usando el conteo
                if (i < lista.length - 1) {
                    generarMatrizConConteo(conteo, m);
                } else {
                    return generarMatrizConConteo(conteo, m);
                }

            } else if (nivel == 3) {
                // recibe una canción y se realiza el conteo de las frecuencias
                return contarFrecuencia(lista[0]);
            }
        }
        return matrizGeneralDeTransicion;
    }

    /**
     * Realiza una simulacion de proceso de sumar todas las matrices de
     * transicion por genero
     *
     * @param realizarDivideYVencerasACanciones representa una matriz de
     * transión por genero
     * @param matrizGeneral matriz general que va acomulando los valores
     * @return devuelve la matriz general de transicion despues de sumarle un
     * nuevo genero
     */
    private static double[][] generarMatrizDeTansicion(Object realizarDivideYVencerasACanciones,
            double[][] matrizGeneral) {
        double matrizPorGenero[][] = (double[][]) realizarDivideYVencerasACanciones;

        printarMatriz(matrizPorGenero);

        for (int i = 0; i < matrizGeneral.length; i++) {
            for (int j = 0; j < matrizGeneral.length; j++) {
                matrizGeneral[i][j] += matrizPorGenero[i][j];
            }
        }
        return matrizGeneral;
    }

    /**
     * Realiza una simulacion de como se debe generan la matriz de transicion
     * por genero
     *
     * @param conteo conteo de las frecuencia de las letras de cada una de las
     * canciones
     * @param m matriz de trancion por cada genero
     * @return matriz de trancion despues de realizar una adicion del conteo de
     * frecuencia de una canciones
     */
    private static double[][] generarMatrizConConteo(Object conteo, double m[][]) {

        for (int i = 0; i < m.length; i++) {
            for (int j = 0; j < m.length; j++) {
                m[i][j] = Math.random();
            }
        }

        return m;
    }

    /**
     * Realiza una simulacion de realizar el conteo por canciones de cada una de
     * las letras por cancion
     *
     * @param cancion cancion a la que se le quiere realizar el conteo
     * @return mapa que contiene la frecuencia de cada letra
     */
    private static HashMap<String, Integer> contarFrecuencia(String cancion) {

        HashMap<String, Integer> conteo = new HashMap<String, Integer>();

        conteo.put("m", (int) (Math.random() * 10));
        conteo.put("i", (int) (Math.random() * 10));
        conteo.put("l", (int) (Math.random() * 10));
        conteo.put("a", (int) (Math.random() * 10));
        conteo.put("n", (int) (Math.random() * 10));

        return conteo;
    }

}
