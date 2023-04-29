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
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 *
 * @author maste
 */
public class Interfaz extends Thread{
    
    private JFrame ventana = new JFrame("Menú principal");
    private final JPanel contenedorTitulo = new JPanel();
    private final JPanel acciones = new JPanel();
    
    private JPanel firmado = new JPanel();
    private JPanel verificados = new JPanel();
    
    JLabel titulo = new JLabel("Modelo simulación expansión del VIH");
    JLabel subtit_Firmado = new JLabel("Monitoreo de firmas");
    JLabel subtit_Verificado = new JLabel("Monitoreo de validaciones");
    
    private final Font f_tit = new Font("Verdana", Font.BOLD, 50);
    private final Font f_subtit = new Font("Verdana", Font.BOLD, 35);
    private final Font f_txt = new Font("Verdana", Font.BOLD, 25);
    
    private JTextField statusFirmado = new JTextField("Sin firmar");
    private JTextField statusVerificado = new JTextField("Sin verificar");
    
    private final Border borde = BorderFactory.createLineBorder(Color.darkGray,3);
    private final Border bordeCompuesto = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,3), BorderFactory.createEmptyBorder(15, 15, 15, 15));
    private BorderLayout layout = new BorderLayout(0, 0);
    
    Interfaz(){
    }

    @Override
    public void run() {
        ventana.setBackground(Color.black);
        ventana.setLayout(layout);
        titulo.setBackground(Color.black);
        titulo.setForeground(Color.white);
        titulo.setFont(f_tit);
        contenedorTitulo.add(titulo);
        contenedorTitulo.setBorder(borde);
        contenedorTitulo.setBackground(Color.black);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.add(contenedorTitulo, BorderLayout.NORTH);
        buildMain();
        ventana.pack();
        ventana.setVisible(true);
    }

    private void buildMain() {
        acciones.setBackground(Color.black);
        acciones.setLayout(new BoxLayout(acciones, BoxLayout.X_AXIS));
        acciones.setBorder(bordeCompuesto);
        firmado.setBackground(Color.black);
        firmado.setLayout(new BorderLayout(5, 5));
        firmado.setBorder(borde);
        subtit_Firmado.setBackground(Color.black);
        subtit_Firmado.setForeground(Color.white);
        subtit_Firmado.setFont(f_subtit);
        firmado.add(subtit_Firmado, BorderLayout.NORTH);
        statusFirmado.setFont(f_txt);
        statusFirmado.setEditable(false);
        statusFirmado.setMaximumSize(new Dimension(200,60));
        firmado.add(statusFirmado,BorderLayout.CENTER);
        
        acciones.add(firmado);
        
        verificados.setBackground(Color.black);
        verificados.setLayout(new BorderLayout(5, 5));
        verificados.setBorder(borde);
        subtit_Verificado.setBackground(Color.black);
        subtit_Verificado.setForeground(Color.white);
        subtit_Verificado.setFont(f_subtit);
        firmado.add(subtit_Verificado, BorderLayout.NORTH);
        statusVerificado.setFont(f_txt);
        statusVerificado.setEditable(false);
        statusVerificado.setMaximumSize(new Dimension(200,60));
        verificados.add(statusVerificado,BorderLayout.CENTER);
        acciones.add(verificados);
        
        subtit_Firmado.setBackground(Color.black);
        subtit_Firmado.setForeground(Color.white);
        subtit_Firmado.setFont(f_subtit);
        subtit_Firmado.setHorizontalAlignment(SwingConstants.CENTER);
        firmado.add(subtit_Firmado, BorderLayout.NORTH);
        
        subtit_Verificado.setBackground(Color.black);
        subtit_Verificado.setForeground(Color.white);
        subtit_Verificado.setFont(f_subtit);
        subtit_Verificado.setHorizontalAlignment(SwingConstants.CENTER);
        verificados.add(subtit_Verificado, BorderLayout.NORTH);
        
        
        ventana.add(acciones,BorderLayout.CENTER);
    }

}
