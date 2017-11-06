package Inventario.Modelo.Calibraciones;

import Inventario.Modelo.Medidas.Medida;
import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Calibracion implements Serializable {

    public Calibracion(int numCaligracion, String instrumento,
            Date fecha) {
        this.numCalibracion = numCaligracion;
        this.instrumento = instrumento;
        this.fecha = fecha;
        this.mediciones = new ArrayList<>();
    }

    public int getNumCalibracion() {
        return numCalibracion;
    }

    public String getInstrumento() {
        return instrumento;
    }

    public Date getFecha() {
        return fecha;
    }

    public static String[] getDesccasillas() {
        return desccasillas;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("{");
        s.append(getNumCalibracion());
        s.append(", ");
        s.append(getInstrumento());
        s.append(", ");
        s.append(new java.util.Date(fecha.getTime()).toString());
        mediciones.forEach((medicion) -> {
            s.append(", ");
            s.append(medicion.toString());
        });
        s.append("}\n");
        return s.toString();
    }

    private static final String[] desccasillas = {
        "numCaligracion", "instrumento", "fecha", "mediciones"
    };

    private final int numCalibracion;
    private final String instrumento;
    private final Date fecha;
    private final List<Medida> mediciones;
}
