package com.tallerwebi.dominio;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.ui.ApplicationFrame;
import org.jfree.chart.ChartUtils;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GraficoHistorialPeso extends JFrame {

    public GraficoHistorialPeso(List<HistoriaPesoUsuario> historialPesos) {
        super("Historial de Peso");


        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        Collections.sort(historialPesos,Comparator.comparing(HistoriaPesoUsuario::getFecha));
        int startIndex=Math.max(0,historialPesos.size()-7);
        List<HistoriaPesoUsuario> ultimosPesos = historialPesos.subList(startIndex, historialPesos.size());



        for (HistoriaPesoUsuario historiaPesoUsuario : ultimosPesos) {
            dataset.addValue(historiaPesoUsuario.getPeso(), "Peso", new SimpleDateFormat("dd-MM-yyyy").format(historiaPesoUsuario.getFecha()));
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Historial de Peso",
                "Fecha",
                "Peso (Kg)",
                dataset,
                org.jfree.chart.plot.PlotOrientation.VERTICAL,
                false,
                true,
                false
        );


        CategoryPlot plot = chart.getCategoryPlot();
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, Color.RED); // Color de las barras
        renderer.setDrawBarOutline(false); // Sin contorno en las barras


        final double anchoBarra = 0.07;
        renderer.setMaximumBarWidth(anchoBarra);


        File chartFile = new File("src/main/webapp/resources/core/img/chart.png");
        try {
            ChartUtils.saveChartAsPNG(chartFile, chart, 1000, 700);
        } catch (IOException e) {
            e.printStackTrace();
        }


        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(1000, 700));


        setContentPane(chartPanel);
    }
}