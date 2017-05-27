/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalanalisis.Logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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
        letra_renglones = new ArrayList<>();
    }

    /**
     * metodo que permite leer la cancion y de una vez calcular la frecuencia de
     * letras
     */
    public void leer_cancion() {
        if (leer_archivo()) {
            frecuencia_letras = Utilidades.getMoldeTabla(calcular_frecuencia_letras(letra_renglones));
            Utilidades.imprimir(frecuencia_letras);
        }
    }

    /**
     * metodo que permite cargar la letra de la canción desde el archivo
     *
     * @return
     */
    public boolean leer_archivo() {

        System.out.println("archivo: " + ruta_archivo);

        try {
            FileReader fileReader = new FileReader(ruta_archivo);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String cadena = "";

            while ((cadena = bufferedReader.readLine()) != null) {
                letra_renglones.add(cadena);
                letra += cadena + " ";
            }
        } catch (IOException e) {
            System.err.println("Error al buscar el archivo, el sistema no encuentra la ruta de acceso especificado");
            return false;
        }

        return true;
    }

    /**
     * metodo que permite calcular la frecuencia de letras de la canción
     *
     * @param cancion
     * @return
     */
    public String[][] calcular_frecuencia_letras(ArrayList<String> cancion) {

        char[] vocabulario = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', ',', '.', ' '};

        String ma[][] = new String[31][31];
        int[][] matriz = new int[30][30];

        for (int i = 0; i < cancion.size(); i++) {
            for (int j = 0; j < vocabulario.length; j++) {
                for (int k = 0; k < cancion.get(i).length(); k++) {
                    if (vocabulario[j] == cancion.get(i).charAt(k)) {
                        if ((k - 1) >= 0) {
                            char x = cancion.get(i).charAt(k - 1);
                            int index = new String(vocabulario).indexOf(x);

//                            System.out.println("Cadena : " + x + " Vocabulario index : " + new String(vocabulario).indexOf(x) + " Vocabulario letra"
//                                    + ": " + vocabulario[new String(vocabulario).indexOf(x)]);
//                            System.out.println("j: " + j + " index: " + index);


                            if(index > 0){
                                matriz[j][index] += 1;
                            }
                            
                        }
                    }
                }
            }
        }

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[0].length; j++) {
                ma[i + 1][j + 1] = String.valueOf(matriz[i][j]);
            }
        }

        matriz = null;        
        return ma;
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
