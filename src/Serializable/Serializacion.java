package Serializable;

import Jugadores.Jugador;

import java.io.*;
import java.util.ArrayList;

public class Serializacion implements Serializable {
    ArrayList<Jugador> jugadores;
    String archivo = "jugadores.dat";

    public void agregarJugador(Jugador jugador) {
        if (jugadores == null) {
            jugadores = new ArrayList<>();
        }
        jugadores.add(jugador);
    }
    public void ordenarJugadores() {
        if (jugadores != null) {
            jugadores.sort((j1, j2) -> Integer.compare(j2.getPuntuacion(), j1.getPuntuacion()));
        }
    }
    public void mostrarJugadores() {
        ordenarJugadores();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            jugadores = (ArrayList<Jugador>) ois.readObject();
            ordenarJugadores();
            for (Jugador jugador : jugadores) {
                System.out.println(jugador);
            }
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al deserializar los jugadores: piu" + e.getMessage());
        }
    }
    public Jugador recuperarJugador(String nombre) {
        Jugador jugadorRecuperado = null;
        if (encontrarJugador(nombre)){
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                jugadores = (ArrayList<Jugador>) ois.readObject();
                ordenarJugadores();
                for (Jugador jugador : jugadores) {
                    if (jugador.getNombreJugador().equalsIgnoreCase(nombre)) {
                        jugadorRecuperado = jugador;
                        break;
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error al recuperar el jugador: " + e.getMessage());
            }
        }

        return jugadorRecuperado;
    }
    public void guardarJugadores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(jugadores);
        } catch (IOException e) {
            System.err.println("Error al guardar los jugadores:s " + e.getMessage());
        }
    }
    public boolean encontrarJugador(String nombre) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            jugadores = (ArrayList<Jugador>) ois.readObject();
            ordenarJugadores();
            for (Jugador jugador : jugadores) {
                if (jugador.getNombreJugador().equalsIgnoreCase(nombre)) {
                    return true;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
        }
        return false;
    }
}
