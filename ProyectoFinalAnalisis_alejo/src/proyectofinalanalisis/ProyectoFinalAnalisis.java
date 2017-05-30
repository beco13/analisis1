/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectofinalanalisis;

import interfaz.InterfazPrincipal;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author alejo
 */
public class ProyectoFinalAnalisis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        try {
            UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.out.println("No cargo");
        }
        
        InterfazPrincipal interfazPrincipal = new InterfazPrincipal();
        interfazPrincipal.setVisible(true);
    }
    
}
