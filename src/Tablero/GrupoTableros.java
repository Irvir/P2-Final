package Tablero;

import java.util.ArrayList;
import java.util.List;


//Composite
public class GrupoTableros implements Tablero {
    private List<TableroIndividual> tableros;


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
    public void imprimirTablero() {
        // Cada Tablero.TableroIndividual tiene 3 filas
        for (int filaGrupo = 0; filaGrupo < 3; filaGrupo++) {
            // Para cada fila interna de los tableros
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
    public int recibirJugada(int plano, int fila, int columna, char simbolo) {
        TableroIndividual t = (TableroIndividual) tableros.get(plano);
        return t.recibirJugada(plano,fila, columna, simbolo);
    }



    @Override
    public boolean verificarGanador(int posicionTablero,char simbolo) {
        Tablero individual = (TableroIndividual) tableros.get(posicionTablero);
        if(individual.verificarGanador(posicionTablero,simbolo)) {
            System.out.println("TableroGanado");
            return true;
        }
        return false; // Placeholder
    }

    public List<TableroIndividual> getTableros() {
        return tableros;
    }

    @Override
    public boolean verificarEmpate(int posicion) {
        Tablero individual = (TableroIndividual) tableros.get(posicion);
        if (individual.verificarEmpate(posicion)){
            System.out.println("TableroEmpatado");
            return true;

        }

        return false; // Placeholder
    }

    @Override
    public void rellenarTablero(char simbolo, int plano) {
        tableros.get(plano).rellenarTablero(simbolo, plano);
    }

}
