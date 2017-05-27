/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalanalisis.Logica;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author alejo
 */
public class Cancion {

    private String nombre;
    private String letra;
    private ArrayList<String> letra_renglones;
    private String ruta_archivo;
    private String[][] frecuencia_letras;

    public Cancion(String ruta_archivo, String nombre) {
        this.nombre = nombre;
        this.ruta_archivo = ruta_archivo;
        frecuencia_letras = Utilidades.getMoldeTabla();
        letra_renglones = new ArrayList<>();
    }

    /**
     * metodo que permite leer la cancion y de una vez calcular la frecuencia de
     * letras
     */
    public void leer_cancion() {
        if (leer_archivo()) {
            calcular_frecuencia_letras();
        }
    }

    /**
     * metodo que permite cargar la letra de la canción desde el archivo
     *
     * @return
     */
    public boolean leer_archivo() {

        try {
            FileReader fileReader = new FileReader(ruta_archivo);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String cadena = "";

            while ((cadena = bufferedReader.readLine()) != null) {
                letra += cadena + " ";
                letra_renglones.add(cadena);
            }
        } catch (IOException e) {
            System.err.println("Error al buscar el archivo, el sistema no encuentra la ruta de acceso especificado");
            return false;
        }

        return true;
    }

    /**
     * metodo que permite calcular la frecuencia de letras de la canción
     */
    public void calcular_frecuencia_letras() {

        char[] vocabulario = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ',', '.', ' '};

        // iteramos la lista de letras por renglones
        for (int i = 0; i < letra_renglones.size(); i++) {

            // iteramos el vocabulario
            for (int j = 0; j < vocabulario.length; j++) {

                // iteramos sobre la cadena que se haye en la posicion i
                for (int k = 0; k < letra_renglones.get(i).length(); k++) {

                    // 
                    if (vocabulario[j] == letra_renglones.get(i).charAt(k)) {

                        if ((k - 1) >= 0) {

                            char x = letra_renglones.get(i).charAt(k - 1);
                            int index = new String(vocabulario).indexOf(x);
                            if (index != -1) {
                                
                                int suma = 0;

                                try {
                                    suma = Integer.parseInt(frecuencia_letras[j + 1][index + 1]) + 1;
                                } catch (NumberFormatException exception) {
                                    System.out.println("Hubo un error: " + frecuencia_letras[j + 1][index + 1] + " valor j " + j + " valor index " + index);

                                }

                                frecuencia_letras[j + 1][index + 1] = "" + suma;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * metodo que permite obtener la frecuencia de letras
     *
     * @return
     */
    public String[][] getFrecuencia_letras() {
        return frecuencia_letras;
    }

}
