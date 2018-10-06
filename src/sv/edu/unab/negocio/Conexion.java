package sv.edu.unab.negocio;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    private static final Logger LOG=Logger.getLogger("sv.edu.unab.agenciavuelo");
    private static BasicDataSource dataSource=new BasicDataSource();
    static{
        try {
            LOG.log(Level.INFO,"[Conexion][INIT]->Aperturando Conexion");
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl("jdbc:postgresql://localhost:5432/agencia_viaje");
            dataSource.setUsername("postgres");
            dataSource.setPassword("andaduper2096");
            dataSource.setMinIdle(5);
            dataSource.setMaxIdle(10);
            dataSource.setInitialSize(5);
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "[Conexion][INIT][Exception]->",e);
        }
    }
    public Connection getConexion()throws SQLException {
        return dataSource.getConnection();
    }
}
