package sv.edu.unab.negocio;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import sv.edu.unab.dominio.Empleado;

public class EmpleadoN {
    private static final Logger LOG=Logger.getLogger("sv.edu.unab.agenciavuelo");

    public static BiConsumer<List<Empleado>, ResultSet> cargarDatos=(listado, rs)->{
        try{
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
        }catch(SQLException e1){

        }

    };
    public static Supplier<List<Empleado>> listadoE=()->{

        PreparedStatement Ps = null;
        ArrayList<Empleado> listado = new ArrayList<>();
        LOG.log(Level.INFO,"[EmpleadoN][INIT]->Listado de Empleados");
        try{
            Connection Conex = new Conexion().getConexion();
            Ps = Conex.prepareStatement("select*from avr.vista_empleados");
            ResultSet rs = Ps.executeQuery();
            cargarDatos.accept(listado,rs);
            Conex.close();
            Ps.close();
        }catch (SQLException e1){

        }
        return listado;
    };
    public Consumer<Empleado> insertarEmpleado=(em)->{

        PreparedStatement p=null;
        LOG.log(Level.INFO,"[EmpleadoN][INIT]->Insertar Empleados");
            try {
            Connection Conex = new Conexion().getConexion();
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

        PreparedStatement p=null;
        LOG.log(Level.INFO,"[EmpleadoN][INIT]->Editar Empleados");

        try {
            Connection Conex = new Conexion().getConexion();
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

        PreparedStatement ps=null;
        LOG.log(Level.INFO,"[EmpleadoN][INIT]->Eliminar Empleados");
        try {
            Connection Conex = new Conexion().getConexion();
            ps=Conex.prepareStatement("SELECT avr.eliminar_empleado("+em.getId()+")");
            ps.executeQuery();
            Conex.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };
}