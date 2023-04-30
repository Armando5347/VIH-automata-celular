// Se importan las librerías necesarias
package automatacelular;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;



/**
 * La clase AutomataCelular representa un autómata celular que simula la evolución temporal de un tejido celular
 * en el que puede haber células sanas, infectadas tipo A, infectadas tipo B y muertas.
 */
public class AutomataCelular {
    // Se definen los atributos de la clase
    private int L; // Tamaño de la matriz cuadrada de células
    private int RA; // Radio de infectividad de células tipo A
    private int RB; // Radio de infectividad de células tipo B
    private int t; // Periodo de incubación de células tipo A
    private double pinfec; // Probabilidad de infección por células infectadas
    private double prepl; // Probabilidad de reparación de células muertas
    private double pVIH; // Porcentaje de células infectadas con VIH
    private Celula[][] matrizCelulas; // Matriz de células
    private NodoDensidad cabeceraDensidades; // Nodo inicial de la lista enlazada de densidades
    private NodoDensidad densidadActual; // Nodo actual de la lista enlazada de densidades
    private Random random; // Generador de números aleatorios

    // Se define el constructor vacío
    public AutomataCelular() {}

    // Se define el constructor con parámetros
    public AutomataCelular(int L, int ra, int rb, int t, double p_infec, double p_vih) {
        // Se inicializan los atributos con los valores recibidos
        this.L = L;
        this.RA = ra;
        this.RB = rb;
        this.t = t;
        this.pinfec = p_infec;
        this.prepl = 1 - p_infec;
        this.pVIH = p_vih;
        this.random = new Random();
        this.matrizCelulas = new Celula[L][L];

        // Se inicializa la matriz de células
        for (int i = 0; i < L; i++) {
            for (int j = 0; j < L; j++) {
                this.matrizCelulas[i][j] = new Celula();
                this.matrizCelulas[i][j].setEstado(Celula.ESTADO_CELULA_SANA);
                this.matrizCelulas[i][j].setPeriodos(t);
            }
        }

        // Se infectan células aleatorias con VIH
        int total_celulas_infectadas_iniciales = (int) ((L * L * p_vih) / 1); 
        this.cabeceraDensidades = new NodoDensidad();
        this.cabeceraDensidades.densidadCelulasSanas = (L*L - total_celulas_infectadas_iniciales) / (L*L);
        this.cabeceraDensidades.densidadCelulasInfectadas = total_celulas_infectadas_iniciales / (L*L);
        this.cabeceraDensidades.densidadCelulasMuertas = 0;
        this.densidadActual = this.cabeceraDensidades;
        while (total_celulas_infectadas_iniciales > 0){
            int i = random.nextInt(L);
            int j = random.nextInt(L);

            if (matrizCelulas[i][j].getEstado() != Celula.ESTADO_CELULA_INFECTADA_A) {
                matrizCelulas[i][j].setEstado(Celula.ESTADO_CELULA_INFECTADA_A);

                total_celulas_infectadas_iniciales--;
            }
        }
    }
    
    public void ejecutarSimulacion(int pasos) {
            for (int i = 0; i < pasos; i++) {
           actualizar_automata();
       }
    }   
    
 void actualizar_automata() {
    int iterador1, iterador2;
    // primero se calculan los valores futuros
    for (iterador1 = 0; iterador1 < this.L; iterador1++) {
        for (iterador2 = 0; iterador2 < this.L; iterador2++) {
            actualizar_celula(this.matrizCelulas[iterador1][iterador2], iterador1, iterador2);
        }
    }

    // se instancia un nuevo nodo de densidad (suponiendo que se mida por semanas)
    NodoDensidad nodoDensidadAuxiliar = this.densidadActual;
    this.densidadActual = new NodoDensidad();
    nodoDensidadAuxiliar.siguienteDensidad = this.densidadActual;
    this.densidadActual.siguienteDensidad = null;
    this.densidadActual.densidadCelulasInfectadas = 0;
    this.densidadActual.densidadCelulasSanas = 0;
    this.densidadActual.densidadCelulasMuertas = 0;

    // inicializar en CERO las densidades
    // luego, se actualizan los valores
    int newEstado;
    for (iterador1 = 0; iterador1 < this.L; iterador1++) {
        for (iterador2 = 0; iterador2 < this.L; iterador2++) {
            newEstado = this.matrizCelulas[iterador1][iterador2].getEstadoFuturo();
            this.matrizCelulas[iterador1][iterador2].setEstado(newEstado);
            if (newEstado == Celula.ESTADO_CELULA_SANA) {
                this.densidadActual.densidadCelulasSanas++;
            } else if (newEstado == Celula.ESTADO_CELULA_MUERTA) {
                this.densidadActual.densidadCelulasMuertas++;
            } else {
                this.densidadActual.densidadCelulasInfectadas++;
            }
        }
    }

    // dividir entre L * L cada densidad
    this.densidadActual.densidadCelulasInfectadas /= this.L * this.L;
    this.densidadActual.densidadCelulasSanas /= this.L * this.L;
    this.densidadActual.densidadCelulasMuertas /= this.L * this.L;
}

// Esta función analiza la vecindad de una célula en una posición dada y cuenta el número de células infectadas tipo A y B adyacentes.
void analizar_vecindad(int pos_x, int pos_y, short[] celulas_infectadas_adyacentes_tipo_A, short[] celulas_infectadas_adyacentes_tipo_B) {
    for (int dx = -1; dx <= 1; dx++) {
        for (int dy = -1; dy <= 1; dy++) {
            if (dx == 0 && dy == 0) continue;
            int vecino_x = (pos_x + dx + this.L) % this.L;
            int vecino_y = (pos_y + dy + this.L) % this.L;
            Celula vecino = this.matrizCelulas[vecino_x][vecino_y];

            if (vecino.getEstado() == Celula.ESTADO_CELULA_INFECTADA_A) {
                celulas_infectadas_adyacentes_tipo_A[0]++;
            } else if (vecino.getEstado() == Celula.ESTADO_CELULA_INFECTADA_B) {
                    celulas_infectadas_adyacentes_tipo_B[0]++;
                }
            }
        }
    }
    
void actualizar_celula(Celula celula_a_actualizar, int pos_x, int pos_y) {
        celula_a_actualizar.setEstadoFuturo(celula_a_actualizar.getEstado());

        short[] celulas_infectadas_adyacentes_tipo_A = {0};
        short[] celulas_infectadas_adyacentes_tipo_B = {0};

        switch (celula_a_actualizar.getEstado()) {
            case Celula.ESTADO_CELULA_SANA:
                analizar_vecindad(pos_x, pos_y, celulas_infectadas_adyacentes_tipo_A, celulas_infectadas_adyacentes_tipo_B);
                if (celulas_infectadas_adyacentes_tipo_A[0] >= this.RA || celulas_infectadas_adyacentes_tipo_B[0] >= this.RB) {
                    celula_a_actualizar.setEstadoFuturo(Celula.ESTADO_CELULA_INFECTADA_A);
                }
                break;

            case Celula.ESTADO_CELULA_INFECTADA_A:
                
            if (celula_a_actualizar.getPeriodo()> 0) {
                celula_a_actualizar.pasoPeriodo();
            } else {
                celula_a_actualizar.setEstadoFuturo(Celula.ESTADO_CELULA_INFECTADA_B);
            }
            break;

        case Celula.ESTADO_CELULA_INFECTADA_B:
            celula_a_actualizar.setEstadoFuturo(Celula.ESTADO_CELULA_MUERTA);
            break;

        case Celula.ESTADO_CELULA_MUERTA:
            // Generar un número aleatorio en el rango [0, 1]
            double random_prob = Math.random();

            // Comparar el número aleatorio con las probabilidades
            if (random_prob <= this.pinfec) {
                celula_a_actualizar.setEstadoFuturo(Celula.ESTADO_CELULA_INFECTADA_A); // Reemplazar por una célula infectada tipo A
                celula_a_actualizar.resetPeriodos(this.t);
            } else if (random_prob <= (this.pinfec + this.prepl)) {
                celula_a_actualizar.setEstadoFuturo(Celula.ESTADO_CELULA_SANA); // Reemplazar por una célula sana
            }
            break;

              default:
         System.out.println("Si llegamos aquí, hubo un error en las estados de una célula");
        break;
        }
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


    public Celula[][] getMatrizCelular() {
    return this.matrizCelulas;
    }
    public NodoDensidad getDensidadActual() {
    return this.densidadActual;
    }
    public int getL(){
        return this.L;
    }
    
    public int getT(){
           return this.t;
    }
    
    public NodoDensidad getcabeceraDensidades() {
    return cabeceraDensidades;
}

    
}