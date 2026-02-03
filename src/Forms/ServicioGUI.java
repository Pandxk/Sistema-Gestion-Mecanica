/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Forms;

import Entidades.Servicio;
import Entidades.Vehiculo;
import Controladores.ServicioJpaController;
import Controladores.VehiculoJpaController;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ServicioGUI extends javax.swing.JPanel {

    private EntityManagerFactory emf;
    private VehiculoJpaController vehControl;
    private ServicioJpaController serControl;
    private Servicio ser;
    private DefaultTableModel modelo;
    javax.swing.DefaultListModel<String> modeloLista = new javax.swing.DefaultListModel<>();
    java.util.ArrayList<RepuestoUso> listaRepuestos = new java.util.ArrayList<>();

    public class RepuestoUso {

        public int idProducto;
        public String nombre;
        public int cantidad;
        public double precioUnitario;
        public double subtotal;

        public RepuestoUso(int id, String nom, int cant, double precio) {
            this.idProducto = id;
            this.nombre = nom;
            this.cantidad = cant;
            this.precioUnitario = precio;
            this.subtotal = cant * precio;
            txtId.setVisible(false);
        }
    }

    public ServicioGUI() {
        initComponents();
        jList1.setModel(modeloLista);
        try {
            emf = Persistence.createEntityManagerFactory("MiProyectoMecanicaPU");
            vehControl = new VehiculoJpaController(emf);
            serControl = new ServicioJpaController(emf);
            ser = new Servicio();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error conectando a BD: " + e.getMessage());
        }
        txt_TotalRepuestos.setEditable(false);
        txtTotal.setEditable(false);
        cargarComboVehiculo();
    }

    private void calcularTotales() {
        double sumaRepuestos = 0.0;
        double sumaManoObra = 0.0;
        for (RepuestoUso r : listaRepuestos) {
            sumaRepuestos += r.subtotal;
        }
        try {
            String texto = txt_ManoDeObra.getText().replace(",", ".");
            if (!texto.trim().isEmpty()) {
                sumaManoObra = Double.parseDouble(texto);
            }
        } catch (NumberFormatException e) {
            sumaManoObra = 0.0;
        }
        double subtotal = sumaRepuestos + sumaManoObra;
        double iva = subtotal * 0.15; // 15% actual en Ecuador
        double totalPagar = subtotal + iva;
        txt_TotalRepuestos.setText(String.format("%.2f", sumaRepuestos));
        txtTotal.setText(String.format("%.2f", totalPagar));
        System.out.println("Subtotal: " + subtotal + " | IVA: " + iva + " | Total: " + totalPagar);
    }

    public void recibirProducto(int id, String nombre, int cantidad, double precio) {
        RepuestoUso nuevo = new RepuestoUso(id, nombre, cantidad, precio);
        listaRepuestos.add(nuevo);
        String texto = cantidad + " x " + nombre + "  -->  $" + String.format("%.2f", nuevo.subtotal);
        modeloLista.addElement(texto);
        calcularTotales();
    }

    public void cargarComboVehiculo() {
        cboVehiculo.removeAllItems();
        if (vehControl != null) {
            List<Vehiculo> listaVehiculo = vehControl.findVehiculoEntities();
            for (Vehiculo v : listaVehiculo) {
                cboVehiculo.addItem(v.getIdVehiculo() + ":" + v.getPlaca());
            }
        }
    }

    public void cargarTabla() {
        if (cboVehiculo.getSelectedItem() == null) {
            return;
        }
        String selectedIteme = cboVehiculo.getSelectedItem().toString();
        int idVehiculoSeleccionado = Integer.parseInt(selectedIteme.split(":")[0]);
        modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Mantenimiento");
        modelo.addColumn("Kilometraje");
        modelo.addColumn("Descripción");
        modelo.addColumn("Estado");
        modelo.addColumn("Costo");

        if (serControl != null) {
            List<Servicio> todosLosServicios = serControl.findServicioEntities();
            for (Servicio s : todosLosServicios) {
                if (s.getVehiculoidVehiculo() != null && s.getVehiculoidVehiculo().getIdVehiculo() == idVehiculoSeleccionado) {
                    modelo.addRow(new Object[]{
                        s.getIdServicio(),
                        s.getMantenimiento(),
                        s.getKilometraje() + " Mil",
                        s.getDescripcion(),
                        s.getEstado(),
                        "$" + s.getCosto()
                    });
                }
            }
        }
    }

    private void mostrarKilometrajeActual() {
        if (cboVehiculo.getSelectedItem() != null && vehControl != null) {
            try {
                String selectedItem = cboVehiculo.getSelectedItem().toString();
                int idVehiculo = Integer.parseInt(selectedItem.split(":")[0]);
                Vehiculo v = vehControl.findVehiculo(idVehiculo);
                if (v != null) {
                    txtKilometraje.setText(v.getKilometraje());
                }
            } catch (Exception e) {
                System.out.println("Error al cargar km: " + e.getMessage());
            }
        }
    }

    private void LimpiarServicio() {
        txtId.setText("");
        txtKilometraje.setText("");
        txt_ManoDeObra.setText("");
        txtTotal.setText("");
        txt_TotalRepuestos.setText("");
        if (txt_mantenimiento != null) {
            txt_mantenimiento.setText("");
        }
        modeloLista.clear();
        listaRepuestos.clear();
    }

    private boolean guardarDetallesSQL(int idServicioGenerado) {
        java.sql.Connection cn = null;
        try {
            cn = java.sql.DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bdmecanica?serverTimezone=UTC",
                    "root", "Jose maldonado18");
            cn.setAutoCommit(false); // Transacción manual
            String sqlInsert = "INSERT INTO detalle_servicio (id_servicio, id_producto, cantidad, precio_unitario, subtotal) VALUES (?, ?, ?, ?, ?)";
            String sqlUpdateStock = "UPDATE producto SET stock = stock - ? WHERE idProducto = ?";
            java.sql.PreparedStatement pstInsert = cn.prepareStatement(sqlInsert);
            java.sql.PreparedStatement pstUpdate = cn.prepareStatement(sqlUpdateStock);
            for (RepuestoUso rep : listaRepuestos) {
                pstInsert.setInt(1, idServicioGenerado);
                pstInsert.setInt(2, rep.idProducto);
                pstInsert.setInt(3, rep.cantidad);
                pstInsert.setDouble(4, rep.precioUnitario);
                pstInsert.setDouble(5, rep.subtotal);
                pstInsert.addBatch();
                pstUpdate.setInt(1, rep.cantidad);
                pstUpdate.setInt(2, rep.idProducto);
                pstUpdate.addBatch();
            }
            pstInsert.executeBatch();
            pstUpdate.executeBatch();
            cn.commit();
            return true;
        } catch (Exception e) {
            System.out.println("Error SQL: " + e.getMessage());
            try {
                if (cn != null) {
                    cn.rollback();
                }
            } catch (Exception ex) {
            }
            return false;
        } finally {
            try {
                if (cn != null) {
                    cn.close();
                }
            } catch (Exception ex) {
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtId = new javax.swing.JTextField();
        jpanelRegistroServicio = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cboVehiculo = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtKilometraje = new javax.swing.JTextField();
        txt_TotalRepuestos = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        txt_ManoDeObra = new javax.swing.JTextField();
        txt_mantenimiento = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        btnGenerarOrdenFinal = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        btnRegistroServicio = new javax.swing.JButton();
        btnEliminarServicio = new javax.swing.JButton();

        setForeground(new java.awt.Color(204, 204, 204));
        setPreferredSize(new java.awt.Dimension(1054, 832));
        setLayout(null);

        jpanelRegistroServicio.setBackground(new java.awt.Color(204, 204, 204));
        jpanelRegistroServicio.setForeground(new java.awt.Color(204, 204, 204));
        jpanelRegistroServicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jpanelRegistroServicioMouseClicked(evt);
            }
        });
        jpanelRegistroServicio.setLayout(null);

        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("ESCOJA UN VEHICULO");
        jpanelRegistroServicio.add(jLabel2);
        jLabel2.setBounds(10, 10, 130, 16);

        cboVehiculo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cboVehiculoItemStateChanged(evt);
            }
        });
        cboVehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboVehiculoActionPerformed(evt);
            }
        });
        jpanelRegistroServicio.add(cboVehiculo);
        cboVehiculo.setBounds(240, 10, 120, 26);

        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("KILOMETRAJE");
        jpanelRegistroServicio.add(jLabel4);
        jLabel4.setBounds(480, 10, 130, 16);

        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("MANTENIMIENTO");
        jpanelRegistroServicio.add(jLabel5);
        jLabel5.setBounds(480, 140, 130, 16);

        txtKilometraje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtKilometrajeActionPerformed(evt);
            }
        });
        txtKilometraje.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKilometrajeKeyTyped(evt);
            }
        });
        jpanelRegistroServicio.add(txtKilometraje);
        txtKilometraje.setBounds(660, 10, 120, 30);
        txtKilometraje.getAccessibleContext().setAccessibleName("KILOMETRAJE");

        txt_TotalRepuestos.setEditable(false);
        jpanelRegistroServicio.add(txt_TotalRepuestos);
        txt_TotalRepuestos.setBounds(240, 150, 120, 30);
        txt_TotalRepuestos.getAccessibleContext().setAccessibleName("TOTAL REPUESTOS");
        txt_TotalRepuestos.getAccessibleContext().setAccessibleDescription("");

        txtTotal.setEditable(false);
        jpanelRegistroServicio.add(txtTotal);
        txtTotal.setBounds(660, 80, 120, 30);
        txtTotal.getAccessibleContext().setAccessibleName("TOTAL MANTENIMIENTO");

        txt_ManoDeObra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_ManoDeObraActionPerformed(evt);
            }
        });
        jpanelRegistroServicio.add(txt_ManoDeObra);
        txt_ManoDeObra.setBounds(240, 80, 120, 30);
        txt_ManoDeObra.getAccessibleContext().setAccessibleName("MANO DE OBRA");
        txt_ManoDeObra.getAccessibleContext().setAccessibleDescription("INGRESE EL VALOR DEL MANO DE OBRA");

        jpanelRegistroServicio.add(txt_mantenimiento);
        txt_mantenimiento.setBounds(660, 140, 240, 90);
        txt_mantenimiento.getAccessibleContext().setAccessibleName("DESCRIPCION DEL MANTENIMIENTO REALIZADO");

        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("MANO DE OBRA");
        jpanelRegistroServicio.add(jLabel6);
        jLabel6.setBounds(10, 80, 90, 16);

        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("TOTAL REPUESTOS");
        jpanelRegistroServicio.add(jLabel7);
        jLabel7.setBounds(10, 150, 120, 16);

        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("TOTAL");
        jpanelRegistroServicio.add(jLabel8);
        jLabel8.setBounds(480, 80, 35, 16);

        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("REPUESTOS USADOS");
        jpanelRegistroServicio.add(jLabel9);
        jLabel9.setBounds(10, 230, 120, 16);

        jScrollPane2.setViewportView(jList1);
        jList1.getAccessibleContext().setAccessibleName("REPUESTOS USADOS EN EL VEHICULO");

        jpanelRegistroServicio.add(jScrollPane2);
        jScrollPane2.setBounds(240, 220, 120, 60);

        jButton1.setText("-");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jpanelRegistroServicio.add(jButton1);
        jButton1.setBounds(380, 250, 40, 27);
        jButton1.getAccessibleContext().setAccessibleName("BOTON ELIMINAR REPUESTOS");
        jButton1.getAccessibleContext().setAccessibleDescription("ELIMINAR EL REPUESTO");

        jButton2.setText("+");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jpanelRegistroServicio.add(jButton2);
        jButton2.setBounds(380, 220, 40, 27);
        jButton2.getAccessibleContext().setAccessibleName("BOTON AGREGAR REPUESTOS");
        jButton2.getAccessibleContext().setAccessibleDescription("AGREGAR REPUESTOS USADOS");

        btnGenerarOrdenFinal.setForeground(new java.awt.Color(0, 0, 0));
        btnGenerarOrdenFinal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/elecciones.png"))); // NOI18N
        btnGenerarOrdenFinal.setMnemonic('G');
        btnGenerarOrdenFinal.setText("GENERAR ORDEN");
        btnGenerarOrdenFinal.setToolTipText("\"Generar Orden (Alt + G)\"");
        btnGenerarOrdenFinal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnGenerarOrdenFinal.setIconTextGap(10);
        btnGenerarOrdenFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarOrdenFinalActionPerformed(evt);
            }
        });
        jpanelRegistroServicio.add(btnGenerarOrdenFinal);
        btnGenerarOrdenFinal.setBounds(10, 330, 160, 50);
        btnGenerarOrdenFinal.getAccessibleContext().setAccessibleDescription("REALIZAR LA ORDEN DEL MANTENIMIENTO");

        jButton4.setForeground(new java.awt.Color(0, 0, 0));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/seguro-de-coche.png"))); // NOI18N
        jButton4.setMnemonic('N');
        jButton4.setText("NUEVO");
        jButton4.setToolTipText("\"Nuevo Mantenimiento (Alt + N)\"");
        jButton4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jButton4.setIconTextGap(10);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jpanelRegistroServicio.add(jButton4);
        jButton4.setBounds(210, 330, 120, 50);
        jButton4.getAccessibleContext().setAccessibleName("NUEVO MANTENIMIENTO");
        jButton4.getAccessibleContext().setAccessibleDescription("BOTON PARA INGRESAR NUEVO MANTENIIMIENTO");

        add(jpanelRegistroServicio);
        jpanelRegistroServicio.setBounds(0, 0, 1050, 620);

        btnRegistroServicio.setText("Generar");
        btnRegistroServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistroServicioActionPerformed(evt);
            }
        });
        add(btnRegistroServicio);
        btnRegistroServicio.setBounds(600, 570, 100, 27);

        btnEliminarServicio.setText("Eliminar");
        btnEliminarServicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarServicioActionPerformed(evt);
            }
        });
        add(btnEliminarServicio);
        btnEliminarServicio.setBounds(730, 570, 77, 27);
    }// </editor-fold>//GEN-END:initComponents

    private void txtKilometrajeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKilometrajeKeyTyped
        if (txtKilometraje.getText().length() >= 6)
            evt.consume();
    }//GEN-LAST:event_txtKilometrajeKeyTyped

    private void btnRegistroServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistroServicioActionPerformed
        String kilometraje = txtKilometraje.getText();

        if (kilometraje.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe ingresar el kilometraje.");
            return;
        }
        try {
            String selectedItem = cboVehiculo.getSelectedItem().toString();
            int idVehiculo = Integer.parseInt(selectedItem.split(":")[0]);
            ser = new Servicio();

            Vehiculo v = vehControl.findVehiculo(idVehiculo);
            ser.setKilometraje(kilometraje);
            ser.setVehiculoidVehiculo(v);

            serControl.create(ser);

            JOptionPane.showMessageDialog(null, "Servicio Registrado Exitosamente");
            cargarTabla();
            LimpiarServicio();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El costo debe ser un número válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al guardar: " + e.getMessage());
        }
    }//GEN-LAST:event_btnRegistroServicioActionPerformed

    private void btnEliminarServicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarServicioActionPerformed
        if (!txtId.getText().isEmpty()) {
            int opt = JOptionPane.showConfirmDialog(null, "¿Eliminar servicio?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                try {
                    int id = Integer.parseInt(txtId.getText());
                    serControl.destroy(id);
                    cargarTabla();
                    LimpiarServicio();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
                }
            }
        }
    }//GEN-LAST:event_btnEliminarServicioActionPerformed

    private void cboVehiculoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cboVehiculoItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            cargarTabla();
            mostrarKilometrajeActual();
        }
    }//GEN-LAST:event_cboVehiculoItemStateChanged

    private void txt_ManoDeObraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_ManoDeObraActionPerformed
        calcularTotales();
    }//GEN-LAST:event_txt_ManoDeObraActionPerformed

    private void jpanelRegistroServicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jpanelRegistroServicioMouseClicked

    }//GEN-LAST:event_jpanelRegistroServicioMouseClicked

    private void txtKilometrajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtKilometrajeActionPerformed
    }//GEN-LAST:event_txtKilometrajeActionPerformed

    private void cboVehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboVehiculoActionPerformed
    }//GEN-LAST:event_cboVehiculoActionPerformed

    private void btnGenerarOrdenFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarOrdenFinalActionPerformed
        if (cboVehiculo.getSelectedItem() == null || cboVehiculo.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "️ Error: Debes seleccionar un Vehículo.", "Falta Dato", JOptionPane.WARNING_MESSAGE);
            cboVehiculo.requestFocus();
            return;
        }
        if (txtKilometraje.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "️ Error: El Kilometraje es obligatorio.", "Falta Dato", JOptionPane.WARNING_MESSAGE);
            txtKilometraje.requestFocus();
            return;
        }
        if (txt_mantenimiento.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, " Error: Describe el mantenimiento.", "Falta Dato", JOptionPane.WARNING_MESSAGE);
            txt_mantenimiento.requestFocus();
            return;
        }
        try {
            Integer.parseInt(txtKilometraje.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "️ Error: Kilometraje inválido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        calcularTotales();

        double manoObra = 0.0;
        try {
            manoObra = Double.parseDouble(txt_ManoDeObra.getText().replace(",", "."));
        } catch (Exception e) {
        }

        double totalFinal = 0.0;
        try {
            totalFinal = Double.parseDouble(txtTotal.getText().replace(",", "."));
        } catch (Exception e) {
        }

        if (listaRepuestos.isEmpty() && manoObra <= 0) {
            JOptionPane.showMessageDialog(this, "️ La orden no puede estar vacía ($0).", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Confirmar orden por $ " + String.format("%.2f", totalFinal) + "?",
                "Generar Orden", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            ser = new Servicio();
            String selectedItem = cboVehiculo.getSelectedItem().toString();
            int idVehiculo = Integer.parseInt(selectedItem.split(":")[0]);
            Vehiculo v = vehControl.findVehiculo(idVehiculo);
            ser.setVehiculoidVehiculo(v);
            ser.setMantenimiento(txt_mantenimiento.getText().trim());
            ser.setKilometraje(txtKilometraje.getText().trim());
            ser.setDescripcion("Orden Taller");
            ser.setEstado("Terminado");
            ser.setCosto(totalFinal);
            serControl.create(ser);
            if (!listaRepuestos.isEmpty()) {
                guardarDetallesSQL(ser.getIdServicio());
            }
            javax.swing.JFrame framePadre = (javax.swing.JFrame) javax.swing.SwingUtilities.getWindowAncestor(this);
            javax.swing.JPanel fondoOscuro = new javax.swing.JPanel() {
                @Override
                protected void paintComponent(java.awt.Graphics g) {
                    g.setColor(new java.awt.Color(0, 0, 0, 150));
                    g.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            fondoOscuro.setOpaque(false);
            fondoOscuro.setLayout(null);
            framePadre.setGlassPane(fondoOscuro);
            fondoOscuro.setVisible(true);
            Forms.ResumenOrdenGUI resumen = new Forms.ResumenOrdenGUI(framePadre, true);
            resumen.cargarDatosResumen(ser, listaRepuestos, manoObra);
            resumen.setLocationRelativeTo(null);
            resumen.setVisible(true);
            fondoOscuro.setVisible(false);
            framePadre.getGlassPane().setVisible(false);
            LimpiarServicio();
            cargarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            e.printStackTrace();
        }
     }//GEN-LAST:event_btnGenerarOrdenFinalActionPerformed
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        java.awt.Frame ventanaPadre = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
        javax.swing.JDialog ventanaFlotante = new javax.swing.JDialog(ventanaPadre, "Seleccionar Repuesto", true);
        Forms.ProductoGUI panelProductos = new Forms.ProductoGUI(this);
        ventanaFlotante.add(panelProductos);
        ventanaFlotante.setSize(850, 550);
        ventanaFlotante.setLocationRelativeTo(null);
        ventanaFlotante.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int indice = jList1.getSelectedIndex();
        if (indice >= 0) {
            modeloLista.remove(indice);
            listaRepuestos.remove(indice);
            calcularTotales();
        } else {
            JOptionPane.showMessageDialog(this, "Selecciona un repuesto para quitar.");
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        int opt = JOptionPane.showConfirmDialog(this, "¿Limpiar todo para una nueva orden?", "Nuevo", JOptionPane.YES_NO_OPTION);
        if (opt == JOptionPane.YES_OPTION) {
            LimpiarServicio();
            cboVehiculo.requestFocus();
        }
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminarServicio;
    private javax.swing.JButton btnGenerarOrdenFinal;
    private javax.swing.JButton btnRegistroServicio;
    private javax.swing.JComboBox<String> cboVehiculo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel jpanelRegistroServicio;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtKilometraje;
    private javax.swing.JTextField txtTotal;
    private javax.swing.JTextField txt_ManoDeObra;
    private javax.swing.JTextField txt_TotalRepuestos;
    private javax.swing.JTextField txt_mantenimiento;
    // End of variables declaration//GEN-END:variables
}
