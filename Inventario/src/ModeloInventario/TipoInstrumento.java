package ModeloInventario;

import java.io.Serializable;

public class TipoInstrumento implements Serializable {

    public TipoInstrumento(String codigo, String nombre, String unidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.unidad = unidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getUnidad() {
        return unidad;
    }

    public static String[] getDesccasillas() {
        return desccasillas;
    }

    @Override
    public String toString() {
        return String.format("{%s, %s ,%s}", codigo, nombre, unidad);
    }

    private static final String[] desccasillas = {
        "codigo", "nombre", "unidad"
    };

    private final String codigo;
    private final String nombre;
    private final String unidad;

}
