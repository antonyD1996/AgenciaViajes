package sv.edu.unab.presentacion;

import javax.swing.*;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import sv.edu.unab.dominio.Cliente;
import sv.edu.unab.dominio.Empleado;
import sv.edu.unab.dominio.Persona;
import sv.edu.unab.infraestructura.*;

public class CRUDCliente extends JInternalFrame{
    public JPanel pnldatos;
    private JTextField txtApellidoPaterno;
    private JTextField txtApellidoMaterno;
    public JFormattedTextField ftxDui;
    public JFormattedTextField ftxNit;
    private JTextField txtNombre;
    public JFormattedTextField ftxTelefono;
    private JTextArea txtDireccion;
    public JFormattedTextField ftxFechaN;
    private JTextField txtEmail;
    private JTextField txtBuscar;
    private JButton btnAgregar;
    private JButton btnActualizar;
    private JButton btnEditar;
    private JButton btnEliminar;
    private JButton btnCancelar;
    private JTable tblCliente;
    public JPanel pnlroot;
    private JLabel lblEdadMenor;
    private JLabel lblMayor;
    private JLabel lblEdadProm;
    private JTextField txtMayorA;
    private JTextField txtMenorA;
    public JInternalFrame internalFrame;

    List<Cliente> listadoModel;
    long ID;
    Clientei cn=new Clientei();
    Long idPersona;
    DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CRUDCliente() {
        internalFrame=new JInternalFrame();

        initcomponentes();
        setClosable(true);
        setIconifiable(true);
        setResizable(false);
        setTitle("Clientes");
        setLayout(null);
        setSize(980,800);
    }
    public void initcomponentes(){

        mostrarClientes.accept(tblCliente);
        tblCliente.setFillsViewportHeight(true);
        if (listadoModel==null){
            listadoModel=new ArrayList<>();
        }
        btnAgregar.addActionListener(e->{
            if (txtNombre.getText()!=null){
                Cliente c=new Cliente();
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
                c.setDatosPersonales(p);
                try {
                    cn.insertarCliente.accept(c);
                    mostrarClientes.accept(tblCliente);
                    JOptionPane.showMessageDialog(null,"Cliente Insertado Correctamente");
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
                    idPersona=Long.valueOf(m.getDatosPersonales().getId());
                    txtNombre.setText(m.getDatosPersonales().getNombre());
                    txtApellidoPaterno.setText(m.getDatosPersonales().getApellidopaterno());
                    txtApellidoMaterno.setText(m.getDatosPersonales().getApellidomaterno());
                    ftxDui.setText(m.getDatosPersonales().getDui());
                    ftxNit.setText(m.getDatosPersonales().getNit());
                    ftxTelefono.setText(m.getDatosPersonales().getTelefono());
                    ftxFechaN.setText(m.getDatosPersonales().getFechaNacimiento().format(dtf));
                    txtDireccion.setText(m.getDatosPersonales().getDireccion());
                    txtEmail.setText(m.getDatosPersonales().getEmail());
                }

            });

        });
        btnActualizar.addActionListener(e->{
            Cliente c=new Cliente();
            Persona p=new Persona();
            p.setId(idPersona);
            p.setNombre(txtNombre.getText());
            p.setApellidopaterno(txtApellidoPaterno.getText());
            p.setApellidomaterno(txtApellidoMaterno.getText());
            p.setDui(ftxDui.getText().replace("-",""));
            p.setNit(ftxNit.getText().replace("-",""));
            p.setFechaNacimiento(LocalDate.parse(ftxFechaN.getText(),dtf));
            p.setTelefono(ftxTelefono.getText().replace("-",""));
            p.setEmail(txtEmail.getText());
            p.setDireccion(txtDireccion.getText());
            c.setDatosPersonales(p);
            c.setId(ID);
            try {
                cn.editarCliente.accept(c);
                mostrarClientes.accept(tblCliente);
                JOptionPane.showMessageDialog(null,"Cliente Actualizado Correctamente");
                limpiar();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        btnEliminar.addActionListener(e->{
            if(JOptionPane.showConfirmDialog(null,"¿Desea Eliminar el Cliente?")==JOptionPane.OK_OPTION){
                Cliente c=new Cliente();
                c.setId(ID);
                try {
                    cn.eliminarCliente.accept(c);
                    mostrarClientes.accept(tblCliente);
                    JOptionPane.showMessageDialog(null,"Cliente eliminado");
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
        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                listadoModel=cn.actualizarDatos.apply(tblCliente);
                List<Cliente> busqueda=listadoModel.stream().filter(m->{
                    boolean respuesta=false;
                    if (m.getDatosPersonales().getNombre().contains(txtBuscar.getText())||
                            m.getDatosPersonales().getApellidopaterno().contains(txtBuscar.getText())||
                            m.getDatosPersonales().getApellidomaterno().contains(txtBuscar.getText())||
                            m.getDatosPersonales().getDui().contains(txtBuscar.getText())||
                            m.getDatosPersonales().getNit().contains(txtBuscar.getText())||
                            m.getDatosPersonales().getTelefono().contains(txtBuscar.getText())||
                            m.getDatosPersonales().getDireccion().contains(txtBuscar.getText())||
                            m.getDatosPersonales().getEmail().contains(txtBuscar.getText())
                    ){
                        respuesta=true;
                    }
                    return  respuesta;
                }).collect(Collectors.toList());
                mostrarCoincidencias.accept(tblCliente,busqueda);
            }
        });
        txtMayorA.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                try {
                    listadoModel=cn.actualizarDatos.apply(tblCliente);
                    List<Cliente> Mayora=listadoModel.stream().filter(m->{
                        boolean respuesta=false;
                        if (m.getDatosPersonales().getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS)>=Integer.valueOf(txtMayorA.getText())){
                            respuesta=true;
                        }
                        return  respuesta;
                    }).collect(Collectors.toList());
                    mostrarCoincidencias.accept(tblCliente,Mayora);
                } catch (NumberFormatException e1){

                }
            }
        });
        txtMenorA.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if (txtMenorA.getText().length()<1){
                    mostrarCoincidencias.accept(tblCliente,listadoModel);
                }else{
                    try {
                        listadoModel=cn.actualizarDatos.apply(tblCliente);
                        List<Cliente> Menora=listadoModel.stream().filter(m->{
                            boolean respuesta=false;
                            if (m.getDatosPersonales().getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS)<=Integer.valueOf(txtMenorA.getText())){
                                respuesta=true;

                            }
                            return  respuesta;
                        }).collect(Collectors.toList());
                        mostrarCoincidencias.accept(tblCliente,Menora);
                    } catch (NumberFormatException e1){
                    }
                }
            }
        });
        tblCliente.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                try {
                    int i= tblCliente.getSelectedRow();
                    ID =Long.valueOf(tblCliente.getValueAt(i,0).toString());
                }catch(ArrayIndexOutOfBoundsException e1){
                }
            }
        });
        FormatearTXT(ftxFechaN, ftxDui, ftxNit, ftxTelefono);

    }
    static void FormatearTXT(JFormattedTextField ftxFechaN, JFormattedTextField ftxDui, JFormattedTextField ftxNit, JFormattedTextField ftxTelefono) {
        try{
            MaskFormatter mascara=new MaskFormatter("##/##/####");
            mascara.setPlaceholderCharacter('_');
            MaskFormatter mascara1=new MaskFormatter("########-#");
            mascara1.setPlaceholderCharacter(' ');
            MaskFormatter mascara2=new MaskFormatter("####-######-###-#");
            mascara.setPlaceholderCharacter(' ');
            MaskFormatter mascara3=new MaskFormatter("####-####");
            mascara3.setPlaceholderCharacter(' ');
            ftxFechaN.setFormatterFactory(new DefaultFormatterFactory(mascara));
            ftxDui.setFormatterFactory(new DefaultFormatterFactory(mascara1));
            ftxNit.setFormatterFactory(new DefaultFormatterFactory(mascara2));
            ftxTelefono.setFormatterFactory(new DefaultFormatterFactory(mascara3));
        }catch(ParseException e){
            e.printStackTrace();
        }
    }
    Consumer<JTable> mostrarClientes=(t)->{
            listadoModel=cn.actualizarDatos.apply(t);
            Cliente edadMenor=listadoModel.stream().min((e1, e2)->{
                Long edad1=e1.getDatosPersonales().getFechaNacimiento().until(LocalDate.now(), ChronoUnit.YEARS);
                Long edad2=e2.getDatosPersonales().getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS);
                return edad1.compareTo(edad2);
            }).get();
            lblEdadMenor.setText("Menor: "+edadMenor.getDatosPersonales().getNombre()+" "+edadMenor.getDatosPersonales().getApellidopaterno()+"("+edadMenor.getDatosPersonales().getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS)+" Años)");

            Cliente edadMayor=listadoModel.stream().max((e1, e2)->{
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

            cn.actualizarDatos.apply(t);
    };
    BiConsumer<JTable,List<Cliente>> mostrarCoincidencias=(t, l)->{
            cn.mostrarCoincidencias.accept(t,l);
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
    }
    public static void main(String[] args) {
        int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        JFrame frm=new JFrame("Administracion de Clientes");
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setContentPane(new CRUDCliente().pnlroot);
        frm.setLocationRelativeTo(null);
        frm.pack();
        frm.setVisible(true);
        frm.setBounds(((ancho / 2) - (frm.getWidth()/ 2)*(1)), (alto / 2) - (frm.getHeight() / 2)*(1), frm.getWidth()*(1), frm.getHeight()*(1));
    }
}
