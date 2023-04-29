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
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Malla extends Thread{
    
    private AutomataCelular automata; //el automata
    
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
        automata = new AutomataCelular(l, ra, rb, t, p_infect, p_vih, 0, 0);
        automata.imprimirDensidades();
        layoutMalla = new GridLayout(l, l);
    }

    @Override
    public void run() {
        ventana.setBackground(Color.black);
        ventana.setLayout(Auxiliar.LAYOUT);

        buildMain();
        
        ventana.pack();
        ventana.setVisible(true);
    }

    private void buildMain() {
        //la parte de arriba
        contenedorCabecera.setLayout( new  BoxLayout(contenedorCabecera, BoxLayout.X_AXIS));
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
        contenedorBotones.setLayout(new BoxLayout(contenedorBotones, BoxLayout.X_AXIS));
        contenedorBotones.setBorder(Auxiliar.BORDE);
        
        //malla
        int lattice = automata.getL();
        Celula matriz[][] = new Celula[lattice][lattice];
        matriz = automata.getMatrizCelulas();
        for (int i = 0; i < lattice; i++) {
            for (int j = 0; j < lattice; j++) {
                System.out.println("Celula ["+ i +"][" + j+"]: "+matriz[i][j].getEstado());
                panelPrincipal.add(matriz[j][i].getRepresentacionGrafica());
            }
        }
        
        ventana.add(panelPrincipal,BorderLayout.CENTER);
        
        
        //botones
        botonFinalizacion.setBackground(Color.DARK_GRAY);
        botonFinalizacion.setForeground(Color.WHITE);
        botonFinalizacion.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        contenedorBotones.add(botonFinalizacion);
        
        botonEstadisticas.setBackground(Color.DARK_GRAY);
        botonEstadisticas.setForeground(Color.WHITE);
        botonEstadisticas.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
            }
        });
        contenedorBotones.add(botonEstadisticas);
        ventana.add(contenedorBotones,BorderLayout.SOUTH);
    }
     
   
}
