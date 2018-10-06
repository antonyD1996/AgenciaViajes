package sv.edu.unab.negocio;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import java.util.function.Supplier;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;

import sv.edu.unab.dominio.Cliente;


public class ClienteN{
    private static final Logger LOG=Logger.getLogger("sv.edu.unab.agenciavuelo");

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
        ArrayList<Cliente> listado = new ArrayList<>();
        LOG.log(Level.INFO,"[ClienteN][INIT]->Listado de Clientes");
        try{

            Connection Conex =new Conexion().getConexion();
            PreparedStatement Ps = null;

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

        LOG.log(Level.INFO,"[ClienteN][INIT]->Insertar Cliente");
        PreparedStatement p=null;
        try {
            Connection Conex =new Conexion().getConexion();
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
        LOG.log(Level.INFO,"[ClienteN][INIT]->Editar Cliente");
        PreparedStatement p=null;
        try {
            Connection Conex =new Conexion().getConexion();
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
        LOG.log(Level.INFO,"[ClienteN][INIT]->Eliminar Cliente");
        PreparedStatement ps=null;

        try {
            Connection Conex =new Conexion().getConexion();
            ps=Conex.prepareStatement("SELECT avr.eliminar_cliente("+c.getId()+")");
            ps.executeQuery();
            Conex.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    };
}