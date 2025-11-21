/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import red.AccionJugador;
import red.EstadoJuego;

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
            out.flush();

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            // Recibir ID del servidor
            int idJugador = in.readInt();
            panel.setIdJugador(idJugador);

            while (true) {
                EstadoJuego estado = (EstadoJuego) in.readObject();
                panel.actualizarEstado(estado);
            }

        } catch (Exception e) {
            System.out.println("Desconectado del servidor");
        }
    }

    public void enviarAccion(red.AccionJugador accion) {
        try {
            if (out != null) {
                out.writeObject(accion);
                out.flush();
            }
        } catch (Exception ignored) {}
    }
}
