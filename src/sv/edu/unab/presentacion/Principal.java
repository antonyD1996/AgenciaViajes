package sv.edu.unab.presentacion;

import javax.swing.*;
import java.sql.SQLException;

public class Principal {
    private JPanel pnlCargar;
    private JButton btnEmpleados;
    private JButton btnClientes;
    private JPanel panel1;
    CRUDEmpleado c;

    public Principal() {
        btnEmpleados.addActionListener(e->{
            int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
            int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
            JFrame frm=new JFrame("Administracion de Empleados");
            //frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            try {
                frm.setContentPane(new CRUDEmpleado().pnlroot);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            frm.setLocationRelativeTo(null);
            frm.pack();
            frm.setVisible(true);
            frm.setBounds(((ancho / 2) - (frm.getWidth()/ 2)*(2)), (alto / 2) - (frm.getHeight() / 2)*(1), frm.getWidth()*(2), frm.getHeight()*(3/2));

        });
        btnClientes.addActionListener(e->{
            int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
            int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
            JFrame frm=new JFrame("Administracion de Clientes");
            //frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frm.setContentPane(new CRUDCliente().pnlroot);
            frm.setLocationRelativeTo(null);
            frm.pack();
            frm.setVisible(true);
            frm.setBounds(((ancho / 2) - (frm.getWidth()/ 2)*(2)), (alto / 2) - (frm.getHeight() / 2)*(1), frm.getWidth()*(2), frm.getHeight()*(3/2));

        });
    }

    public static void main(String[] args) {
        int ancho = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
        int alto = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;

        JFrame frm=new JFrame("Administracion de Clientes");
        frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frm.setContentPane(new Principal().pnlCargar);
        frm.setLocationRelativeTo(null);
        frm.pack();
        frm.setVisible(true);
        int a=frm.getWidth()*3/2;
        int b=frm.getHeight()*2;
        frm.setBounds(((ancho / 2) - (a/ 2)), (alto / 2) - (b / 2), a, b);

    }

}
