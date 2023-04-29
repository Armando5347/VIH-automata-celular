/**
 * @author Jarillo Hernández Armando Damián
 *         Sigala Morales Said
 * 
 */
package modelo.java;
import java.awt.Color;
import javax.swing.JPanel;

public class Celula {
    // Estados posibles para una célula
    public static final int ESTADO_CELULA_SANA = 0;
    public static final int ESTADO_CELULA_INFECTADA_A = 1;
    public static final int ESTADO_CELULA_INFECTADA_B = 2;
    public static final int ESTADO_CELULA_MUERTA = 3;

    // Variables de la célula
    private int estado;
    private int estadoFuturo;
    private int periodosRestantes;
    private JPanel representacionGrafica;
    
    public static final Color COLORES[] = {Color.BLUE, Color.YELLOW, Color.GREEN, Color.RED};

    // Constructor de la célula, inicializa los valores de la célula
    public Celula() {
        estado = ESTADO_CELULA_SANA;
        estadoFuturo = ESTADO_CELULA_SANA;
        periodosRestantes = 0;
        representacionGrafica = new JPanel();
        representacionGrafica.setBackground(Color.BLUE);
        representacionGrafica.setBorder(Auxiliar.BORDE_LIGERO);
    }

    // Métodos get y set para acceder y modificar el estado de la célula
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
        
        // Cambiar el color de la representación gráfica de la célula según su estado
        this.representacionGrafica.setBackground(COLORES[estado]);
        this.representacionGrafica.repaint();
    }

    // Métodos get y set para acceder y modificar el estado futuro de la célula
    public int getEstadoFuturo() {
        return estadoFuturo;
    }

    public void setEstadoFuturo(int estadoFuturo) {
        this.estadoFuturo = estadoFuturo;
    }

    // Métodos get y set para acceder y modificar los periodos restantes de la célula
    public int getPeriodo() {
        return periodosRestantes;
    }

    public void setPeriodos(int periodos) {
        this.periodosRestantes = periodos;
    }

    // Método que decrementa en uno los periodos restantes de la célula
    public void pasoPeriodo() {
        this.periodosRestantes--;
    }

    // Métodos get y set para acceder y modificar la representación gráfica de la célula
    public JPanel getRepresentacionGrafica() {
        return representacionGrafica;
    }

    public void setRepresentacionGrafica(JPanel representacionGrafica) {
        this.representacionGrafica = representacionGrafica;
    }
    
    // Método que resetea los periodos restantes de la célula a un valor dado
    public void resetPeriodos(int periodos) {
        this.periodosRestantes = periodos;
    }
}

