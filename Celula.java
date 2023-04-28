package automatacelular;

public class Celula {
    public static final int estado_celula_sana = 0;
    public static final int estado_celula_infentada_A = 1;
    public static final int estado_celula_infentada_B = 2; // Corregido el nombre de la variable
    public static final int estado_celula_muerta = 3;

    private int estado;
    private int estadoFuturo;
    private int periodosRestantes;

    public Celula() {
        estado = estado_celula_sana;
        estadoFuturo = estado_celula_sana;
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

