package Jugadores;
import Tablero.GrupoTableros;

public class PCDificil implements Jugador{
    private String nombreJugador;
    private int puntuacion;
    private char simbolo;
    private int partidasGanadas;
    private int partidasPerdidas;
    private int partidasEmpatadas;

    public PCDificil() {
        this.nombreJugador = "PC Difícil";
        this.puntuacion = 0;
        this.partidasGanadas = 0;
        this.partidasPerdidas = 0;
        this.partidasEmpatadas = 0;
    }

    @Override
    public String getNombreJugador() {
        return nombreJugador;
    }

    @Override
    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    @Override
    public int getPuntuacion() {
        return puntuacion;
    }

    @Override
    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    @Override
    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }

    @Override
    public int getPartidasPerdidas() {
        return partidasPerdidas;
    }

    @Override
    public void setPartidasPerdidas(int partidasPerdidas) {
        this.partidasPerdidas = partidasPerdidas;
    }

    @Override
    public int getPartidasEmpatadas() {
        return partidasEmpatadas;
    }

    @Override
    public void setPartidasEmpatadas(int partidasEmpatadas) {
        this.partidasEmpatadas = partidasEmpatadas;
    }

    @Override
    public String toString() {
        return "Tipo: PC Hard [ " +
                "Nombre='" + nombreJugador + '\'' +
                ", Ganadas=" + partidasGanadas +
                ", Perdidas=" + partidasPerdidas +
                ", Empatadas=" + partidasEmpatadas +
                ']';
    }
    @Override
    public int hacerJugada(int plano, int posicion, GrupoTableros tableros, char simbolo) {
        // Implementación de la lógica para que el PC difícil haga una jugada
        // Aquí se puede agregar la lógica avanzada para el PC difícil
        int planoId = plano - 1;
        int fila = (posicion - 1) / 3;
        int columna = (posicion - 1) % 3;
        return tableros.recibirJugada(plano, fila,columna, simbolo);
    }

    @Override
    public char getSimbolo() {
        return simbolo;
    }

    @Override
    public void setSimbolo(char simbolo) {
        this.simbolo = simbolo;
    }
}
