/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import java.awt.BorderLayout;

public class VistaJuegoCliente extends javax.swing.JFrame {

    public VistaJuegoCliente(String ip, int puerto) {
        setTitle("Arkanoid Multiplayer");
        setSize(800, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        PanelJuegoMultijugador panel = new PanelJuegoMultijugador();
        add(panel, BorderLayout.CENTER);

        ConexionServidor conexion = new ConexionServidor(ip, puerto, panel);
        panel.setConexion(conexion);
        panel.setIdJugador(1); // Temporal, el servidor debe enviar el real
        conexion.start();

        panel.requestFocusInWindow();
    }
}

