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
    private Socket socket;
    private boolean conectado = true;

    public ConexionServidor(String ip, int puerto, PanelJuegoMultijugador panel) {
        
        this.ip = ip;
        this.puerto = puerto;
        this.panel = panel;
    }

    @Override
    public void run() {
        try {
            System.out.println("Intentando conectar a " + ip + ":" + puerto);
            socket = new Socket(ip, puerto);
            System.out.println("Conectado al servidor");

            // Crear streams en el orden correcto
            out = new ObjectOutputStream(socket.getOutputStream());
            out.flush(); // IMPORTANTE: Flush después de crear
            System.out.println("ObjectOutputStream creado");

            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            System.out.println("ObjectInputStream creado");

            // Recibir ID del jugador del servidor
            int idJugador = in.readInt();
            panel.setIdJugador(idJugador);
            System.out.println("ID recibido del servidor: " + idJugador);

            // Recibir estados constantemente
            while (conectado) {
                EstadoJuego estado = (EstadoJuego) in.readObject();
                panel.actualizarEstado(estado);
            }

        } catch (Exception e) {
            System.err.println("Error en conexión: " + e.getMessage());
            e.printStackTrace();
        } finally {
            desconectar();
        }
    }

    public void enviarAccion(AccionJugador accion) {
        try {
            if (out != null && conectado) {
                out.writeObject(accion);
                out.flush();
            }
        } catch (Exception e) {
            System.err.println("Error enviando acción: " + e.getMessage());
        }
    }

    public void desconectar() {
        conectado = false;
        try {
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
            System.out.println("Desconectado del servidor");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}