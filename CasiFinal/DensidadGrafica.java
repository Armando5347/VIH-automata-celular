package automatacelular;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javax.swing.*;
import java.util.List;

public class DensidadGrafica {
    private JFXPanel jfxPanel;
    private LineChart<Number, Number> lineChart;

    public DensidadGrafica() {
        jfxPanel = new JFXPanel();
        Platform.runLater(() -> {
            NumberAxis xAxis = new NumberAxis();
            NumberAxis yAxis = new NumberAxis();
            xAxis.setLabel("Tiempo (Semanas)");
            yAxis.setLabel("Densidad de celulas");
            xAxis.setAutoRanging(true);
            yAxis.setAutoRanging(true);

            lineChart = new LineChart<>(xAxis, yAxis);
            lineChart.setTitle("Evolución de las densidades");

            Scene scene = new Scene(lineChart, 800, 600);
            jfxPanel.setScene(scene);
        });
    }

  public void mostrarGrafico(NodoDensidad cabeceraDensidades) {
    SwingUtilities.invokeLater(() -> {
        JFrame frame = new JFrame("Gráfico de densidades");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(jfxPanel);
        frame.pack();
        frame.setVisible(true);
    });

    Platform.runLater(() -> {
        XYChart.Series<Number, Number> sanasSeries = new XYChart.Series<>();
        sanasSeries.setName("Sanas");
        XYChart.Series<Number, Number> infectadasSeries = new XYChart.Series<>();
        infectadasSeries.setName("Infectadas");
        XYChart.Series<Number, Number> muertasSeries = new XYChart.Series<>();
        muertasSeries.setName("Muertas");

        double minDensidad = Double.MAX_VALUE;
        double maxDensidad = Double.MIN_VALUE;
        int tiempo = 0;
        NodoDensidad nodoDensidad = cabeceraDensidades;
        while (nodoDensidad != null) {
            double densidadSanas = nodoDensidad.densidadCelulasSanas;
            double densidadInfectadasTotal = nodoDensidad.densidadCelulasInfectadas;
            double densidadMuertas = nodoDensidad.densidadCelulasMuertas;

            sanasSeries.getData().add(new XYChart.Data<>(tiempo, densidadSanas));
            infectadasSeries.getData().add(new XYChart.Data<>(tiempo, densidadInfectadasTotal));
            muertasSeries.getData().add(new XYChart.Data<>(tiempo, densidadMuertas));

            minDensidad = Math.min(minDensidad, densidadSanas);
            maxDensidad = Math.max(maxDensidad, densidadSanas);
            minDensidad = Math.min(minDensidad, densidadInfectadasTotal);
            maxDensidad = Math.max(maxDensidad, densidadInfectadasTotal);
            minDensidad = Math.min(minDensidad, densidadMuertas);
            maxDensidad = Math.max(maxDensidad, densidadMuertas);

            nodoDensidad = nodoDensidad.siguienteDensidad;
            tiempo++;
        }

        // Ajustar los ejes X e Y para que se adapten a los valores
        NumberAxis xAxis = (NumberAxis) lineChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) lineChart.getYAxis();
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);

        lineChart.getData().clear();
        lineChart.getData().addAll(sanasSeries, infectadasSeries, muertasSeries);
    });
}


}
