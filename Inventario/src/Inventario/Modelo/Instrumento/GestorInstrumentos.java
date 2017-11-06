package Inventario.Modelo.Instrumento;

import Inventario.Modelo.BD.GestorBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestorInstrumentos {

    private GestorInstrumentos() throws
            InstantiationException,
            ClassNotFoundException,
            IllegalAccessException {
        bd = GestorBaseDatos.getInstancia(
                GestorBaseDatos.GBD.MYSQL_SERVER,
                GestorBaseDatos.SERVIDOR_POR_DEFECTO);
    }

    public static GestorInstrumentos getInstancia() throws
            InstantiationException,
            ClassNotFoundException,
            IllegalAccessException {
        if (instancia == null) {
            instancia = new GestorInstrumentos();
        }
        return instancia;
    }

    // (C)reate
    public boolean agregar(Instrumento nuevoInstrumento) {
        boolean exito = false;
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    PreparedStatement stm = cnx.prepareStatement(CMD_AGREGAR)) {
                stm.clearParameters();
                stm.setString(1, nuevoInstrumento.getSerie());
                stm.setString(2, nuevoInstrumento.getTipo());
                stm.setString(3, nuevoInstrumento.getDescripcion());
                stm.setInt(4, nuevoInstrumento.getMinimo());
                stm.setInt(5, nuevoInstrumento.getMaximo());
                stm.setInt(6, nuevoInstrumento.getTolerancia());
                int r = stm.executeUpdate();
                exito = (r == 1);
            }
        } catch (SQLException ex) {
            System.err.printf("Excepción: '%s'%n",
                    ex.getMessage());
        }
        return exito;
    }

    // (R)etrieve
    public Instrumento recuperar(String id) {
        Instrumento r = null;
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR)) {
                stm.clearParameters();
                stm.setString(1, id);
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        r = new Instrumento(
                                rs.getString("serie"),
                                rs.getString("tipo"),
                                rs.getString("descripcion"),
                                rs.getInt("minimo"),
                                rs.getInt("maximo"),
                                rs.getInt("tolerancia")
                        );
                    }
                }
            }
        } catch (SQLException ex) {
            System.err.printf("Excepción: '%s'%n",
                    ex.getMessage());
        }
        return r;
    }

    // (U)pdate
    public boolean actualizar(Instrumento i) {
        boolean exito = false;
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    PreparedStatement stm = cnx.prepareStatement(CMD_ACTUALIZAR)) {
                stm.clearParameters();
                stm.setString(1, i.getSerie());
                stm.setString(2, i.getTipo());
                stm.setString(3, i.getDescripcion());
                stm.setInt(4, i.getMinimo());
                stm.setInt(5, i.getMaximo());
                stm.setInt(6, i.getTolerancia());
                int r = stm.executeUpdate();
                exito = (r == 1);
            }
        } catch (SQLException ex) {
            System.err.printf("Excepción: '%s'%n",
                    ex.getMessage());
        }
        return exito;
    }

    // (D)elete
    public boolean eliminar(int serie) {
        boolean exito = false;
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    PreparedStatement stm = cnx.prepareStatement(CMD_ELIMINAR)) {
                stm.clearParameters();
                stm.setInt(1, serie);
                int r = stm.executeUpdate();
                exito = (r == 1);
            }
        } catch (SQLException ex) {
            System.err.printf("Excepción: '%s'%n",
                    ex.getMessage());
        }
        return exito;
    }

    public List<Instrumento> listaMedidas() {
        List<Instrumento> r = new ArrayList<>();
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    Statement stm = cnx.createStatement();
                    ResultSet rs = stm.executeQuery(CMD_LISTAR)) {
                while (rs.next()) {
                    r.add(new Instrumento(
                            rs.getString("serie"),
                            rs.getString("tipo"),
                            rs.getString("descripcion"),
                            rs.getInt("minimo"),
                            rs.getInt("maximo"),
                            rs.getInt("tolerancia")
                    ));
                }
            }
        } catch (SQLException ex) {
            System.err.printf("Excepción: '%s'%n",
                    ex.getMessage());
        }
        return r;
    }

    public Object[][] obtenerTabla() {
        List<Instrumento> instrumentos = listaMedidas();
        Object[][] r = new Object[instrumentos.size()][3];
        int i = 0;
        for (Instrumento in : instrumentos) {
            r[i][0] = in.getSerie();
            r[i][1] = in.getTipo();
            r[i][2] = in.getDescripcion();
            r[i][3] = in.getMinimo();
            r[i][4] = in.getMaximo();
            r[i][5] = in.getTolerancia();
            i++;
        }
        return r;
    }

    private static final String BASE_DATOS = "SILDB";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    private static final String CMD_LISTAR
            = "SELECT serie, tipo, descripcion, minimo, maximo,"
            + " tolerancia FROM instrumentos ORDER BY serie;";
    private static final String CMD_AGREGAR
            = "INSERT INTO instrumentos (serie, tipo, descripcion,"
            + " minimo, maximo, tolerancia) VALUES (?, ?, ?);";
    private static final String CMD_RECUPERAR
            = "SELECT serie, tipo, descripcion, minimo, maximo,"
            + " tolerancia FROM instrumentos WHERE serie=?; ";
    private static final String CMD_ACTUALIZAR
            = "UPDATE instrumentos SET tipo=?, descripcion=?, "
            + "minimo=?, maximo=?, tolerancia=? WHERE serie=?;";
    private static final String CMD_ELIMINAR
            = "DELETE FROM instrumentos WHERE serie=?; ";
    private static GestorInstrumentos instancia = null;
    private final GestorBaseDatos bd;
}
