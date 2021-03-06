/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import logica.Compositor;

/**
 *
 * @author xJoni
 */
public class InterfazCargarPropiedades extends javax.swing.JFrame {

    private final Compositor compositor;
    private Properties propiedades;

    /**
     * Creates new form InterfazCargarPropiedades
     *
     * @param compositor
     */
    public InterfazCargarPropiedades(Compositor compositor) {
        initComponents();
        setSize(205, 465);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/multimedia/logo.png").getImage());
        this.compositor = compositor;
    }

    public void cargarPropiedades() {
        propiedades = compositor.cargar_configuracion(cmbGenero.getSelectedItem().toString().toLowerCase());
        if (propiedades != null) {
            modificarCampos();
        }
    }

    private void guardarPropiedades() {        

        propiedades = new Properties();
        propiedades.setProperty("bachata", txtBachata.getText());
        propiedades.setProperty("balada", txtBalada.getText());
        propiedades.setProperty("merengue", txtMerengue.getText());
        propiedades.setProperty("pop", txtPop.getText());
        propiedades.setProperty("ranchera", txtRanchera.getText());
        propiedades.setProperty("reggaeton", txtReggaeton.getText());
        propiedades.setProperty("rock", txtRock.getText());
        propiedades.setProperty("salsa", txtSalsa.getText());
        propiedades.setProperty("vallenato", txtVallenato.getText());
        
        if(compositor.guardar_configuraciones(cmbGenero.getSelectedItem().toString().toLowerCase(), propiedades)){
            JOptionPane.showMessageDialog(this, "Valores guardados.");
        }else{
            JOptionPane.showMessageDialog(this, "Existe un error, no se guardarán los cambios. \nPor favor verifique: \n1. La suma de los valores sea igual a uno \n2. Solo sean valores númericos \n3. El valor del género seleccionado debe ser mayor a los demás géneros.");
        }
    }

    private void modificarCampos() {
        txtBachata.setText(propiedades.getProperty("bachata"));
        txtBalada.setText(propiedades.getProperty("balada"));
        txtMerengue.setText(propiedades.getProperty("merengue"));
        txtPop.setText(propiedades.getProperty("pop"));
        txtSalsa.setText(propiedades.getProperty("salsa"));
        txtRanchera.setText(propiedades.getProperty("ranchera"));
        txtReggaeton.setText(propiedades.getProperty("reggaeton"));
        txtRock.setText(propiedades.getProperty("rock"));
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
        lblReggaeton = new javax.swing.JLabel();
        lblRock = new javax.swing.JLabel();
        lblSalsa = new javax.swing.JLabel();
        lblVallenato = new javax.swing.JLabel();
        txtBachata = new javax.swing.JTextField();
        txtBalada = new javax.swing.JTextField();
        txtMerengue = new javax.swing.JTextField();
        txtPop = new javax.swing.JTextField();
        txtSalsa = new javax.swing.JTextField();
        txtRanchera = new javax.swing.JTextField();
        txtReggaeton = new javax.swing.JTextField();
        txtRock = new javax.swing.JTextField();
        txtVallenato = new javax.swing.JTextField();
        btnGuardar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        lblFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(null);

        panel.setLayout(null);

        cmbGenero.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        cmbGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bachata", "Balada", "Merengue", "Pop", "Ranchera", "Reggaeton", "Rock", "Salsa", "Vallenato" }));
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
        lblBachata.setBounds(10, 75, 44, 30);

        lblBalada.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblBalada.setText("Balada");
        panel.add(lblBalada);
        lblBalada.setBounds(10, 110, 38, 30);

        lblMerengue.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblMerengue.setText("Merengue");
        panel.add(lblMerengue);
        lblMerengue.setBounds(10, 145, 56, 30);

        lblPop.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblPop.setText("Pop");
        panel.add(lblPop);
        lblPop.setBounds(10, 180, 21, 30);

        lblRanchera.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblRanchera.setText("Ranchera");
        panel.add(lblRanchera);
        lblRanchera.setBounds(10, 215, 52, 30);

        lblReggaeton.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblReggaeton.setText("Reggaeton");
        panel.add(lblReggaeton);
        lblReggaeton.setBounds(10, 250, 60, 30);

        lblRock.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblRock.setText("Rock");
        panel.add(lblRock);
        lblRock.setBounds(10, 285, 26, 30);

        lblSalsa.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblSalsa.setText("Salsa");
        panel.add(lblSalsa);
        lblSalsa.setBounds(10, 320, 29, 30);

        lblVallenato.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblVallenato.setText("Vallenato");
        panel.add(lblVallenato);
        lblVallenato.setBounds(10, 355, 52, 30);

        txtBachata.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtBachata);
        txtBachata.setBounds(90, 75, 100, 30);

        txtBalada.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtBalada);
        txtBalada.setBounds(90, 110, 100, 30);

        txtMerengue.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtMerengue);
        txtMerengue.setBounds(90, 145, 100, 30);

        txtPop.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtPop);
        txtPop.setBounds(90, 180, 100, 30);

        txtSalsa.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtSalsa);
        txtSalsa.setBounds(90, 320, 100, 30);

        txtRanchera.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtRanchera);
        txtRanchera.setBounds(90, 215, 100, 30);

        txtReggaeton.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtReggaeton);
        txtReggaeton.setBounds(90, 250, 100, 30);

        txtRock.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtRock);
        txtRock.setBounds(90, 285, 100, 30);

        txtVallenato.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        panel.add(txtVallenato);
        txtVallenato.setBounds(90, 355, 100, 30);

        btnGuardar.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGuardar.setFocusable(false);
        btnGuardar.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        panel.add(btnGuardar);
        btnGuardar.setBounds(90, 390, 100, 30);

        btnSalir.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/cerrar-pequeño.png"))); // NOI18N
        btnSalir.setText("Salir");
        btnSalir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalir.setFocusable(false);
        btnSalir.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        panel.add(btnSalir);
        btnSalir.setBounds(10, 390, 75, 30);

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/fondo-propiedades.png"))); // NOI18N
        panel.add(lblFondo);
        lblFondo.setBounds(0, 0, 200, 435);

        getContentPane().add(panel);
        panel.setBounds(0, 0, 200, 440);

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
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblMerengue;
    private javax.swing.JLabel lblPop;
    private javax.swing.JLabel lblRanchera;
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
    private javax.swing.JTextField txtReggaeton;
    private javax.swing.JTextField txtRock;
    private javax.swing.JTextField txtSalsa;
    private javax.swing.JTextField txtVallenato;
    // End of variables declaration//GEN-END:variables
}
