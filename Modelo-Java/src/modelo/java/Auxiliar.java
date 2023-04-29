/**
 * @author Jarillo Hernández Armando Damián
 *         Sigala Morales Said
 * 
 */
package modelo.java;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


public class Auxiliar {
    
    
    public static final Font FUENTE_TITULO = new Font("Verdana", Font.BOLD, 35);
    public static final Font FUENTE_TEXTO = new Font("Verdana", Font.PLAIN, 15);
    public static final Font FUENTE_MINI = new Font("Verdana", Font.PLAIN, 10);
    
    public static final Border BORDE = BorderFactory.createLineBorder(Color.darkGray,3);
    
    public static final Border BORDE_COMPUESTO = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,3), BorderFactory.createEmptyBorder(15, 15, 15, 15));
    public static final BorderLayout LAYOUT = new BorderLayout(0, 0);
    
    public static GridLayout layoutMalla;
    
    public final Dimension tamanioMaximoInput = new Dimension(3000, 40);
    
    //funciones de diseño
    public static void asignarEstiloLabel(JLabel labelObjetivo, Font fuente, JPanel panelDestino){
        labelObjetivo.setBackground(Color.BLACK);
        labelObjetivo.setForeground(Color.WHITE);
        labelObjetivo.setFont(fuente);
        labelObjetivo.setHorizontalAlignment(SwingConstants.CENTER);
        panelDestino.add(labelObjetivo);
    }

    public static void asignarEstiloTextLabel(JTextField input, Font fuente, JPanel panelDestino){
        input.setBackground(Color.BLACK);
        input.setForeground(Color.WHITE);
        input.setFont(fuente);
        input.setMaximumSize(tamanioMaximoInput);
        panelDestino.add(input);
    }
    
    public static  void asignarEstiloSlider(JSlider slider, JPanel panelDestino, int maxTC, int minTC) {
        slider.setBackground(Color.BLACK);
        slider.setForeground(Color.WHITE);
        slider.setMajorTickSpacing(maxTC);
        slider.setMinorTickSpacing(minTC);
        slider.setFont(FUENTE_MINI);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        panelDestino.add(slider);
    }
}
