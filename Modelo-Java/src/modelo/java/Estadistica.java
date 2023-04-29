/**
 * @author Jarillo Hernández Armando Damián
 *         Sigala Morales Said
 * 
 */
package modelo.java;

import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;


public class Estadistica {
    
    public Estadistica() {
    }

    
    public static void generargrafica(NodoDensidad densidadesModelo){
        try{
        DefaultCategoryDataset ds = new DefaultCategoryDataset();
        NodoDensidad nodoIterador = densidadesModelo;
        int contadorDensidades = 0;
        while (nodoIterador != null){
            ds.addValue(nodoIterador.densidadCelulasSanas, "Células sanas", String.valueOf(contadorDensidades));
            ds.addValue(nodoIterador.densidadCelulasInfectadas, "Células infectadas", String.valueOf(contadorDensidades));
            ds.addValue(nodoIterador.densidadCelulasMuertas, "Células muertas", String.valueOf(contadorDensidades));
            nodoIterador = nodoIterador.getSiguiente();
            contadorDensidades ++;
        }
        
        JFreeChart jf = ChartFactory.createLineChart("Densidades de celulas",
                "Peridodos (semanas)",
                "Densidad",
                ds, 
                PlotOrientation.VERTICAL, 
                true, 
                true, 
                true);
            ChartFrame f = new ChartFrame("Grafica de densidades",jf);
            f.setSize(1000,600);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        
        }catch(Exception e){
            System.out.println("Error" + e);
        }
        
    }

}
