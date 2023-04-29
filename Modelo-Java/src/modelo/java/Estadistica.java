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
    
    private ArrayList <NodoDensidad> densidadesModelo;

    public Estadistica() {
    }

    public Estadistica(ArrayList<NodoDensidad> densidadesModelo) {
        this.densidadesModelo = densidadesModelo;
    }
    
    public static void generargrafica(){
        try{
        DefaultCategoryDataset ds =new DefaultCategoryDataset();
        ds.addValue(1518, "Delitos contra la vida y la Integridad Corporal", "");
        ds.addValue(261, "Delitos contra la Libertad", "");
        ds.addValue(944, "Delitos contra la libertad sexual", "");
        ds.addValue(20139, "Delitos contra el patrimonio", "");
        
        
        JFreeChart jf= ChartFactory.createBarChart3D("Delitos en Cuautemoc", "Crímenes", "Delitos cometidos a lo largo del 2019", ds, PlotOrientation.VERTICAL, true, true, true);
            ChartFrame f= new ChartFrame("Grafica inseguridad",jf);
            f.setSize(1000,600);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        
        }catch(Exception e){
            System.out.println("Error" + e);
        }
        
    }

    public ArrayList<NodoDensidad> getDensidadesModelo() {
        return densidadesModelo;
    }

    public void setDensidadesModelo(ArrayList<NodoDensidad> densidadesModelo) {
        this.densidadesModelo = densidadesModelo;
    }
    
}
