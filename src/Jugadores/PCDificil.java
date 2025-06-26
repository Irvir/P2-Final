package Jugadores;
import Tablero.GrupoTableros;
import Tablero.TableroIndividual;

public class PCDificil implements Jugador{
    private String nombreJugador;
    private char simbolo;
    private int partidasGanadas;
    private int partidasPerdidas;
    private int partidasEmpatadas;

    public PCDificil() {
        this.nombreJugador = "PC Difícil";
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
    // Método para hacer una jugada en el juego.
    public int hacerJugada(int plano, int posicion, GrupoTableros tableros, char simbolo) {
        int planoId = plano - 1;
        int fila = (posicion - 1) / 3;
        int columna = (posicion - 1) % 3;
        // Llama al método recibirJugada del tablero específico.
        return tableros.recibirJugada(planoId, fila,columna, simbolo);
    }
    // Método para hacer una jugada difícil en un plano específico.
    public int hacerJugadaDificilPlano(int plano, char simbolo, char[][] tablero) {
    char simboloOponente = (simbolo == 'X') ? 'O' : 'X';
    // 1. Ganar en fila con una jugada.
    for (int i = 0; i < 3; i++) {
        int cuenta = 0, columnaVacia = -1;
        for (int j = 0; j < 3; j++) {
            if (tablero[i][j] == simbolo) cuenta++;
            else if (tablero[i][j] == '-') columnaVacia = j;
        }
        if (cuenta == 2 && columnaVacia != -1) return i * 3 + columnaVacia + 1;
    }
    // 2. Ganar en columna con una jugada.
    for (int j = 0; j < 3; j++) {
        int cuenta = 0, filaVacia = -1;
        for (int i = 0; i < 3; i++) {
            if (tablero[i][j] == simbolo) cuenta++;
            else if (tablero[i][j] == '-') filaVacia = i;
        }
        if (cuenta == 2 && filaVacia != -1) return filaVacia * 3 + j + 1;
    }
    // 3. Ganar en diagonal principal
    int cuenta = 0, diagonalVacia = -1;
    for (int i = 0; i < 3; i++) {
        if (tablero[i][i] == simbolo) cuenta++;
        else if (tablero[i][i] == '-') diagonalVacia = i;
    }
    if (cuenta == 2 && diagonalVacia != -1) return diagonalVacia * 3 + diagonalVacia + 1;
    // 4. Ganar en diagonal secundaria
    cuenta = 0; diagonalVacia = -1;
    for (int i = 0; i < 3; i++) {
        if (tablero[i][2 - i] == simbolo) cuenta++;
        else if (tablero[i][2 - i] == '-') diagonalVacia = i;
    }
    if (cuenta == 2 && diagonalVacia != -1) return diagonalVacia * 3 + (2 - diagonalVacia) + 1;
    // 5. Bloquear fila
    for (int i = 0; i < 3; i++) {
        int cuentaOponente = 0, columnaVacia = -1;
        for (int j = 0; j < 3; j++) {
            if (tablero[i][j] == simboloOponente) cuentaOponente++;
            else if (tablero[i][j] == '-') columnaVacia = j;
        }
        if (cuentaOponente == 2 && columnaVacia != -1) return i * 3 + columnaVacia + 1;
    }
    // 6. Bloquear columna
    for (int j = 0; j < 3; j++) {
        int cuentaOponente = 0, filaVacia = -1;
        for (int i = 0; i < 3; i++) {
            if (tablero[i][j] == simboloOponente) cuentaOponente++;
            else if (tablero[i][j] == '-') filaVacia = i;
        }
        if (cuentaOponente == 2 && filaVacia != -1) return filaVacia * 3 + j + 1;
    }
    // 7. Bloquear diagonal principal
    cuenta = 0; diagonalVacia = -1;
    for (int i = 0; i < 3; i++) {
        if (tablero[i][i] == simboloOponente) cuenta++;
        else if (tablero[i][i] == '-') diagonalVacia = i;
    }
    if (cuenta == 2 && diagonalVacia != -1) return diagonalVacia * 3 + diagonalVacia + 1;
    // 8. Bloquear diagonal secundaria
    cuenta = 0; diagonalVacia = -1;
    for (int i = 0; i < 3; i++) {
        if (tablero[i][2 - i] == simboloOponente) cuenta++;
        else if (tablero[i][2 - i] == '-') diagonalVacia = i;
    }
    if (cuenta == 2 && diagonalVacia != -1) return diagonalVacia * 3 + (2 - diagonalVacia) + 1;
    // 9. Centro
    if (tablero[1][1] == '-') return 5;
    // 10. Esquinas
    if (tablero[0][0] == '-') return 1;
    if (tablero[0][2] == '-') return 3;
    if (tablero[2][0] == '-') return 7;
    if (tablero[2][2] == '-') return 9;
    // 11. Cualquier espacio libre
    for (int i = 0; i < 3; i++)
        for (int j = 0; j < 3; j++)
            if (tablero[i][j] == '-') return i * 3 + j + 1;
    // No hay jugada posible
    return -1;
}
    // Método para hacer una jugada difícil en un grupo de tableros.
    //Analiza cada tablero en el grupo y busca realizar una jugada.
    public int hacerJugadaDificilGrupo(GrupoTableros grupo, char simbolo) {
        for (int plano = 0; plano < 9; plano++) {
            char[][] tablero = grupo.getTableros().get(plano).getTablero();

            int posicion = hacerJugadaDificilPlano(plano, simbolo, tablero);
            if (posicion != -1) {
                System.out.println("Posición encontrada en plano " + (plano + 1) + ": " + posicion);

                return plano + 1;
            }

        }
        return -1;
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
