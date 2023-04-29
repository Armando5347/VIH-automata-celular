/**
 * @author Jarillo Hernández Armando Damián
 *         Sigala Morales Said
 * 
 */
package modelo.java;
        
//librerias importadas
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;


public class Malla extends Thread{
    
    private AutomataCelular automata; //el automata
    
    private static final long SEGUNDO = 1000;
    
    private static volatile boolean ordenContinuar = true; //por defecto, se encuentra como verdadera, de que DEBE CONTINUAR
    
    private final JFrame ventana;
    private final JPanel contenedorCabecera;
    private final JPanel contenedorBotones;
    
    private JPanel panelPrincipal = new JPanel();
    
    private JLabel nombreLeyenda = new JLabel("Leyenda:");
    private JLabel nombreSanas = new JLabel("Celulas sanas: AZÚL");
    private JLabel nombreTipoA = new JLabel("Infectadas tipo A: AMARILLO");
    private JLabel nombreTipoB = new JLabel("Infectadas tipo B: VERDE");
    private JLabel nombreMuertas = new JLabel("Ceulas muertas: ROJO");
    private JLabel nombreTimesSteps = new JLabel("Periodos transcurridos");

    private JTextField tiposTranscurridos = new JTextField("0");
    
    private JButton botonFinalizacion = new JButton("Terminar simulacion");
    private JButton botonEstadisticas = new JButton("Ver estatisticas de densidad");
    
    private GridLayout layoutMalla;
    
    private boolean banderaFinalizacion = false;
    
    
    Malla(int l, double p_vih, double p_infect, int ra, int rb, int t){
        //inicializar componentes
        System.out.println("Nuevo automata");
        this.panelPrincipal= new JPanel();
        this.contenedorBotones = new JPanel();
        this.contenedorCabecera = new JPanel();
        this.ventana = new JFrame("Simulación del automata");
        automata = new AutomataCelular(l, ra, rb, t, p_infect, p_vih);
        automata.imprimirDensidades();
        layoutMalla = new GridLayout(l, l);
    }

    @Override
    public void run() {
        ventana.setBackground(Color.black);
        ventana.setLayout(Auxiliar.LAYOUT);
        ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        buildMain();
        
        ventana.pack();
        ventana.setVisible(true);
        int semanas = 0;
        while(ordenContinuar){
            try {
                automata.ejecutarSimulacion(1);
                semanas ++; //avanza 1 semana o time step
                panelPrincipal.repaint();
                panelPrincipal.updateUI();
                System.out.println("Estamos en el t = " + semanas );
                synchronized (this){
                    wait(Malla.SEGUNDO);
                }
                //espera un segundo para realziar la siguiente iteracion
            } catch (InterruptedException ex) {
                Logger.getLogger(Malla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        automata.imprimirDensidades();
    }

    private void buildMain() {
        //la parte de arriba
        contenedorCabecera.setLayout( new  GridLayout(1, 0, 5, 1));
        contenedorCabecera.setBackground(Color.black);
        
        Auxiliar.asignarEstiloLabel(nombreSanas, Auxiliar.FUENTE_MINI, contenedorCabecera);
        Auxiliar.asignarEstiloLabel(nombreTipoA, Auxiliar.FUENTE_MINI, contenedorCabecera);
        Auxiliar.asignarEstiloLabel(nombreTipoB, Auxiliar.FUENTE_MINI, contenedorCabecera);
        Auxiliar.asignarEstiloLabel(nombreMuertas, Auxiliar.FUENTE_MINI, contenedorCabecera);
        ventana.add(contenedorCabecera,BorderLayout.NORTH);
        
        //centro
        panelPrincipal.setBackground(Color.black);
        panelPrincipal.setLayout(layoutMalla);
        panelPrincipal.setBorder(Auxiliar.BORDE_COMPUESTO);
        
        //parte de abajo
        contenedorBotones.setBackground(Color.black);
        contenedorBotones.setLayout(new GridLayout(1, 0, 2, 2));
        contenedorBotones.setBorder(Auxiliar.BORDE);
        
        //malla
        int lattice = automata.getL();
        Celula matriz[][] = automata.getMatrizCelular();
        for (int i = 0; i < lattice; i++) {
            for (int j = 0; j < lattice; j++) {
                panelPrincipal.add(matriz[j][i].getRepresentacionGrafica());
            }
        }
        
        ventana.add(panelPrincipal,BorderLayout.CENTER);
        
        
        //botones
        contenedorBotones.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        contenedorBotones.setAlignmentY(JPanel.CENTER_ALIGNMENT);
        
        Auxiliar.asignarEstilosBoton(botonFinalizacion, contenedorBotones);
        botonFinalizacion.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                terminarSimulacion();
            }
        });
        
        Auxiliar.asignarEstilosBoton(botonEstadisticas, contenedorBotones);
        
        botonEstadisticas.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        
        ventana.add(contenedorBotones,BorderLayout.SOUTH);
    }
     
    
    private void terminarSimulacion() {
        ordenContinuar = false; //se detiene el pasod e los time stems (semanas)
    }
}
