/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import javax.swing.JLabel;
import javax.swing.JProgressBar;

/**
 *
 * @author xJoni
 */
public class BarraProgreso implements Runnable {

    private JLabel lblGenerando;
    private JProgressBar progressBar;
    private boolean ejecutando;
    private Thread hiloProgreso;
    private int progreso;

    public BarraProgreso(JLabel lblGenerando, JProgressBar progressBar) {
        this.lblGenerando = lblGenerando;
        this.progressBar = progressBar;
    }

    public synchronized void start() {
        if (ejecutando) {
            return;
        }
        ejecutando = true;
        hiloProgreso = new Thread(this);
        hiloProgreso.start();
    }

    @Override
    public void run() {
        progressBar.setIndeterminate(true);
        while (ejecutando) {
            progressBar.setValue(progreso);
        }
        progressBar.setValue(100);
        progressBar.setIndeterminate(false);
    }

    public void setEjecutando(boolean ejecutando) {
        this.ejecutando = ejecutando;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }
    
    
}
