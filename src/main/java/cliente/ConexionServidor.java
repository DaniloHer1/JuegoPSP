/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cliente;

/**
 *
 * @author Diurno
 */

import red.*;
import cliente.PanelJuegoMultijugador;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConexionServidor extends Thread {

    private String ip;
    private int puerto;
    private PanelJuegoMultijugador panel;

    private ObjectOutputStream out;

    public ConexionServidor(String ip, int puerto, PanelJuegoMultijugador panel) {
        this.ip = ip;
        this.puerto = puerto;
        this.panel = panel;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket(ip, puerto)) {

            out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Thread para recibir estados constantemente
            while (true) {
                EstadoJuego estado = (EstadoJuego) in.readObject();
                panel.actualizarEstado(estado);
            }

        } catch (Exception e) {
            System.out.println("Desconectado del servidor");
        }
    }

    public void enviar(AccionJugador accion) {
        try {
            out.writeObject(accion);
        } catch (Exception ignored) {}
    }
}
