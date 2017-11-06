
package Inventario.Modelo.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GestorMySQL extends GestorBaseDatos{
    // <editor-fold defaultstate="collapsed" desc="constructores">
    GestorMySQL(String servidor)
            throws ClassNotFoundException,
            IllegalAccessException,
            InstantiationException {
        super("Gestor MySQL", servidor);
        Class.forName(MANEJADOR_DB).newInstance();
    }

    GestorMySQL()
            throws ClassNotFoundException,
            IllegalAccessException,
            InstantiationException {
        this(SERVIDOR_POR_DEFECTO);
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="métodos">
    @Override
    public Connection obtenerConexion(String baseDatos,
            String usuario, String claveAcceso)
            throws SQLException {
        cerrarConexion();
        String URL_conexion
                = String.format("%s//%s/%s", PROTOCOLO, URL_servidor, baseDatos);

        cnx = DriverManager.getConnection(URL_conexion, usuario, claveAcceso);
        return cnx;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="atributos">
    // Parámetros para la conexión a un servidor de base de datos MySQL.
    private static final String MANEJADOR_DB = "com.mysql.jdbc.Driver";
    private static final String PROTOCOLO = "jdbc:mysql:";
    // </editor-fold>
}
