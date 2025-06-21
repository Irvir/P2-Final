package Observer;

import Jugadores.Jugador;

public interface Observador {
    void actualizar(Jugador jugador, String tipo);
}
