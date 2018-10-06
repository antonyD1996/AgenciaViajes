package sv.edu.unab.presentacion;

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
        setContentPane(dpnEscritorio);

        //eventos del frm
        menuArchivoClientes.addActionListener(e->{
            CRUDCliente cl=new CRUDCliente();
            cl.setResizable(true);
            cl.setContentPane(cl.pnlroot);
            dpnEscritorio.add(cl);
            cl.show();
        });
        menuArchivoEmpleados.addActionListener(e->{
            CRUDEmpleado ce=new CRUDEmpleado();
            ce.setResizable(true);
            ce.setContentPane(ce.pnlroot);
            dpnEscritorio.add(ce);
            ce.show();
        });
        menuSalir.addActionListener(e->{
            System.exit(0);
        });
    }
}
