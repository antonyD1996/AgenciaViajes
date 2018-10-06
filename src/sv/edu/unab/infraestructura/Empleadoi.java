package sv.edu.unab.infraestructura;

import sv.edu.unab.dominio.Cliente;
import sv.edu.unab.dominio.Empleado;
import sv.edu.unab.negocio.*;
import sv.edu.unab.presentacion.CRUDEmpleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class Empleadoi {
    DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd/MM/yyyy");
    List<Empleado> listado;
    EmpleadoN em=new EmpleadoN();
    CRUDEmpleado crudEmpleado;

    public Consumer<Empleado> insertarEmpleado= em -> {
        new EmpleadoN().insertarEmpleado.accept(em);
    };
    public Consumer<Empleado> editarEmpleado= em -> {
        new EmpleadoN().editarEmpleado.accept(em);
    };
    public Consumer<Empleado> eliminarEmpleado= em -> {
        new EmpleadoN().eliminarEmpleado.accept(em);
    };

    public BiConsumer<JTable, List<Empleado>> cargarTabla=(tabla, listado)->{
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
        model.addColumn("Seguro");
        model.addColumn("AFP");

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
                    p.getEmail(),
                    p.getSeguro(),
                    p.getAfp()
            });
        });
        tabla.setModel(model);
    };
    public Function<JTable,List<Empleado>> actualizarDatos= tabla->{
        listado=EmpleadoN.listadoE.get();
        cargarTabla.accept(tabla, listado);
        TableColumn columna = tabla.getColumnModel().getColumn(0);
        columna.setMaxWidth(0);
        columna.setMinWidth(0);
        columna.setPreferredWidth(0);
        tabla.doLayout();
        return listado;
    };
    public BiConsumer<JTable, List<Empleado>> mostrarCoincidencias=(tabla, listado)->{
        cargarTabla.accept(tabla,listado);
        TableColumn columna = tabla.getColumnModel().getColumn(0);
        columna.setMaxWidth(0);
        columna.setMinWidth(0);
        columna.setPreferredWidth(0);
        tabla.doLayout();
    };

}
