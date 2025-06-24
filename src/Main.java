
public class Main {
    public static void main(String[] args) {
        System.out.println("Intrucciones del Juego Gato de Gatos: ");
        System.out.println("1. El jugador1 deberá ingresar su nombre.");
        System.out.println("2. El juego se juega en un Gran tablero dentro de tableros de 3x3, en caso de pedir posición :");
        System.out.print("El formato es: Plano (1-9) Espacio y Posición (1-9) \n");
        System.out.println("Caso contrario: solo se deberá ingresar la posición (1-9) para jugar en el tablero anterior.");
        System.out.println("3. El jugador que gane la lanzada de datos comienza eligiendo el tablero y la posición, el jugador perdedor estará obligado a jugar en el tablero de la posción del jugador ganador.");
        System.out.println("(OPCIONAL) En cualquier momento se puede ingresar 'rendir' para terminar el juego, y se actualizarán las estadísticas de los jugadores.");
        Menu menu = new Menu();
        menu.menu();

    }
}