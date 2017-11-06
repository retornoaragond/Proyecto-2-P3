package Inventario.Modelo.TipoInstrumento;

import javax.swing.table.AbstractTableModel;

public class TablaTipoInstrumentos extends AbstractTableModel {

    private TablaTipoInstrumentos(){
        try {
            tiposinstrumentos = GestorTipoInstrumentos.getInstancia().obtenerTabla();
        } catch (ClassNotFoundException
                | IllegalAccessException
                | InstantiationException ex) {
            System.err.printf("%s%n", ex.getMessage());
        }
    }

    public static TablaTipoInstrumentos getInstancia() {
        if (instancia == null) {
            instancia = new TablaTipoInstrumentos();
        }
        return instancia;
    }

    @Override
    public int getRowCount() {
        return tiposinstrumentos.length;
    }

    @Override
    public int getColumnCount() {
        return TipoInstrumento.getDesccasillas().length;
    }

    @Override
    public String getColumnName(int i) {
        return TipoInstrumento.getDesccasillas()[i];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0;
    }

    @Override
    public Object getValueAt(int fila, int columna) {
        return tiposinstrumentos[fila][columna];
    }

    @Override
    public void setValueAt(Object valor, int fila, int columna) {
        System.out.printf(
                "Intenta actualizar el valor en la posición [%d,%d]: '%s'%n",
                fila, columna, valor);
        tiposinstrumentos[fila][columna] = valor;
        TipoInstrumento ti = new TipoInstrumento(
                tiposinstrumentos[fila][0].toString(),
                tiposinstrumentos[fila][1].toString(),
                tiposinstrumentos[fila][2].toString()
        );
        try {
            GestorTipoInstrumentos.getInstancia()
                    .actualizar(ti);
        } catch (ClassNotFoundException
                | IllegalAccessException
                | InstantiationException ex) {
            System.out.printf("Excepción: %s%n", ex.getMessage());
        }
    }

    private static TablaTipoInstrumentos instancia = null;
    private Object[][] tiposinstrumentos;
}
