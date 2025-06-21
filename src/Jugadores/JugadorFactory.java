package Jugadores;

public class JugadorFactory{

    static public Jugador crearJugador(String nombreJugador, String tipoJugador) {
        switch (tipoJugador) {
            case "Humano":
                return new JugadorHumano(nombreJugador);
            case "Easy":
                return new PCFacil();
            case "Hard":
                return new PCDificil();
            default:
                throw new IllegalArgumentException("Tipo de jugador no reconocido: " + tipoJugador);
        }
    }

}
