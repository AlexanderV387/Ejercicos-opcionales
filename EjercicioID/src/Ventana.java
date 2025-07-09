import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.List;


public class Ventana extends JFrame {
    private JPanel panel1;
    private JLabel lblId, lblNombre, lblPoder, lblFaccion;
    private JTextField txtId, txtNombre, txtPoder, txtFaccion;
    private JButton btmCrear, btnModificar, btnEliminar, btnLimpiar;
    private JTable tblResultados;
    private JScrollPane scrResultados;

    private final Coleccion coleccion = new Coleccion();

    public Ventana() {
        super("Gestión de Transformers");
        setContentPane(panel1);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        initTable();
        initListeners();
        actualizarTabla();
        clearInputs();
        setVisible(true);
    }

    private void initTable() {
        DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Poder", "Facción"}, 0
        );
        tblResultados.setModel(model);
        tblResultados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblResultados.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int row = tblResultados.getSelectedRow();
                    if (row >= 0) {
                        txtId.setText(tblResultados.getValueAt(row, 0).toString());
                        txtNombre.setText(tblResultados.getValueAt(row, 1).toString());
                        txtPoder.setText(tblResultados.getValueAt(row, 2).toString());
                        txtFaccion.setText(tblResultados.getValueAt(row, 3).toString());
                    }
                }
            }
        });
    }

    private void initListeners() {
        btmCrear.addActionListener(e -> crearTransformer());
        btnModificar.addActionListener(e -> modificarTransformer());
        btnEliminar.addActionListener(e -> eliminarTransformer());
        btnLimpiar.addActionListener(e -> {
            coleccion.clearAll();
            actualizarTabla();
            clearInputs();
        });
    }

    private void crearTransformer() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            Transformer t = new Transformer(
                    id,
                    txtNombre.getText().trim(),
                    txtPoder.getText().trim(),
                    txtFaccion.getText().trim()
            );
            coleccion.agregar(t);
            actualizarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID debe ser un número entero.",
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
        } catch (DuplicateIdException ex) {
            JOptionPane.showMessageDialog(this,
                    ex.getMessage(),
                    "ID duplicado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void modificarTransformer() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            Transformer t = new Transformer(
                    id,
                    txtNombre.getText().trim(),
                    txtPoder.getText().trim(),
                    txtFaccion.getText().trim()
            );
            if (coleccion.actualizar(t)) {
                actualizarTabla();
                clearInputs();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No existe Transformer con ID: " + id,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID debe ser un número entero.",
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarTransformer() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            if (coleccion.eliminar(id)) {
                actualizarTabla();
                clearInputs();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No existe Transformer con ID: " + id,
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "ID debe ser un número entero.",
                    "Error de formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla() {
        DefaultTableModel model = (DefaultTableModel) tblResultados.getModel();
        model.setRowCount(0);
        for (Transformer t : coleccion.getTodos()) {
            model.addRow(new Object[]{
                    t.getId(), t.getNombre(), t.getPoder(), t.getFaccion()});
        }
    }

    private void clearInputs() {
        txtId.setText("");
        txtNombre.setText("");
        txtPoder.setText("");
        txtFaccion.setText("");
        tblResultados.clearSelection();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ventana());
    }
}
