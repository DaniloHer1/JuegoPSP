/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Diurno
 */
public class Bloques {

    private double ancho;
    private double alto;
    private Color color;
    private double posicionX;
    private double posicionY;
    private Estado estadoBloque;

    public Bloques(double ancho, double alto, Color color, double posicionX, double posicionY, Estado estadoBloque) {
        this.ancho = ancho;
        this.alto = alto;
        this.color = color;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.estadoBloque = estadoBloque;
    }

    public void recibirGolpe() {
        switch (estadoBloque) {
            case INTACTO:
                estadoBloque = Estado.DESTRUIDO;
                break;
            case DESTRUIDO:
                break;
        }
    }

    public void pintarBloque(Graphics g) {
        if (estadoBloque == Estado.INTACTO) {
            g.setColor(color);
            g.fillRect((int) posicionX, (int) posicionY, (int) ancho, (int) alto);

            g.setColor(Color.BLACK);
            g.drawRect((int) posicionX, (int) posicionY, (int) ancho, (int) alto);
        }

    }

    public Rectangle getLimites() {
        return new Rectangle((int) posicionX, (int) posicionY, (int) ancho, (int) alto);
    }

    public Estado getEstadoBloque() {
        return estadoBloque;
    }

    public double getAncho() {
        return ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
    }

    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getPosicionX() {
        return posicionX;
    }

    public void setPosicionX(double posicionX) {
        this.posicionX = posicionX;
    }

    public double getPosicionY() {
        return posicionY;
    }

    public void setPosicionY(double posicionY) {
        this.posicionY = posicionY;
    }
    
    
    
    

}
