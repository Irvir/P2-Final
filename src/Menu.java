import Jugadores.Jugador;
import Jugadores.JugadorFactory;
import Serializable.Serializacion;
import Tablero.GrupoTableros;
import Tablero.TableroIndividual;

import java.util.Scanner;

public class Menu {
    String nombreUsuario;
    String nombreJugador;
    TableroIndividual metaTablero;
    GrupoTableros tableros;
    Jugador jugador1;
    Jugador jugador2;
    Juego juego;

    public void menu() {
        Serializacion serializacion = new Serializacion();
        Scanner in = new Scanner(System.in);
        while (true){
        System.out.println("Bienvenido al Gato de Gatos!");

        System.out.println("Ingrese su nombre de usuario:");
        nombreUsuario = in.nextLine();

        if (serializacion.encontrarJugador(nombreUsuario)) {
            System.out.println("Jugadores.Jugador encontrado: " + nombreUsuario);
            jugador1 = serializacion.recuperarJugador(nombreUsuario);
        } else {
            System.out.println("Jugadores.Jugador no encontrado: " + nombreUsuario+" ,creando nuevo jugador.");
            jugador1 = JugadorFactory.crearJugador(nombreUsuario, "Humano");
            serializacion.agregarJugador(jugador1);
            serializacion.guardarJugadores();
            serializacion.mostrarJugadores();

        }


        System.out.println("1. Empezar Juego");
        System.out.println("2. Cargar Jugadores");
        System.out.println("3. Exit");
        System.out.print("Seleccione una opción: ");
        int opcion = in.nextInt();
        switch (opcion){
            case 1:
                System.out.println("Iniciando el juego...");
                System.out.println("1- Humano vs Humano");
                System.out.println("2- Humano vs PC Fácil");
                System.out.println("3- Humano vs PC Difícil");
                opcion = in.nextInt();
                switch (opcion){
                    case 1:
                        System.out.println("Ingrese el nombre del segundo jugador:");
                        in.nextLine();
                        String nombreJugador2 = in.nextLine();
                        if (!serializacion.encontrarJugador(nombreJugador2)){
                        System.out.println("------------NO SE ENCONTRÓ EL JUGADOR, CREANDO NUEVO JUGADOR------------");
                        jugador2 = JugadorFactory.crearJugador(nombreJugador2, "Humano");
                        serializacion.agregarJugador(jugador2);}

                        else {
                            jugador2 = serializacion.recuperarJugador(nombreJugador2);
                        }
                        serializacion.guardarJugadores();
                        break;
                    case 2:
                        jugador2 = JugadorFactory.crearJugador("PC Fácil", "PC Fácil");
                        break;
                    case 3:
                        jugador2 = JugadorFactory.crearJugador("PC Difícil", "PC Difícil");
                        break;
                    default:
                        System.out.println("Opción no válida, volviendo al menú principal.");
                        continue;
                }

                juego = new Juego();


                juego.Juego(jugador1, jugador2, metaTablero, tableros);
                break;
            case 2:
                System.out.println("Cargando jugadores...");
                serializacion.mostrarJugadores();
                break;
            case 3:
                System.out.println("Saliendo del juego. ¡Hasta luego!");
                return; // Salir del bucle y terminar el programa
            default:
                System.out.println("Opción no válida, por favor intente de nuevo.");
                break;
        }
        }


    }

}
