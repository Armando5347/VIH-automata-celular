/**
 * @author Jarillo Hernández Armando Damián
 *         Sigala Morales Said
 * 
 */
package modelo.java;
        
//librerias importadas
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

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
        ventana.setLayout(Auxiliar.LAYOUT);
        
        Auxiliar.asignarEstiloLabel(titulo, Auxiliar.FUENTE_TITULO, contenedorTitulo);
        
        contenedorTitulo.setBorder(Auxiliar.BORDE);
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
        panelPrincipal.setBorder(Auxiliar.BORDE_COMPUESTO);
        
        panelOpciones1.setBackground(Color.black);
        panelOpciones1.setLayout(new BoxLayout(panelOpciones1, BoxLayout.Y_AXIS));
        panelOpciones1.setBorder(Auxiliar.BORDE);
        
        panelOpciones2.setBackground(Color.black);
        panelOpciones2.setLayout(new BoxLayout(panelOpciones2, BoxLayout.Y_AXIS));
        panelOpciones2.setBorder(Auxiliar.BORDE);
        
        //panel opciones uno
        Auxiliar.asignarEstiloLabel(nombreLattice, Auxiliar.FUENTE_TEXTO, panelOpciones1);
        
       Auxiliar.asignarEstiloTextLabel(valor_Lattice, Auxiliar.FUENTE_TEXTO, panelOpciones1);
        
        Auxiliar.asignarEstiloLabel(nombrePorcionVIH, Auxiliar.FUENTE_TEXTO, panelOpciones1);
        
        
        
        Auxiliar.asignarEstiloSlider(valor_pVIH,panelOpciones1, 10, 1);
        
        Auxiliar.asignarEstiloLabel(nombreProbabilidadCelulaInfectada, Auxiliar.FUENTE_TEXTO, panelOpciones1);
        Auxiliar.asignarEstiloSlider(valor_P_infec, panelOpciones1, 10, 1);
        
        panelPrincipal.add(panelOpciones1); // se acplola el primer panel de opciones
        
        //panel opciones dos
        Auxiliar.asignarEstiloLabel(nombreResistenciaTipoA, Auxiliar.FUENTE_TEXTO, panelOpciones2);
        
        Auxiliar.asignarEstiloSlider(valor_RA, panelOpciones2, 8, 1);
        
        Auxiliar.asignarEstiloLabel(nombreResistenciaTipoB, Auxiliar.FUENTE_TEXTO, panelOpciones2);
        
        Auxiliar.asignarEstiloSlider(valor_RB, panelOpciones2, 8, 1);
        
        panelPrincipal.add(panelOpciones2); // se acopla el segundo panle de opciones
        
        Auxiliar.asignarEstiloLabel(nombreTimesSteps, Auxiliar.FUENTE_TEXTO, panelOpciones2);
        
        Auxiliar.asignarEstiloTextLabel(valor_T, Auxiliar.FUENTE_TEXTO, panelOpciones2);
        
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
        int lattice;
        try{
            lattice = Integer.parseInt(valor_Lattice.getText()); 
            if(lattice == 0){
                JOptionPane.showMessageDialog(ventana, "El valor de lattice no puede ser cero", "Error en los datos",JOptionPane.ERROR_MESSAGE);
                return;
            }
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(ventana, "El valor de lattice debe ser entero", "Error en los datos",JOptionPane.ERROR_MESSAGE);
            return;
        }
        double p_VIH = (double) valor_pVIH.getValue() / 100 ; 
        if(p_VIH == 0){
            JOptionPane.showMessageDialog(ventana, "La porcion de celulas infectas iniciales no puede ser cero", "Error en los datos",JOptionPane.ERROR_MESSAGE);
            return;
        }
        double p_infect =(double) valor_P_infec.getValue() / 100 ; //no estoy seguro si puede valer cero
        
        int ra = valor_RA.getValue();
        
        int rb = valor_RB.getValue();
        
        int t = Integer.parseInt(valor_T.getText());
        
        Malla malla = new Malla(lattice, p_VIH, p_infect, ra, rb, t);
        malla.start();
    }
    

}
    

}
