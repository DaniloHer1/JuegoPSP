/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import red.AccionJugador;
import red.Constantes;
import red.EstadoJuego;

/**
 *
 * @author Diurno
 */
public class ManejadorCliente extends Thread {

    private Socket socket;
    private int idJugador;
    private ServidorArkanoid servidor;
    private LogicaJuego logicaJuego;

    private ObjectOutputStream salida;
    private ObjectInputStream entrada;

    private boolean conectado;

    public ManejadorCliente(Socket socket, int idJugador, ServidorArkanoid servidor, LogicaJuego logicaJuego) {
        this.socket = socket;
        this.idJugador = idJugador;
        this.servidor = servidor;
        this.logicaJuego = logicaJuego;
        this.conectado = true;
    }

    @Override
    public void run() {
        try {
            configurarStreams();

            enviarIdJugador();

            servidor.agregarLog("Jugador " + idJugador + " configurado correctamente");
            while (conectado) {
                recibirAccion();
                enviarEstadoJuego();

                Thread.sleep(Constantes.DELAY_FRAME);
            }
        } catch (Exception e) {
        }
    }

    private void configurarStreams() throws IOException {

        salida = new ObjectOutputStream(socket.getOutputStream());
        salida.flush();

        entrada = new ObjectInputStream(socket.getInputStream());

    }

    private void enviarIdJugador() throws IOException {
        salida.writeInt(idJugador);
        salida.flush();
        servidor.agregarLog("→ ID " + idJugador + " enviado al cliente");
    }
    
    /**
     * Recibe una acción del cliente
     */
    private void recibirAccion() throws IOException, ClassNotFoundException {
        
        if (entrada.available() > 0) {
            AccionJugador accion = (AccionJugador) entrada.readObject();
            
            // Procesar la acción en la lógica del juego
            logicaJuego.procesarAccionJugador(accion);
        }
    }

    private void enviarEstadoJuego() throws IOException {
        EstadoJuego estado = logicaJuego.generarEstadoJuego();

        salida.reset();
        salida.writeObject(estado);
        salida.flush();
    }

    public void desconectar() {
        conectado = false;

        try {
            if (entrada != null) {
                entrada.close();
            }
            if (salida != null) {
                salida.close();
            }
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }

            servidor.agregarLog("Jugador " + idJugador + " desconectado completamente");

        } catch (IOException e) {
            servidor.agregarLog("Error al cerrar recursos");
        }
    }

    public boolean isConectado() {
        return conectado && !socket.isClosed();
    }
    
     public int getIdJugador() {
        return idJugador;
    }
     
      public String getInfoCliente() {
        return "• Jugador " + idJugador + " - " + 
               socket.getInetAddress().getHostAddress() + ":" + 
               socket.getPort();
    }
}
