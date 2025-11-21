package modelo;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Diurno
 */
public class Pelota {

    private double velocidadX;
    private double velocidadY;
    private double posicionX;
    private double posicionY;

    private double tamaño = 30;

    private Color color;

    public Pelota(double velocidadX, double velocidadY, double posicionX, double posicionY, double tamaño, Color color) {
        this.velocidadX = velocidadX;
        this.velocidadY = velocidadY;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.tamaño = tamaño;
        this.color = color;
    }

    public void rebotarParedes(double anchoVentana, double altoVentana) {

        if (posicionX <= 0) {
            velocidadX = -velocidadX;
            posicionX = 0;
        }

        if (posicionX + tamaño >= anchoVentana) {
            velocidadX = -velocidadX;
            posicionX = anchoVentana - tamaño;
        }
        if (posicionY <= 0) {
            velocidadY = -velocidadY;
            posicionY = 0;
        }

        if (posicionY + tamaño >= altoVentana) {
            gameOver();
        }

    }

    public double getVelocidadX() {
        return velocidadX;
    }

    public void setVelocidadX(double velocidadX) {
        this.velocidadX = velocidadX;
    }

    public double getVelocidadY() {
        return velocidadY;
    }

    public void setVelocidadY(double velocidadY) {
        this.velocidadY = velocidadY;
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

    public double getTamaño() {
        return tamaño;
    }

    public void setTamaño(double tamaño) {
        this.tamaño = tamaño;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    

    public void rebotarBloques(Bloques bloque) {
        if (bloque.getEstadoBloque() == Estado.INTACTO) {
            if (this.getLimites().intersects(bloque.getLimites())) {
                velocidadY = -velocidadY;
                bloque.recibirGolpe();
            }
        }
    }

    public void rebotarBase(Base base) {

        if (this.getLimites().intersects(base.getLimites())) {
            if (velocidadY > 0) {
                velocidadY = -velocidadY;
                posicionY = base.getPosicionY() - tamaño;
            }
        }
    }

    public void mover() {
        posicionX += velocidadX;
        posicionY += velocidadY;
    }

    public void pintarPelota(Graphics g) {

        g.setColor(color);
        g.fillOval((int) posicionX, (int) posicionY, (int) tamaño, (int) tamaño);

    }

    public Rectangle getLimites() {
        return new Rectangle((int) posicionX, (int) posicionY, (int) tamaño, (int) tamaño);
    }

    private void gameOver() {

    }

}
