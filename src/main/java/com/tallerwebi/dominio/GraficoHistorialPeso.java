package com.tallerwebi.dominio;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ui.UIUtils;
import org.jfree.chart.ChartUtils;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class GraficoHistorialPeso extends JFrame {

    public GraficoHistorialPeso(List<HistoriaPesoUsuario> historialPesos) {
        super("Historial de Peso");

        // Obtener el valor mínimo del historial de peso
        double minValue = Double.MAX_VALUE;
        for (HistoriaPesoUsuario historiaPesoUsuario : historialPesos) {
            minValue = Math.min(minValue, historiaPesoUsuario.getPeso());
        }

        // Establecer un valor mínimo predeterminado para el eje Y
        double defaultMinValue = minValue ; //quiero que me de 2 valores abajo del peso minimo

        // Crear el conjunto de datos XY
        TimeSeries series = new TimeSeries("Peso");
        for (HistoriaPesoUsuario historiaPesoUsuario : historialPesos) {
            series.add(new Day(historiaPesoUsuario.getFecha()), historiaPesoUsuario.getPeso());
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection(series);
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Historial de Peso",
                "Fecha",
                "Peso (Kg)",
                dataset,
                false,
                true,
                false
        );

        // Configurar el rango del eje Y para que comience desde el valor mínimo predeterminado
        XYPlot plot = chart.getXYPlot();
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy"));

        // Guardar el gráfico como un archivo PNG
        File chartFile = new File("src/main/webapp/resources/core/img/chart.png");
        try {
            ChartUtils.saveChartAsPNG(chartFile, chart, 1000, 700);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Configurar el panel del gráfico
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1000, 700));

        // Agregar el panel al JFrame
        setContentPane(chartPanel);
    }
}