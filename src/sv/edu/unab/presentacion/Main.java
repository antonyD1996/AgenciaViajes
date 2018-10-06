package sv.edu.unab.presentacion;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Menu menu=new Menu();
        menu.setLocationRelativeTo(null);
        menu.setTitle("Agencia de Viajes");
        menu.setResizable(true);
        menu.setExtendedState(JFrame.MAXIMIZED_BOTH);
        menu.setLayout(null);
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menu.setVisible(true);
    }
}
