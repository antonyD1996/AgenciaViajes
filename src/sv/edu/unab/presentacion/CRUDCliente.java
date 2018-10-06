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
import sv.edu.unab.infraestructura.*;
import javax.swing.table.TableColumn;

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
    String Nombre;
    String ApellidoPaterno;
    String ApellidoMaterno;
    String DUI;
    String NIT;
    LocalDate FechaN;
    String Telefono;
    String Direccion;
    String Email;
    Clientei cn=new Clientei();
    DateTimeFormatter dtf=DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public CRUDCliente() {
        internalFrame=new JInternalFrame();

        initcomponentes();
        setClosable(true);
        setIconifiable(true);
        setResizable(false);
        setTitle("Clientes");
        setLayout(null);
        setSize(800,700);

//        txtNombre.setText("Antony David");
//        txtApellidoPaterno.setText("Duarte");
//        txtApellidoMaterno.setText("Perlera");
//        ftxDui.setText("123456789");
//        ftxNit.setText("12340511961231");
//        ftxTelefono.setText("70079032");
//        ftxFechaN.setText("05111996");
//        txtDireccion.setText("Potrero Sula");
//        txtEmail.setText("antony@gmail.com");

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
                c.setNombre(txtNombre.getText());
                c.setApellidopaterno(txtApellidoPaterno.getText());
                c.setApellidomaterno(txtApellidoMaterno.getText());
                c.setDui(ftxDui.getText().replace("-",""));
                c.setNit(ftxNit.getText().replace("-",""));
                c.setFechaNacimiento(LocalDate.parse(ftxFechaN.getText(),dtf));
                c.setTelefono(ftxTelefono.getText().replace("-",""));
                c.setEmail(txtEmail.getText());
                c.setDireccion(txtDireccion.getText());
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
            txtNombre.setText(Nombre);
            txtApellidoPaterno.setText(ApellidoPaterno);
            txtApellidoMaterno.setText(ApellidoMaterno);
            ftxDui.setText(DUI);
            ftxNit.setText(NIT);
            ftxTelefono.setText(Telefono);
            ftxFechaN.setText(FechaN.format(dtf));
            txtDireccion.setText(Direccion);
            txtEmail.setText(Email);
        });
        btnActualizar.addActionListener(e->{
            Cliente c=new Cliente();
            c.setId(ID);
            c.setNombre(txtNombre.getText());
            c.setApellidopaterno(txtApellidoPaterno.getText());
            c.setApellidomaterno(txtApellidoMaterno.getText());
            c.setDui(ftxDui.getText().replace("-",""));
            c.setNit(ftxNit.getText().replace("-",""));
            c.setFechaNacimiento(LocalDate.parse(ftxFechaN.getText(),dtf));
            c.setTelefono(ftxTelefono.getText().replace("-",""));
            c.setEmail(txtEmail.getText());
            c.setDireccion(txtDireccion.getText());
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
                    if (m.getNombre().contains(txtBuscar.getText())||
                            m.getApellidopaterno().contains(txtBuscar.getText())||
                            m.getApellidomaterno().contains(txtBuscar.getText())||
                            m.getDui().contains(txtBuscar.getText())||
                            m.getNit().contains(txtBuscar.getText())||
                            m.getTelefono().contains(txtBuscar.getText())||
                            m.getDireccion().contains(txtBuscar.getText())||
                            m.getEmail().contains(txtBuscar.getText())
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
                        if (m.getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS)>=Integer.valueOf(txtMayorA.getText())){
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
                            if (m.getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS)<=Integer.valueOf(txtMenorA.getText())){
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
                    Nombre=(tblCliente.getValueAt(i,1).toString());
                    ApellidoPaterno=(tblCliente.getValueAt(i,2).toString());
                    ApellidoMaterno=(tblCliente.getValueAt(i,3).toString());
                    DUI=(tblCliente.getValueAt(i,4).toString());
                    NIT=(tblCliente.getValueAt(i,5).toString());
                    FechaN=LocalDate.parse(tblCliente.getValueAt(i,6).toString(),dtf);
                    Telefono=(tblCliente.getValueAt(i,8).toString());
                    Direccion=(tblCliente.getValueAt(i,9).toString());
                    Email=(tblCliente.getValueAt(i,10).toString());
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
            Cliente edadMenor=listadoModel.stream().min((e1,e2)->{
                Long edad1=e1.getFechaNacimiento().until(LocalDate.now(), ChronoUnit.YEARS);
                Long edad2=e2.getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS);
                return edad1.compareTo(edad2);
            }).get();
            lblEdadMenor.setText("Menor: "+edadMenor.getNombre()+" "+edadMenor.getApellidopaterno()+"("+edadMenor.getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS)+" Años)");

            Cliente edadMayor=listadoModel.stream().max((e1,e2)->{
                Long edad1=e1.getFechaNacimiento().until(LocalDate.now(), ChronoUnit.YEARS);
                Long edad2=e2.getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS);
                return edad1.compareTo(edad2);
            }).get();
            lblMayor.setText("Mayor: "+edadMayor.getNombre()+" "+edadMayor.getApellidopaterno()+"("+edadMayor.getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS)+" Años)");

            BigDecimal edadPromedio=listadoModel.stream()
                    .map(e->{
                        Long edad=e.getFechaNacimiento().until(LocalDate.now(),ChronoUnit.YEARS);
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
