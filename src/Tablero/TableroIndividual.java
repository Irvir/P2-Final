package Tablero;

import Jugadores.Jugador;
import Observer.Observador;

import java.util.ArrayList;

public class TableroIndividual implements Tablero {
    private Observador observador;

    private char[][] tablero;
    public TableroIndividual() {
        tablero = new char[3][3];
        char simbolo = '-';
        rellenarTablero(simbolo,0);
    }
    public void registraObservador(Observador observador) {
        this.observador = observador;
    }
    public void notificarObservador(Jugador jugador, String mensaje) {
        observador.actualizar(jugador, mensaje);
    }


    @Override
    public void rellenarTablero(char simbolo, int plano) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = simbolo;
            }
        }

    }

    @Override
    public void imprimirTablero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + tablero[i][j] + " ");
                if (j < 2) System.out.print("|");
            }
            System.out.println();
            if (i < 2) {
                System.out.println("---+---+---");
            }
        }
        System.out.println();
    }

    @Override
    public int recibirJugada(int plano,int fila, int columna ,char simbolo) {
        if (tablero[fila][columna] == '-') {
            tablero[fila][columna] = simbolo;
            return 1; // Retornar 1 para indicar jugada exitosa
        } else {

            System.out.println("Posición ya ocupada: " + (fila* 3 +columna+1)+" intenta otra vez.");
            return -1;
        }
    }

    @Override
    public boolean verificarGanador(int posicionTablero,char simbolo) {

        for (int i = 0; i < 3; i++) {
            // Verificar filas
            if (tablero[i][0] == tablero[i][1] && tablero[i][1] == tablero[i][2] && tablero[i][0] == simbolo) {
                return true;
            }
            // Verificar columnas
            if (tablero[0][i] == tablero[1][i] && tablero[1][i] == tablero[2][i] && tablero[0][i] == simbolo) {
                return true;
            }
            //Diagonales
            for (int j = 0; j < 3; j++) {
                if (tablero[0][0] == tablero[1][1] && tablero[1][1] == tablero[2][2] && tablero[0][0] == simbolo) {
                    return true;
                }
                if (tablero[0][2] == tablero[1][1] && tablero[1][1] == tablero[2][0] && tablero[0][2] == simbolo) {
                    return true;
                }
            }

        }
        // Implementar lógica para verificar ganador
        return false;
    }
    @Override
    public boolean verificarEmpate(int posicionTablero) {
        // Si hay un ganador, no es empate
        if (verificarGanador(posicionTablero, 'X') || verificarGanador(posicionTablero, 'O')) {
            return false;
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }
    // Metodo para verificar empate global en el meta-tablero
    public boolean empateGlobal(TableroIndividual metaTablero) {
        // Si hay un ganador, no es empate
        if (metaTablero.verificarGanador(0, 'X') || metaTablero.verificarGanador(0, 'O')) {
            return false;
        }
        char[][] tablero = metaTablero.getTablero();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[i][j] == '-' || tablero[i][j] == '/') {
                    return false;
                }
            }
        }
        return true;
    }

    public char[][] getTablero() {
        return tablero;
    }


}
