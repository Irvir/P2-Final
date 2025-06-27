import Jugadores.Jugador;
import Jugadores.JugadorHumano;
import Jugadores.PCDificil;
import Jugadores.PCFacil;
import Observer.Observador;
import Serializable.Serializacion;
import Tablero.GrupoTableros;
import Tablero.Tablero;
import Tablero.TableroIndividual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import Observer.Observador;

//La clase Juego Implementa la interfaz Observador debido a que notifica cuando hay que incrementar las
// estadísticas de cada Jugador.
public class Juego implements Observador {

    TableroIndividual metaTablero;
    GrupoTableros tableros;

    Serializacion serializacion = new Serializacion();
    // Actualiza las estadísticas del jugador según el resultado recibido.
    @Override
    public void actualizar(Jugador jugador, String tipo) {
        switch (tipo){
            case "ganado":
                jugador.setPartidasGanadas(jugador.getPartidasGanadas() + 1);
                break;
            case "perdido":
                jugador.setPartidasPerdidas(jugador.getPartidasPerdidas() + 1);
                break;
            case "empate":
                jugador.setPartidasEmpatadas(jugador.getPartidasEmpatadas() + 1);
                break;
        }
    }
    // Determina qué jugador inicia, asigna símbolos y prepara los tableros.
    public void Juego(Jugador jugador1, Jugador jugador2, TableroIndividual metaTablero, GrupoTableros tableros) {
        this.metaTablero = metaTablero;
        this.tableros = tableros;

        Scanner in = new Scanner(System.in);
        Random random = new Random();
        System.out.println("Escojiendo los dados para J1");
        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        int sumaDados1 = dado1 + dado2;


        dado1 = random.nextInt(6) + 1;
        dado2 = random.nextInt(6) + 1;
        int sumaDados2 = dado1 + dado2;

        char signoGanador;
        char signoPerdedor;
        char signoAux;

// Lógica para decidir quién escoge el símbolo y quién inicia
        if (sumaDados1 > sumaDados2) {
            System.out.println("Jugador '" + jugador1.getNombreJugador() + "' Tiene que escoger el signo.");
            signoAux = in.next().charAt(0);
            if (signoAux=='x' || signoAux=='X') {
                signoGanador = 'X';
                jugador1.setSimbolo(signoGanador);
                signoPerdedor = 'O';
                jugador2.setSimbolo(signoPerdedor);
            }
            else if (signoAux=='o' || signoAux=='O') {
                signoGanador = 'O';
                jugador2.setSimbolo(signoGanador);
                signoPerdedor = 'X';
                jugador1.setSimbolo(signoPerdedor);
            }

        } else if (sumaDados1 < sumaDados2) {
            if (jugador2 instanceof PCFacil || jugador2 instanceof PCDificil) {
                System.out.println("Jugador '" + jugador2.getNombreJugador() + "' no puede escoger el signo.");
                System.out.println("-- Por lo que su signo será 'O' y el del jugador 1 será 'X'.");
                signoGanador = 'O';
                jugador2.setSimbolo(signoGanador);
                signoPerdedor = 'X';
                jugador1.setSimbolo(signoPerdedor);
            } else {
                System.out.println("Jugador '" + jugador2.getNombreJugador() + "' Tiene que escoger el signo.");
                signoAux = in.next().charAt(0);
                if (signoAux=='x' || signoAux=='X') {
                    signoGanador = 'X';
                    jugador2.setSimbolo(signoGanador);
                    signoPerdedor = 'O';
                    jugador1.setSimbolo(signoPerdedor);
                }
                else if (signoAux=='o' || signoAux=='O') {
                    signoGanador = 'O';
                    jugador1.setSimbolo(signoGanador);
                    signoPerdedor = 'X';
                    jugador2.setSimbolo(signoPerdedor);
                }
            }
        }
        // Si hay empate en los dados, se repite el proceso
        else {
            System.out.println("1. Empate en los dados, se vuelve a lanzar.");
            Juego(jugador1,jugador2, metaTablero, tableros);
        }
        // Inicializa los tableros y registra observadores
        System.out.println("Vista General del Meta - Tablero.Tablero:");
        metaTablero = new TableroIndividual();
        metaTablero.imprimirTablero();
        System.out.println("Tableros de Juego:");
        tableros = new GrupoTableros();
        // Registra observadores en cada tablero individual

        for(TableroIndividual t: tableros.getTableros()){
            t.registraObservador(this);
        }
        tableros.imprimirTablero();
        // Llama a la lógica principal del juego según el jugador que inicia
        if (sumaDados1> sumaDados2) {
            jugar(jugador1, jugador2, metaTablero, tableros,"J1");
        } else {
            jugar(jugador1, jugador2, metaTablero, tableros,"J2");
        }



    }
    //Jugar implementa la lógica del Juego.// Lógica principal del juego: turnos, jugadas, verificación de ganadores y empates.
    public void jugar(Jugador jugador1, Jugador jugador2, TableroIndividual metaTablero, GrupoTableros tableros, String gandador) {
        Scanner in = new Scanner(System.in);
        ArrayList<Integer> planosCompletados = new ArrayList<>();
        ArrayList<Integer> planosIncompletos = new ArrayList<>();
        boolean tableroCompleto = false;
        int resultado = 0;
        int plano, posicion = 0;
        int siguientePlano = -1; // -1 indica que es el primer turno
        boolean turnoJ1 = gandador.equals("J1");
        boolean turnoJ2 = gandador.equals("J2");
        int turno = 0;
        for (int i = 0;i<9;i++){
            planosIncompletos.add(i);
        }
        // Estructura de control para manejar los diferentes casos de inicio de juego
        switch (gandador) {
            // Lógica para cuando J1 inicia
            case "J1":
                //Caso de que J2 sea Humano.
                if (jugador2 instanceof JugadorHumano) {
                    while (true) {
                        if (turnoJ1) {
                            
                            // Determina si el siguiente plano está completado o es el primer turno
                            boolean pedirPlano = (turno == 0) || planosCompletados.contains(siguientePlano - 1);
                            if (pedirPlano) {
                                System.out.println("Turno de '" + jugador1.getNombreJugador() + "'. Escoja plano (1-9) y posición (1-9):");
                                String input = in.nextLine();
                                String[] partes = input.split(" ");
                                plano = Integer.parseInt(partes[0]);
                                posicion = Integer.parseInt(partes[1]);
                                // Mientras el plano esté completo no se puede realizar Jugada
                                while (planosCompletados.contains(plano - 1)) {
                                    System.out.println("Ese plano ya está completado. Elija otro plano y posición (1-9 1-9):");
                                    input = in.nextLine();
                                    partes = input.split(" ");
                                    plano = Integer.parseInt(partes[0]);
                                    posicion = Integer.parseInt(partes[1]);
                                }
                                resultado = jugador1.hacerJugada(plano, posicion, tableros, jugador1.getSimbolo());
                            } else {
                                int planoActual = (siguientePlano == 1) ? 1 : siguientePlano;
                                // Verificar si el plano actual ya está completado
                                if (planosCompletados.contains(planoActual - 1)) {
                                    System.out.println("Ese plano ya está completado. Elija otro plano y posición (1-9 1-9):");
                                    String input = in.nextLine();
                                    String[] partes = input.split(" ");
                                    plano = Integer.parseInt(partes[0]);
                                    posicion = Integer.parseInt(partes[1]);
                                    // Mientras el plano esté completo no se puede realizar Jugada
                                    while (planosCompletados.contains(plano - 1)) {
                                        System.out.println("Ese plano ya está completado. Elija otro plano y posición (1-9 1-9):");
                                        input = in.nextLine();
                                        partes = input.split(" ");
                                        plano = Integer.parseInt(partes[0]);
                                        posicion = Integer.parseInt(partes[1]);
                                    }
                                    resultado = jugador1.hacerJugada(plano, posicion, tableros, jugador1.getSimbolo());
                                } else {
                                    System.out.println("Turno de '" + jugador1.getNombreJugador() + "'. posición (1-9) en el tablero: " + planoActual);
                                    String input = in.nextLine();
                                    posicion = Integer.parseInt(input);
                                    resultado = jugador1.hacerJugada(planoActual, posicion, tableros, jugador1.getSimbolo());
                                    plano = planoActual;
                                }
                            }
                            // Verifica ganador y empate después de la jugada
                            if (tableros.verificarGanador(plano - 1, jugador1.getSimbolo())) {
                                System.out.println("! Tablero Ganado !");
                                tableros.rellenarTablero(jugador1.getSimbolo(), plano - 1);
                                int filaMeta = (plano - 1) / 3;
                                int columnaMeta = (plano - 1) % 3;
                                if (!planosCompletados.contains(plano - 1)) {
                                    planosCompletados.add(plano - 1);

                                }
                                //Recibe Jugada (rellena Casilla) dentro de MetaTablero.
                                metaTablero.recibirJugada(0, filaMeta, columnaMeta, jugador1.getSimbolo());
                                System.out.println("Vista General del Meta - Tablero.Tablero:");
                                metaTablero.imprimirTablero();
                                } else if (metaTablero.empateGlobal(metaTablero)) {
                                System.out.println("Tablero Empatado");
                                tableros.rellenarTablero('/', plano - 1);
                                if (!planosCompletados.contains(plano - 1)) {
                                    planosCompletados.add(plano - 1);
                                     
                                }
                                int filaMeta = (plano - 1) / 3;
                                int columnaMeta = (plano - 1) % 3;
                                metaTablero.recibirJugada(0, filaMeta, columnaMeta, '/');

                                System.out.println("Vista General del Meta - Tablero.Tablero:");
                                metaTablero.imprimirTablero();
                            }
                            // Verifica si el jugador 1 ha ganado en el MetaTablero
                            if (metaTablero.verificarGanador(0, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(1, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(2, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(3, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(4, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(5, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(6, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(7, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(8, jugador1.getSimbolo())) {
                                // Ganó el jugador
                                System.out.println("¡" + jugador1.getNombreJugador() + " ha ganado el juego!");
                                // Actualiza las estadísticas y termina el turno.
                                actualizar(jugador1, "ganado");
                                actualizar(jugador2, "perdido");
                                serializacion.actualizarJugador(jugador1);
                                serializacion.actualizarJugador(jugador2);
                                System.out.println("Estadísticas actualizadas:");
                                serializacion.mostrarJugadores();
                                //Termina el juego.
                                return;

                            }
                            //Verifica dentro del MetaTablero Completo si está empatado.
                            else if (metaTablero.empateGlobal(metaTablero)) {

                                System.out.println("El juego ha terminado en empate.");
                                // Actualiza las estadísticas y termina el turno.

                                actualizar(jugador1, "empate");
                                actualizar(jugador2, "empate");
                                serializacion.actualizarJugador(jugador1);
                                serializacion.actualizarJugador(jugador2);
                                System.out.println("Estadísticas actualizadas:");
                                serializacion.mostrarJugadores();
                                //Termina el juego.

                                return;
                            }
                            tableros.imprimirTablero();
                            siguientePlano = posicion;
                            // Guardar la posición elegida (1-9)
                        }
                        //Jugador 2
                        else {
                            //Verifica si el J2 pide el plano si está el plano siguiente dentro de los planos
                            //Completos.

                            boolean pedirPlano = planosCompletados.contains(siguientePlano - 1);
                            if (pedirPlano) {
                                System.out.println("Turno de '" + jugador2.getNombreJugador() + "'. Escoja plano (1-9) y posición (1-9):");
                                String input = in.nextLine();
                                String[] partes = input.split(" ");
                                plano = Integer.parseInt(partes[0]);
                                posicion = Integer.parseInt(partes[1]);
                                // Mientras el plano esté completo no se puede realizar Jugada
                                while (planosCompletados.contains(plano - 1)) {
                                    System.out.println("Ese plano ya está completado. Elija otro plano y posición (1-9 1-9):");
                                    input = in.nextLine();
                                    partes = input.split(" ");
                                    plano = Integer.parseInt(partes[0]);
                                    posicion = Integer.parseInt(partes[1]);
                                }
                                resultado = jugador2.hacerJugada(plano, posicion, tableros, jugador2.getSimbolo());
                            } else {

                                int planoActual = (siguientePlano == 1) ? 1 : siguientePlano;
                                //Verifica si el plano está completo.
                                if (planosCompletados.contains(planoActual - 1)) {
                                    System.out.println("Ese plano ya está completado. Elija otro plano y posición (1-9 1-9):");
                                    String input = in.nextLine();
                                    String[] partes = input.split(" ");
                                    plano = Integer.parseInt(partes[0]);
                                    posicion = Integer.parseInt(partes[1]);
                                    // Mientras el plano esté completo no se puede realizar Jugada
                                    while (planosCompletados.contains(plano - 1)) {
                                        System.out.println("Ese plano ya está completado. Elija otro plano y posición (1-9 1-9):");
                                        input = in.nextLine();
                                        partes = input.split(" ");
                                        plano = Integer.parseInt(partes[0]);
                                        posicion = Integer.parseInt(partes[1]);
                                    }
                                    resultado = jugador2.hacerJugada(plano, posicion, tableros, jugador2.getSimbolo());
                                }
                                //Juega en el siguiente plano.
                                else {
                                    System.out.println("Turno de '" + jugador2.getNombreJugador() + "'. Juega en el plano " + planoActual + ". Escoja la posición (1-9):");
                                    String input = in.nextLine();
                                    posicion = Integer.parseInt(input);
                                    resultado = jugador2.hacerJugada(planoActual, posicion, tableros, jugador2.getSimbolo());
                                    plano = planoActual;
                                }
                            }
                            // Verificar ganador y empate después de la jugada
                            if (tableros.verificarGanador(plano - 1, jugador2.getSimbolo())) {
                                System.out.println("! Tablero Ganado !");
                                tableros.rellenarTablero(jugador2.getSimbolo(), plano - 1);
                                int filaMeta = (plano - 1) / 3;
                                int columnaMeta = (plano - 1) % 3;
                                if (!planosCompletados.contains(plano - 1)) {
                                    planosCompletados.add(plano - 1);
                                     
                                }
                                metaTablero.recibirJugada(0, filaMeta, columnaMeta, jugador2.getSimbolo());
                                System.out.println("Vista General del Meta - Tablero.Tablero:");
                                metaTablero.imprimirTablero();
                                } else if (metaTablero.empateGlobal(metaTablero)) {
                                        System.out.println("Tablero Empatado");
                                        tableros.rellenarTablero('/', plano - 1);
                                        if (!planosCompletados.contains(plano - 1)) {
                                        planosCompletados.add(plano - 1);
                                         
                                        }

                                int filaMeta = (plano - 1) / 3;
                                int columnaMeta = (plano - 1) % 3;
                                metaTablero.recibirJugada(0, filaMeta, columnaMeta, '/');

                                System.out.println("Vista General del Meta - Tablero.Tablero:");
                                metaTablero.imprimirTablero();
                            }
                            if (metaTablero.verificarGanador(0, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(1, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(2, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(3, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(4, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(5, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(6, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(7, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(8, jugador2.getSimbolo())) {
                                // Ganó el jugador
                                System.out.println("¡" + jugador2.getNombreJugador() + " ha ganado el juego!");
                                actualizar(jugador2, "ganado");
                                actualizar(jugador1, "perdido");
                                serializacion.actualizarJugador(jugador1);
                                serializacion.actualizarJugador(jugador2);
                                System.out.println("Estadísticas actualizadas:");
                                serializacion.mostrarJugadores();
                                return;
                            } else if (metaTablero.empateGlobal(metaTablero)) {

                                System.out.println("El juego ha terminado en empate.");
                                actualizar(jugador1, "empate");
                                actualizar(jugador2, "empate");
                                serializacion.actualizarJugador(jugador1);
                                serializacion.actualizarJugador(jugador2);
                                System.out.println("Estadísticas actualizadas:");
                                serializacion.mostrarJugadores();
                                return; // Terminar el juego
                            }
                            tableros.imprimirTablero();
                            siguientePlano = posicion;
                        }
                        // Alterna turno del Jugador 1.
                        turnoJ1 = !turnoJ1;
                        turno++;
                    }
                }
                //Si J2 es PC-Fácil.
                if (jugador2 instanceof PCFacil) {
                    Random random = new Random();
                    while (true) {
                        if (turnoJ1) {
                            // Determina si el siguiente plano está completado o es el primer turno
                            boolean pedirPlano = (turno == 0) || planosCompletados.contains(siguientePlano - 1);
                            if (pedirPlano) {
                                System.out.println("Turno de '" + jugador1.getNombreJugador() + "'. Escoja plano (1-9) y posición (1-9):");
                                String input = in.nextLine();
                                String[] partes = input.split(" ");
                                plano = Integer.parseInt(partes[0]);
                                posicion = Integer.parseInt(partes[1]);
                                while (planosCompletados.contains(plano - 1)) {
                                    System.out.println("Ese plano ya está completado. Elija otro plano y posición (1-9 1-9):");
                                    input = in.nextLine();
                                    partes = input.split(" ");
                                    plano = Integer.parseInt(partes[0]);
                                    posicion = Integer.parseInt(partes[1]);
                                }
                                resultado = jugador1.hacerJugada(plano, posicion, tableros, jugador1.getSimbolo());
                            } else {
                                int planoActual = (siguientePlano == 1) ? 1 : siguientePlano;
                                // Verifica si el plano actual ya está completado
                                if (planosCompletados.contains(planoActual - 1)) {
                                    System.out.println("Ese plano ya está completado. Elija otro plano y posición (1-9 1-9):");
                                    String input = in.nextLine();
                                    String[] partes = input.split(" ");
                                    plano = Integer.parseInt(partes[0]);
                                    posicion = Integer.parseInt(partes[1]);
                                    while (planosCompletados.contains(plano - 1)) {
                                        System.out.println("Ese plano ya está completado. Elija otro plano y posición (1-9 1-9):");
                                        input = in.nextLine();
                                        partes = input.split(" ");
                                        plano = Integer.parseInt(partes[0]);
                                        posicion = Integer.parseInt(partes[1]);
                                    }
                                    resultado = jugador1.hacerJugada(plano, posicion, tableros, jugador1.getSimbolo());
                                } else {
                                    System.out.println("Turno de '" + jugador1.getNombreJugador() + "'. posición (1-9) en el tablero: " + planoActual);
                                    String input = in.nextLine();
                                    posicion = Integer.parseInt(input);
                                    resultado = jugador1.hacerJugada(planoActual, posicion, tableros, jugador1.getSimbolo());
                                    plano = planoActual;
                                }
                            }
                            // Verifica ganador y empate después de la jugada
                            if (tableros.verificarGanador(plano - 1, jugador1.getSimbolo())) {
                                System.out.println("! Tablero Ganado !" );
                                tableros.rellenarTablero(jugador1.getSimbolo(), plano - 1);
                                int filaMeta = (plano - 1) / 3;
                                int columnaMeta = (plano - 1) % 3;
                                if (!planosCompletados.contains(plano - 1)) {
                                    planosCompletados.add(plano - 1);

                                }
                                metaTablero.recibirJugada(0, filaMeta, columnaMeta, jugador1.getSimbolo());
                                System.out.println("Vista General del Meta - Tablero.Tablero:");
                                metaTablero.imprimirTablero();
                            } else if (tableros.getTableros().get(plano -1).verificarEmpate(0)) {
                                System.out.println("Tablero Empatado");
                                tableros.rellenarTablero('/', plano - 1);
                                if (!planosCompletados.contains(plano - 1)) {
                                    planosCompletados.add(plano - 1);
                                     
                                }
                                int filaMeta = (plano - 1) / 3;
                                int columnaMeta = (plano - 1) % 3;
                                metaTablero.recibirJugada(0, filaMeta, columnaMeta, '/');

                                System.out.println("Vista General del Meta - Tablero.Tablero:");
                                metaTablero.imprimirTablero();
                            }
                            if (metaTablero.verificarGanador(0, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(1, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(2, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(3, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(4, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(5, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(6, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(7, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(8, jugador1.getSimbolo())) {
                                // Ganó el jugador
                                System.out.println("¡" + jugador1.getNombreJugador() + " ha ganado el juego!");
                                actualizar(jugador2, "ganado");
                                actualizar(jugador1, "perdido");
                                serializacion.actualizarJugador(jugador1);
                                serializacion.actualizarJugador(jugador2);
                                System.out.println("Estadísticas actualizadas:");
                                serializacion.mostrarJugadores();
                                return;
                            } else if (metaTablero.empateGlobal(metaTablero)) {

                                System.out.println("El juego ha terminado en empate.");
                                actualizar(jugador1, "empate");
                                actualizar(jugador2, "empate");
                                serializacion.actualizarJugador(jugador1);
                                serializacion.actualizarJugador(jugador2);
                                System.out.println("Estadísticas actualizadas:");
                                serializacion.mostrarJugadores();
                                return; // Termina el juego
                            }
                            tableros.imprimirTablero();
                            siguientePlano = posicion; // Guarda la posición elegida (1-9)
                        }
                        //Turno de J2.
                        else {
                            //Pide Coordenadas Aleatorias, si estas están ocupadas pierde el Turno.

                            boolean pedirPlano = planosCompletados.contains(siguientePlano - 1);
                            if (pedirPlano) {
                                System.out.println("Turno de '" + jugador2.getNombreJugador() + "'. Se escogerá un plano (1-9) y posición (1-9) aleatoria:");
                                plano = random.nextInt(9)+1;
                                posicion = random.nextInt(9)+1;
                                System.out.println("Plano: " + plano + ", Posición: " + posicion);

                                while (planosCompletados.contains(plano - 1)) {
                                    System.out.println("Ese plano ya está completado. Elija otro plano.");
                                    plano = random.nextInt(9)+1;
                                    posicion = random.nextInt(9)+1;

                                    System.out.println("PC - Fácil escogió plano: "+plano+" posición: "+posicion);
                                }
                                resultado = jugador2.hacerJugada(plano, posicion, tableros, jugador2.getSimbolo());
                            } else {
                                int planoActual = (siguientePlano == 1) ? 1 : siguientePlano;
                                if (planosCompletados.contains(planoActual - 1)) {
                                    System.out.println("Plano: " + planoActual + ", Posición: " + posicion);

                                    System.out.println("Ese plano ya está completado. Se elegirá otro plano y posición (1-9 1-9) aleatorio:");
                                    plano = random.nextInt(9)+1;
                                    posicion = random.nextInt(9)+1;
                                    System.out.println("Plano: " + plano + ", Posición: " + posicion);

                                    while (planosCompletados.contains(plano - 1)) {
                                        System.out.println("Ese plano ya está completado. Elija otro plano.");
                                        plano = random.nextInt(9)+1;
                                        posicion = random.nextInt(9)+1;

                                        System.out.println("PC - Fácil escogió plano: "+plano+" posición: "+posicion);
                                    }
                                    resultado = jugador2.hacerJugada(plano, posicion, tableros, jugador2.getSimbolo());
                                } else {
                                    System.out.println("Turno de '" + jugador2.getNombreJugador() + "'. Juega en el plano " + planoActual + ". Escoja la posición (1-9):");

                                    posicion = random.nextInt(9)+1;
                                    System.out.println("Plano: " + planoActual + ", Posición: " + posicion);

                                    resultado = jugador2.hacerJugada(planoActual, posicion, tableros, jugador2.getSimbolo());
                                    plano = planoActual;
                                }
                            }
                            // Verifica ganador y empate después de la jugada
                            if (tableros.verificarGanador(plano - 1, jugador2.getSimbolo())) {
                                System.out.println("! Tablero Ganado !");
                                tableros.rellenarTablero(jugador2.getSimbolo(), plano - 1);
                                int filaMeta = (plano - 1) / 3;
                                int columnaMeta = (plano - 1) % 3;
                                if (!planosCompletados.contains(plano - 1)) {
                                    planosCompletados.add(plano - 1);

                                }
                                metaTablero.recibirJugada(0, filaMeta, columnaMeta, jugador2.getSimbolo());
                                System.out.println("Vista General del Meta - Tablero.Tablero:");
                                metaTablero.imprimirTablero();
                            } else if (tableros.getTableros().get(plano -1).verificarEmpate(0)) {

                                System.out.println("Tablero Empatado");
                                tableros.rellenarTablero('/', plano - 1);
                                if (!planosCompletados.contains(plano - 1)) {
                                planosCompletados.add(plano - 1);
                                                                 }
                                tableros.rellenarTablero('/', plano - 1);

                                int filaMeta = (plano - 1) / 3;
                                int columnaMeta = (plano - 1) % 3;
                                metaTablero.recibirJugada(0, filaMeta, columnaMeta, '/');
                                System.out.println("Vista General del Meta - Tablero.Tablero:");
                                metaTablero.imprimirTablero();
                            }
                            if (metaTablero.verificarGanador(0, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(1, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(2, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(3, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(4, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(5, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(6, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(7, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(8, jugador2.getSimbolo())) {
                                // Ganó el jugador
                                System.out.println("¡" + jugador2.getNombreJugador() + " ha ganado el juego!");
                                actualizar(jugador2, "ganado");
                                actualizar(jugador1, "perdido");
                                serializacion.actualizarJugador(jugador1);
                                serializacion.actualizarJugador(jugador2);
                                System.out.println("Estadísticas actualizadas:");
                                serializacion.mostrarJugadores();
                                return;
                            } else if (metaTablero.empateGlobal(metaTablero)) {

                                System.out.println("El juego ha terminado en empate.");
                                actualizar(jugador1, "empate");
                                actualizar(jugador2, "empate");
                                serializacion.actualizarJugador(jugador1);
                                serializacion.actualizarJugador(jugador2);
                                System.out.println("Estadísticas actualizadas:");
                                serializacion.mostrarJugadores();
                                return; // Termina el juego
                            }
                            // Esperar 3 segundos antes de la siguiente jugada
                            try{
                                Thread.sleep(3000);
                            } catch (InterruptedException e) {
                                System.out.println("Error al esperar entre jugadas: " + e.getMessage());
                            }

                            siguientePlano = posicion;
                        }
                        // Alternar turno de J1.
                        turnoJ1 = !turnoJ1;
                        turno++;
                    }

                }
                //Si J2 es PC-Difícil.
                else{
                    while (true) {
                        if (turnoJ1) {
                            // Determina si el siguiente plano está completado o es el primer turno
                            boolean pedirPlano = (turno == 0) || planosCompletados.contains(siguientePlano - 1);
                            if (pedirPlano) {
                                System.out.println("Turno de '" + jugador1.getNombreJugador() + "'. Escoja plano (1-9) y posición (1-9):");
                                String input = in.nextLine();
                                String[] partes = input.split(" ");
                                plano = Integer.parseInt(partes[0]);
                                posicion = Integer.parseInt(partes[1]);
                                while (planosCompletados.contains(plano - 1)) {
                                    System.out.println("Ese plano ya está completado. Elija otro plano.");
                                    input = in.nextLine();
                                    partes = input.split(" ");
                                    plano = Integer.parseInt(partes[0]);
                                    posicion = Integer.parseInt(partes[1]);
                                }
                                resultado = jugador1.hacerJugada(plano, posicion, tableros, jugador1.getSimbolo());
                            } else {
                                int planoActual = (siguientePlano == 1) ? 1 : siguientePlano;
                                // Verifica si el plano actual ya está completado
                                if (planosCompletados.contains(planoActual - 1)) {
                                    System.out.println("Ese plano ya está completado. Elija otro plano y posición (1-9 1-9):");
                                    String input = in.nextLine();
                                    String[] partes = input.split(" ");
                                    plano = Integer.parseInt(partes[0]);
                                    posicion = Integer.parseInt(partes[1]);
                                    while (planosCompletados.contains(plano - 1)) {
                                        System.out.println("Ese plano también está completado. Elija otro.");
                                        input = in.nextLine();
                                        partes = input.split(" ");
                                        plano = Integer.parseInt(partes[0]);
                                        posicion = Integer.parseInt(partes[1]);
                                    }
                                    resultado = jugador1.hacerJugada(plano, posicion, tableros, jugador1.getSimbolo());
                                } else {
                                    System.out.println("Turno de '" + jugador1.getNombreJugador() + "'. posición (1-9) en el tablero: " + planoActual);
                                    String input = in.nextLine();
                                    posicion = Integer.parseInt(input);
                                    resultado = jugador1.hacerJugada(planoActual, posicion, tableros, jugador1.getSimbolo());
                                    plano = planoActual;
                                }
                            }
                            // Verifica ganador y empate después de la jugada
                            if (tableros.verificarGanador(plano - 1, jugador1.getSimbolo())) {
                                System.out.println("! Tablero Ganado !");
                                tableros.rellenarTablero(jugador1.getSimbolo(), plano - 1);
                                int filaMeta = (plano - 1) / 3;
                                int columnaMeta = (plano - 1) % 3;
                                if (!planosCompletados.contains(plano - 1)) {
                                    planosCompletados.add(plano - 1);
                                     
                                }
                                metaTablero.recibirJugada(0, filaMeta, columnaMeta, jugador1.getSimbolo());
                                System.out.println("Vista General del Meta - Tablero.Tablero:");
                                metaTablero.imprimirTablero();
                            } else if (tableros.getTableros().get(plano -1).verificarEmpate(0)) {

                                System.out.println("Tablero Empatado");
                                tableros.rellenarTablero('/', plano - 1);
                                if (!planosCompletados.contains(plano - 1)) {
                                    planosCompletados.add(plano - 1);
                                     
                                }
                                int filaMeta = (plano - 1) / 3;
                                int columnaMeta = (plano - 1) % 3;
                                metaTablero.recibirJugada(0, filaMeta, columnaMeta, '/');

                                System.out.println("Vista General del Meta - Tablero.Tablero:");
                                metaTablero.imprimirTablero();
                            }
                            if (metaTablero.verificarGanador(0, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(1, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(2, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(3, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(4, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(5, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(6, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(7, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(8, jugador1.getSimbolo())) {
                                // Ganó el jugador
                                System.out.println("¡" + jugador1.getNombreJugador() + " ha ganado el juego!");
                                actualizar(jugador1, "ganado");
                                actualizar(jugador2, "perdido");
                                serializacion.actualizarJugador(jugador1);
                                serializacion.actualizarJugador(jugador2);
                                System.out.println("Estadísticas actualizadas:");
                                serializacion.mostrarJugadores();
                                return;

                            } else if (metaTablero.empateGlobal(metaTablero)) {

                                System.out.println("El juego ha terminado en empate.");
                                actualizar(jugador1, "empate");
                                actualizar(jugador2, "empate");
                                serializacion.actualizarJugador(jugador1);
                                serializacion.actualizarJugador(jugador2);
                                System.out.println("Estadísticas actualizadas:");
                                serializacion.mostrarJugadores();
                                return; // Termina el juego.
                            }
                            tableros.imprimirTablero();
                            siguientePlano = posicion; // Guardar la posición elegida (1-9).
                        }
                        //Turno de J2.
                        else {
                            //Crea instancia de pcDifícil, para poder utilizar
                            PCDificil pcDificil = (PCDificil) jugador2;
                            boolean pedirPlano = planosCompletados.contains(siguientePlano - 1);
                            if (pedirPlano) {
                                System.out.println("Turno de '" + jugador2.getNombreJugador() + "'. Escoja plano (1-9) y posición (1-9):");
                                //Realiza un análisis y después realizar la Jugada.
                                plano = pcDificil.hacerJugadaDificilGrupo(tableros, jugador2.getSimbolo());
                                posicion = pcDificil.hacerJugadaDificilPlano(plano - 1, jugador2.getSimbolo(), tableros.getTableros().get(plano - 1).getTablero());
                                System.out.println("Plano: " + plano + ", Posición: " + posicion);

                                while (planosCompletados.contains(plano - 1)) {
                                    System.out.println("Ese plano ya está completado. Elija otro plano.");
                                    plano = pcDificil.hacerJugadaDificilGrupo(tableros, jugador2.getSimbolo());
                                    posicion = pcDificil.hacerJugadaDificilPlano(plano - 1, jugador2.getSimbolo(), tableros.getTableros().get(plano - 1).getTablero());

                                    System.out.println("PC - Difícil escogió plano: "+plano+ " posición: "+ posicion);
                                }
                                resultado = jugador2.hacerJugada(plano, posicion, tableros, jugador2.getSimbolo());
                            } else {

                                int planoActual = (siguientePlano == 1) ? 1 : siguientePlano;
                                if (planosCompletados.contains(planoActual - 1)) {
                                    System.out.println("Ese plano ya está completado. Elija otro plano y posición (1-9 1-9):");

                                    plano = pcDificil.hacerJugadaDificilGrupo(tableros, jugador2.getSimbolo());
                                    posicion = pcDificil.hacerJugadaDificilPlano(plano, jugador2.getSimbolo(), tableros.getTableros().get(plano - 1).getTablero());
                                    System.out.println("Plano: " + plano + ", Posición: " + posicion);

                                    if (planosCompletados.contains(plano - 1)) {
                                        System.out.println("Ese plano también está completado. Elija otro.");
                                        continue;
                                    }
                                    resultado = jugador2.hacerJugada(plano, posicion, tableros, jugador2.getSimbolo());
                                } else {
                                    System.out.println("Turno de '" + jugador2.getNombreJugador() + "'. Juega en el plano " + planoActual + ". Escoja la posición (1-9):");

                                    posicion = pcDificil.hacerJugadaDificilPlano(planoActual, jugador2.getSimbolo(), tableros.getTableros().get(planoActual - 1).getTablero());
                                    System.out.println("Plano: " + planoActual + ", Posición: " + posicion);

                                    resultado = jugador2.hacerJugada(planoActual, posicion, tableros, jugador2.getSimbolo());
                                    plano = planoActual;
                                }
                            }
                            // Verificar ganador y empate después de la jugada
                            if (tableros.verificarGanador(plano - 1, jugador2.getSimbolo())) {
                                System.out.println("! Tablero Ganado !");
                                tableros.rellenarTablero(jugador2.getSimbolo(), plano - 1);
                                int filaMeta = (plano - 1) / 3;
                                int columnaMeta = (plano - 1) % 3;
                                if (!planosCompletados.contains(plano - 1)) {
                                    planosCompletados.add(plano - 1);
                                     
                                }
                                metaTablero.recibirJugada(0, filaMeta, columnaMeta, jugador2.getSimbolo());
                                System.out.println("Vista General del Meta - Tablero.Tablero:");
                                metaTablero.imprimirTablero();
                            } else if (tableros.getTableros().get(plano -1).verificarEmpate(0)) {

                                System.out.println("Tablero Empatado");
                                tableros.rellenarTablero('/', plano - 1);
                                if (!planosCompletados.contains(plano - 1)) {
                                planosCompletados.add(plano - 1);
                                                                 }

                                int filaMeta = (plano - 1) / 3;
                                int columnaMeta = (plano - 1) % 3;
                                metaTablero.recibirJugada(0, filaMeta, columnaMeta, '/');

                                System.out.println("Vista General del Meta - Tablero.Tablero:");
                                metaTablero.imprimirTablero();
                            }
                            if (metaTablero.verificarGanador(0, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(1, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(2, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(3, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(4, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(5, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(6, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(7, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(8, jugador2.getSimbolo())) {
                                // Ganó el jugador J2
                                System.out.println("¡" + jugador2.getNombreJugador() + " ha ganado el juego!");
                                actualizar(jugador2, "ganado");
                                actualizar(jugador1, "perdido");
                                serializacion.actualizarJugador(jugador1);
                                serializacion.actualizarJugador(jugador2);
                                System.out.println("Estadísticas actualizadas:");
                                serializacion.mostrarJugadores();
                                return;
                            } else if (metaTablero.empateGlobal(metaTablero)) {

                                System.out.println("El juego ha terminado en empate.");
                                actualizar(jugador1, "empate");
                                actualizar(jugador2, "empate");
                                serializacion.actualizarJugador(jugador1);
                                serializacion.actualizarJugador(jugador2);
                                System.out.println("Estadísticas actualizadas:");
                                serializacion.mostrarJugadores();
                                return; // Termina el juego
                            }
                            try{
                                Thread.sleep(3000); // Esperar 3 segundos antes de la siguiente jugada
                            } catch (InterruptedException e) {
                                System.out.println("Error al esperar entre jugadas: " + e.getMessage());
                            }
                            tableros.imprimirTablero();
                            siguientePlano = posicion;
                        }
                        // Alternar turno
                        turnoJ1 = !turnoJ1;
                        turno++;
                        // Aquí puedes agregar lógica para verificar si el juego terminó
                    }


                }

            case "J2":
                if (jugador2 instanceof JugadorHumano){
                    while (true) {
                    if (turnoJ2) {
                        // Determinar si el siguiente plano está completado o es el primer turno
                        boolean pedirPlano = (turno == 0) || planosCompletados.contains(siguientePlano - 1);
                        if (pedirPlano) {
                            System.out.println("Turno de '" + jugador2.getNombreJugador() + "'. Escoja plano (1-9) y posición (1-9):");
                            String input = in.nextLine();
                            String[] partes = input.split(" ");
                            plano = Integer.parseInt(partes[0]);
                            posicion = Integer.parseInt(partes[1]);
                            while (planosCompletados.contains(plano - 1)) {
                                System.out.println("Ese plano ya está completado. Elija otro plano.");
                                input = in.nextLine();
                                partes = input.split(" ");
                                plano = Integer.parseInt(partes[0]);
                                posicion = Integer.parseInt(partes[1]);
                            }
                            resultado = jugador2.hacerJugada(plano, posicion, tableros, jugador2.getSimbolo());
                        } else {
                            int planoActual = (siguientePlano == 1) ? 1 : siguientePlano;
                            // Verificar si el plano actual ya está completado
                            if (planosCompletados.contains(planoActual - 1)) {
                                System.out.println("Ese plano ya está completado. Elija otro plano y posición (1-9 1-9):");
                                String input = in.nextLine();
                                String[] partes = input.split(" ");
                                plano = Integer.parseInt(partes[0]);
                                posicion = Integer.parseInt(partes[1]);
                                while (planosCompletados.contains(plano - 1)) {
                                    System.out.println("Ese plano también está completado. Elija otro.");
                                    input = in.nextLine();
                                    partes = input.split(" ");
                                    plano = Integer.parseInt(partes[0]);
                                    posicion = Integer.parseInt(partes[1]);
                                }
                                resultado = jugador2.hacerJugada(plano, posicion, tableros, jugador2.getSimbolo());
                            } else {
                                System.out.println("Turno de '" + jugador2.getNombreJugador() + "'. posición (1-9) en el tablero: " + planoActual);
                                String input = in.nextLine();
                                posicion = Integer.parseInt(input);
                                resultado = jugador2.hacerJugada(planoActual, posicion, tableros, jugador2.getSimbolo());
                                plano = planoActual;
                            }
                        }
                        // Verificar ganador y empate después de la jugada
                        if (tableros.verificarGanador(plano - 1, jugador2.getSimbolo())) {
                            System.out.println("! Tablero Ganado !");
                            tableros.rellenarTablero(jugador2.getSimbolo(), plano - 1);
                            int filaMeta = (plano - 1) / 3;
                            int columnaMeta = (plano - 1) % 3;
                            if (!planosCompletados.contains(plano - 1)) {
                                planosCompletados.add(plano - 1);
                            }
                            metaTablero.recibirJugada(0, filaMeta, columnaMeta, jugador2.getSimbolo());
                            System.out.println("Vista General del Meta - Tablero.Tablero:");
                            metaTablero.imprimirTablero();
                        } else if (tableros.verificarEmpate(plano - 1)) {
                            System.out.println("Tablero Empatado");
                            tableros.rellenarTablero('-', plano - 1);
                            if (!planosCompletados.contains(plano - 1)) {

                                planosCompletados.add(plano - 1);
                            }
                            int filaMeta = (plano - 1) / 3;
                            int columnaMeta = (plano - 1) % 3;
                            metaTablero.recibirJugada(0, filaMeta, columnaMeta, '/');

                            System.out.println("Vista General del Meta - Tablero.Tablero:");
                            metaTablero.imprimirTablero();
                        }
                        if (metaTablero.verificarGanador(0, jugador2.getSimbolo()) ||
                                metaTablero.verificarGanador(1, jugador2.getSimbolo()) ||
                                metaTablero.verificarGanador(2, jugador2.getSimbolo()) ||
                                metaTablero.verificarGanador(3, jugador2.getSimbolo()) ||
                                metaTablero.verificarGanador(4, jugador2.getSimbolo()) ||
                                metaTablero.verificarGanador(5, jugador2.getSimbolo()) ||
                                metaTablero.verificarGanador(6, jugador2.getSimbolo()) ||
                                metaTablero.verificarGanador(7, jugador2.getSimbolo()) ||
                                metaTablero.verificarGanador(8, jugador2.getSimbolo())) {
                            // Ganó el jugador
                            System.out.println("¡" + jugador2.getNombreJugador() + " ha ganado el juego!");
                            actualizar(jugador2, "ganado");
                            actualizar(jugador1, "perdido");
                            serializacion.actualizarJugador(jugador1);
                            serializacion.actualizarJugador(jugador2);
                            System.out.println("Estadísticas actualizadas:");
                            serializacion.mostrarJugadores();
                            return;

                            } else if (metaTablero.empateGlobal(metaTablero)) {
                            System.out.println("El juego ha terminado en empate.");
                            actualizar(jugador1, "empate");
                            actualizar(jugador2, "empate");
                            serializacion.actualizarJugador(jugador1);
                            serializacion.actualizarJugador(jugador2);
                            System.out.println("Estadísticas actualizadas:");
                            serializacion.mostrarJugadores();
                            return; // Terminar el juego
                        }
                        tableros.imprimirTablero();
                        siguientePlano = posicion; // Guardar la posición elegida (1-9)
                    }
                    //Jugador 1
                    else {
                        boolean pedirPlano = planosCompletados.contains(siguientePlano - 1);
                        if (pedirPlano) {
                            System.out.println("Turno de '" + jugador1.getNombreJugador() + "'. Escoja plano (1-9) y posición (1-9):");
                            String input = in.nextLine();
                            String[] partes = input.split(" ");
                            plano = Integer.parseInt(partes[0]);
                            posicion = Integer.parseInt(partes[1]);
                            while (planosCompletados.contains(plano - 1)) {
                                System.out.println("Ese plano ya está completado. Elija otro plano.");
                                input = in.nextLine();
                                partes = input.split(" ");
                                plano = Integer.parseInt(partes[0]);
                                posicion = Integer.parseInt(partes[1]);
                            }
                            resultado = jugador1.hacerJugada(plano, posicion, tableros, jugador1.getSimbolo());
                        } else {
                            int planoActual = (siguientePlano == 1) ? 1 : siguientePlano;
                            if (planosCompletados.contains(planoActual - 1)) {
                                System.out.println("Ese plano ya está completado. Elija otro plano y posición (1-9 1-9):");
                                String input = in.nextLine();
                                String[] partes = input.split(" ");
                                plano = Integer.parseInt(partes[0]);
                                posicion = Integer.parseInt(partes[1]);
                                while (planosCompletados.contains(plano - 1)) {
                                    System.out.println("Ese plano también está completado. Elija otro.");
                                    input = in.nextLine();
                                    partes = input.split(" ");
                                    plano = Integer.parseInt(partes[0]);
                                    posicion = Integer.parseInt(partes[1]);
                                }
                                resultado = jugador1.hacerJugada(plano, posicion, tableros, jugador1.getSimbolo());
                            } else {
                                System.out.println("Turno de '" + jugador1.getNombreJugador() + "'. Juega en el plano " + planoActual + ". Escoja la posición (1-9):");
                                String input = in.nextLine();
                                posicion = Integer.parseInt(input);
                                resultado = jugador1.hacerJugada(planoActual, posicion, tableros, jugador1.getSimbolo());
                                plano = planoActual;
                            }
                        }
                        // Verificar ganador y empate después de la jugada
                        if (tableros.verificarGanador(plano - 1, jugador1.getSimbolo())) {
                            System.out.println("! Tablero Ganado !");
                            tableros.rellenarTablero(jugador1.getSimbolo(), plano - 1);
                            int filaMeta = (plano - 1) / 3;
                            int columnaMeta = (plano - 1) % 3;
                            if (!planosCompletados.contains(plano - 1)) {
                                planosCompletados.add(plano - 1);
                            }
                            metaTablero.recibirJugada(0, filaMeta, columnaMeta, jugador1.getSimbolo());
                            System.out.println("Vista General del Meta - Tablero.Tablero:");
                            metaTablero.imprimirTablero();
                        } else if (tableros.verificarEmpate(plano - 1)) {
                            System.out.println("Tablero Empatado");
                            tableros.rellenarTablero('/', plano - 1);
                            if (!planosCompletados.contains(plano - 1)) {
                                planosCompletados.add(plano - 1);
                            }

                            int filaMeta = (plano - 1) / 3;
                            int columnaMeta = (plano - 1) % 3;
                            metaTablero.recibirJugada(0, filaMeta, columnaMeta, '/');


                            System.out.println("Vista General del Meta - Tablero.Tablero:");
                            metaTablero.imprimirTablero();
                        }
                        if (metaTablero.verificarGanador(0, jugador1.getSimbolo()) ||
                                metaTablero.verificarGanador(1, jugador1.getSimbolo()) ||
                                metaTablero.verificarGanador(2, jugador1.getSimbolo()) ||
                                metaTablero.verificarGanador(3, jugador1.getSimbolo()) ||
                                metaTablero.verificarGanador(4, jugador1.getSimbolo()) ||
                                metaTablero.verificarGanador(5, jugador1.getSimbolo()) ||
                                metaTablero.verificarGanador(6, jugador1.getSimbolo()) ||
                                metaTablero.verificarGanador(7, jugador1.getSimbolo()) ||
                                metaTablero.verificarGanador(8, jugador1.getSimbolo())) {
                            // Ganó el jugador
                            System.out.println("¡" + jugador1.getNombreJugador() + " ha ganado el juego!");
                            actualizar(jugador1, "ganado");
                            actualizar(jugador2, "perdido");
                            serializacion.actualizarJugador(jugador1);
                            serializacion.actualizarJugador(jugador2);
                            System.out.println("Estadísticas actualizadas:");
                            serializacion.mostrarJugadores();
                            return;
                        } else if (metaTablero.empateGlobal(metaTablero)) {
                            System.out.println("El juego ha terminado en empate.");
                            actualizar(jugador1, "empate");
                            actualizar(jugador2, "empate");
                            serializacion.actualizarJugador(jugador1);
                            serializacion.actualizarJugador(jugador2);
                            System.out.println("Estadísticas actualizadas:");
                            serializacion.mostrarJugadores();
                            return; // Terminar el juego
                        }

                        tableros.imprimirTablero();
                        siguientePlano = posicion;
                    }
                    // Alternar turno
                    turnoJ2 = !turnoJ2;
                    turno++;
                    // Aquí puedes agregar lógica para verificar si el juego terminó
                }}
                if (jugador2 instanceof PCFacil){
                    Random random = new Random();
                    while (true) {
                        if (turnoJ2) {
                            // Determinar si el siguiente plano está completado o es el primer turno
                            boolean pedirPlano = (turno == 0) || siguientePlano == -1 || planosCompletados.contains(siguientePlano - 1);


                            if (pedirPlano) {
                                System.out.println("Turno de '" + jugador2.getNombreJugador() + "'. Se escogerá un plano (1-9) y posición (1-9) aleatoria:");

                                plano = random.nextInt(9) + 1;
                                posicion = random.nextInt(9) + 1;
                                System.out.println("Plano: " + plano + ", Posición: " + posicion);

                                while (planosCompletados.contains(plano - 1)) {
                                    System.out.println("Ese plano ya está completado. Elija otro plano.");
                                    continue;
                                }
                                resultado = jugador2.hacerJugada(plano, posicion, tableros, jugador2.getSimbolo());
                            } else {
                                int planoActual = (siguientePlano == 1) ? 1 : siguientePlano;
                                if (planosCompletados.contains(planoActual - 1)) {
                                    System.out.println("Ese plano ya está completado. Se elegirá otro plano y posición (1-9 1-9) aleatorio:");
                                    plano = random.nextInt(9) + 1;
                                    posicion = random.nextInt(9) + 1;
                                    while (planosCompletados.contains(plano - 1)) {
                                        System.out.println("Ese plano también está completado. Elija otro.");
                                        plano = random.nextInt(9) + 1;
                                        posicion = random.nextInt(9) + 1;
                                        System.out.println("PC - Fácil escoigió plano: "+planoActual + " posición: "+posicion);

                                    }
                                    resultado = jugador2.hacerJugada(plano, posicion, tableros, jugador2.getSimbolo());
                                } else {
                                    System.out.println("Turno de '" + jugador2.getNombreJugador() + "'. Juega en el plano " + planoActual + ". Escoja la posición (1-9):");
                                    posicion = random.nextInt(9) + 1;
                                    System.out.println("PC - Fácil escoigió plano: "+planoActual + " posición: "+posicion);
                                    resultado = jugador2.hacerJugada(planoActual, posicion, tableros, jugador2.getSimbolo());
                                    plano = planoActual;
                                }
                            }
                            // Verificar ganador y empate después de la jugada
                            if (tableros.verificarGanador(plano - 1, jugador2.getSimbolo())) {
                                System.out.println("! Tablero Ganado !" );
                                tableros.rellenarTablero(jugador2.getSimbolo(), plano - 1);
                                int filaMeta = (plano - 1) / 3;
                                int columnaMeta = (plano - 1) % 3;
                                if (!planosCompletados.contains(plano - 1)) {
                                    planosCompletados.add(plano - 1);
                                     
                                }
                                metaTablero.recibirJugada(0, filaMeta, columnaMeta, jugador2.getSimbolo());
                                System.out.println("Vista General del Meta - Tablero.Tablero:");
                                metaTablero.imprimirTablero();
                            } else if (tableros.getTableros().get(plano -1).verificarEmpate(0)) {
                                tableros.rellenarTablero('/', plano - 1);
                                System.out.println("Tablero Empatado");
                                int filaMeta = (plano - 1) / 3;
                                int columnaMeta = (plano - 1) % 3;
                                metaTablero.recibirJugada(0, filaMeta, columnaMeta, '/');

                                if (!planosCompletados.contains(plano - 1)) {

                                    planosCompletados.add(plano - 1);
                                     
                                }
                               System.out.println("Vista General del Meta - Tablero.Tablero:");
                                metaTablero.imprimirTablero();
                            }
                            if (metaTablero.verificarGanador(0, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(1, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(2, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(3, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(4, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(5, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(6, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(7, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(8, jugador2.getSimbolo())) {
                                // Ganó el jugador
                                System.out.println("¡" + jugador2.getNombreJugador() + " ha ganado el juego!");
                                actualizar(jugador2, "ganado");
                                actualizar(jugador1, "perdido");
                                serializacion.actualizarJugador(jugador1);
                                serializacion.actualizarJugador(jugador2);
                                System.out.println("Estadísticas actualizadas:");
                                serializacion.mostrarJugadores();
                                return;
                            } else if (metaTablero.empateGlobal(metaTablero)) {
                                System.out.println("El juego ha terminado en empate.");
                                actualizar(jugador1, "empate");
                                actualizar(jugador2, "empate");
                                serializacion.actualizarJugador(jugador1);
                                serializacion.actualizarJugador(jugador2);
                                System.out.println("Estadísticas actualizadas:");
                                serializacion.mostrarJugadores();
                                return; // Terminar el juego
                            }
                            try{
                                Thread.sleep(3000); // Esperar 3 segundos antes de la siguiente jugada
                            } catch (InterruptedException e) {
                                System.out.println("Error al esperar entre jugadas: " + e.getMessage());
                            }
                            tableros.imprimirTablero();
                            siguientePlano = posicion; // Guardar la posición elegida (1-9)
                        }
                        //Turno J2
                        boolean pedirPlano = planosCompletados.contains(siguientePlano - 1);
                        if (pedirPlano) {
                            System.out.println("Turno de '" + jugador2.getNombreJugador() + "'. Escoja plano (1-9) y posición (1-9):");
                            String input = in.nextLine();
                            String[] partes = input.split(" ");
                            plano = Integer.parseInt(partes[0]);
                            posicion = Integer.parseInt(partes[1]);
                            System.out.println("Plano: " + plano + ", Posición: " + posicion);

                            while (planosCompletados.contains(plano - 1)) {
                                System.out.println("Ese plano ya está completado. Elija otro plano.");
                                input = in.nextLine();
                                partes = input.split(" ");
                                plano = Integer.parseInt(partes[0]);
                                posicion = Integer.parseInt(partes[1]);
                            }
                            resultado = jugador2.hacerJugada(plano, posicion, tableros, jugador2.getSimbolo());
                        }

                        else {
                            int planoActual = (siguientePlano == 1) ? 1 : siguientePlano;
                            if (planosCompletados.contains(planoActual - 1)) {
                                System.out.println("Ese plano ya está completado. Elija otro plano y posición (1-9 1-9):");
                                String input = in.nextLine();
                                String[] partes = input.split(" ");
                                plano = Integer.parseInt(partes[0]);
                                posicion = Integer.parseInt(partes[1]);
                                System.out.println("Plano: " + plano + ", Posición: " + posicion);

                                while (planosCompletados.contains(plano - 1)) {
                                    System.out.println("Ese plano también está completado. Elija otro.");
                                    input = in.nextLine();
                                    partes = input.split(" ");
                                    plano = Integer.parseInt(partes[0]);
                                    posicion = Integer.parseInt(partes[1]);
                                    System.out.println("Plano: " + plano + ", Posición: " + posicion);
                                }
                                resultado = jugador1.hacerJugada(plano, posicion, tableros, jugador1.getSimbolo());
                            } else {
                                System.out.println("Turno de '" + jugador1.getNombreJugador() + "'. posición (1-9) en el tablero: " + planoActual);
                                String input = in.nextLine();
                                posicion = Integer.parseInt(input);
                                System.out.println("Plano: " + planoActual + ", Posición: " + posicion);

                                resultado = jugador1.hacerJugada(planoActual, posicion, tableros, jugador1.getSimbolo());
                                plano = planoActual;
                            }
                        }
                            // Verificar ganador y empate después de la jugada
                        if (tableros.verificarGanador(plano - 1, jugador1.getSimbolo())) {
                            System.out.println("! Tablero Ganado !");
                                tableros.rellenarTablero(jugador1.getSimbolo(), plano - 1);
                                int filaMeta = (plano - 1) / 3;
                                int columnaMeta = (plano - 1) % 3;
                                if (!planosCompletados.contains(plano - 1)) {
                                    planosCompletados.add(plano - 1);
                                     
                                }
                                metaTablero.recibirJugada(0, filaMeta, columnaMeta, jugador1.getSimbolo());
                                System.out.println("Vista General del Meta - Tablero.Tablero:");
                                metaTablero.imprimirTablero();
                        } else if (tableros.getTableros().get(plano -1).verificarEmpate(0)) {
                            System.out.println("Tablero Empatado");
                            if (!planosCompletados.contains(plano - 1)) {

                                planosCompletados.add(plano - 1);
                            }
                                metaTablero.rellenarTablero('/', plano - 1);
                                System.out.println("Vista General del Meta - Tablero.Tablero:");
                                metaTablero.imprimirTablero();
                            }
                            if (metaTablero.verificarGanador(0, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(1, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(2, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(3, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(4, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(5, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(6, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(7, jugador1.getSimbolo()) ||
                                    metaTablero.verificarGanador(8, jugador1.getSimbolo())) {
                                // Ganó el jugador
                                System.out.println("¡" + jugador1.getNombreJugador() + " ha ganado el juego!");
                                actualizar(jugador1, "ganado");
                                actualizar(jugador2, "perdido");
                                serializacion.actualizarJugador(jugador1);
                                serializacion.actualizarJugador(jugador2);
                                System.out.println("Estadísticas actualizadas:");
                                serializacion.mostrarJugadores();
                                return;
                                } else if (metaTablero.empateGlobal(metaTablero)) {
                                System.out.println("El juego ha terminado en empate.");
                                actualizar(jugador1, "empate");
                                actualizar(jugador2, "empate");
                                serializacion.actualizarJugador(jugador1);
                                serializacion.actualizarJugador(jugador2);
                                System.out.println("Estadísticas actualizadas:");
                                serializacion.mostrarJugadores();
                                return; // Terminar el juego
                            }
                            tableros.imprimirTablero();
                            siguientePlano = posicion;
                            turno++;
                        }

                    }

                else{
                    plano = -1;
                    System.out.println("---Iniciando el juego entre '" + jugador1.getNombreJugador() + "' y '" + jugador2.getNombreJugador()+"' ---");

                    while (true) {
                        if (turnoJ2) {
                            PCDificil pcDificil = (PCDificil) jugador2;
                            System.out.println("----------------------------------------------------------------------");

                            // Determinar si el siguiente plano está completado o es el primer turno
                            boolean pedirPlano = (turno == 0) || planosCompletados.contains(siguientePlano - 1);
                            if (pedirPlano) {
                                System.out.println("Turno de '" + jugador2.getNombreJugador() + "'. Escoja plano (1-9) y posición (1-9):");

                                plano = pcDificil.hacerJugadaDificilGrupo(tableros, jugador2.getSimbolo());
                                posicion = pcDificil.hacerJugadaDificilPlano(plano - 1, jugador2.getSimbolo(), tableros.getTableros().get(plano - 1).getTablero());
                                System.out.println("Plano: " + plano + ", Posición: " + posicion);

                                while (planosCompletados.contains(plano - 1)) {
                                    System.out.println("Ese plano ya está completado. Elija otro plano.");
                                    plano = pcDificil.hacerJugadaDificilGrupo(tableros, jugador2.getSimbolo());
                                    posicion = pcDificil.hacerJugadaDificilPlano(plano - 1, jugador2.getSimbolo(), tableros.getTableros().get(plano - 1).getTablero());
                                    System.out.println("Plano: " + plano + ", Posición: " + posicion);

                                }
                                resultado = jugador2.hacerJugada(plano, posicion, tableros, jugador2.getSimbolo());
                            }
                             else {
                                int planoActual = (siguientePlano == 1) ? 1 : siguientePlano;
                                if (planosCompletados.contains(planoActual - 1)) {
                                    System.out.println("Ese plano ya está completado. Se elegirá otro plano y posición (1-9 1-9) aleatorio:");
                                    boolean jugadaValida = false;
                                    for (int intentos = 0; intentos < 9; intentos++) {
                                        plano = pcDificil.hacerJugadaDificilGrupo(tableros, jugador2.getSimbolo());
                                        if (planosCompletados.contains(plano - 1)) continue;
                                        posicion = pcDificil.hacerJugadaDificilPlano(plano - 1, jugador2.getSimbolo(), tableros.getTableros().get(plano - 1).getTablero());
                                        if (posicion == -1) continue;
                                        System.out.println("Plano: " + plano + ", Posición: " + posicion);

                                        resultado = pcDificil.hacerJugada(plano, posicion, tableros, jugador2.getSimbolo());
                                        jugadaValida = true;
                                        break;
                                    }
                                    if (!jugadaValida) {
                                        System.out.println("No hay jugadas posibles para el PC Difícil.");
                                        return;
                                    }
                                }


                                else {
                                    System.out.println("Turno de '" + jugador2.getNombreJugador() + "'. Juega en el plano " + planoActual + ". Escoja la posición (1-9):");
                                    posicion = pcDificil.hacerJugadaDificilPlano(planoActual, jugador2.getSimbolo(), tableros.getTableros().get(planoActual - 1).getTablero());
                                    resultado = jugador2.hacerJugada(planoActual, posicion, tableros, jugador2.getSimbolo());
                                    System.out.println("Plano: " + plano + ", Posición: " + posicion);

                                    plano = planoActual;
                                }
                            }
                            // Verificar ganador y empate después de la jugada
                            if (tableros.verificarGanador(plano - 1, jugador2.getSimbolo())) {
                                System.out.println("! Tablero Ganado !" );
                                tableros.rellenarTablero(jugador2.getSimbolo(), plano - 1);
                                int filaMeta = (plano - 1) / 3;
                                int columnaMeta = (plano - 1) % 3;
                                if (!planosCompletados.contains(plano - 1)) {

                                    planosCompletados.add(plano - 1);
                                                                         
                                }
                                metaTablero.recibirJugada(0, filaMeta, columnaMeta, jugador2.getSimbolo());
                                System.out.println("Vista General del Meta - Tablero.Tablero:");
                                metaTablero.imprimirTablero();
                            } else if (tableros.getTableros().get(plano -1).verificarEmpate(0)) {

                                System.out.println("Tablero Empatado");
                                tableros.rellenarTablero('/',plano - 1);
                                if (!planosCompletados.contains(plano - 1)) {

                                    planosCompletados.add(plano - 1);
                                      
                                }
                                int filaMeta = (plano - 1) / 3;
                                int columnaMeta = (plano - 1) % 3;
                                metaTablero.recibirJugada(0, filaMeta, columnaMeta, '/');

                                System.out.println("Vista General del Meta - Tablero.Tablero:");
                                metaTablero.imprimirTablero();
                            }
                            if (metaTablero.verificarGanador(0, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(1, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(2, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(3, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(4, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(5, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(6, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(7, jugador2.getSimbolo()) ||
                                    metaTablero.verificarGanador(8, jugador2.getSimbolo())) {
                                // Ganó el jugador
                                System.out.println("¡" + jugador2.getNombreJugador() + " ha ganado el juego!");
                                actualizar(jugador2, "ganado");
                                actualizar(jugador1, "perdido");
                                serializacion.actualizarJugador(jugador1);
                                serializacion.actualizarJugador(jugador2);
                                System.out.println("Estadísticas actualizadas:");
                                serializacion.mostrarJugadores();
                                return;
                            } else if (metaTablero.empateGlobal(metaTablero)) {
                                System.out.println("El juego ha terminado en empate.");
                                actualizar(jugador1, "empate");
                                actualizar(jugador2, "empate");
                                serializacion.actualizarJugador(jugador1);
                                serializacion.actualizarJugador(jugador2);
                                System.out.println("Estadísticas actualizadas:");
                                serializacion.mostrarJugadores();
                                return; // Terminar el juego
                            }

                            try{
                                Thread.sleep(3000); // Esperar 3 segundos antes de la siguiente jugada
                            } catch (InterruptedException e) {
                                System.out.println("Error al esperar entre jugadas: " + e.getMessage());
                            }

                            tableros.imprimirTablero();
                            siguientePlano = posicion; // Guardar la posición elegida (1-9)
                            turnoJ2 = !turnoJ2;

                        }
                        //J2
                        boolean pedirPlano = siguientePlano == -1 || planosCompletados.contains(siguientePlano - 1);

                        if (pedirPlano) {
                            System.out.println("Turno de '" + jugador1.getNombreJugador() + "'. Escoja plano (1-9) y posición (1-9):");
                            String input = in.nextLine();
                            String[] partes = input.split(" ");
                            plano = Integer.parseInt(partes[0]);
                            posicion = Integer.parseInt(partes[1]);
                            if (planosCompletados.contains(plano - 1)) {
                                System.out.println("Ese plano ya está completado. Elija otro plano.");
                                continue;
                            }
                            resultado = jugador1.hacerJugada(plano, posicion, tableros, jugador1.getSimbolo());
                        }
                        //Jugador 2

                        else {
                            int planoActual = (siguientePlano == 1) ? 1 : siguientePlano;
                            if (planosCompletados.contains(planoActual - 1)) {
                                System.out.println("Ese plano ya está completado. Elija otro plano y posición (1-9 1-9):");
                                String input = in.nextLine();
                                String[] partes = input.split(" ");
                                plano = Integer.parseInt(partes[0]);
                                posicion = Integer.parseInt(partes[1]);
                                if (planosCompletados.contains(plano - 1)) {
                                    System.out.println("Ese plano también está completado. Elija otro.");
                                    continue;
                                }
                                resultado = jugador1.hacerJugada(plano, posicion, tableros, jugador1.getSimbolo());
                            } else {
                                System.out.println("Turno de '" + jugador1.getNombreJugador() + "'. posición (1-9) en el tablero: " + planoActual);
                                String input = in.nextLine();
                                posicion = Integer.parseInt(input);
                                resultado = jugador1.hacerJugada(planoActual, posicion, tableros, jugador1.getSimbolo());
                                plano = planoActual;
                            }
                        }
                        // Verificar ganador y empate después de la jugada
                        if (tableros.verificarGanador(plano - 1, jugador1.getSimbolo())) {
                            System.out.println("! Tablero Ganado !");
                            tableros.rellenarTablero(jugador1.getSimbolo(), plano - 1);
                            int filaMeta = (plano - 1) / 3;
                            int columnaMeta = (plano - 1) % 3;
                            if (!planosCompletados.contains(plano - 1)) {
                                planosCompletados.add(plano - 1);
                            }
                            metaTablero.recibirJugada(0, filaMeta, columnaMeta, jugador1.getSimbolo());
                            System.out.println("Vista General del Meta - Tablero.Tablero:");
                            metaTablero.imprimirTablero();
                        } else if (metaTablero.empateGlobal(metaTablero)) {
                            System.out.println("Tablero Empatado");
                            tableros.rellenarTablero('/', plano - 1);
                            if (!planosCompletados.contains(plano - 1)) {
                                planosCompletados.add(plano - 1);
                            }
                            metaTablero.rellenarTablero('/', plano - 1);
                            System.out.println("Vista General del Meta - Tablero.Tablero:");
                            metaTablero.imprimirTablero();
                        }
                        if (metaTablero.verificarGanador(0, jugador1.getSimbolo()) ||
                                metaTablero.verificarGanador(1, jugador1.getSimbolo()) ||
                                metaTablero.verificarGanador(2, jugador1.getSimbolo()) ||
                                metaTablero.verificarGanador(3, jugador1.getSimbolo()) ||
                                metaTablero.verificarGanador(4, jugador1.getSimbolo()) ||
                                metaTablero.verificarGanador(5, jugador1.getSimbolo()) ||
                                metaTablero.verificarGanador(6, jugador1.getSimbolo()) ||
                                metaTablero.verificarGanador(7, jugador1.getSimbolo()) ||
                                metaTablero.verificarGanador(8, jugador1.getSimbolo())) {
                            // Ganó el jugador
                            System.out.println("¡" + jugador1.getNombreJugador() + " ha ganado el juego!");
                            actualizar(jugador1, "ganado");
                            actualizar(jugador2, "perdido");
                            serializacion.actualizarJugador(jugador1);
                            serializacion.actualizarJugador(jugador2);
                            System.out.println("Estadísticas actualizadas:");
                            serializacion.mostrarJugadores();
                            return;
                            } else if (metaTablero.empateGlobal(metaTablero)) {

                            System.out.println("El juego ha terminado en empate.");
                            actualizar(jugador1, "empate");
                            actualizar(jugador2, "empate");
                            serializacion.actualizarJugador(jugador1);
                            serializacion.actualizarJugador(jugador2);
                            System.out.println("Estadísticas actualizadas:");
                            serializacion.mostrarJugadores();
                            return; // Terminar el juego
                        }
                        tableros.imprimirTablero();
                        turnoJ2 = !turnoJ2;
                        siguientePlano = posicion;


                        turno++;
                    }


                }
            default:
                System.out.println("Error al determinar el jugador inicial.");
                return;
        }
    }
}
