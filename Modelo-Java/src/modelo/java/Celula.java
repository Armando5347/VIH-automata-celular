/**
 * @author Jarillo Henrández Armando Damián
 *         Sigala Morales Said
 * 
 */
package modelo.java;

public class Celula {
    public static final int ESTADO_CELULA_SANA = 0;
    public static final int ESTADO_CELULA_INFECTADA_A = 1;
    public static final int ESTADO_CELULA_INFECTADA_B = 2; // Corregido el nombre de la variable
    public static final int ESTADO_CELULA_MUERTA = 3;

    private int estado;
    private int estadoFuturo;
    private int periodosRestantes;

    public Celula() {
        estado = ESTADO_CELULA_SANA;
        estadoFuturo = ESTADO_CELULA_SANA;
        periodosRestantes = 0;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getEstadoFuturo() {
        return estadoFuturo;
    }

    public void setEstadoFuturo(int estadoFuturo) {
        this.estadoFuturo = estadoFuturo;
    }

    public int getPeriodo() {
        return periodosRestantes;
    }

    public void setPeriodos(int periodos) {
        this.periodosRestantes = periodos;
    }

    public void pasoPeriodo() {
        this.periodosRestantes--;
    }
}