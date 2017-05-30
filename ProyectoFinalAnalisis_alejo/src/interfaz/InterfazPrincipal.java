/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaz;

import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import logica.Compositor;
import logica.CallbackCompositor;

/**
 *
 * @author xJoni
 */
public class InterfazPrincipal extends javax.swing.JFrame {

    private final Compositor compositor;

    /**
     * Constructor de la clase VentanaPrincipal
     */
    public InterfazPrincipal() {
        initComponents();
        setSize(330, 520);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/multimedia/logo.png").getImage());
        compositor = new Compositor();
    }

    private void configurarProbabilidades() {
        InterfazCargarPropiedades interfazCargarPropiedades = new InterfazCargarPropiedades(compositor);
        interfazCargarPropiedades.cargarPropiedades();
        interfazCargarPropiedades.setVisible(true);
    }

    /**
     *
     */
    private void generarCancion() {
        if (txtLetra.getText().length() > 1) {

            txtLetra.setForeground(Color.red);

            lblGenerando.setText("El campo letra debe contener un solo caracter.");

            return;
        }

        int tamano;

        try {

            tamano = Integer.parseInt(txtTamano.getText());

        } catch (NumberFormatException e) {

            if (!txtTamano.getText().equals("Tamaño")) {
                txtTamano.setForeground(Color.red);

                lblGenerando.setText("El tamaño debe ser un número.");

                return;
            }

            tamano = 0;
        }

        compositor.inicializar_barra_progreso(lblGenerando, progressbar);

        char caracter = txtLetra.getText().charAt(0);        
        
        compositor.setCancion_creada(new CallbackCompositor() {
            
            @Override
            public void callback(boolean estado) {
                if (estado) {
                    lblGenerando.setText("Canción generada!");
                    InterfazReproducir interfazReproducir = new InterfazReproducir(compositor.getCaracteres(), compositor.getProbabilidades(), compositor.getTiempo(), cmbGenero.getSelectedItem().toString(), compositor.getProbabilidades_trabajas());
                    interfazReproducir.setVisible(true);
                } else {
                    lblGenerando.setForeground(Color.red);
                    lblGenerando.setText("Error al generar la nueva canción.");
                }
            }
        }
        );
        
        compositor.generar_cancion(caracter, cmbGenero.getSelectedItem().toString().toLowerCase(), tamano);        

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        btnConfigurarProbabilidades = new javax.swing.JButton();
        btnConfigurarProbabilidades1 = new javax.swing.JButton();
        txtTamano = new javax.swing.JTextField();
        txtLetra = new javax.swing.JTextField();
        cmbGenero = new javax.swing.JComboBox<>();
        progressbar = new javax.swing.JProgressBar();
        lblGenerando = new javax.swing.JLabel();
        lblFondo = new javax.swing.JLabel();
        menuPrincipal = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        itemMenuSalir = new javax.swing.JMenuItem();
        menuAcerca = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        panelPrincipal.setLayout(null);

        btnConfigurarProbabilidades.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        btnConfigurarProbabilidades.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/crear-nuevo-boton-del-lapiz.png"))); // NOI18N
        btnConfigurarProbabilidades.setText("Configurar Probabilidades");
        btnConfigurarProbabilidades.setBorderPainted(false);
        btnConfigurarProbabilidades.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfigurarProbabilidades.setFocusable(false);
        btnConfigurarProbabilidades.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigurarProbabilidadesActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnConfigurarProbabilidades);
        btnConfigurarProbabilidades.setBounds(10, 290, 306, 40);

        btnConfigurarProbabilidades1.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        btnConfigurarProbabilidades1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/cabeza-pensante.png"))); // NOI18N
        btnConfigurarProbabilidades1.setText("Generar Canción");
        btnConfigurarProbabilidades1.setBorderPainted(false);
        btnConfigurarProbabilidades1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConfigurarProbabilidades1.setFocusPainted(false);
        btnConfigurarProbabilidades1.setFocusable(false);
        btnConfigurarProbabilidades1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfigurarProbabilidades1ActionPerformed(evt);
            }
        });
        panelPrincipal.add(btnConfigurarProbabilidades1);
        btnConfigurarProbabilidades1.setBounds(10, 410, 306, 40);

        txtTamano.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        txtTamano.setText("Tamaño");
        txtTamano.setToolTipText("");
        txtTamano.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTamanoFocusLost(evt);
            }
        });
        txtTamano.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtTamanoMouseClicked(evt);
            }
        });
        panelPrincipal.add(txtTamano);
        txtTamano.setBounds(120, 350, 90, 40);

        txtLetra.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        txtLetra.setText("Letra");
        txtLetra.setToolTipText("");
        txtLetra.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLetraFocusLost(evt);
            }
        });
        txtLetra.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtLetraMouseClicked(evt);
            }
        });
        panelPrincipal.add(txtLetra);
        txtLetra.setBounds(10, 350, 90, 40);

        cmbGenero.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        cmbGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bachata", "Balada", "Merengue", "Pop", "Ranchera", "Reggae", "Reggaeton", "Rock", "Salsa", "Vallenato" }));
        cmbGenero.setFocusable(false);
        panelPrincipal.add(cmbGenero);
        cmbGenero.setBounds(220, 350, 90, 40);
        panelPrincipal.add(progressbar);
        progressbar.setBounds(10, 260, 306, 14);

        lblGenerando.setFont(new java.awt.Font("Tw Cen MT Condensed", 0, 18)); // NOI18N
        lblGenerando.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        panelPrincipal.add(lblGenerando);
        lblGenerando.setBounds(0, 220, 330, 29);

        lblFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/multimedia/fondo.png"))); // NOI18N
        panelPrincipal.add(lblFondo);
        lblFondo.setBounds(0, 0, 330, 490);

        menuArchivo.setText("Archivo");

        itemMenuSalir.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        itemMenuSalir.setText("Salir");
        itemMenuSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemMenuSalirActionPerformed(evt);
            }
        });
        menuArchivo.add(itemMenuSalir);

        menuPrincipal.add(menuArchivo);

        menuAcerca.setText("Acerca de");
        menuPrincipal.add(menuAcerca);

        setJMenuBar(menuPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemMenuSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemMenuSalirActionPerformed
        System.exit(0);
    }//GEN-LAST:event_itemMenuSalirActionPerformed

    private void txtLetraMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtLetraMouseClicked
        txtLetra.setForeground(Color.black);
        if (txtLetra.getText().equals("Letra")) {
            txtLetra.setText("");
        }
    }//GEN-LAST:event_txtLetraMouseClicked

    private void txtLetraFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLetraFocusLost
        if (txtLetra.getText().isEmpty()) {
            txtLetra.setText("Letra");
        }
    }//GEN-LAST:event_txtLetraFocusLost

    private void btnConfigurarProbabilidades1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigurarProbabilidades1ActionPerformed
        lblGenerando.setForeground(Color.black);
        lblGenerando.setText("Generando canción...");
        generarCancion();
    }//GEN-LAST:event_btnConfigurarProbabilidades1ActionPerformed

    private void btnConfigurarProbabilidadesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfigurarProbabilidadesActionPerformed
        configurarProbabilidades();
    }//GEN-LAST:event_btnConfigurarProbabilidadesActionPerformed

    private void txtTamanoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTamanoFocusLost
        if (txtTamano.getText().isEmpty()) {
            txtTamano.setText("Tamaño");
        }
    }//GEN-LAST:event_txtTamanoFocusLost

    private void txtTamanoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtTamanoMouseClicked
        txtLetra.setForeground(Color.black);
        if (txtTamano.getText().equals("Tamaño")) {
            txtTamano.setText("");
        }
    }//GEN-LAST:event_txtTamanoMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfigurarProbabilidades;
    private javax.swing.JButton btnConfigurarProbabilidades1;
    private javax.swing.JComboBox<String> cmbGenero;
    private javax.swing.JMenuItem itemMenuSalir;
    private javax.swing.JLabel lblFondo;
    private javax.swing.JLabel lblGenerando;
    private javax.swing.JMenu menuAcerca;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenuBar menuPrincipal;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JProgressBar progressbar;
    private javax.swing.JTextField txtLetra;
    private javax.swing.JTextField txtTamano;
    // End of variables declaration//GEN-END:variables
}
