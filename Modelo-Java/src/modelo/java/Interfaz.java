/**
 * @author Jarillo Henrández Armando Damián
 *         Sigala Morales Said
 * 
 */
package modelo.java;
        
//librerias importadas
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author maste
 */
public class Interfaz extends Thread{
    
    private final JFrame ventana;
    private final JPanel contenedorTitulo;
    private final JPanel panelPrincipal;
    
    private JPanel panelOpciones1 = new JPanel();
    private JPanel panelOpciones2 = new JPanel();
    
    private JLabel titulo = new JLabel("Modelo simulación expansión del VIH");
    private JLabel nombreLattice = new JLabel("Tamaño de la cuadricula");
    private JLabel nombrePorcionVIH = new JLabel("Porcion de celulas infectadas iniciales (1-100%)");
    private JLabel nombreResistenciaTipoA = new JLabel("Resitencia de las celulas a la presencia de ceulas infectadas tipo A (RA)");
    private JLabel nombreResistenciaTipoB = new JLabel("Resitencia de las celulas a la presencia de ceulas infectadas tipo B (RB)");
    private JLabel nombreProbabilidadCelulaInfectada = new JLabel("Posibilidad de que una célula muerta sea reemeplaza por una celula infectada (0-100%)");
    private JLabel nombreTimesSteps = new JLabel("Número de periodos (t)");
    
    private JTextField valor_Lattice = new JTextField("10");
    private JTextField valor_T = new JTextField("4");
    
    private JSlider valor_pVIH = new JSlider(JSlider.HORIZONTAL, 0, 100, 5);
    private JSlider valor_RA = new JSlider(JSlider.HORIZONTAL, 1, 9, 1);
    private JSlider valor_RB = new JSlider(JSlider.HORIZONTAL, 1, 9, 4);
    private JSlider valor_P_infec = new JSlider(JSlider.HORIZONTAL, 0, 100, 1);
    
    private JButton botton_generar_automata = new JButton("Generar automata celular");
    
    private final Font FUENTE_TITULO = new Font("Verdana", Font.BOLD, 35);
    private final Font FUENTE_TEXTO = new Font("Verdana", Font.PLAIN, 15);
    private final Font FUENTE_MINI = new Font("Verdana", Font.PLAIN, 10);
    
    private final Border BORDE = BorderFactory.createLineBorder(Color.darkGray,3);
    private final Border BORDE_COMPUESTO = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,3), BorderFactory.createEmptyBorder(15, 15, 15, 15));
    private BorderLayout layout = new BorderLayout(0, 0);
    
    private final Dimension tamanioMaximoInput = new Dimension(3000, 40);
    
// Lattice, p_VIH, RA, RB, T, P_infec
    
    
    Interfaz(){
        //inicializar componentes
        this.panelPrincipal = new JPanel();
        this.contenedorTitulo = new JPanel();
        this.ventana = new JFrame("Menú principal");
    }

    @Override
    public void run() {
        ventana.setBackground(Color.black);
        ventana.setLayout(layout);
        titulo.setBackground(Color.black);
        titulo.setForeground(Color.white);
        titulo.setFont(FUENTE_TITULO);
        contenedorTitulo.add(titulo);
        contenedorTitulo.setBorder(BORDE);
        contenedorTitulo.setBackground(Color.black);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.add(contenedorTitulo, BorderLayout.NORTH);
        buildMain();
        ventana.pack();
        ventana.setVisible(true);
    }

    private void buildMain() {
        panelPrincipal.setBackground(Color.black);
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.X_AXIS));
        panelPrincipal.setBorder(BORDE_COMPUESTO);
        
        panelOpciones1.setBackground(Color.black);
        panelOpciones1.setLayout(new BoxLayout(panelOpciones1, BoxLayout.Y_AXIS));
        panelOpciones1.setBorder(BORDE);
        
        panelOpciones2.setBackground(Color.black);
        panelOpciones2.setLayout(new BoxLayout(panelOpciones2, BoxLayout.Y_AXIS));
        panelOpciones2.setBorder(BORDE);
        
        //panel opciones uno
        asignarEstiloLabel(nombreLattice, FUENTE_TEXTO, panelOpciones1);
        
       asignarEstiloTextLabel(valor_Lattice, FUENTE_TEXTO, panelOpciones1);
        
        asignarEstiloLabel(nombrePorcionVIH, FUENTE_TEXTO, panelOpciones1);
        
        
        
        asignarEstiloSlider(valor_pVIH,panelOpciones1, 10, 1);
        
        asignarEstiloLabel(nombreProbabilidadCelulaInfectada, FUENTE_TEXTO, panelOpciones1);
        asignarEstiloSlider(valor_P_infec, panelOpciones1, 10, 1);
        
        panelPrincipal.add(panelOpciones1); // se acplola el primer panel de opciones
        
        //panel opciones dos
        asignarEstiloLabel(nombreResistenciaTipoA, FUENTE_TEXTO, panelOpciones2);
        
        asignarEstiloSlider(valor_RA, panelOpciones2, 8, 1);
        
        asignarEstiloLabel(nombreResistenciaTipoB, FUENTE_TEXTO, panelOpciones2);
        
        asignarEstiloSlider(valor_RB, panelOpciones2, 8, 1);
        
        panelPrincipal.add(panelOpciones2); // se acopla el segundo panle de opciones
        
        asignarEstiloLabel(nombreTimesSteps, FUENTE_TEXTO, panelOpciones2);
        
        asignarEstiloTextLabel(valor_T, FUENTE_TEXTO, panelOpciones2);
        
        ventana.add(panelPrincipal,BorderLayout.CENTER);
        
        //finalmente, el boton que da inicio a la magia
        botton_generar_automata.setBackground(Color.DARK_GRAY);
        botton_generar_automata.setForeground(Color.WHITE);
        
        botton_generar_automata.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recabarInformacionAutomata();
            }
        });
        
        ventana.add(botton_generar_automata,BorderLayout.SOUTH);
    }
    
    
    private void recabarInformacionAutomata() {
        int lattice = Integer.parseInt(valor_Lattice.getText()); //falta validacion (>0)
        
        double p_VIH = valor_pVIH.getValue() / 100 ; //falta validacion (> 0)
        
        double p_infect = valor_P_infec.getValue() / 100 ; //no estoy seguro si puede valer cero
        
        int ra = valor_RA.getValue();
        
        int rb = valor_RB.getValue();
        
        int t = Integer.parseInt(valor_T.getText());
        
        
    }
    
    //funciones de diseño
    private void asignarEstiloLabel(JLabel labelObjetivo, Font fuente, JPanel panelDestino){
        labelObjetivo.setBackground(Color.BLACK);
        labelObjetivo.setForeground(Color.WHITE);
        labelObjetivo.setFont(fuente);
        labelObjetivo.setHorizontalAlignment(SwingConstants.CENTER);
        panelDestino.add(labelObjetivo);
    }

    private void asignarEstiloTextLabel(JTextField input, Font fuente, JPanel panelDestino){
        input.setBackground(Color.BLACK);
        input.setForeground(Color.WHITE);
        input.setFont(fuente);
        input.setMaximumSize(tamanioMaximoInput);
        panelDestino.add(input);
    }
    
    private void asignarEstiloSlider(JSlider slider, JPanel panelDestino, int maxTC, int minTC) {
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
