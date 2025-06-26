package Serializable;

import Jugadores.Jugador;

import java.io.*;
import java.util.ArrayList;
//Implementa la serialización para guardar y recuperar jugadores en un archivo.
public class Serializacion implements Serializable {
    private ArrayList<Jugador> jugadores = new ArrayList<>();
    private final String archivo = "jugadores.txt";

    // Cargar jugadores desde el archivo
    private void cargarJugadores() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            jugadores = (ArrayList<Jugador>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            jugadores = new ArrayList<>();
        }
    }

    // Guarda jugadores al archivo
    public void guardarJugadores() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(jugadores);
        } catch (IOException e) {
            System.err.println("Error al guardar los jugadores: " + e.getMessage());
        }
    }

    // Agregar jugador si no existe
    public void agregarJugador(Jugador jugador) {
        cargarJugadores();
        boolean existe = false;
        for (Jugador j : jugadores) {
            if (j.getNombreJugador().equalsIgnoreCase(jugador.getNombreJugador())) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            jugadores.add(jugador);
            guardarJugadores();
        }
    }

    // Mostrar jugadores
    public void mostrarJugadores() {
        cargarJugadores();
        ordenarJugadores();
        int i = 1;
        for (Jugador jugador : jugadores) {
            System.out.println(i+"- "+jugador);
            i++;
        }
    }

    // Ordenar por puntuación descendente
    private void ordenarJugadores() {
        jugadores.sort((j1, j2) -> Integer.compare(j2.getPartidasGanadas(), j1.getPartidasGanadas()));
    }

    // Buscar jugador por nombre
    public Jugador recuperarJugador(String nombre) {
        cargarJugadores();
        for (Jugador jugador : jugadores) {
            if (jugador.getNombreJugador().equalsIgnoreCase(nombre)) {
                return jugador;
            }
        }
        return null;
    }

    // Verificar si jugador existe
    public boolean encontrarJugador(String nombre) {
        cargarJugadores();
        for (Jugador jugador : jugadores) {
            if (jugador.getNombreJugador().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }
    // Actualizar jugador existente
    public void actualizarJugador(Jugador jugador) {
        cargarJugadores();
        for (int i = 0; i < jugadores.size(); i++) {
            if (jugadores.get(i).getNombreJugador().equalsIgnoreCase(jugador.getNombreJugador())) {
                jugadores.set(i, jugador);
                guardarJugadores();
                return;
            }
        }
        System.out.println("Jugador no encontrado para actualizar: " + jugador.getNombreJugador());
    }
}
