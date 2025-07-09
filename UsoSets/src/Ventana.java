import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.Set;

public class Ventana extends JFrame {
    private JPanel panel1;
    private JPanel pnlMain;
    private JTextField txtCodigo;
    private JLabel lblCodigo;
    private JTextField txtNombre;
    private JTextField txtRendimiento;
    private JTextField txtPosicion;
    private JTable tblJugadores;
    private JButton btnAgregar;
    private JButton btnModificar;
    private JButton btnEliminar;
    private JButton btnLimpiar;
    private JLabel lblNombre;
    private JLabel lblRendimiento;
    private JLabel lblPosicion;
    private JScrollPane scrJugadores;

    private final Equipo equipo = new Equipo();

    public Ventana() {
        super("Gestión de Jugadores con Set");
        initComponents();
        initListeners();
        actualizarTabla();
    }

    private void initComponents() {
        // Panel raíz
        panel1 = new JPanel(new BorderLayout());

        // Panel principal interno
        pnlMain = new JPanel(new BorderLayout(10, 10));

        // Campos NORTH
        JPanel pnlCampos = new JPanel(new GridLayout(4, 2, 5, 5));
        lblCodigo = new JLabel("Código:");
        txtCodigo = new JTextField(); txtCodigo.setEditable(false);
        lblNombre = new JLabel("Nombre:");
        txtNombre = new JTextField();
        lblRendimiento = new JLabel("Rendimiento:");
        txtRendimiento = new JTextField();
        lblPosicion = new JLabel("Posición:");
        txtPosicion = new JTextField();
        pnlCampos.add(lblCodigo);      pnlCampos.add(txtCodigo);
        pnlCampos.add(lblNombre);      pnlCampos.add(txtNombre);
        pnlCampos.add(lblRendimiento); pnlCampos.add(txtRendimiento);
        pnlCampos.add(lblPosicion);    pnlCampos.add(txtPosicion);

        // Tabla CENTER
        tblJugadores = new JTable(new DefaultTableModel(
                new String[]{"Código","Nombre","Rendimiento","Posición"}, 0));
        scrJugadores = new JScrollPane(tblJugadores);

        // Botones SOUTH
        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnAgregar  = new JButton("Agregar");
        btnModificar= new JButton("Modificar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar  = new JButton("Limpiar");
        pnlBotones.add(btnAgregar);
        pnlBotones.add(btnModificar);
        pnlBotones.add(btnEliminar);
        pnlBotones.add(btnLimpiar);

        // Montaje de paneles
        pnlMain.add(pnlCampos, BorderLayout.NORTH);
        pnlMain.add(scrJugadores, BorderLayout.CENTER);
        pnlMain.add(pnlBotones, BorderLayout.SOUTH);
        panel1.add(pnlMain, BorderLayout.CENTER);

        setContentPane(panel1);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initListeners() {
        btnAgregar.addActionListener(e -> {
            try {
                String nom = txtNombre.getText().trim();
                float rend = Float.parseFloat(txtRendimiento.getText().trim());
                String pos = txtPosicion.getText().trim();
                equipo.agregar(nom, rend, pos);
                actualizarTabla();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Rendimiento debe ser un número.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException | DuplicateJugadorException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnModificar.addActionListener(e -> {
            try {
                int cod = Integer.parseInt(txtCodigo.getText().trim());
                String nom = txtNombre.getText().trim();
                float rend = Float.parseFloat(txtRendimiento.getText().trim());
                String pos = txtPosicion.getText().trim();
                if (!equipo.actualizar(cod, nom, rend, pos)) {
                    JOptionPane.showMessageDialog(this,
                            "No existe jugador con código: " + cod,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                actualizarTabla();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Código y rendimiento deben ser números.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this,
                        ex.getMessage(),
                        "Error", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnEliminar.addActionListener(e -> {
            try {
                int cod = Integer.parseInt(txtCodigo.getText().trim());
                if (!equipo.eliminar(cod)) {
                    JOptionPane.showMessageDialog(this,
                            "No existe jugador con código: " + cod,
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
                actualizarTabla();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Código debe ser un número.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnLimpiar.addActionListener(e -> {
            equipo.clearAll();
            actualizarTabla();
            clearInputs();
        });

        tblJugadores.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            int row = tblJugadores.getSelectedRow();
            if (row >= 0) {
                txtCodigo.setText(tblJugadores.getValueAt(row,0).toString());
                txtNombre.setText(tblJugadores.getValueAt(row,1).toString());
                txtRendimiento.setText(tblJugadores.getValueAt(row,2).toString());
                txtPosicion.setText(tblJugadores.getValueAt(row,3).toString());
            }
        });
    }

    private void actualizarTabla() {
        DefaultTableModel m = (DefaultTableModel) tblJugadores.getModel();
        m.setRowCount(0);
        Set<Jugador> todos = equipo.getTodos();
        for (Jugador j : todos) {
            m.addRow(new Object[]{j.getCodigo(), j.getNombre(), j.getRendimiento(), j.getPosicion()});
        }
    }

    private void clearInputs() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtRendimiento.setText("");
        txtPosicion.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Ventana::new);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
