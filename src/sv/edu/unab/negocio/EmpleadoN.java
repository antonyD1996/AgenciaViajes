package sv.edu.unab.negocio;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import sv.edu.unab.dominio.Empleado;

public class EmpleadoN {
    List<Empleado> listado;
    public static List<Empleado> mostrar_Empleado() throws SQLException{
        Connection Conex = ConexionDB.getConnection();
        PreparedStatement Ps = null;
        ArrayList<Empleado> listado = new ArrayList<Empleado>();

        Ps = Conex.prepareStatement("select*from avr.vista_Empleados");
        ResultSet rs = Ps.executeQuery();
        while (rs.next()) {
            Empleado em = new Empleado();
            em.setId(rs.getLong("id"));
            em.setNombre(rs.getString("nombre"));
            em.setApellidopaterno(rs.getString("apellidopaterno"));
            em.setApellidomaterno(rs.getString("apellidomaterno"));
            em.setDui(rs.getString("dui"));
            em.setNit(rs.getString("nit"));
            em.setTelefono(rs.getString("telefono"));
            em.setEmail(rs.getString("email"));
            em.setDireccion(rs.getString("direccion"));
            em.setFechaNacimiento(LocalDate.parse(rs.getString("fechanacimiento")));
            em.setSeguro(rs.getString("seguro"));
            em.setAfp(rs.getString("afp"));
            listado.add(em);
        }
        Conex.close();
        Ps.close();
        return listado;
    }
    public Consumer<Empleado> insertarEmpleado=(em)->{
        Connection Conex=ConexionDB.getConnection();
        PreparedStatement p=null;
        try {
            p=Conex.prepareStatement("select avr.insert_empleado('"
                    +em.getNombre()+"','"
                    +em.getApellidopaterno()+"','"
                    +em.getApellidomaterno()+"','"
                    +em.getDui()+"','"
                    +em.getNit()+"','"
                    +em.getTelefono()+"','"
                    +em.getEmail()+"','"
                    +em.getDireccion()+"','"
                    +em.getFechaNacimiento().toString()+"','"
                    +em.getSeguro()+"','"
                    +em.getAfp()+"')");
            p.executeQuery();
            Conex.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };

    public Consumer<Empleado> editarEmpleado=(em)->{
        Connection Conex=ConexionDB.getConnection();
        PreparedStatement p=null;
        try {
            p=Conex.prepareStatement("select avr.update_empleado("
                    +em.getId()+",'"
                    +em.getNombre()+"','"
                    +em.getApellidopaterno()+"','"
                    +em.getApellidomaterno()+"','"
                    +em.getDui()+"','"
                    +em.getNit()+"','"
                    +em.getTelefono()+"','"
                    +em.getEmail()+"','"
                    +em.getDireccion()+"','"
                    +em.getFechaNacimiento().toString()+"','"
                    +em.getSeguro()+"','"
                    +em.getAfp()+"')");
            p.executeQuery();
            Conex.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };
    public Consumer<Empleado> eliminarEmpleado=(em)->{
        Connection Conex=ConexionDB.getConnection();
        PreparedStatement ps=null;

        try {
            ps=Conex.prepareStatement("SELECT avr.eliminar_empleado("+em.getId()+")");
            ps.executeQuery();
            Conex.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };
    public static List<Empleado> buscar_Empleado(String buscar) throws SQLException{
        Connection Conex = ConexionDB.getConnection();
        PreparedStatement Ps = null;
        ArrayList<Empleado> listado = new ArrayList<Empleado>();

        Ps = Conex.prepareStatement("select*from avr.buscar_empleado('%"+buscar+"%')");
        ResultSet Rs = Ps.executeQuery();

        while (Rs.next()) {
            Empleado em = new Empleado();
            em.setId(Rs.getLong("id"));
            em.setNombre(Rs.getString("nombre"));
            em.setApellidopaterno(Rs.getString("apellidopaterno"));
            em.setApellidomaterno(Rs.getString("apellidomaterno"));
            em.setDui(Rs.getString("dui"));
            em.setNit(Rs.getString("nit"));
            em.setTelefono(Rs.getString("telefono"));
            em.setEmail(Rs.getString("email"));
            em.setDireccion(Rs.getString("direccion"));
            em.setFechaNacimiento(LocalDate.parse(Rs.getString("fechanacimiento")));
            em.setSeguro(Rs.getString("seguro"));
            em.setAfp(Rs.getString("afp"));
            listado.add(em);
        }
        List<Empleado> Resultado=listado.stream().collect(Collectors.toList());
        Conex.close();
        Ps.close();
        return Resultado;
    }
}