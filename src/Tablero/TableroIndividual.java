package Tablero;

import Jugadores.Jugador;
import Observer.Observador;

import java.util.ArrayList;
// Utiliza el patrón Composite para manejar múltiples tableros individuales como un solo tablero compuesto.
public class TableroIndividual implements Tablero {
    private Observador observador;

    private char[][] tablero;
    // Constructor que inicializa un tablero individual de 3x3.
    public TableroIndividual() {
        tablero = new char[3][3];
        char simbolo = '-';
        rellenarTablero(simbolo,0);
    }
    //Método del Observer para registrar un observador.
    public void registraObservador(Observador observador) {

        this.observador = observador;
    }
    // Método del Observer para notificar al observador con un mensaje.
    public void notificarObservador(Jugador jugador, String mensaje) {

        observador.actualizar(jugador, mensaje);
    }


    @Override
    // Método para rellenar el tablero con un símbolo específico.
    public void rellenarTablero(char simbolo, int plano) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                tablero[i][j] = simbolo;
            }
        }

    }


    @Override
    // Método para imprimir el estado del tablero individual en un formato de 3x3.
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
    // Método para recibir una jugada en el tablero individual.
    public int recibirJugada(int plano,int fila, int columna ,char simbolo) {
        if (tablero[fila][columna] == '-') {
            tablero[fila][columna] = simbolo;
            // Retornar 1 para indicar jugada exitosa
            return 1;
        } else {
            System.out.println("Posición ya ocupada: " + (fila* 3 +columna+1)+" intenta otra vez.");
            return -1;
        }
    }

    @Override
    // Método para verificar si un jugador ha ganado en el tablero individual.
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
        return false;
    }
    @Override
    //Método para verificar si un tablero individual ha empatado.
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
