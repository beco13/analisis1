/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author alejo
 */
public class Genero implements Runnable {

    private String nombre;
    private String ruta_carpeta;
    private ArrayList<Cancion> lista_canciones = new ArrayList<>();
    private String[][] frecuencia_acumulada;
    private String[][] matriz_transicion;
    private ConfiguracionProbabilidades configuracion;

    public Genero(String ruta_carpeta, String nombre) {
        this.ruta_carpeta = ruta_carpeta;
        this.nombre = nombre;
        frecuencia_acumulada = Utilidades.getMoldeTabla();
        matriz_transicion = Utilidades.getMoldeTabla();
    }

    public String getNombre() {
        return nombre;
    }

    /**
     * metodo que permite cargar el archivo de configuracion del genero
     *
     * @return
     */
    public boolean cargar_configuracion() {

        String ruta_archivo_config = Utilidades.BANCO_CONFIGURACION + "/" + nombre + ".properties";

        // cargamos la ruta del archivo de configuracion
        File archivo_configuracion = new File(ruta_archivo_config);

        // verificamos que exista el archivo
        if (archivo_configuracion.exists()) {
            configuracion = new ConfiguracionProbabilidades(ruta_archivo_config);
            return true;
        }

        return false;
    }

    /**
     * metodo que permite cargar las canciones del genero
     */
    public void cargar_canciones() {

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
    public void calcular_frecuencia_letras() {

        int matrizPrueba[][] = new int[30][30];

        // recorremos todas las listas de canciones
        for (int i = 0; i < lista_canciones.size(); i++) {

            // tamaño de la matriz de frecuencia de letras de la canción
            int tamano_matriz = lista_canciones.get(i).getFrecuencia_letras().length;

            // recorrer la matriz de frecuencia de letras de cada una de las canciones: iteramos cada fila
            for (int j = 1; j < tamano_matriz; j++) {

                // iteramos las columnas de la matriz de frecuencia de letras de cada una de las canciones 
                for (int k = 1; k < tamano_matriz; k++) {

                    try {

                        // casteamos a entero 
                        //int suma_almacenada = Integer.parseInt(frecuencia_acumulada[j][k]);
                        int frecuencia_letra = Integer.parseInt(lista_canciones.get(i).getFrecuencia_letras()[j][k]);

                        // almacenamos la suma de matrices
                        matrizPrueba[j - 1][k - 1] += frecuencia_letra;

                    } catch (NumberFormatException exception) {
                        System.err.println("Ocurrió un error con el calculo de la matriz, existe una cadena, error: " + exception.getMessage());
                    }
                }
            }

        }

        for (int i = 0; i < matrizPrueba.length; i++) {
            for (int j = 0; j < matrizPrueba[0].length; j++) {
                frecuencia_acumulada[i + 1][j + 1] = String.valueOf(matrizPrueba[i][j]);
            }
        }

    }

    @Override
    public void run() {

        cargar_canciones();

        calcular_frecuencia_letras();

    }
}
