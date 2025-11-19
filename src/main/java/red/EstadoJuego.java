/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package red;

import modelo.Base;
import modelo.Bloques;
import modelo.Estado;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Diurno
 */
public class EstadoJuego implements Serializable {

    private double pelotaX;
    private double pelotaY;
    private double pelotaTamaño;

    private List<BloqueData> bloques;

    private BaseData baseJugador1;
    private BaseData basejugador2;


    private int puntosJugador1;
    private int puntosJugador2;
    private boolean juegoTerminado;
    private String Ganador;

    public EstadoJuego() {
        this.bloques = new ArrayList<>();
    }

    public double getPelotaX() {
        return pelotaX;
    }

    public void setPelotaX(double pelotaX) {
        this.pelotaX = pelotaX;
    }

    public double getPelotaY() {
        return pelotaY;
    }

    public void setPelotaY(double pelotaY) {
        this.pelotaY = pelotaY;
    }

    public double getPelotaTamaño() {
        return pelotaTamaño;
    }

    public void setPelotaTamaño(double pelotaTamaño) {
        this.pelotaTamaño = pelotaTamaño;
    }

    public List<BloqueData> getBloques() {
        return bloques;
    }

    public void setBloques(List<BloqueData> bloques) {
        this.bloques = bloques;
    }

    public BaseData getBaseJugador1() {
        return baseJugador1;
    }

    public void setBaseJugador1(BaseData baseJugador1) {
        this.baseJugador1 = baseJugador1;
    }

    public BaseData getBasejugador2() {
        return basejugador2;
    }

    public void setBasejugador2(BaseData basejugador2) {
        this.basejugador2 = basejugador2;
    }

    public int getPuntosJugador1() {
        return puntosJugador1;
    }

    public void setPuntosJugador1(int puntosJugador1) {
        this.puntosJugador1 = puntosJugador1;
    }

    public int getPuntosJugador2() {
        return puntosJugador2;
    }

    public void setPuntosJugador2(int puntosJugador2) {
        this.puntosJugador2 = puntosJugador2;
    }

    public boolean isJuegoTerminado() {
        return juegoTerminado;
    }

    public void setJuegoTerminado(boolean juegoTerminado) {
        this.juegoTerminado = juegoTerminado;
    }

    public String getGanador() {
        return Ganador;
    }

    public void setGanador(String ganador) {
        Ganador = ganador;
    }

    // Clases para que el servidor maneje la sincornizacion
    public static class BloqueData implements Serializable {
        private double x, y, ancho, alto;
        private Color color;
        private Estado estado;


        public BloqueData(double x, double y, double ancho, double alto, Color color, Estado estado) {
            this.x = x;
            this.y = y;
            this.ancho = ancho;
            this.alto = alto;
            this.color = color;
            this.estado = estado;
        }

    }

    public static class BaseData implements Serializable {
        private static final long serialVersionUID = 1L;
        public double x, y, ancho, alto;
        public Color color;

        public BaseData(double x, double y, double ancho, double alto, Color color) {
            this.x = x;
            this.y = y;
            this.ancho = ancho;
            this.alto = alto;
            this.color = color;
        }
    }
}
