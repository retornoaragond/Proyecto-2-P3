package Inventario.Modelo.Medidas;

import Inventario.Modelo.BD.GestorBaseDatos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GestorMedidas {

    private GestorMedidas() throws
            InstantiationException,
            ClassNotFoundException,
            IllegalAccessException {
        bd = GestorBaseDatos.getInstancia(
                GestorBaseDatos.GBD.MYSQL_SERVER,
                GestorBaseDatos.SERVIDOR_POR_DEFECTO);
    }

    public static GestorMedidas getInstancia() throws
            InstantiationException,
            ClassNotFoundException,
            IllegalAccessException {
        if (instancia == null) {
            instancia = new GestorMedidas();
        }
        return instancia;
    }

    // (C)reate
    public boolean agregar(Medida nuevaMedida) {
        boolean exito = false;
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    PreparedStatement stm = cnx.prepareStatement(CMD_AGREGAR)) {
                stm.clearParameters();
                stm.setInt(1, nuevaMedida.getNumero());
                stm.setInt(2, nuevaMedida.getReferencia());
                stm.setInt(3, nuevaMedida.getLectura());
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
    public Medida recuperar(String id) {
        Medida r = null;
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    PreparedStatement stm = cnx.prepareStatement(CMD_RECUPERAR)) {
                stm.clearParameters();
                stm.setString(1, id);

                try (ResultSet rs = stm.executeQuery()) {
                    if (rs.next()) {
                        r = new Medida(
                                rs.getInt("numero"),
                                rs.getInt("referencia"),
                                rs.getInt("lectura")
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
    public boolean actualizar(Medida m) {
        boolean exito = false;
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    PreparedStatement stm = cnx.prepareStatement(CMD_ACTUALIZAR)) {
                stm.clearParameters();
                stm.setInt(2, m.getReferencia());
                stm.setInt(3, m.getLectura());
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
    public boolean eliminar(int numero) {
        boolean exito = false;
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    PreparedStatement stm = cnx.prepareStatement(CMD_ELIMINAR)) {
                stm.clearParameters();
                stm.setInt(1, numero);

                int r = stm.executeUpdate();
                exito = (r == 1);
            }
        } catch (SQLException ex) {
            System.err.printf("Excepción: '%s'%n",
                    ex.getMessage());
        }
        return exito;
    }
    
    public List<Medida> listaMedidas() {
        List<Medida> r = new ArrayList<>();
        try {
            try (Connection cnx = bd.obtenerConexion(BASE_DATOS, USUARIO, CLAVE);
                    Statement stm = cnx.createStatement();
                    ResultSet rs = stm.executeQuery(CMD_LISTAR)) {

                while (rs.next()) {
                    r.add(new Medida(
                            rs.getInt("numero"),
                            rs.getInt("referencia"),
                            rs.getInt("lectura")
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
        List<Medida> medidas = listaMedidas();
        Object[][] r = new Object[medidas.size()][3];
        int i = 0;
        for (Medida e : medidas) {
            r[i][0] = e.getNumero();
            r[i][1] = e.getReferencia();
            r[i][2] = e.getLectura();
            i++;
        }
        return r;
    }

    private static final String BASE_DATOS = "SILDB";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    private static final String CMD_LISTAR
            = "SELECT numero, referencia, lectura FROM medidas ORDER BY numero;";
    private static final String CMD_AGREGAR
            = "INSERT INTO medidas (numero, referencia, lectura) VALUES (?, ?, ?);";
    private static final String CMD_RECUPERAR
            = "SELECT numero, referencia, lectura FROM medidas WHERE numero=?; ";
    private static final String CMD_ACTUALIZAR
            = "UPDATE medidas SET referencia=?, lectura=? WHERE numero=?;";
    private static final String CMD_ELIMINAR
            = "DELETE FROM medidas WHERE numero=?; ";
    private static GestorMedidas instancia = null;
    private final GestorBaseDatos bd;
}
