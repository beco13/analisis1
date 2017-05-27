/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoanalisis;

import interfaz.InterfazPrincipal;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author xJoni
 */
public class ProyectoAnalisis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.jgoodies.looks.windows.WindowsLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(ProyectoAnalisis.class.getName()).log(Level.SEVERE, null, ex);
        }

        InterfazPrincipal ventanaPrincipal = new InterfazPrincipal();
        ventanaPrincipal.setVisible(true);
    }

}
