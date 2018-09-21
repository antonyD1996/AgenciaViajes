package sv.edu.unab.negocio;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import sv.edu.unab.dominio.Cliente;

import javax.swing.*;

public class ClienteN {
    List<Cliente> listado;
    public static List<Cliente> mostrar_Cliente() throws SQLException{
        Connection Conex = ConexionDB.getConnection();
        PreparedStatement Ps = null;
        ArrayList<Cliente> listado = new ArrayList<Cliente>();

        Ps = Conex.prepareStatement("select*from avr.vista_clientes");
        ResultSet rs = Ps.executeQuery();
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
        Conex.close();
        Ps.close();
        return listado;
    }
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
    public static List<Cliente> buscar_Cliente(String buscar) throws SQLException{
        Connection Conex = ConexionDB.getConnection();
        PreparedStatement Ps = null;
        ArrayList<Cliente> listado = new ArrayList<Cliente>();

        Ps = Conex.prepareStatement("select*from avr.buscar_cliente('%"+buscar+"%')");
        ResultSet Rs = Ps.executeQuery();

        while (Rs.next()) {
            Cliente c = new Cliente();
            c.setId(Rs.getLong("id"));
            c.setNombre(Rs.getString("nombre"));
            c.setApellidopaterno(Rs.getString("apellidopaterno"));
            c.setApellidomaterno(Rs.getString("apellidomaterno"));
            c.setDui(Rs.getString("dui"));
            c.setNit(Rs.getString("nit"));
            c.setTelefono(Rs.getString("telefono"));
            c.setEmail(Rs.getString("email"));
            c.setDireccion(Rs.getString("direccion"));
            c.setFechaNacimiento(LocalDate.parse(Rs.getString("fechanacimiento")));
            listado.add(c);
        }
        List<Cliente> Resultado=listado.stream().collect(Collectors.toList());
        Conex.close();
        Ps.close();
        return Resultado;
    }
}