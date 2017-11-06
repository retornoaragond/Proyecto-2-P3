package Inventario.Modelo.Calibraciones;

import Inventario.Modelo.BD.GestorBaseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestorCalibraciones {

    private GestorCalibraciones() throws
            InstantiationException,
            ClassNotFoundException,
            IllegalAccessException {
        bd = GestorBaseDatos.getInstancia(
                GestorBaseDatos.GBD.MYSQL_SERVER,
                GestorBaseDatos.SERVIDOR_POR_DEFECTO);
    }

    public static GestorCalibraciones getInstancia() throws
            InstantiationException,
            ClassNotFoundException,
            IllegalAccessException {
        if (instancia == null) {
            instancia = new GestorCalibraciones();
        }
        return instancia;
    }

    // (C)reate
    public boolean agregar(Calibracion nuevaCalibracion) {
        boolean exito = false;
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    PreparedStatement stm = cnx.prepareStatement(CMD_AGREGAR)) {
                stm.clearParameters();
                stm.setInt(1, nuevaCalibracion.getNumCalibracion());
                stm.setString(2, nuevaCalibracion.getInstrumento());
                stm.setDate(3, nuevaCalibracion.getFecha());
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
    public Calibracion recuperar(String id) {
        Calibracion r = null;
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR)) {
                stm.clearParameters();
                stm.setString(1, id);
                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        r = new Calibracion(
                                rs.getInt("numCalibracion"),
                                rs.getString("instrumento"),
                                rs.getDate("lectura")
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
    public boolean actualizar(Calibracion c) {
        boolean exito = false;
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    PreparedStatement stm = cnx.prepareStatement(CMD_ACTUALIZAR)) {
                stm.clearParameters();
                stm.setInt(1, c.getNumCalibracion());
                stm.setString(2, c.getInstrumento());
                stm.setDate(3, c.getFecha());
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

    public List<Calibracion> listaMedidas() {
        List<Calibracion> r = new ArrayList<>();
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    Statement stm = cnx.createStatement();
                    ResultSet rs = stm.executeQuery(CMD_LISTAR)) {

                while (rs.next()) {
                    r.add(new Calibracion(
                            rs.getInt("numCalibracion"),
                            rs.getString("instrumento"),
                            rs.getDate("fecha")
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
        List<Calibracion> calibraciones = listaMedidas();
        Object[][] r = new Object[calibraciones.size()][3];
        int i = 0;
        for (Calibracion c : calibraciones) {
            r[i][0] = c.getNumCalibracion();
            r[i][1] = c.getInstrumento();
            r[i][2] = c.getFecha();
            i++;
        }
        return r;
    }

    private static final String BASE_DATOS = "SILDB";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    private static final String CMD_LISTAR
            = "SELECT numCalibracion, instrumento, fecha FROM calibraciones ORDER BY numCalibracion;";
    private static final String CMD_AGREGAR
            = "INSERT INTO calibraciones (numCalibracion, instrumento, fecha) VALUES (?, ?, ?);";
    private static final String CMD_RECUPERAR
            = "SELECT numCalibracion, instrumento, fecha FROM calibraciones WHERE numCalibracion=?; ";
    private static final String CMD_ACTUALIZAR
            = "UPDATE calibraciones SET instrumento=?, fecha=? WHERE numCalibracion=?;";
    private static final String CMD_ELIMINAR
            = "DELETE FROM calibraciones WHERE numCalibracion=?; ";
    private static GestorCalibraciones instancia = null;
    private final GestorBaseDatos bd;
}
