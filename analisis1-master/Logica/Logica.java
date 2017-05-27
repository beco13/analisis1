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
public class Logica {

    private ArrayList<Genero> generos = new ArrayList<>();

    /**
     * metodo que permite cargar los generos de la carpeta de banco
     */
    public void cargar_generos() {

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

    public void cargar_letras() {

        for (int i = 0; i < generos.size(); i++) {
            // cargamos el genero (hilo)
            Thread tmpThreadGenero = new Thread(generos.get(i), generos.get(i).getNombre());

            // inicializamos el genero
            tmpThreadGenero.start();

        }

    }

}
