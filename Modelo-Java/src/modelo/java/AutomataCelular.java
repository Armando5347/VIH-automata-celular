/**
 * @author Jarillo Henrández Armando Damián
 *         Sigala Morales Said
 * 
 */
package modelo.java;

import java.util.Random;

public class AutomataCelular {
    private int L;
    private int RA;
    private int RB;
    private Celula[][] matrizCelulas;
    private int t;
    private double prepl;
    private double pinfec;
    private double pVIH;
    private NodoDensidad cabeceraDensidades;
    private NodoDensidad densidadActual;
    private NodoDensidad nodoDensidadAuxiliar;
    private double p_i;//inecesarias, checar eso
    private double p_m;
    private Random random;
    private int celulasInfectadasA;
    private int celulasInfectadasB;

    public AutomataCelular() {}

    public AutomataCelular(int L, int ra, int rb, int t, double p_infec, double p_vih, double p_i, double p_m) {
        this.L = L;
        this.RA = ra;
        this.RB = rb;
        this.t = t;
        this.pinfec = p_infec;
        this.prepl = 1 - p_infec;
        this.pVIH = p_vih;
        this.p_i = p_i;
        this.p_m = p_m;
        this.random = new Random();
        this.matrizCelulas = new Celula[L][L];

        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                this.matrizCelulas[i][j] = new Celula();
                this.matrizCelulas[i][j].setEstado(Celula.estado_celula_sana);
                this.matrizCelulas[i][j].setPeriodos(t);
            }
        }

        int totalCelulasInfectadas = (int) (L * L * p_vih);
        int celulasInfectadas = 0;

        while (celulasInfectadas < totalCelulasInfectadas) {
            int i = random.nextInt(L);
            int j = random.nextInt(L);

            if (matrizCelulas[i][j].getEstado() == Celula.estado_celula_sana) {
                matrizCelulas[i][j].setEstado(Celula.estado_celula_infentada_A);

                celulasInfectadas++;
            }
        }

        this.cabeceraDensidades = new NodoDensidad();
        this.densidadActual = this.cabeceraDensidades;
    }

    public void ejecutarSimulacion(int pasos) {
        for (int paso = 0; paso < pasos; paso++) {
            for (int i = 0; i < L; i++) {
                for (int j = 0; j < L; j++) {
                    actualizarEstadoFuturo(i, j);
                }
            }

            for (int i = 0; i < L; i++) {
                for (int j = 0; j < L; j++) {
                    matrizCelulas[i][j].setEstado(matrizCelulas[i][j].getEstadoFuturo());
                }
            }

            calcularDensidades();
        }
    }

   private void actualizarEstadoFuturo(int i, int j) {
    Celula celula = matrizCelulas[i][j];
    int estado = celula.getEstado();
    int vecinosInfectados = contarVecinosInfectados(i, j);

    if (estado == Celula.estado_celula_sana && vecinosInfectados > 0) {
        double probabilidadInfeccion = 1 - Math.pow((1 - p_i), vecinosInfectados);
        if (random.nextDouble() < probabilidadInfeccion) {
            celula.setEstadoFuturo(Celula.estado_celula_infentada_A);
        }
    } else if (estado == Celula.estado_celula_infentada_A || estado == Celula.estado_celula_infectada_B) {
        if (Math.random() < prepl) { // Usando prepl como probabilidad de recuperación
            celula.setEstadoFuturo(Celula.estado_celula_sana);
        } else {
            celula.setEstadoFuturo(Celula.estado_celula_muerta);
        }
    } else if (estado == Celula.estado_celula_muerta) {
        double randomValue = Math.random();
        if (randomValue < prepl) { // Usando prepl como probabilidad de resurrección
            celula.setEstadoFuturo(Celula.estado_celula_sana);
        } else if (randomValue >= prepl && randomValue < prepl + pinfec) { // Usando pinfec como probabilidad de infección
            celula.setEstadoFuturo(Celula.estado_celula_infentada_A);
        }
    }
}


private int contarVecinosInfectados(int i, int j) {
    int vecinosInfectados = 0;
    for (int x = -1; x <= 1; x++) {
        for (int y = -1; y <= 1; y++) {
            if (x == 0 && y == 0) continue;
            int vecinoI = (i + x + L) % L;
            int vecinoJ = (j + y + L) % L;
            //Aun une las celulas, checar eso
            if (matrizCelulas[vecinoI][vecinoJ].getEstado() != Celula.estado_celula_sana) {
                vecinosInfectados++;
            }
        }
    }
    return vecinosInfectados;
}

private void calcularDensidades() {
    int celulasSanas = 0;
    celulasInfectadasA = 0;
    celulasInfectadasB = 0;
    int celulasMuertas = 0;

    for (int i = 0; i < L; i++) {
        for (int j = 0; j < L; j++) {
            int estado = matrizCelulas[i][j].getEstado();
            if (estado == Celula.estado_celula_sana) {
                celulasSanas++;
            } else if (estado == Celula.estado_celula_infentada_A) {
                celulasInfectadasA++;
            } else if (estado == Celula.estado_celula_infectada_B) {
                celulasInfectadasB++;
            } else if (estado == Celula.estado_celula_muerta) {
                celulasMuertas++;
            }
        }
    }

    double densidadSanas = (double) celulasSanas / (L * L);
    double densidadInfectadasA = (double) celulasInfectadasA / (L * L);
    double densidadInfectadasB = (double) celulasInfectadasB / (L * L);
    double densidadMuertas = (double) celulasMuertas / (L * L);

    NodoDensidad nuevoNodo = new NodoDensidad(densidadSanas, densidadInfectadasA + densidadInfectadasB, densidadMuertas);
    densidadActual.setSiguiente(nuevoNodo);
    densidadActual = nuevoNodo;
}

public void imprimirDensidades() {
    int tiempo = 0;
    NodoDensidad nodo;
    nodo = cabeceraDensidades;
    System.out.println("Tiempo\tSanas\t\tInfectadas\tMuertas");
    while (nodo != null) {
System.out.printf("%d\t%.6f\t%.6f\t%.6f\n", tiempo, nodo.getDensidadCelulasSanas(), nodo.getDensidadCelulasInfectadas(), nodo.getDensidadCelulasMuertas());
nodo = nodo.getSiguiente();
tiempo++;
}
}
public void imprimirMatriz() {
    for (int i = 0; i < L; i++) {
        for (int j = 0; j < L; j++) {
            System.out.print(matrizCelulas[i][j].getEstado() + " ");
        }
        System.out.println();
    }
}

}


