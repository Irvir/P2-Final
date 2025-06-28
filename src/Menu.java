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
        System.out.println("Instrucciones:");
        System.out.println("1- Cuando se pide un tablero y posición el formato es el siguiente: Plano(1-9) Espacio Posición");
        System.out.println("2- Para rendirse tiene que escribir la palabra 'rendir' por lo que finalizará la partida");
        System.out.println("Ingrese su nombre de usuario:");
        nombreUsuario = in.nextLine();
        //Encuentra Jugador, si lo encuentra lo recupera (las estadísticas).
        if (serializacion.encontrarJugador(nombreUsuario)) {
            System.out.println("Jugadores.Jugador encontrado: " + nombreUsuario);
            jugador1 = serializacion.recuperarJugador(nombreUsuario);
        //Caso contrario lo crea.
        } else {
            System.out.println("Jugadores.Jugador no encontrado: " + nombreUsuario+" ,creando nuevo jugador.");
            jugador1 = JugadorFactory.crearJugador(nombreUsuario, "Humano");
            serializacion.agregarJugador(jugador1);

        }


        System.out.println("1. Empezar Juego");
        System.out.println("2. Cargar Jugadores");
        System.out.println("3. Exit");
        System.out.print("Seleccione una opción: ");
        int opcion = in.nextInt();
        //Crea Jugadores.
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
                        //Encuentra al Jugador 2, y realiza los procedimientos anteriores del Jugador1
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
                        jugador2 = JugadorFactory.crearJugador("PC Fácil", "Easy");
                        serializacion.agregarJugador(jugador2);
                        break;
                    case 3:
                        jugador2 = JugadorFactory.crearJugador("PC Difícil", "Hard");
                        serializacion.agregarJugador(jugador2);
                        break;
                    default:
                        System.out.println("Opción no válida, volviendo al menú principal.");
                        continue;
                }
                System.out.println("Estadísticas de los jugadores:");
                System.out.println("Jugador 1: " + jugador1);
                System.out.println("Jugador 2: " + jugador2);

                metaTablero = new TableroIndividual();
                tableros = new GrupoTableros();
                juego = new Juego();

                juego.Juego(jugador1, jugador2, metaTablero, tableros);
                break;
            case 2:
                System.out.println("Ranking Jugadores");
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
