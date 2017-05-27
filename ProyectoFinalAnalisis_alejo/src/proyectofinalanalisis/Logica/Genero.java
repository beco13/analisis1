/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalanalisis.Logica;

import java.io.File;
import java.util.ArrayList;

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

                    break;
                }
            }
        }
    }

    /**
     * metodo que permite calcular la frecuencia de letras de todas las
     * canciones
     */
    public void calcular_frecuencia_letras() {

        // recorremos todas las listas de canciones
        for (int i = 0; i < lista_canciones.size(); i++) {

            // tamaño de la matriz de frecuencia acumulada de letras de la canción
            int tamano_matriz_acumlada = frecuencia_acumulada.length;

            // iteramos las filas de la matriz acumalada
            for (int a = 1; a < tamano_matriz_acumlada; a++) {

                // iteramos las columnas de la matriz acumulada
                for (int b = 1; b < tamano_matriz_acumlada; b++) {

                    // tamaño de la matriz de frecuencia de letras de la canción
                    int tamano_matriz = lista_canciones.get(i).getFrecuencia_letras().length;

                    // recorrer la matriz de frecuencia de letras de cada una de las canciones: iteramos cada fila
                    for (int j = 1; j < tamano_matriz; j++) {

                        // iteramos las columnas de la matriz de frecuencia de letras de cada una de las canciones 
                        for (int k = 1; k < tamano_matriz; k++) {

                            // obtenemos la suma almacenada 
                            try {
                                // casteamos a enteros los valores de las matrices
                                int suma_almacenada = Integer.parseInt(frecuencia_acumulada[a][b]);
                                int frecuencia_letra = Integer.parseInt(lista_canciones.get(i).getFrecuencia_letras()[j][k]);

                                // almacenamos la suma de matrices
                                frecuencia_acumulada[a][b] = "" + (suma_almacenada + frecuencia_letra);

                            } catch (NumberFormatException exception) {
                                System.err.println("Ocurrió un error con el calculo de la matriz, existe una cadena, error: " + exception.getMessage());
                            }
                        }
                    }
                }
            }
        }

        System.out.println("MATRIZZZZZZZZ FRECUENCI ACUMULADA");
        System.out.println("");

        for (int i = 0; i < frecuencia_acumulada.length; i++) {
            for (int j = 0; j < frecuencia_acumulada.length; j++) {
                System.out.print(frecuencia_acumulada[i][j]);
            }
            System.out.println("");
        }

    }
    
    
    
    

    @Override
    public void run() {

        cargar_canciones();

        calcular_frecuencia_letras();

    }

}
