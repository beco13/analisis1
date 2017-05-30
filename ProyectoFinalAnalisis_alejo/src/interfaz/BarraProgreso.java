/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author alejo
 */
public class BarraProgreso extends SwingWorker<Integer, String> {

    private JLabel lblGenerando;
    private JProgressBar progressBar;
    private String texto_en_pantalla;
    private int progreso;

    public BarraProgreso(JLabel lblGenerando, JProgressBar progressBar) {
        this.lblGenerando = lblGenerando;
        this.progressBar = progressBar;
        this.texto_en_pantalla = "Inicio de creación de nueva canción";
    }

    public void modificarProgressBar(int valor, String texto) {
        progressBar.setValue(valor);
        lblGenerando.setText(texto);
    }    

    @Override
    protected Integer doInBackground() {

        return 0;
    }

    public void setProgreso(int progreso) {
        this.progreso = progreso;
    }

    public void setTexto_en_pantalla(String texto_en_pantalla) {
        this.texto_en_pantalla = texto_en_pantalla;
    }
}
