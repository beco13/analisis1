/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.util.Properties;
import proyectofinalanalisis.Logica.Logica;


/**
 *
 * @author xJoni
 */
public class InterfazCargarPropiedades extends javax.swing.JFrame {

    private final Logica compositor;
    private Properties propiedades;

    /**
     * Creates new form InterfazCargarPropiedades
     *
     * @param compositor
     */
    public InterfazCargarPropiedades(Logica compositor) {
        initComponents();
        setSize(218, 540);
        setLocationRelativeTo(null);
        this.compositor = new Logica();
    }

    public void cargarPropiedades() {
        //propiedades = compositor.cargarConfiguraciones(cmbGenero.getSelectedItem().toString().toLowerCase());
        if (propiedades != null) {
            modificarCampos();
        }
    }
    
    private void guardarPropiedades(){
        propiedades.setProperty("bachata", txtBachata.getText());
        propiedades.setProperty("balada", txtBalada.getText());
        propiedades.setProperty("merengue", txtMerengue.getText());
        propiedades.setProperty("pop", txtPop.getText());
        propiedades.setProperty("ranchera", txtRanchera.getText());
        propiedades.setProperty("reggae", txtReggae.getText());
        propiedades.setProperty("reggaeton", txtReggaeton.getText());
        propiedades.setProperty("rock", txtRock.getText());
        propiedades.setProperty("salsa", txtSalsa.getText());
        propiedades.setProperty("vallenato", txtVallenato.getText());
//        compositor.guardarConfiguraciones(cmbGenero.getSelectedItem().toString().toLowerCase(), propiedades);
    }
    
    private void modificarCampos(){
        txtBachata.setText(propiedades.getProperty("bachata"));
        txtBalada.setText(propiedades.getProperty("balada"));
        txtMerengue.setText(propiedades.getProperty("merengue"));
        txtPop.setText(propiedades.getProperty("pop"));
        txtRanchera.setText(propiedades.getProperty("ranchera"));
        txtReggae.setText(propiedades.getProperty("reggae"));
        txtReggaeton.setText(propiedades.getProperty("reggaeton"));
        txtRock.setText(propiedades.getProperty("rock"));
        txtSalsa.setText(propiedades.getProperty("salsa"));
        txtVallenato.setText(propiedades.getProperty("vallenato"));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        cmbGenero = new javax.swing.JComboBox<>();
        lblTitulo = new javax.swing.JLabel();
        lblBachata = new javax.swing.JLabel();
        lblBalada = new javax.swing.JLabel();
        lblMerengue = new javax.swing.JLabel();
        lblPop = new javax.swing.JLabel();
        lblRanchera = new javax.swing.JLabel();
        lblReggae = new javax.swing.JLabel();
        lblReggaeton = new javax.swing.JLabel();
        lblRock = new javax.swing.JLabel();
        lblSalsa = new javax.swing.JLabel();
        lblVallenato = new javax.swing.JLabel();
        txtBachata = new javax.swing.JTextField();
        txtBalada = new javax.swing.JTextField();
        txtMerengue = new javax.swing.JTextField();
        txtPop = new javax.swing.JTextField();
        txtRanchera = new javax.swing.JTextField();
        txtReggae = new javax.swing.JTextField();
        txtReggaeton = new javax.swing.JTextField();
        txtRock = new javax.swing.JTextField();
        txtSalsa = new javax.swing.JTextField();
        txtVallenato = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);

        panel.setLayout(null);

        cmbGenero.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        cmbGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bachata", "Balada", "Merengue", "Pop", "Ranchera", "Reggae", "Reggaeton", "Rock", "Salsa", "Vallenato" }));
        cmbGenero.setFocusable(false);
        cmbGenero.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbGeneroItemStateChanged(evt);
            }
        });
        panel.add(cmbGenero);
        cmbGenero.setBounds(10, 35, 180, 30);

        lblTitulo.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblTitulo.setText("Selecciona el género a modificar");
        panel.add(lblTitulo);
        lblTitulo.setBounds(10, 10, 180, 20);

        lblBachata.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblBachata.setText("Bachata");
        panel.add(lblBachata);
        lblBachata.setBounds(10, 74, 44, 30);

        lblBalada.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblBalada.setText("Balada");
        panel.add(lblBalada);
        lblBalada.setBounds(10, 111, 38, 30);

        lblMerengue.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblMerengue.setText("Merengue");
        panel.add(lblMerengue);
        lblMerengue.setBounds(10, 148, 56, 30);

        lblPop.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblPop.setText("Pop");
        panel.add(lblPop);
        lblPop.setBounds(10, 185, 21, 30);

        lblRanchera.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblRanchera.setText("Ranchera");
        panel.add(lblRanchera);
        lblRanchera.setBounds(10, 222, 52, 30);

        lblReggae.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblReggae.setText("Reggae");
        panel.add(lblReggae);
        lblReggae.setBounds(10, 259, 42, 30);

        lblReggaeton.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblReggaeton.setText("Reggaeton");
        panel.add(lblReggaeton);
        lblReggaeton.setBounds(10, 296, 60, 30);

        lblRock.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblRock.setText("Rock");
        panel.add(lblRock);
        lblRock.setBounds(10, 334, 26, 30);

        lblSalsa.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblSalsa.setText("Salsa");
        panel.add(lblSalsa);
        lblSalsa.setBounds(10, 371, 29, 30);

        lblVallenato.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblVallenato.setText("Vallenato");
        panel.add(lblVallenato);
        lblVallenato.setBounds(10, 408, 52, 30);

        txtBachata.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtBachata);
        txtBachata.setBounds(90, 74, 100, 30);

        txtBalada.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtBalada);
        txtBalada.setBounds(90, 111, 100, 30);

        txtMerengue.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtMerengue);
        txtMerengue.setBounds(90, 148, 100, 30);

        txtPop.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtPop);
        txtPop.setBounds(90, 185, 100, 30);

        txtRanchera.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtRanchera);
        txtRanchera.setBounds(90, 370, 100, 30);

        txtReggae.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtReggae);
        txtReggae.setBounds(90, 222, 100, 30);

        txtReggaeton.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtReggaeton);
        txtReggaeton.setBounds(90, 259, 100, 30);

        txtRock.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtRock);
        txtRock.setBounds(90, 296, 100, 30);

        txtSalsa.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtSalsa);
        txtSalsa.setBounds(90, 333, 100, 30);

        txtVallenato.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtVallenato);
        txtVallenato.setBounds(90, 408, 100, 30);

        btnGuardar.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setFocusable(false);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        panel.add(btnGuardar);
        btnGuardar.setBounds(110, 452, 80, 30);

        btnSalir.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalir.setFocusable(false);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        panel.add(btnSalir);
        btnSalir.setBounds(10, 452, 80, 30);

        getContentPane().add(panel);
        panel.setBounds(0, 0, 220, 500);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cmbGeneroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbGeneroItemStateChanged
        if (evt.getStateChange() == 1) {
            cargarPropiedades();
        }
    }//GEN-LAST:event_cmbGeneroItemStateChanged

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        guardarPropiedades();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox<String> cmbGenero;
    private javax.swing.JLabel lblBachata;
    private javax.swing.JLabel lblBalada;
    private javax.swing.JLabel lblMerengue;
    private javax.swing.JLabel lblPop;
    private javax.swing.JLabel lblRanchera;
    private javax.swing.JLabel lblReggae;
    private javax.swing.JLabel lblReggaeton;
    private javax.swing.JLabel lblRock;
    private javax.swing.JLabel lblSalsa;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblVallenato;
    private javax.swing.JPanel panel;
    private javax.swing.JTextField txtBachata;
    private javax.swing.JTextField txtBalada;
    private javax.swing.JTextField txtMerengue;
    private javax.swing.JTextField txtPop;
    private javax.swing.JTextField txtRanchera;
    private javax.swing.JTextField txtReggae;
    private javax.swing.JTextField txtReggaeton;
    private javax.swing.JTextField txtRock;
    private javax.swing.JTextField txtSalsa;
    private javax.swing.JTextField txtVallenato;
    // End of variables declaration//GEN-END:variables
}