package Tablero;

public interface Tablero {
    void imprimirTablero();
    int recibirJugada(int plano, int fila, int columna, char simbolo);
    boolean verificarGanador(int posicionTablero, char simbolo);
    boolean verificarEmpate(int posicionTablero);
    void rellenarTablero(char simbolo, int plano);
}
