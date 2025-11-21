/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cliente;

/**
 *
 * @author Diurno
 */

import javax.swing.*;
import java.awt.*;

public class VistaJuegoCliente extends JFrame {

    public VistaJuegoCliente(String ip, int puerto) {
        setTitle("Arkanoid Multiplayer");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        PanelJuegoMultijugador panel = new PanelJuegoMultijugador();
        add(panel, BorderLayout.CENTER);

        ConexionServidor conexion = new ConexionServidor(ip, puerto, panel);
        conexion.start();

        addKeyListener(panel);
    }
}
