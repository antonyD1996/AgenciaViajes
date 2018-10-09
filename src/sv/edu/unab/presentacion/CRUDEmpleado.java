package sv.edu.unab.presentacion;

import sv.edu.unab.dominio.Empleado;
import sv.edu.unab.dominio.Persona;
import sv.edu.unab.infraestructura.Empleadoi;

import javax.swing.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;
import java.time.temporal.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CRUDEmpleado extends JInternalFrame{
    public JPanel pnlroot;
    private JPanel pnldatos;
    private JTextField txtApellidoPaterno;
    private JTextField txtApellidoMaterno;
    private JFormattedTextField ftxDui;
    private JFormattedTextField ftxNit;
    private JTextField txtNombre;
    private JFormattedTextField ftxTelefono;
    private JTextArea txtDireccion;
    private JTextField txtEmail;
    private JTextField txtSeguro;
    private JTextField txtAFP;
    private JFormattedTextField ftxFechaN;
    private JTable tblEmpleado;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnCancelar;
    private JTextField txtBuscar;
    private JLabel lblEdadMenor;
    private JLabel lblMayor;
    private JLabel lblEdadProm;
    private JTextField txtMayorA;
    private JTextField txtMenorA;

    List<Empleado> listadoModel;
    long ID;
    Empleadoi emp=new Empleadoi();
    DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public CRUDEmpleado() {

        initcomponentes();
        setClosable(true);
        setIconifiable(true);
        setResizable(false);
        setTitle("Empleados");
        setLayout(null);
        setSize(900,800);
        txtNombre.setText("Antony David");
        txtApellidoPaterno.setText("Duarte");
        txtApellidoMaterno.setText("Perlera");
        ftxDui.setText("123456789");
        ftxNit.setText("12340511961231");
        ftxTelefono.setText("70079032");
        ftxFechaN.setText("05111996");
        txtDireccion.setText("Potrero Sula");
        txtEmail.setText("antony@gmail.com");
        txtSeguro.setText("123456789");
        txtAFP.setText("Crecer");
    }

    public void initcomponentes() {
        mostrarEmpleados.accept(tblEmpleado);

        tblEmpleado.setFillsViewportHeight(true);
        if (listadoModel==null){
            listadoModel=new ArrayList<>();
        }
        btnAgregar.addActionListener(e->{
            if (txtNombre.getText()!=null){
                Persona p=new Persona();
                Empleado em=new Empleado();
                p.setNombre(txtNombre.getText());
                p.setApellidopaterno(txtApellidoPaterno.getText());
                p.setApellidomaterno(txtApellidoMaterno.getText());
                p.setDui(ftxDui.getText().replace("-",""));
                p.setNit(ftxNit.getText().replace("-",""));
                p.setFechaNacimiento(LocalDate.parse(ftxFechaN.getText(),dtf));
                p.setTelefono(ftxTelefono.getText().replace("-",""));
                p.setEmail(txtEmail.getText());
                p.setDireccion(txtDireccion.getText());
                em.setDatosPersonales(p);
                em.setIsss(txtSeguro.getText());
                em.setAfp(txtAFP.getText());
                try {
                    emp.insertarEmpleado.accept(em);
                    mostrarEmpleados.accept(tblEmpleado);
                    JOptionPane.showMessageDialog(null,"Empleado Insertado Correctamente");
                    limpiar();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Todos los campos son obligatorios");
            }
        });
        btnEditar.addActionListener(e->{
            listadoModel.forEach(m->{
                if(m.getId().equals(ID)){
                    txtNombre.setText(m.getDatosPersonales().getNombre());
                    txtApellidoPaterno.setText(m.getDatosPersonales().getApellidopaterno());
                    txtApellidoMaterno.setText(m.getDatosPersonales().getApellidomaterno());
                    ftxDui.setText(m.getDatosPersonales().getDui());
                    ftxNit.setText(m.getDatosPersonales().getNit());
                    ftxTelefono.setText(m.getDatosPersonales().getTelefono());
                    ftxFechaN.setText(m.getDatosPersonales().getFechaNacimiento().format(dtf));
                    txtDireccion.setText(m.getDatosPersonales().getDireccion());
                    txtEmail.setText(m.getDatosPersonales().getEmail());
                    txtSeguro.setText(m.getIsss());
                    txtAFP.setText(m.getAfp());
                }
            });

        });
        btnActualizar.addActionListener(e->{
            Empleado em=new Empleado();
            Persona p=new Persona();
            p.setNombre(txtNombre.getText());
            p.setApellidopaterno(txtApellidoPaterno.getText());
            p.setApellidomaterno(txtApellidoMaterno.getText());
            p.setDui(ftxDui.getText().replace("-",""));
            p.setNit(ftxNit.getText().replace("-",""));
            p.setFechaNacimiento(LocalDate.parse(ftxFechaN.getText(),dtf));
            p.setTelefono(ftxTelefono.getText().replace("-",""));
            p.setEmail(txtEmail.getText());
            p.setDireccion(txtDireccion.getText());
            em.setDatosPersonales(p);
            em.setId(ID);
            em.setIsss(txtSeguro.getText());
            em.setAfp(txtAFP.getText());
            try {
                emp.editarEmpleado.accept(em);
                mostrarEmpleados.accept(tblEmpleado);
                JOptionPane.showMessageDialog(null,"Empleado Actualizado Correctamente");
                limpiar();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        btnEliminar.addActionListener(e->{
            if(JOptionPane.showConfirmDialog(null,"¿Desea Eliminar el Empleado?")==JOptionPane.OK_OPTION){
                Empleado em=new Empleado();
                em.setId(ID);
                try {
                    emp.eliminarEmpleado.accept(em);
                    mostrarEmpleados.accept(tblEmpleado);
                    JOptionPane.showMessageDialog(null,"Empleado eliminado");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        btnCancelar.addActionListener(e->{
            if (JOptionPane.showConfirmDialog(null,"¿Desea cancelar la operacion?")==JOptionPane.OK_OPTION){
                limpiar();
            }
        });

        txtBuscar.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

                listadoModel=emp.actualizarDatos.apply(tblEmpleado);
                List<Empleado> busqueda=listadoModel.stream().filter(m->{
                    boolean respuesta=false;
                    if (m.getDatosPersonales().getNombre().contains(txtBuscar.getText())||
                            m.getDatosPersonales().getApellidopaterno().contains(txtBuscar.getText())||
                            m.getDatosPersonales().getApellidomaterno().contains(txtBuscar.getText())||
                            m.getDatosPersonales().getDui().contains(txtBuscar.getText())||
                            m.getDatosPersonales().getNit().contains(txtBuscar.getText())||
                            m.getDatosPersonales().getTelefono().contains(txtBuscar.getText())||
                            m.getDatosPersonales().getDireccion().contains(txtBuscar.getText())||
                            m.getDatosPersonales().getEmail().contains(txtBuscar.getText())||
                            m.getAfp().contains(txtBuscar.getText())||
                            m.getIsss().contains(txtBuscar.getText())
                    ){
                        respuesta=true;
                    }
                    return  respuesta;
                }).collect(Collectors.toList());
                mostrarCoincidencias.accept(tblEmpleado,busqueda);
             }
        });
        txtMayorA.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                try {
                    listadoModel=emp.actualizarDatos.apply(tblEmpleado);
                    List<Empleado> Mayora=listadoModel.stream().filter(m->{
                        boolean respuesta=false;
                        if (m.getDatosPersonales().getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS)>=Integer.valueOf(txtMayorA.getText())){
                            respuesta=true;
                        }
                        return  respuesta;
                    }).collect(Collectors.toList());
                    mostrarCoincidencias.accept(tblEmpleado,Mayora);
                } catch (NumberFormatException e1){

                }
            }
        });
        txtMenorA.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (txtMenorA.getText().length()<1){
                    mostrarCoincidencias.accept(tblEmpleado,listadoModel);
                }else{
                    try {
                        listadoModel=emp.actualizarDatos.apply(tblEmpleado);
                        List<Empleado> Menora=listadoModel.stream().filter(m->{
                            boolean respuesta=false;
                            if (m.getDatosPersonales().getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS)<=Integer.valueOf(txtMenorA.getText())){
                                respuesta=true;

                            }
                            return  respuesta;
                        }).collect(Collectors.toList());
                        mostrarCoincidencias.accept(tblEmpleado,Menora);
                    } catch (NumberFormatException e1){
                    }
                }
            }
        });

        tblEmpleado.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                try {
                    int i=tblEmpleado.getSelectedRow();
                    ID =Long.valueOf(tblEmpleado.getValueAt(i,0).toString());
                }catch (ArrayIndexOutOfBoundsException e1){

                }

            }

        });

        CRUDCliente.FormatearTXT(ftxFechaN, ftxDui, ftxNit, ftxTelefono);
    }
    Consumer<JTable> mostrarEmpleados=(t)->{
        listadoModel=emp.actualizarDatos.apply(t);
        Empleado edadMenor=listadoModel.stream().min((e1, e2)->{
            Long edad1=e1.getDatosPersonales().getFechaNacimiento().until(LocalDate.now(), ChronoUnit.YEARS);
            Long edad2=e2.getDatosPersonales().getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS);
            return edad1.compareTo(edad2);
        }).get();
        lblEdadMenor.setText("Menor: "+edadMenor.getDatosPersonales().getNombre()+" "+edadMenor.getDatosPersonales().getApellidopaterno()+"("+edadMenor.getDatosPersonales().getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS)+" Años)");

        Empleado edadMayor=listadoModel.stream().max((e1, e2)->{
            Long edad1=e1.getDatosPersonales().getFechaNacimiento().until(LocalDate.now(), ChronoUnit.YEARS);
            Long edad2=e2.getDatosPersonales().getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS);
            return edad1.compareTo(edad2);
        }).get();
        lblMayor.setText("Mayor: "+edadMayor.getDatosPersonales().getNombre()+" "+edadMayor.getDatosPersonales().getApellidopaterno()+"("+edadMayor.getDatosPersonales().getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS)+" Años)");

        BigDecimal edadPromedio=listadoModel.stream()
                .map(e->{
                    Long edad=e.getDatosPersonales().getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS);
                   return new BigDecimal(edad);
               })
                .reduce((e1,e2)->(e1.add(e2).divide(new BigDecimal(2)))).get();
        BigDecimal bd=edadPromedio.setScale(2, RoundingMode.HALF_UP);
       lblEdadProm.setText("Edad Promedio: "+bd.setScale(2,RoundingMode.HALF_UP).toString()+" Años");
            emp.actualizarDatos.apply(t);
    };
        BiConsumer<JTable,List<Empleado>> mostrarCoincidencias=(t, l)->{
           emp.mostrarCoincidencias.accept(t,l);
       };
    public void limpiar(){
        txtNombre.setText(null);
        txtApellidoPaterno.setText(null);
        txtApellidoMaterno.setText(null);
        ftxDui.setText(null);
        ftxNit.setText(null);
        ftxTelefono.setText(null);
        ftxFechaN.setText(null);
        txtDireccion.setText(null);
        txtEmail.setText(null);
        txtSeguro.setText(null);
        txtAFP.setText(null);

    }

    public static void main(String[] args) throws SQLException {
        int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        JFrame frm=new JFrame("Administracion de Empleados");
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setContentPane(new CRUDEmpleado().pnlroot);
        frm.setLocationRelativeTo(null);
        frm.pack();
        frm.setVisible(true);
        frm.setBounds(((ancho / 2) - (frm.getWidth()/ 2)*(1)), (alto / 2) - (frm.getHeight() / 2)*(1), frm.getWidth()*(1), frm.getHeight()*(1));

    }
}