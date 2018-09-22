package sv.edu.unab.negocio;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import java.util.function.Supplier;
import java.util.function.BiConsumer;
import sv.edu.unab.dominio.Cliente;


public class ClienteN{
    public static BiConsumer<List<Cliente>, ResultSet> cargarDatos=(listado,rs)->{
        try{
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getLong("id"));
                c.setNombre(rs.getString("nombre"));
                c.setApellidopaterno(rs.getString("apellidopaterno"));
                c.setApellidomaterno(rs.getString("apellidomaterno"));
                c.setDui(rs.getString("dui"));
                c.setNit(rs.getString("nit"));
                c.setTelefono(rs.getString("telefono"));
                c.setEmail(rs.getString("email"));
                c.setDireccion(rs.getString("direccion"));
                c.setFechaNacimiento(LocalDate.parse(rs.getString("fechanacimiento")));
                listado.add(c);
            }
        }catch(SQLException e1){

        }

    };
    public static Supplier<List<Cliente>> listadoC=()->{
        Connection Conex = ConexionDB.getConnection();
        PreparedStatement Ps = null;
        ArrayList<Cliente> listado = new ArrayList<Cliente>();
        try{
            Ps = Conex.prepareStatement("select*from avr.vista_clientes");
            ResultSet rs = Ps.executeQuery();
            cargarDatos.accept(listado,rs);
            Conex.close();
            Ps.close();
        }catch (SQLException e1){

        }
        return listado;
    };


    public Consumer<Cliente> insertarCliente=(c)->{
        Connection Conex=ConexionDB.getConnection();
        PreparedStatement p=null;
        try {
            p=Conex.prepareStatement("select avr.insert_cliente('"
                    +c.getNombre()+"','"
                    +c.getApellidopaterno()+"','"
                    +c.getApellidomaterno()+"','"
                    +c.getDui()+"','"
                    +c.getNit()+"','"
                    +c.getTelefono()+"','"
                    +c.getEmail()+"','"
                    +c.getDireccion()+"','"
                    +c.getFechaNacimiento().toString()+"')");
            p.executeQuery();
            Conex.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

    public Consumer<Cliente> editarCliente=(c)->{
        Connection Conex=ConexionDB.getConnection();
        PreparedStatement p=null;
        try {
            p=Conex.prepareStatement("select avr.update_cliente("
                    +c.getId()+",'"
                    +c.getNombre()+"','"
                    +c.getApellidopaterno()+"','"
                    +c.getApellidomaterno()+"','"
                    +c.getDui()+"','"
                    +c.getNit()+"','"
                    +c.getTelefono()+"','"
                    +c.getEmail()+"','"
                    +c.getDireccion()+"','"
                    +c.getFechaNacimiento().toString()+"')");
            p.executeQuery();
            Conex.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };
    public Consumer<Cliente> eliminarCliente=(c)->{
        Connection Conex=ConexionDB.getConnection();
        PreparedStatement ps=null;

        try {
            ps=Conex.prepareStatement("SELECT avr.eliminar_cliente("+c.getId()+")");
            ps.executeQuery();
            Conex.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };
}