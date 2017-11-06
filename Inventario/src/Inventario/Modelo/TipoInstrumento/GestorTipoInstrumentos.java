package Inventario.Modelo.TipoInstrumento;

import Inventario.Modelo.BD.GestorBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestorTipoInstrumentos {
    private GestorTipoInstrumentos() throws
            InstantiationException,
            ClassNotFoundException,
            IllegalAccessException {
        bd = GestorBaseDatos.getInstancia(
                GestorBaseDatos.GBD.MYSQL_SERVER,
                GestorBaseDatos.SERVIDOR_POR_DEFECTO);
    }

    public static GestorTipoInstrumentos getInstancia() throws
            InstantiationException,
            ClassNotFoundException,
            IllegalAccessException {
        if (instancia == null) {
            instancia = new GestorTipoInstrumentos();
        }
        return instancia;
    }

    // (C)reate
    public boolean agregar(TipoInstrumento nuevoTipoInstrumento) {
        boolean exito = false;
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    PreparedStatement stm = cnx.prepareStatement(CMD_AGREGAR)) {
                stm.clearParameters();
                stm.setString(1, nuevoTipoInstrumento.getCodigo());
                stm.setString(2, nuevoTipoInstrumento.getNombre());
                stm.setString(3, nuevoTipoInstrumento.getUnidad());
                //preguntar 
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
    public TipoInstrumento recuperar(String id) {
        TipoInstrumento r = null;
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR)) {
                stm.clearParameters();
                stm.setString(1, id);
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        r = new TipoInstrumento(
                                rs.getString("codigo"),
                                rs.getString("nombre"),
                                rs.getString("unidad")
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
    public boolean actualizar(TipoInstrumento ti) {
        boolean exito = false;
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    PreparedStatement stm = cnx.prepareStatement(CMD_ACTUALIZAR)) {
                stm.clearParameters();
                stm.setString(1, ti.getCodigo());
                stm.setString(2, ti.getNombre());
                stm.setString(3, ti.getUnidad());
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
    public boolean eliminar(int numCalibracion) {
        boolean exito = false;
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    PreparedStatement stm = cnx.prepareStatement(CMD_ELIMINAR)) {
                stm.clearParameters();
                stm.setInt(1, numCalibracion);
                int r = stm.executeUpdate();
                exito = (r == 1);
            }
        } catch (SQLException ex) {
            System.err.printf("Excepción: '%s'%n",
                    ex.getMessage());
        }
        return exito;
    }

    public List<TipoInstrumento> listaMedidas() {
        List<TipoInstrumento> r = new ArrayList<>();
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    Statement stm = cnx.createStatement();
                    ResultSet rs = stm.executeQuery(CMD_LISTAR)) {
                while (rs.next()) {
                    r.add(new TipoInstrumento(
                            rs.getString("codigo"),
                            rs.getString("nombre"),
                            rs.getString("unidad")
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
        List<TipoInstrumento> tiposinstrumentos = listaMedidas();
        Object[][] r = new Object[tiposinstrumentos.size()][3];
        int i = 0;
        for (TipoInstrumento ti : tiposinstrumentos) {
            r[i][0] = ti.getCodigo();
            r[i][1] = ti.getNombre();
            r[i][2] = ti.getUnidad();
            i++;
        }
        return r;
    }

    private static final String BASE_DATOS = "SILDB";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    private static final String CMD_LISTAR
            = "SELECT codigo, nombre, unidad FROM tiposinstrumentos ORDER BY codigo;";
    private static final String CMD_AGREGAR
            = "INSERT INTO tiposinstrumentos (codigo, nombre, unidad) VALUES (?, ?, ?);";
    private static final String CMD_RECUPERAR
            = "SELECT codigo, nombre, unidad FROM tiposinstrumentos WHERE codigo=?; ";
    private static final String CMD_ACTUALIZAR
            = "UPDATE tiposinstrumentos SET nombre=?, unidad=? WHERE codigo=?;";
    private static final String CMD_ELIMINAR
            = "DELETE FROM tiposinstrumentos WHERE codigo=?; ";
    private static GestorTipoInstrumentos instancia = null;
    private final GestorBaseDatos bd;
}
