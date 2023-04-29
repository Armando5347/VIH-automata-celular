/**
 * @author Jarillo Hernández Armando Damián
 *         Sigala Morales Said
 * 
 */
package modelo.java;
import java.awt.Color;
import javax.swing.JPanel;

public class Celula {
    public static final int ESTADO_CELULA_SANA = 0;
    public static final int ESTADO_CELULA_INFECTADA_A = 1;
    public static final int ESTADO_CELULA_INFECTADA_B = 2; // Corregido el nombre de la variable
    public static final int ESTADO_CELULA_MUERTA = 3;

    private int estado;
    private int estadoFuturo;
    private int periodosRestantes;
    private JPanel representacionGrafica;

    public Celula() {
        estado = ESTADO_CELULA_SANA;
        estadoFuturo = ESTADO_CELULA_SANA;
        periodosRestantes = 0;
        representacionGrafica = new JPanel();
        representacionGrafica.setBackground(Color.BLUE);
        representacionGrafica.setBorder(Auxiliar.BORDE_LIGERO);
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
        switch(estado){
            case 0:
                this.representacionGrafica.setBackground(Color.BLUE);
                break;
            case 1:
                this.representacionGrafica.setBackground(Color.YELLOW);
                break;
            case 2:
                this.representacionGrafica.setBackground(Color.GREEN);
                break;
            case 3:
                this.representacionGrafica.setBackground(Color.RED);
        }
        this.representacionGrafica.repaint();
        this.representacionGrafica.updateUI();
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

    public JPanel getRepresentacionGrafica() {
        return representacionGrafica;
    }

    public void setRepresentacionGrafica(JPanel representacionGrafica) {
        this.representacionGrafica = representacionGrafica;
    }
    
    
    
}
