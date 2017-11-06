package Inventario.Modelo.Medidas;

import java.io.Serializable;

public class Medida implements Serializable {

    public Medida(int numero, int referencia, int lectura) {
        this.numero = numero;
        this.referencia = referencia;
        this.lectura = lectura;
    }

    public int getNumero() {
        return numero;
    }

    public int getReferencia() {
        return referencia;
    }

    public int getLectura() {
        return lectura;
    }

    public static String[] getDesccasillas() {
        return desccasillas;
    }

    @Override
    public String toString() {
        return String.format("{%d, %d %d}", numero, referencia, lectura);
    }

    private static final String[] desccasillas = {
        "numero", "referencia", "lectura"
    };

    private final int numero;
    private final int referencia;
    private final int lectura;
}
