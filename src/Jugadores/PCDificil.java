package Jugadores;
import Tablero.GrupoTableros;
import Tablero.TableroIndividual;

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
        int planoId = plano - 1;
        int fila = (posicion - 1) / 3;
        int columna = (posicion - 1) % 3;
        return tableros.recibirJugada(planoId, fila,columna, simbolo);
    }
    public int hacerJugadaDificilPlano(int plano, char simbolo, char[][] tablero) {
    char simboloOponente = (simbolo == 'X') ? 'O' : 'X';
    // 1. Ganar en fila
    for (int i = 0; i < 3; i++) {
        int count = 0, emptyCol = -1;
        for (int j = 0; j < 3; j++) {
            if (tablero[i][j] == simbolo) count++;
            else if (tablero[i][j] == '-') emptyCol = j;
        }
        if (count == 2 && emptyCol != -1) return i * 3 + emptyCol + 1;
    }
    // 2. Ganar en columna
    for (int j = 0; j < 3; j++) {
        int count = 0, emptyRow = -1;
        for (int i = 0; i < 3; i++) {
            if (tablero[i][j] == simbolo) count++;
            else if (tablero[i][j] == '-') emptyRow = i;
        }
        if (count == 2 && emptyRow != -1) return emptyRow * 3 + j + 1;
    }
    // 3. Ganar en diagonal principal
    int count = 0, emptyDiag = -1;
    for (int i = 0; i < 3; i++) {
        if (tablero[i][i] == simbolo) count++;
        else if (tablero[i][i] == '-') emptyDiag = i;
    }
    if (count == 2 && emptyDiag != -1) return emptyDiag * 3 + emptyDiag + 1;
    // 4. Ganar en diagonal secundaria
    count = 0; emptyDiag = -1;
    for (int i = 0; i < 3; i++) {
        if (tablero[i][2 - i] == simbolo) count++;
        else if (tablero[i][2 - i] == '-') emptyDiag = i;
    }
    if (count == 2 && emptyDiag != -1) return emptyDiag * 3 + (2 - emptyDiag) + 1;
    // 5. Bloquear fila
    for (int i = 0; i < 3; i++) {
        int countOponente = 0, emptyCol = -1;
        for (int j = 0; j < 3; j++) {
            if (tablero[i][j] == simboloOponente) countOponente++;
            else if (tablero[i][j] == '-') emptyCol = j;
        }
        if (countOponente == 2 && emptyCol != -1) return i * 3 + emptyCol + 1;
    }
    // 6. Bloquear columna
    for (int j = 0; j < 3; j++) {
        int countOponente = 0, emptyRow = -1;
        for (int i = 0; i < 3; i++) {
            if (tablero[i][j] == simboloOponente) countOponente++;
            else if (tablero[i][j] == '-') emptyRow = i;
        }
        if (countOponente == 2 && emptyRow != -1) return emptyRow * 3 + j + 1;
    }
    // 7. Bloquear diagonal principal
    count = 0; emptyDiag = -1;
    for (int i = 0; i < 3; i++) {
        if (tablero[i][i] == simboloOponente) count++;
        else if (tablero[i][i] == '-') emptyDiag = i;
    }
    if (count == 2 && emptyDiag != -1) return emptyDiag * 3 + emptyDiag + 1;
    // 8. Bloquear diagonal secundaria
    count = 0; emptyDiag = -1;
    for (int i = 0; i < 3; i++) {
        if (tablero[i][2 - i] == simboloOponente) count++;
        else if (tablero[i][2 - i] == '-') emptyDiag = i;
    }
    if (count == 2 && emptyDiag != -1) return emptyDiag * 3 + (2 - emptyDiag) + 1;
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
    return -1; // No hay jugada posible
}
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
