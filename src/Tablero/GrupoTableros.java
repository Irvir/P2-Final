package Tablero;

import java.util.ArrayList;
import java.util.List;


//Utiliza el patrón Composite para manejar múltiples tableros individuales como un solo tablero compuesto.
public class GrupoTableros implements Tablero {
    private List<TableroIndividual> tableros;

    // Constructor que inicializa un grupo de 9 tableros individuales.
    public GrupoTableros() {
        tableros = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            tableros.add(new TableroIndividual());
        }
    }

    public void agregarTablero(TableroIndividual tablero) {
        tableros.add(tablero);
    }

    @Override
    // Método para imprimir el estado de todos los tableros individuales en un formato de 3x3.
    public void imprimirTablero() {
        for (int filaGrupo = 0; filaGrupo < 3; filaGrupo++) {
            for (int filaInterna = 0; filaInterna < 3; filaInterna++) {
                for (int colGrupo = 0; colGrupo < 3; colGrupo++) {
                    TableroIndividual t = (TableroIndividual) tableros.get(filaGrupo * 3 + colGrupo);
                    char[][] matriz = t.getTablero();
                    for (int colInterna = 0; colInterna < 3; colInterna++) {
                        System.out.print(" " + matriz[filaInterna][colInterna] + " ");
                        if (colInterna < 2) System.out.print("|");
                    }
                    if (colGrupo < 2) System.out.print(" ||");
                }
                System.out.println();
            }
            if (filaGrupo < 2) {
                System.out.println("-----------||-----------||-----------");
            }
        }
    }

    @Override
    // Método para recibir una jugada en un tablero específico.
    public int recibirJugada(int plano, int fila, int columna, char simbolo) {
        TableroIndividual t = (TableroIndividual) tableros.get(plano);
        return t.recibirJugada(plano,fila, columna, simbolo);
    }



    @Override
    // Método para verificar si un tablero específico está lleno.
    public boolean verificarGanador(int posicionTablero,char simbolo) {
        Tablero individual = (TableroIndividual) tableros.get(posicionTablero);
        if(individual.verificarGanador(posicionTablero,simbolo)) {
            return true;
        }
        return false; // Placeholder
    }

    public List<TableroIndividual> getTableros() {
        return tableros;
    }

    @Override
    // Método para verificar si un tablero específico ha empatado.
    public boolean verificarEmpate(int posicion) {
        Tablero individual = (TableroIndividual) tableros.get(posicion);
        if (individual.verificarEmpate(posicion)){
            return true;

        }

        return false; // Placeholder
    }

    @Override
    // Método para rellenar un tablero específico con un símbolo dado.
    public void rellenarTablero(char simbolo, int plano) {

        tableros.get(plano).rellenarTablero(simbolo, plano);
    }

}
