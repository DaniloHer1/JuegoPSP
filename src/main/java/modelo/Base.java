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
public class Base {

    private double alto;
    private double ancho;
    private double posicionX;
    private double posicionY;
    private double VelocidadMovimiento;
    private double limiteIzquierdo;
    private double limiteDerecho;

    public Base(double alto, double ancho, double posicionX, double posicionY, double VelocidadMovimiento, double limiteIzquierdo, double limiteDerecho) {
        this.alto = alto;
        this.ancho = ancho;
        this.posicionX = posicionX;
        this.posicionY = posicionY;
        this.VelocidadMovimiento = VelocidadMovimiento;
        this.limiteIzquierdo = 0;
        this.limiteDerecho = limiteDerecho;
    }

    public void moverIzquierda() {
        posicionX -= VelocidadMovimiento;
        if (posicionX < limiteIzquierdo) {
            posicionX = limiteIzquierdo;
        }
    }

    public void moverDerecha() {
        posicionX += VelocidadMovimiento;
        if (posicionX + ancho > limiteDerecho) {
            posicionX = limiteDerecho-ancho;
        }
    }

    private void detectarColosion() {

    }

    public void pintarBase(Graphics g) {

        g.setColor(Color.BLUE);
        g.fillRect((int) posicionX, (int) posicionY, (int) ancho, (int) alto);

    }

    public Rectangle getLimites() {
        return new Rectangle((int) posicionX, (int) posicionY, (int) ancho, (int) alto);
    }

    public double getAlto() {
        return alto;
    }

    public void setAlto(double alto) {
        this.alto = alto;
    }

    public double getAncho() {
        return ancho;
    }

    public void setAncho(double ancho) {
        this.ancho = ancho;
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

    public double getVelocidadMovimiento() {
        return VelocidadMovimiento;
    }

    public void setVelocidadMovimiento(double VelocidadMovimiento) {
        this.VelocidadMovimiento = VelocidadMovimiento;
    }

}
