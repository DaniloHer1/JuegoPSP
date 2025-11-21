/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.BorderLayout;
import red.Constantes;

public class VistaJuegoCliente extends javax.swing.JFrame {

    public VistaJuegoCliente(String ip, int puerto) {
        setTitle("Arkanoid Multiplayer");
        setSize(Constantes.ANCHO_VENTANA, Constantes.ALTO_VENTANA);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        PanelJuegoMultijugador panel = new PanelJuegoMultijugador();
        add(panel, BorderLayout.CENTER);

        ConexionServidor conexion = new ConexionServidor(ip, puerto, panel);
        panel.setConexion(conexion);
        conexion.start();

        panel.requestFocusInWindow();
    }
}

