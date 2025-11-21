/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import red.AccionJugador;
import red.EstadoJuego;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PanelJuegoMultijugador extends JPanel implements KeyListener {

    private ConexionServidor conexion;
    private EstadoJuego estadoActual;
    private int idJugador; // 1 o 2

    private int direccion = 0; // -1 izquierda, 1 derecha, 0 nada

    public PanelJuegoMultijugador() {
        this.estadoActual = new EstadoJuego();

        setPreferredSize(new Dimension(600, 700));
        setBackground(Color.BLACK);

        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(this);
        
        Timer repaintTimer = new Timer(16, e -> repaint());
        repaintTimer.start();
    }

    public void setConexion(ConexionServidor conexion) {
        this.conexion = conexion;
    }

    public void setIdJugador(int id) {
        this.idJugador = id;
    }

    public synchronized void actualizarEstado(EstadoJuego estado) {
        this.estadoActual = estado;
        repaint();
    }

    private void enviarMovimiento() {
        if (conexion == null) {
            return;
        }

        AccionJugador.TipoAccion accion;
        if (direccion == -1) {
            accion = AccionJugador.TipoAccion.MOVER_IZQUIERDA;
        } else if (direccion == 1) {
            accion = AccionJugador.TipoAccion.MOVER_DERECHA;
        } else {
            accion = AccionJugador.TipoAccion.NADA;
        }

        conexion.enviarAccion(new AccionJugador(accion, idJugador));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (estadoActual == null) {
            return;
        }

        // Pelota
        g.setColor(estadoActual.getPelotaColor());
        g.fillOval((int) estadoActual.getPelotaX(),
                (int) estadoActual.getPelotaY(),
                (int) estadoActual.getPelotaTamaño(),
                (int) estadoActual.getPelotaTamaño());

        // Bases
        if (estadoActual.getBaseJugador1() != null) {
            g.setColor(estadoActual.getBaseJugador1().color);
            g.fillRect((int) estadoActual.getBaseJugador1().x,
                    (int) estadoActual.getBaseJugador1().y,
                    (int) estadoActual.getBaseJugador1().ancho,
                    (int) estadoActual.getBaseJugador1().alto);
        }
        if (estadoActual.getBasejugador2() != null) {
            g.setColor(estadoActual.getBasejugador2().color);
            g.fillRect((int) estadoActual.getBasejugador2().x,
                    (int) estadoActual.getBasejugador2().y,
                    (int) estadoActual.getBasejugador2().ancho,
                    (int) estadoActual.getBasejugador2().alto);
        }

        // Bloques
        if (estadoActual.getBloques() != null) {
            for (EstadoJuego.BloqueData b : estadoActual.getBloques()) {
                if (b.estado == modelo.Estado.INTACTO) {
                    g.setColor(b.color);
                    g.fillRect((int) b.x, (int) b.y, (int) b.ancho, (int) b.alto);
                    g.setColor(Color.BLACK);
                    g.drawRect((int) b.x, (int) b.y, (int) b.ancho, (int) b.alto);
                }
            }
        }

        // Puntos
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("J1: " + estadoActual.getPuntosJugador1(), 20, 20);
        g.drawString("J2: " + estadoActual.getPuntosJugador2(), 520, 20);

        // Fin de juego
        if (estadoActual.isJuegoTerminado()) {
            g.setFont(new Font("Arial", Font.BOLD, 30));
            g.drawString("FIN DEL JUEGO", 200, 350);
            g.drawString(estadoActual.getGanador(), 200, 400);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            direccion = -1;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            direccion = 1;
        }
        enviarMovimiento();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT) {
            direccion = 0;
            enviarMovimiento();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
