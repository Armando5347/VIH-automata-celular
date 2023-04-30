/**
 * @author Jarillo Hernández Armando Damián
 *         Sigala Morales Said
 * 
 */
package modelo.java;
        
//librerias importadas
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
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
import javax.swing.SwingUtilities;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.Dimension;



public class Malla extends Thread {

    private AutomataCelular automata;
    private static final long FRAME_DELAY = 500;
    private static volatile boolean ordenContinuar = true;
    private final JFrame ventana;
    private final JPanel contenedorCabecera;
    private final JPanel contenedorBotones;
    private JButton botonPausarReanudar = new JButton("Pausar");
    private JPanel panelPrincipal;
    private BufferedImage imageBuffer;

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
    


    Malla(int l, double p_vih, double p_infect, int ra, int rb, int t) {
        System.out.println("Nuevo automata");
        this.panelPrincipal = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(imageBuffer, 0, 0, null);
            }
        };
        panelPrincipal.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                createImageBuffer();
                updateAnimation();
                panelPrincipal.repaint();
            }
        });
        this.contenedorBotones = new JPanel();
        this.contenedorCabecera = new JPanel();
        this.ventana = new JFrame("Simulación del automata");
        automata = new AutomataCelular(l, ra, rb, t, p_infect, p_vih);
        automata.imprimirDensidades();
        layoutMalla = new GridLayout(l, l);
        botonEstadisticas.setEnabled(false);
       // imageBuffer = new BufferedImage(l * Celula.TAMANO_CELDA, l * Celula.TAMANO_CELDA, BufferedImage.TYPE_INT_ARGB);
        createImageBuffer();

    }
    
    public void run() {
        ventana.setBackground(Color.black);
        ventana.setLayout(new BorderLayout());
        ventana.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        // Establece un tamaño mínimo para la ventana de la simulación
         int minWindowSize = 900;
         ventana.setMinimumSize(new Dimension(minWindowSize, minWindowSize));

         buildMain();
         
         ventana.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (ventana.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
                    createImageBuffer();
                    updateAnimation();
                    panelPrincipal.repaint();
                }
            }
        });

         ventana.pack();
         ventana.setVisible(true);
        int semanas = 0;
        while (ordenContinuar) {
            if (!pausado) {
                try {
                    automata.ejecutarSimulacion(1);
                    semanas++;
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            updateAnimation();
                            panelPrincipal.repaint();
                        }
                    });
                    System.out.println("Estamos en el t = " + semanas);
                    synchronized (this) {
                        wait(FRAME_DELAY);
                    }
                } catch (InterruptedException ex) { Logger.getLogger(Malla.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    } else {
                        synchronized (this) {
                        try {
                        wait();
                        } catch (InterruptedException ex) {
                        Logger.getLogger(Malla.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
            automata.imprimirDensidades();
}

private void buildMain() {
    contenedorCabecera.setLayout(new GridLayout(1, 0, 5, 1));
    contenedorCabecera.setBackground(Color.black);
    DensidadGrafica densidadGrafica = new DensidadGrafica();

    Auxiliar.asignarEstiloLabel(nombreSanas, Auxiliar.FUENTE_MINI, contenedorCabecera);
    Auxiliar.asignarEstiloLabel(nombreTipoA, Auxiliar.FUENTE_MINI, contenedorCabecera);
    Auxiliar.asignarEstiloLabel(nombreTipoB, Auxiliar.FUENTE_MINI, contenedorCabecera);
    Auxiliar.asignarEstiloLabel(nombreMuertas, Auxiliar.FUENTE_MINI, contenedorCabecera);
    ventana.add(contenedorCabecera,BorderLayout.NORTH);
    
    panelPrincipal.setBackground(Color.black);
    ventana.add(panelPrincipal, BorderLayout.CENTER);

    contenedorBotones.setBackground(Color.black);
    contenedorBotones.setLayout(new GridLayout(1, 0, 2, 2));
    ventana.add(contenedorBotones, BorderLayout.SOUTH);

    Auxiliar.asignarEstilosBoton(botonFinalizacion, contenedorBotones);
    botonFinalizacion.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            terminarSimulacion();
        }
    });

    Auxiliar.asignarEstilosBoton(botonPausarReanudar, contenedorBotones);
    botonPausarReanudar.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            pausarReanudarSimulacion();
        }
    });

    Auxiliar.asignarEstilosBoton(botonEstadisticas, contenedorBotones);
    botonEstadisticas.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            densidadGrafica.mostrarGrafico(automata.getHistorialDensidades()); // Añade esta línea para mostrar el gráfico de densidades
        }
    });
}



private void updateAnimation() {
    Graphics g = imageBuffer.getGraphics();
    int lattice = automata.getL();
    Celula matriz[][] = automata.getMatrizCelular();
    int cellWidth = panelPrincipal.getWidth() / lattice;
    int cellHeight = panelPrincipal.getHeight() / lattice;
    int margin = 1; // Añade un margen de 1 píxel

    for (int i = 0; i < lattice; i++) {
        for (int j = 0; j < lattice; j++) {
            g.setColor(matriz[j][i].getRepresentacionGrafica().getBackground());
            g.fillRect(j * cellWidth + margin, i * cellHeight + margin, cellWidth - 2 * margin, cellHeight - 2 * margin);
        }
    }

    panelPrincipal.revalidate(); // Agrega esto
    panelPrincipal.repaint(); // Agrega esto
}

private void terminarSimulacion() {
    ordenContinuar = false;
    botonEstadisticas.setEnabled(true);
    botonPausarReanudar.setEnabled(false);
}

private volatile boolean pausado = false;

private void pausarReanudarSimulacion() {
    pausado = !pausado;
    if (pausado) {
        botonPausarReanudar.setText("Reanudar");
    } else {
        botonPausarReanudar.setText("Pausar");
        synchronized (this) {
            notify();
            }
        }
    }
    private void createImageBuffer() {
        int width = panelPrincipal.getWidth();
        int height = panelPrincipal.getHeight();
        if (width > 0 && height > 0) {
            imageBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }
    }

}
