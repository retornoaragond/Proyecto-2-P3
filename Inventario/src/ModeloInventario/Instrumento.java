package ModeloInventario;

import java.io.Serializable;

public class Instrumento implements Serializable {

    public Instrumento(String serie, String tipo, String descripcion,
            int minimo, int maximo, int tolerancia) {
        this.serie = serie;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.minimo = minimo;
        this.maximo = maximo;
        this.tolerancia = tolerancia;
    }

    public String getSerie() {
        return serie;
    }

    public String getTipo() {
        return tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getMinimo() {
        return minimo;
    }

    public int getMaximo() {
        return maximo;
    }

    public int getTolerancia() {
        return tolerancia;
    }

    public static String[] getDesccasillas() {
        return desccasillas;
    }
    
    @Override
    public String toString(){
        return String.format("{%s, %s ,%s , %d, %d ,%d}", serie, tipo,
                descripcion,minimo,maximo,tolerancia);
    }

    private static final String[] desccasillas = {
        "serie", "tipo", "descripcion", "minimo", "maximo", "tolerancia"
    };

    private final String serie;
    private final String tipo;
    private final String descripcion;
    private final int minimo;
    private final int maximo;
    private final int tolerancia;
}
