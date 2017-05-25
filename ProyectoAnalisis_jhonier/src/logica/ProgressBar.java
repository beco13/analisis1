/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author xJoni
 */
public class ProgressBar extends SwingWorker<Integer, String> {

    private final JLabel lblGenerando;
    private JProgressBar progressBar;
    private boolean ejecutando;
    private int progreso;

    public ProgressBar(JLabel lblGenerando, JProgressBar progressBar) {
        this.lblGenerando = lblGenerando;
        this.progressBar = progressBar;
        ejecutando = true;
    }
    
    public void modificarProgressBar(int valor){
        progressBar.setValue(valor);
    }

    @Override
    protected Integer doInBackground() {
        try {
            lblGenerando.setText("Generando canción...");
            
            while (ejecutando) {
                modificarProgressBar(progreso);
            }
        } catch (Exception e) {
            
        }       
        return 0;
    }

    public void setEjecutando(boolean ejecutando) {
        this.ejecutando = ejecutando;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }
    
    
}
