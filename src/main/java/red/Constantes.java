package red;

public class Constantes {
    public static final int ANCHO_VENTANA = 600;
    public static final int ALTO_VENTANA = 700;

    //  PELOTA
    public static final double TAMANO_PELOTA = 20;
    public static final double VELOCIDAD_PELOTA_X = 3;
    public static final double VELOCIDAD_PELOTA_Y = 3;

    //  BASES
    public static final double ANCHO_BASE = 100;
    public static final double ALTO_BASE = 20;
    public static final double VELOCIDAD_BASE = 15;

    // Posiciones iniciales de las bases
    public static final double BASE_J1_Y = 650; // Jugador 1 abajo
    public static final double BASE_J2_Y = 30;  // Jugador 2 arriba
    public static final double BASE_INICIAL_X = 250; // Centrada

    // BLOQUES
    public static final int BLOQUES_FILAS = 5;
    public static final int BLOQUES_COLUMNAS = 8;
    public static final double BLOQUE_ANCHO = 60;
    public static final double BLOQUE_ALTO = 20;
    public static final double BLOQUE_ESPACIADO = 5;
    public static final double BLOQUE_MARGEN_SUPERIOR = 200; // Centro de la pantalla
    public static final double BLOQUE_MARGEN_IZQUIERDO = 50;

    // JUEGO
    public static final int FPS = 60; // Frames por segundo
    public static final int DELAY_FRAME = 1000 / FPS; // 16ms para 60 FPS
    public static final int PUNTOS_POR_BLOQUE = 10;

    // RED
    public static final int PUERTO_DEFECTO = 5050;
    public static final int MAX_JUGADORES = 2;
    public static final int TIMEOUT_CONEXION = 5000; // 5 segundos

    // MENSAJES
    public static final String MSG_ESPERANDO_JUGADORES = "Esperando a otro jugador...";
    public static final String MSG_JUEGO_INICIADO = "¡JUEGO INICIADO!";
    public static final String MSG_JUGADOR_1 = "JUGADOR 1 (TÚ)";
    public static final String MSG_JUGADOR_2 = "JUGADOR 2 (TÚ)";
    public static final String MSG_RIVAL = "RIVAL";

}
