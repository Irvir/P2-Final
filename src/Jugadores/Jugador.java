package Jugadores;

import java.io.Serializable;
import Tablero.*;

public interface Jugador extends Serializable {
    String getNombreJugador();
    void setNombreJugador(String nombreJugador);

    int getPartidasGanadas();
    void setPartidasGanadas(int partidasGanadas);

    int getPartidasPerdidas();
    void setPartidasPerdidas(int partidasPerdidas);

    int getPartidasEmpatadas();
    void setPartidasEmpatadas(int partidasEmpatadas);

    char getSimbolo();
    void setSimbolo(char simbolo);

    int hacerJugada(int plano, int posicion, GrupoTableros tableros, char simbolo);

}
