package Jugadores;

import java.util.Random;
import Tablero.GrupoTableros;
public class PCFacil implements Jugador{
    private String nombreJugador;
    private int puntuacion;
    private char simbolo;
    private int partidasGanadas;
    private int partidasPerdidas;
    private int partidasEmpatadas;

    public PCFacil() {
        this.nombreJugador = "PC Fácil";
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
        return "PC Fácil {" +
                "nombreJugador='" + nombreJugador + '\'' +
                ", puntuacion=" + puntuacion +
                ", partidasGanadas=" + partidasGanadas +
                ", partidasPerdidas=" + partidasPerdidas +
                ", partidasEmpatadas=" + partidasEmpatadas +
                '}';
    }
    @Override
    public int hacerJugada(int plano, int posicion, GrupoTableros tableros, char simbolo) {
        Random random = new Random();
        int posicionRandom = random.nextInt(9)+1;
        int planoId = plano - 1;
        int fila = (posicion - 1) / 3;
        int columna = (posicion - 1) % 3;

        tableros.recibirJugada(plano,fila, columna,simbolo);
        return posicion;
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
