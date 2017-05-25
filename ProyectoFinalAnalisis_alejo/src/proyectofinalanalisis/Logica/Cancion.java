/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalanalisis.Logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author alejo
 */
public class Cancion {

//    private String nombre;
    private String letra;
    private ArrayList<String> letra_renglones;
    private String ruta_archivo;
    private String[][] frecuencia_letras;
    

    public Cancion(String ruta_archivo) {
        this.ruta_archivo = ruta_archivo;
        frecuencia_letras = Utilidades.getMoldeTabla();

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

        File f = new File(ruta_archivo);
        String cadena;
        Scanner entrada = null;
        try {
            entrada = new Scanner(f);
            while (entrada.hasNext()) {
                // leemos la linea
                cadena = entrada.nextLine();
                letra += cadena + " ";
                letra_renglones.add(letra);
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            if (entrada != null) {
                entrada.close();
            }
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

                        if ((k - 1) >= 1) {

                            char x = letra_renglones.get(i).charAt(k - 1);
                            int index = new String(vocabulario).indexOf(x);

                            frecuencia_letras[j + 1][index + 1] += 1;

                        }

                    }

                }
            }
        }

    }

    
    /**
     * metodo que permite obtener la frecuencia de letras
     * @return 
     */
    public String[][] getFrecuencia_letras() {
        return frecuencia_letras;
    }

}
