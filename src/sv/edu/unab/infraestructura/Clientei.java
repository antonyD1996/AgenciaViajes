package sv.edu.unab.infraestructura;

import sv.edu.unab.dominio.Cliente;
import sv.edu.unab.negocio.*;
import sv.edu.unab.presentacion.CRUDCliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.BiConsumer;
import java.util.function.Function;

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
    public BiConsumer<JTable, List<Cliente>> cargarTabla=(tabla, listado)->{
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
    };
    public Function<JTable,List<Cliente>> actualizarDatos=tabla->{
        listado=ClienteN.listadoC.get();
        cargarTabla.accept(tabla, listado);
        TableColumn columna = tabla.getColumnModel().getColumn(0);
        columna.setMaxWidth(0);
        columna.setMinWidth(0);
        columna.setPreferredWidth(0);
        tabla.doLayout();
        return listado;
    };

    public BiConsumer<JTable, List<Cliente>> mostrarCoincidencias=(tabla, listado)->{
        cargarTabla.accept(tabla,listado);
        TableColumn columna = tabla.getColumnModel().getColumn(0);
        columna.setMaxWidth(0);
        columna.setMinWidth(0);
        columna.setPreferredWidth(0);
        tabla.doLayout();
    };

}

