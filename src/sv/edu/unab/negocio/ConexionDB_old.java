package sv.edu.unab.negocio;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB_old {
    public static Connection getConnection(){
        Connection cn=null;
        try  {
            Class.forName("org.postgresql.Driver");
            String url="jdbc:postgresql://localhost:5432/agencia_viaje";
            String user="postgres";
            String password="andaduper2096";
            cn= DriverManager.getConnection(url, user, password);
        }catch(ClassNotFoundException e){
            cn=null;
            JOptionPane.showMessageDialog(null, "No se puede cargar el driver"+e.getMessage());
        }
        catch(SQLException e){
            cn=null;
            if (e.getErrorCode()==0)
            {
                JOptionPane.showMessageDialog(null, "El servidor de base de datos no responde.\n",
                        "Error: Coneccion Fallida",JOptionPane.ERROR_MESSAGE);
            }

        }
        return cn;
    }
}
