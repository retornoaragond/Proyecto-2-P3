package Inventario.Modelo.Calibraciones;

import java.sql.Date;
import javax.swing.table.AbstractTableModel;

public class TablaCalibraciones extends AbstractTableModel {

    private TablaCalibraciones() {
        try {
            calibraciones = GestorCalibraciones.getInstancia().obtenerTabla();
        } catch (ClassNotFoundException
                | IllegalAccessException
                | InstantiationException ex) {
            System.err.printf("%s%n", ex.getMessage());
        }
    }

    public static TablaCalibraciones getInstancia() {
        if (instancia == null) {
            instancia = new TablaCalibraciones();
        }
        return instancia;
    }

    @Override
    public int getRowCount() {
        return calibraciones.length;
    }

    @Override
    public int getColumnCount() {
        return Calibracion.getDesccasillas().length;
    }

    @Override
    public String getColumnName(int i) {
        return Calibracion.getDesccasillas()[i];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        return calibraciones[fila][columna];
    }

    @Override
    public void setValueAt(Object valor, int fila, int columna) {
        System.out.printf(
                "Intenta actualizar el valor en la posición [%d,%d]: '%s'%n",
                fila, columna, valor);
        calibraciones[fila][columna] = valor;
        Calibracion c = new Calibracion(
                (Integer) calibraciones[fila][0],
                calibraciones[fila][1].toString(),
                (Date) calibraciones[fila][2]
        );
        try {
            GestorCalibraciones.getInstancia()
                    .actualizar(c);
        } catch (ClassNotFoundException
                | IllegalAccessException
                | InstantiationException ex) {
            System.out.printf("Excepción: %s%n", ex.getMessage());
        }
    }

    private static TablaCalibraciones instancia = null;
    private Object[][] calibraciones;
}
