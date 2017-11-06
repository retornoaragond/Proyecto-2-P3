package Inventario.Modelo.BD;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class GestorBaseDatos {

    protected GestorBaseDatos(String descripcion, String URL_servidor) {
        this.descripcion = descripcion;
        this.URL_servidor = (URL_servidor != null) ? URL_servidor : SERVIDOR_POR_DEFECTO;
    }
    
    public abstract Connection obtenerConexion(String baseDatos, String usuario, String claveAcceso)
            throws SQLException;
    
    public void cerrarConexion() {
        if (cnx != null) {
            try {
                cnx.close();
            } catch (SQLException e) {
                e.printStackTrace(System.err);
            }
        }
    }
    
    public static GestorBaseDatos getInstancia(GBD tipoServidor, String URL_servidor)
            throws InstantiationException, ClassNotFoundException, IllegalAccessException {
        if (instancia == null) {
            switch (tipoServidor) {
                case MYSQL_SERVER:
                    instancia = new GestorMySQL(URL_servidor);
                    break;
                default:
                    throw new InstantiationException("El tipo de servidor no se encuentra implementado.");
            }
        }
        return instancia;
    }
    
    public static GestorBaseDatos getInstancia(GBD tipoServidor)
            throws InstantiationException, ClassNotFoundException, IllegalAccessException {
        return getInstancia(tipoServidor, SERVIDOR_POR_DEFECTO);
    }

    public static GestorBaseDatos getInstancia()
            throws InstantiationException {
        if (instancia == null) {
            throw new InstantiationException("Instancia inválida.");
        }
        return instancia;
    }
    
    public String getDescripcion() {
        return descripcion;
    }

    public String getURL_servidor() {
        return URL_servidor;
    }
    
    @Override
    public String toString() {
        StringBuilder r = new StringBuilder();
        r.append(String.format(
                "%s%nServidor de base de datos en: %s",
                descripcion, URL_servidor));
        return r.toString();
    }

    public enum GBD {
        MYSQL_SERVER,
        POSTGRESQL,
        MSSQL_SERVER,
        ORACLE_SERVER
    };

    
    private String descripcion = null;
    protected String URL_servidor = null;
    protected Connection cnx = null;
    protected static GestorBaseDatos instancia = null;
    public static final String SERVIDOR_POR_DEFECTO = "localhost";
}
