/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalanalisis.Logica;

/**
 *
 * @author alejo
 */
public class Utilidades {

    final static String BANCO_CANCIONES = "banco/canciones";
    final static String BANCO_CONFIGURACION = "banco/config";

    /**
     * metodo que permite crear una tabla vacia lista para usar
     *
     * @return
     */
    public static String[][] getMoldeTabla() {

        // almacenamos los simbolos que van a interactuar en la tabla
        String simbolos = "abcdefghijklmnñopqrstuvwxyz,. ";

        String[][] molde = new String[31][31];

        for (int i = 0; i < simbolos.length(); i++) {
            molde[0][i + 1] = Character.toString(simbolos.charAt(i));
            molde[i + 1][0] = Character.toString(simbolos.charAt(i));
        }
        
        for(int a = 1; a < molde.length; a++){
            for(int b = 1; b < molde.length; b++){
                molde[a][b] = "0";
            }
        }

        return molde;
    }
}
