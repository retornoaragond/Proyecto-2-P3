package Inventario.Modelo.Medidas;

import javax.swing.table.AbstractTableModel;

public class TablaMedidas extends AbstractTableModel {

    private TablaMedidas() {
        try {
            medidas = GestorMedidas.getInstancia().obtenerTabla();
        } catch (ClassNotFoundException
                | IllegalAccessException
                | InstantiationException ex) {
            System.err.printf("%s%n", ex.getMessage());
        }
    }

    public static TablaMedidas getInstancia() {
        if (instancia == null) {
            instancia = new TablaMedidas();
        }
        return instancia;
    }

    @Override
    public int getRowCount() {
        return medidas.length;
    }

    @Override
    public int getColumnCount() {
        return Medida.getDesccasillas().length;
    }

    @Override
    public String getColumnName(int i) {
        return Medida.getDesccasillas()[i];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        return medidas[fila][columna];
    }

    @Override
    public void setValueAt(Object valor, int fila, int columna) {
        System.out.printf(
                "Intenta actualizar el valor en la posición [%d,%d]: '%s'%n",
                fila, columna, valor);
        medidas[fila][columna] = valor;
        Medida e = new Medida(
                (Integer) medidas[fila][0],
                (Integer) medidas[fila][1],
                (Integer) medidas[fila][2]
        );
        try {
            GestorMedidas.getInstancia()
                    .actualizar(e);
        } catch (ClassNotFoundException
                | IllegalAccessException
                | InstantiationException ex) {
            System.out.printf("Excepción: %s%n", ex.getMessage());
        }
    }

    private static TablaMedidas instancia = null;
    private Object[][] medidas;
}
