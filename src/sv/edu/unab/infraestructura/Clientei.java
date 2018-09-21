package sv.edu.unab.infraestructura;

import sv.edu.unab.dominio.Cliente;
import sv.edu.unab.negocio.*;
import sv.edu.unab.presentacion.CRUDCliente;
import sv.edu.unab.presentacion.CRUDEmpleado;
import sv.edu.unab.presentacion.CRUDCliente.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Consumer;

public class Clientei {
    DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd/MM/yyyy");
    List<Cliente> listado;
    ClienteN cn=new ClienteN();
    CRUDCliente crud;

    public Consumer<Cliente> insertarCliente= c -> {
        new ClienteN().insertarCliente.accept(c);
    };
    public Consumer<Cliente> editarCliente= c -> {
        new ClienteN().editarCliente.accept(c);
    };
    public Consumer<Cliente> eliminarCliente= c -> {
        new ClienteN().eliminarCliente.accept(c);
    };
    public void actualizarDatos(JTable tabla) throws SQLException {
        listado=ClienteN.mostrar_Cliente();
        cargarTabla(tabla, listado);
    }
    public void mostrarCoincidencias(JTable tabla, List<Cliente> listado1) throws SQLException {
        cargarTabla(tabla,listado1);
    }
    public void cargarTabla(JTable tabla, List<Cliente> listado){

        DefaultTableModel model=new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Nombres");
        model.addColumn("Apellido P");
        model.addColumn("Apellido M");
        model.addColumn("DUI");
        model.addColumn("NIT");
        model.addColumn("FechaN");
        model.addColumn("Edad");
        model.addColumn("Telefono");
        model.addColumn("Direccion");
        model.addColumn("Email");

        listado.stream().forEach(p->{
            model.addRow(new Object[]{
                    p.getId(),
                    p.getNombre(),
                    p.getApellidopaterno(),
                    p.getApellidomaterno(),
                    p.getDui(),
                    p.getNit(),
                    p.getFechaNacimiento().format(dtf),
                    p.getFechaNacimiento().until(LocalDate.now(), ChronoUnit.YEARS),
                    p.getTelefono(),
                    p.getDireccion(),
                    p.getEmail()
            });
        });
        tabla.setModel(model);

    }


}

