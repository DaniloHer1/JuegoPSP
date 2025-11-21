/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servidor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Timer;
import modelo.Base;
import modelo.Bloques;
import modelo.Estado;
import modelo.Pelota;
import red.AccionJugador;
import red.Constantes;
import red.EstadoJuego;

/**
 *
 * @author Diurno
 */
public class LogicaJuego {

    private Pelota pelota;
    private Base baseJugador1;
    private Base baseJugador2;
    private List<Bloques> listaBloques;

    private Timer timer;
    private boolean juegoActivo;
    private boolean juegoTerminado;

    private int puntosJugador1;
    private int puntosJugador2;

    private ServidorArkanoid servidor;

    public LogicaJuego(ServidorArkanoid servidor) {
        this.servidor = servidor;
        this.listaBloques = new ArrayList<>();
        this.juegoActivo = false;
        this.juegoTerminado = false;
        this.puntosJugador1 = 0;
        this.puntosJugador2 = 0;

        inicializarJuego();

    }

    private void inicializarJuego() {
        servidor.agregarLog("Inicializando elementos");

        pelota = new Pelota(Constantes.VELOCIDAD_PELOTA_X,
                Constantes.VELOCIDAD_PELOTA_Y,
                Constantes.POSICION_INICIAL_PELOTA_X,
                Constantes.POSICION_INICIAL_PELOTA_Y,
                Constantes.TAMANO_PELOTA,
                Color.YELLOW);

        baseJugador1 = new Base(
                Constantes.ALTO_BASE,
                Constantes.ANCHO_BASE,
                Constantes.BASE_INICIAL_X,
                Constantes.BASE_J1_Y,
                Constantes.VELOCIDAD_BASE,
                0,
                Constantes.ANCHO_VENTANA
        );

        baseJugador2 = new Base(
                Constantes.ALTO_BASE,
                Constantes.ANCHO_BASE,
                Constantes.BASE_INICIAL_X,
                Constantes.BASE_J2_Y,
                Constantes.VELOCIDAD_BASE,
                0,
                Constantes.ANCHO_VENTANA
        );

        crearBloques();
        servidor.agregarLog("Pelota creada");
        servidor.agregarLog("Bases de jugadores creadas");
        servidor.agregarLog(listaBloques.size() + " bloques creados");
    }

    private void crearBloques() {
        listaBloques.clear();

        for (int i = 0; i < Constantes.BLOQUES_FILAS; i++) {

            for (int j = 0; j < Constantes.BLOQUES_COLUMNAS; j++) {

                double x = Constantes.BLOQUE_MARGEN_IZQUIERDO
                        + j * (Constantes.BLOQUE_ANCHO + Constantes.BLOQUE_ESPACIADO);
                double y = Constantes.BLOQUE_MARGEN_SUPERIOR
                        + i * (Constantes.BLOQUE_ALTO + Constantes.BLOQUE_ESPACIADO);

                // Colores alternados
                Color color;
                switch (i) {
                    case 0:
                        color = Color.RED;
                        break;
                    case 1:
                        color = Color.ORANGE;
                        break;
                    case 2:
                        color = Color.YELLOW;
                        break;
                    case 3:
                        color = Color.GREEN;
                        break;
                    case 4:
                        color = Color.CYAN;
                        break;
                    default:
                        color = Color.MAGENTA;
                }

                Bloques bloque = new Bloques(
                        Constantes.BLOQUE_ANCHO,
                        Constantes.BLOQUE_ALTO,
                        color,
                        x, y,
                        Estado.INTACTO
                );

                listaBloques.add(bloque);
            }
        }

    }

    public void iniciarTimer() {
        if (timer != null && timer.isRunning()) {
            return;

        }
        juegoActivo = true;
        timer = new Timer(Constantes.DELAY_FRAME, e -> {
            actualizarJuego();
        });
    }

    public void detenerTimer() {
        if (timer != null) {
            timer.stop();
            juegoActivo = false;

        }
    }

    private void actualizarJuego() {
        if (!juegoActivo || juegoTerminado) {
            return;
        }

        pelota.mover();

        rebotarParedes();

        pelota.rebotarBase(baseJugador1);
        pelota.rebotarBase(baseJugador2);
        detectarColisionBloques();

        detectarPuntos();

        verificarFinDeJuego();
    }

    private void rebotarParedes() {

        double posX = pelota.getPosicionX();
        double posY = pelota.getPosicionY();
        double tamaño = pelota.getTamaño();

        if (posX <= 0) {
            pelota.setVelocidadX(-pelota.getVelocidadX());
            pelota.setPosicionX(0);
        }

        // Rebote derecha
        if (posX + tamaño >= Constantes.ANCHO_VENTANA) {
            pelota.setVelocidadX(-pelota.getVelocidadX());
            pelota.setPosicionX(Constantes.ANCHO_VENTANA - tamaño);
        }

    }

    private void detectarColisionBloques() {
        for (Bloques bloque : listaBloques) {
            if (bloque.getEstadoBloque() == Estado.INTACTO) {
                if (pelota.getLimites().intersects(bloque.getLimites())) {
                    // Rebote
                    pelota.setVelocidadY(-pelota.getVelocidadY());

                    // Destruir bloque
                    bloque.recibirGolpe();

                    // Determinar qué jugador rompió el bloque
                    if (pelota.getVelocidadY() > 0) {
                        // La pelota va hacia abajo: punto para Jugador 2
                        puntosJugador2 += Constantes.PUNTOS_POR_BLOQUE;
                        servidor.agregarLog("Jugador 2: +10 puntos (" + puntosJugador2 + " total)");
                    } else {
                        // La pelota va hacia arriba: punto para Jugador 1
                        puntosJugador1 += Constantes.PUNTOS_POR_BLOQUE;
                        servidor.agregarLog("Jugador 1: +10 puntos (" + puntosJugador1 + " total)");
                    }

                    break;
                }
            }
        }
    }

    private void detectarPuntos() {
        double posY = pelota.getPosicionY();
        double tamaño = pelota.getTamaño();

        if (posY + tamaño >= Constantes.ALTO_VENTANA) {
            puntosJugador2 += 50;
            servidor.agregarLog("Jugador 1 dejó pasar la pelota! +50 pts para J2");
            reiniciarPelota();
        }
        if (posY <= 0) {
            puntosJugador1 += 50;
            servidor.agregarLog("Jugador 2 dejó pasar la pelota! +50 pts para J1");
            reiniciarPelota();
        }
    }

    private void reiniciarPelota() {
        pelota.setPosicionX(Constantes.POSICION_INICIAL_PELOTA_X);
        pelota.setPosicionY(Constantes.POSICION_INICIAL_PELOTA_Y);

        // Invertir dirección aleatoriamente
        pelota.setVelocidadX(Math.random() > 0.5
                ? Constantes.VELOCIDAD_PELOTA_X : -Constantes.VELOCIDAD_PELOTA_X);
        pelota.setVelocidadY(Math.random() > 0.5
                ? Constantes.VELOCIDAD_PELOTA_Y : -Constantes.VELOCIDAD_PELOTA_Y);
    }

    private void verificarFinDeJuego() {
        long bloquesRestantes = listaBloques.stream()
                .filter(b -> b.getEstadoBloque() == Estado.INTACTO)
                .count();

        if (bloquesRestantes == 0) {
            juegoTerminado = true;
            detenerTimer();

            String ganador;
            if (puntosJugador1 > puntosJugador2) {
                ganador = Constantes.MSG_GANADOR_J1;
            } else if (puntosJugador2 > puntosJugador1) {
                ganador = Constantes.MSG_GANADOR_J2;
            } else {
                ganador = Constantes.MSG_EMPATE;
            }

            servidor.agregarLog(Constantes.MSG_JUEGO_TERMINADO);
            servidor.agregarLog(ganador);
            servidor.agregarLog("   Jugador 1: " + puntosJugador1 + " puntos");
            servidor.agregarLog("   Jugador 2: " + puntosJugador2 + "puntos");
        }
    }

    public void procesarAccionJugador(AccionJugador accion) {
        if (!juegoActivo || juegoTerminado) {
            return;
        }

        Base base = (accion.getIdJugador() == 1) ? baseJugador1 : baseJugador2;

        switch (accion.getTipoAccion()) {
            case MOVER_IZQUIERDA:
                base.moverIzquierda();
                break;
            case MOVER_DERECHA:
                base.moverDerecha();
                break;
            case NADA:
                break;
        }
    }
    
    public EstadoJuego generarEstadoJuego(){
        EstadoJuego estado =new EstadoJuego();
        estado.setPelotaX(pelota.getPosicionY());
        estado.setPelotaY(pelota.getPosicionY());
        estado.setPelotaTamaño(pelota.getTamaño());
        estado.setPelotaColor(pelota.getColor());
         estado.setBaseJugador1(new EstadoJuego.BaseData(
            baseJugador1.getPosicionX(),
            baseJugador1.getPosicionY(),
            baseJugador1.getAncho(),
            baseJugador1.getAlto(),
            Color.BLUE
        ));
        
        estado.setBasejugador2(new EstadoJuego.BaseData(
            baseJugador2.getPosicionX(),
            baseJugador2.getPosicionY(),
            baseJugador2.getAncho(),
            baseJugador2.getAlto(),
            Color.RED
        ));
        
        // Datos de los bloques
        List<EstadoJuego.BloqueData> bloquesData = new ArrayList<>();
        for (Bloques bloque : listaBloques) {
            bloquesData.add(new EstadoJuego.BloqueData(
                bloque.getPosicionX(),
                bloque.getPosicionY(),
                bloque.getAncho(),
                bloque.getAlto(),
                bloque.getColor(),
                bloque.getEstadoBloque()
            ));
        }
        estado.setBloques(bloquesData);
        
        // Información del juego
        estado.setPuntosJugador1(puntosJugador1);
        estado.setPuntosJugador2(puntosJugador2);
        estado.setJuegoTerminado(juegoTerminado);
        estado.setJuegoIniciado(juegoActivo);
        
        if (juegoTerminado) {
            if (puntosJugador1 > puntosJugador2) {
                estado.setGanador(Constantes.MSG_GANADOR_J1);
            } else if (puntosJugador2 > puntosJugador1) {
                estado.setGanador(Constantes.MSG_GANADOR_J2);
            } else {
                estado.setGanador(Constantes.MSG_EMPATE);
            }
        }
        
        return estado;
        
    }

    public boolean isJuegoActivo() {
        return juegoActivo;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }
    

}
