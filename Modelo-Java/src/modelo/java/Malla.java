/**
 * @author Jarillo Hernández Armando Damián
 *         Sigala Morales Said
 * 
 */
package modelo.java;
        
//librerias importadas
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
public class Malla extends Thread{
    
    private AutomataCelular automata; //el automata
    
    private final JFrame ventana;
    private final JPanel contenedorCabecera;
    private final JPanel contenedorBotones;
    
    private JPanel panelMalla = new JPanel();
    
    private JLabel nombreLeyenda = new JLabel("Leyenda:");
    private JLabel nombreSanas = new JLabel("Celulas sanas: AZÚL");
    private JLabel nombreTipoA = new JLabel("Infectadas tipo A: AMARILLO");
    private JLabel nombreTipoB = new JLabel("Infectadas tipo B: VERDE");
    private JLabel nombreMuertas = new JLabel("Ceulas muertas: ROJO");
    private JLabel nombreTimesSteps = new JLabel("Periodos transcurridos");

    private JTextField tiposTranscurridos = new JTextField("0");
    
    private JButton botonFinalizacion = new JButton("Terminar simulacion");
    private JButton bottonEstadisticas = new JButton("Ver estatisticas de densidad");
    
    private boolean banderaFinalizacion = false;
    
    
    Malla(int l, double p_vih, double p_infect, int ra, int rb, int t){
        //inicializar componentes
        System.out.println("Nuevo automata");
        this.panelMalla= new JPanel();
        this.contenedorBotones = new JPanel();
          this.contenedorCabecera = new JPanel();
        this.ventana = new JFrame("Simulación del automata");
        automata = new AutomataCelular(l, ra, rb, t, p_infect, p_vih, 0, 0);
        layoutMalla = new GridLayout(l, l);
    }

    @Override
    public void run() {
        ventana.setBackground(Color.black);
        ventana.setLayout(layout);
        titulo.setBackground(Color.black);
        titulo.setForeground(Color.white);
        titulo.setFont(FUENTE_TITULO);
        contenedorCabecera.add(titulo);
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
        
        Malla malla = new Malla(lattice, p_VIH, p_infect, ra, rb, t);
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
