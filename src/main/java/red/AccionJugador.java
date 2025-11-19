/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package red;

import java.io.Serial;
import java.io.Serializable;

/**
 *
 * @author Diurno
 */
public class AccionJugador implements Serializable{
    
    public enum TipoAccion{
        
        MOVER_DERECHA,
        MOVER_IZQUIERDA,
        NADA        
    }
    
    
    private TipoAccion tipoAccion;
    
    private int idJugador;

    public AccionJugador(TipoAccion tipoAccion, int idJugador) {
        this.tipoAccion = tipoAccion;
        this.idJugador = idJugador;
    }

    public TipoAccion getTipoAccion() {
        return tipoAccion;
    }

    public void setTipoAccion(TipoAccion tipoAccion) {
        this.tipoAccion = tipoAccion;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }
    
    
    
    
    
    
}
