package ModeloInventario;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;

public class Calibracion implements Serializable {

    public Calibracion(int numCaligracion, String instrumento,
            Date fecha, ArrayList<Medida> mediciones) {
        this.numCaligracion = numCaligracion;
        this.instrumento = instrumento;
        this.fecha = fecha;
        this.mediciones = mediciones;
    }

    public int getNumCaligracion() {
        return numCaligracion;
    }

    public String getInstrumento() {
        return instrumento;
    }

    public Date getFecha() {
        return fecha;
    }

    public ArrayList<Medida> getMediciones() {
        return mediciones;
    }
    
    public static String[] getDesccasillas() {
        return desccasillas;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("{");
        s.append(getNumCaligracion());
        s.append(", ");
        s.append(getInstrumento());
        s.append(", ");
        s.append(new java.util.Date(fecha.getTime()));
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
    

    private final int numCaligracion;
    private final String instrumento;
    private final Date fecha;
    private final ArrayList<Medida> mediciones;
}
