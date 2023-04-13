import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.TimeZone;

public class ConexionMySql {
    private String host;
    private String usuario;
    private String pass;
    private String bd;
    private Connection connection;

    // Constructor
    public ConexionMySql(String host, String bd, String usuario, String pass) {
        this.host = host;
        this.usuario = usuario;
        this.pass = "";
        this.bd = bd;
    }

    /**
     * Conecta una base de datos jdbc mediante sqlDriver
     * 
     * @return Un valor booleano
     */
    public boolean conectar()
            throws SQLException {

        Calendar now = Calendar.getInstance();

        TimeZone zonahoraria = now.getTimeZone();

        connection = (Connection) DriverManager.getConnection("jdbc:mysql://" + host + "/" + bd + "?user="
                + usuario + "&password=" + pass + "&useLegacyDateTimeCode=false&serverTimeZone=" + zonahoraria.getID());
        if (connection == null) {
            return false;
        }
        return true;
    }

    /**
     * Meteodo para cerrar la base de datos
     * 
     * @param connection Recibe la base de datos a desconectar
     * @return Un mensaje de comprobacion
     */
    public String desconectar(Connection connection) throws SQLException {
        String desc = "La base de datos ha sido desconectada";

        if (!connection.isClosed() && connection != null) {
            connection.close();
            return desc;
        } else {
            return "Error al cerrar la base de datos";
        }

    }

    /**
     * Ejecuta una consulta SQL e imprime su resltado por pantalla
     * 
     * @param consulta Consulta SQL
     */
    public void ejecutarSelect(String consulta) throws SQLException {

        Statement stmt = connection.createStatement();

        ResultSet resultado = stmt.executeQuery(consulta);

        ResultSetMetaData rsmt = resultado.getMetaData();

        try {
            while (resultado.next()) {
                for (int i = 1; i <= rsmt.getColumnCount(); i++) {
                    System.out.print(resultado.getString(i) + " - ");
                }
                System.out.println("");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo para Insertar, Actualizar y Borrar datos de una tabla
     * 
     * @param consulta SQL recibido para ejecutar
     * @return resultado Numero de filas moificadas
     * @throws SQLException
     */
    public String ejecutarInsertUpdateDelete(String consulta) throws SQLException {

        int resultado = 0;
        try {
            Statement stmt = connection.createStatement();
            resultado = stmt.executeUpdate(consulta);
        } catch (SQLException e) {
            e.getMessage();
        }

        return "El numero de filas afectadas es: " + resultado;
    }
}
