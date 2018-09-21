package sv.edu.unab.infraestructura;

import sv.edu.unab.dominio.Empleado;
import sv.edu.unab.negocio.*;
import sv.edu.unab.presentacion.CRUDEmpleado;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.function.Consumer;

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
    public void actualizarDatos(JTable tabla) throws SQLException {
        listado=EmpleadoN.mostrar_Empleado();
        cargarTabla(tabla, listado);
//        List<Persona> listado=EmpleadoN.mostrar_Empleado();
//
//        Persona edadMenor=listado.stream().min((e1,e2)->{
//            Long edad1=e1.getFechaNacimiento().until(LocalDate.now(), ChronoUnit.YEARS);
//            Long edad2=e2.getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS);
//            return edad1.compareTo(edad2);
//        }).get();
//
//        crudEmpleado.lblEdadMenor.setText(edadMenor.getNombre()+" "+edadMenor.getApellidopaterno());
    }
    public void mostrarCoincidencias(JTable tabla, String buscar) throws SQLException {
        listado=EmpleadoN.buscar_Empleado(buscar);
        cargarTabla(tabla,listado);
    }
    public void cargarTabla(JTable tabla, List<Empleado> listado){

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

    }

}
