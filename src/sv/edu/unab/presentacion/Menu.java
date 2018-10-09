package sv.edu.unab.presentacion;

import org.eclipse.persistence.exceptions.PersistenceUnitLoadingException;
import org.omg.PortableServer.ForwardRequest;
import sv.edu.unab.dominio.Persona;
import sv.edu.unab.dominio.Cliente;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.*;
import java.awt.*;

public class Menu extends  JFrame{
    private JMenuBar menuAgencia;
    private JMenu menuArchivo;
    private JMenu menuAyuda;
    private JMenu menuAcerca;
    private JMenuItem menuArchivoClientes;
    private JMenuItem menuArchivoEmpleados;
    private JMenuItem menuSalir;
    private JSeparator menuArchivoSparador;
    private JSeparator menuArchivoSparador1;
    private JSeparator menuArchivoSparador2;
    public JDesktopPane dpnEscritorio;
    private Image imagen;

    public Menu() throws HeadlessException {

        //creacion de iconos
        Icon iconArchivo=new ImageIcon(getClass().getResource("/sv/edu/unab/imagenes/Archivo.png"));
        Icon iconClientes=new ImageIcon(getClass().getResource("/sv/edu/unab/imagenes/Clientes.png"));
        Icon iconEmpleados=new ImageIcon(getClass().getResource("/sv/edu/unab/imagenes/Empleados.png"));
        Icon iconSalir=new ImageIcon(getClass().getResource("/sv/edu/unab/imagenes/Salir.png"));
        Icon iconAyuda=new ImageIcon(getClass().getResource("/sv/edu/unab/imagenes/Ayuda.png"));
        Icon iconAcercaDe=new ImageIcon(getClass().getResource("/sv/edu/unab/imagenes/AcercaDE.png"));
        Icon iconClientesF=new ImageIcon(getClass().getResource("/sv/edu/unab/imagenes/Clientes1.png"));
        Icon iconEmpleadoF=new ImageIcon(getClass().getResource("/sv/edu/unab/imagenes/Empleados1.png"));

        //creacion de objetos
        menuAgencia=new JMenuBar();
        menuArchivo=new JMenu("Archivo");
        menuAyuda=new JMenu("Ayuda");
        menuAcerca=new JMenu("Acerca De");
        menuArchivoClientes=new JMenuItem("Clientes");
        menuArchivoEmpleados=new JMenuItem("Empleados");
        menuArchivoSparador=new JSeparator();
        menuSalir=new JMenuItem("Salir");
        dpnEscritorio=new JDesktopPane();
        dpnEscritorio.setBorder(new ImagenFondo());

        //fondo para desktopPane


        //asignacion de iconos
        menuArchivo.setIcon(iconArchivo);
        menuArchivoClientes.setIcon(iconClientes);
        menuArchivoEmpleados.setIcon(iconEmpleados);
        menuSalir.setIcon(iconSalir);
        menuAyuda.setIcon(iconAyuda);
        menuAcerca.setIcon(iconAcercaDe);

        //armado de menus
        menuArchivo.add(menuArchivoClientes);
        menuArchivo.add(menuArchivoEmpleados);
        menuArchivo.add(menuArchivoSparador);
        menuArchivo.add(menuSalir);
        menuAgencia.add(menuArchivo);
        menuAgencia.add(menuAyuda);
        menuAgencia.add(menuAcerca);

        //mas propiedades del formulario
        setJMenuBar(menuAgencia);
        menuAgencia.setBorderPainted(true);
        menuAgencia.setBackground(Color.white);
        menuArchivo.setBackground(Color.white);
        menuArchivoEmpleados.setBackground(Color.white);
        menuArchivoClientes.setBackground(Color.white);
        menuSalir.setBackground(Color.white);
        setContentPane(dpnEscritorio);

        //eventos del frm
        menuArchivoClientes.addActionListener(e->{
            CRUDCliente cl=new CRUDCliente();
            cl.setResizable(true);
            cl.setContentPane(cl.pnlroot);
            cl.setFrameIcon(iconClientesF);
            dpnEscritorio.add(cl);
            centrar(dpnEscritorio,cl);
            cl.show();
        });
        menuArchivoEmpleados.addActionListener(e->{
            CRUDEmpleado ce=new CRUDEmpleado();
            ce.setResizable(true);
            ce.setContentPane(ce.pnlroot);
            ce.setFrameIcon(iconEmpleadoF);
            centrar(dpnEscritorio,ce);
            dpnEscritorio.add(ce);
            ce.show();
        });
        menuSalir.addActionListener(e->{

            if(JOptionPane.showConfirmDialog(null,"¿Desea Salir del Sistema?")==JOptionPane.OK_OPTION){
                System.exit(0);
            }
        });


    }
    public void centrar(JDesktopPane dpn,JInternalFrame jf){
        jf.setLocation((dpn.getWidth()-jf.getWidth())/2,(dpn.getHeight()-jf.getHeight())/2);
    }
    public static void main(String[] args) {
        Menu menu=new Menu();
        menu.setLocationRelativeTo(null);
        menu.setTitle("Agencia de Viajes");
        menu.setResizable(true);
        menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
        menu.setLayout(null);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setVisible(true);

//            EntityManagerFactory emf= Persistence.createEntityManagerFactory("AgenciaVueloPU");
//            EntityManager em=emf.createEntityManager();
//            Query query=em.createNamedQuery("Persona.findAll");
//            List<Persona> resultado=query.getResultList();
//            resultado.forEach(p->{
//                System.out.println("Persona: "+p);
//            });
//        EntityManagerFactory emf= Persistence.createEntityManagerFactory("AgenciaVueloPU");
//        EntityManager em=emf.createEntityManager();
//        Query query=em.createNamedQuery("Cliente.findAll");
//        List<Cliente> resultado=query.getResultList();
//        resultado.forEach(c->{
//            System.out.println("Cliente: "+c);
//        });


    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
