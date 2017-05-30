/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 *
 * @author alejo
 */
public class Utilidades {


    /**
     * metodo que permite crear una tabla vacia lista para usar
     *
     * @return
     */
    public String[][] getMoldeTabla() {

        // almacenamos los simbolos que van a interactuar en la tabla
        String simbolos = "abcdefghijklmnñopqrstuvwxyz,. ";

        String[][] molde = new String[31][31];

        for (int i = 0; i < simbolos.length(); i++) {
            molde[0][i + 1] = Character.toString(simbolos.charAt(i));
            molde[i + 1][0] = Character.toString(simbolos.charAt(i));
        }

        for(int i = 1; i < molde.length; i++){
            for(int j = 1; j < molde.length; j++){
                molde[i][j] = "0";
            }
        }
        
        return molde;
    }

}
