
package Inventario.Modelo.Instrumento;

import javax.swing.table.AbstractTableModel;

public class TablaInstrumentos extends AbstractTableModel{
    private TablaInstrumentos() {
        try {
            instrumentos = GestorInstrumentos.getInstancia().obtenerTabla();
        } catch (ClassNotFoundException
                | IllegalAccessException
                | InstantiationException ex) {
            System.err.printf("%s%n", ex.getMessage());
        }
    }

    public static TablaInstrumentos getInstancia() {
        if (instancia == null) {
            instancia = new TablaInstrumentos();
        }
        return instancia;
    }

    @Override
    public int getRowCount() {
        return instrumentos.length;
    }

    @Override
    public int getColumnCount() {
        return Instrumento.getDesccasillas().length;
    }

    @Override
    public String getColumnName(int i) {
        return Instrumento.getDesccasillas()[i];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        return instrumentos[fila][columna];
    }

    @Override
    public void setValueAt(Object valor, int fila, int columna) {
        System.out.printf(
                "Intenta actualizar el valor en la posición [%d,%d]: '%s'%n",
                fila, columna, valor);
        instrumentos[fila][columna] = valor;
        Instrumento in = new Instrumento(
                instrumentos[fila][0].toString(),
                instrumentos[fila][1].toString(),
                instrumentos[fila][2].toString(),
                (Integer) instrumentos[fila][3],
                (Integer) instrumentos[fila][4],
                (Integer) instrumentos[fila][5]
        );
        try {
            GestorInstrumentos.getInstancia()
                    .actualizar(in);
        } catch (ClassNotFoundException
                | IllegalAccessException
                | InstantiationException ex) {
            System.out.printf("Excepción: %s%n", ex.getMessage());
        }
    }

    private static TablaInstrumentos instancia = null;
    private Object[][] instrumentos;
}
