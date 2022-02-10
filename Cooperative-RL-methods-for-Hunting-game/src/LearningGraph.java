/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myprogram;

import ChartDirector.Chart;
import ChartDirector.ChartViewer;
import ChartDirector.LineLayer;
import ChartDirector.XYChart;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *
 * @author Wiem
 */
public class LearningGraph {

    //Name of demo program
    public String toString() {
        return "Multi-Line Chart";
    }

    //Number of charts produced in this demo
    public int getNoOfCharts() {
        return 1;
    }

    //Main code for creating charts
    public void createChart(ChartViewer viewer, int index) {
        // The data for the line chart
        double[] data0 = {42, 49, 33, 38, 51, 46, 29, 41, 44, 57, 59, 52, 37, 34, 51,
            56, 56, 60, 70, 76, 63, 67, 75, 64, 51};
        double[] data1 = {50, 55, 47, 34, 42, 49, 63, 62, 73, 59, 56, 50, 64, 60, 67,
            67, 58, 59, 73, 77, 84, 82, 80, 84, 98};
        double[] data2 = {36, 28, 25, 33, 38, 20, 22, 30, 25, 33, 30, 24, 28, 15, 21,
            26, 46, 42, 48, 45, 43, 52, 64, 60, 70};

        // The labels for the line chart
        String[] labels = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22",
            "23", "24"};

        // Create an XYChart object of size 600 x 300 pixels, with a light blue
        // (EEEEFF) background, black border, 1 pxiel 3D border effect and rounded
        // corners
        XYChart c = new XYChart(600, 300, 0xeeeeff, 0x000000, 1);
        c.setRoundedFrame();

        // Set the plotarea at (55, 58) and of size 520 x 195 pixels, with white
        // background. Turn on both horizontal and vertical grid lines with light
        // grey color (0xcccccc)
        c.setPlotArea(55, 58, 520, 195, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);

        // Add a legend box at (50, 30) (top of the chart) with horizontal layout.
        // Use 9 pts Arial Bold font. Set the background and border color to
        // Transparent.
        c.addLegend(50, 30, false, "Arial Bold", 9).setBackground(Chart.Transparent);

        // Add a title box to the chart using 15 pts Times Bold Italic font, on a
        // light blue (CCCCFF) background with glass effect. white (0xffffff) on a
        // dark red (0x800000) background, with a 1 pixel 3D border.
        c.addTitle("Application Server Throughput", "Times New Roman Bold Italic", 15
        ).setBackground(0xccccff, 0x000000, Chart.glassEffect());

        // Add a title to the y axis
        c.yAxis().setTitle("MBytes per hour");

        // Set the labels on the x axis.
        c.xAxis().setLabels(labels);

        // Display 1 out of 3 labels on the x-axis.
        c.xAxis().setLabelStep(10);//40

        // Add a title to the x axis
        c.xAxis().setTitle("Jun 12, 2006");

        // Add a line layer to the chart
        LineLayer layer = c.addLineLayer2();

        // Set the default line width to 2 pixels
        layer.setLineWidth(2);

        // Add the three data sets to the line layer. For demo purpose, we use a dash
        // line color for the last line
        layer.addDataSet(data0, 0xff0000, "Server #1");
        layer.addDataSet(data1, 0x008800, "Server #2");
        layer.addDataSet(data2, c.dashLineColor(0x3333ff, Chart.DashLine),
                "Server #3");

        // Output the chart
        viewer.setChart(c);

        //include tool tip for the chart
        viewer.setImageMap(c.getHTMLImageMap("clickable", "",
                "title='[{dataSetName}] Hour {xLabel}: {value} MBytes'"));
    }

    //dataAntC max des robots learning_graph0()
    public void createChart(String titre, String method, ChartViewer viewer, int index, double[] data, String[] labels) {

        for (int k = 0; k < data.length; k++) {
           System.out.println("episode n°=" + k + "  data=" + data[k]);
        }
        System.out.println("l'ensemble des episodes");
        for (int k = 0; k < data.length; k++) {
            System.out.print(data[k] + ", ");
        }

        //System.out.println("data0("+k+")="+data0[k]+"labels("+k+")="+labels[k]);
        // Create an XYChart object of size 600 x 300 pixels, with a light blue
        // (EEEEFF) background, black border, 1 pxiel 3D border effect and rounded
        // corners
        //XYChart c = new XYChart(500, 350, 0xeeeeff, 0x000000, 1);
        XYChart c = new XYChart(400, 350, 0xeeeeff, 0x000000, 1);//(600, 650, 0xeeeeff, 0x000000, 1);
        c.setRoundedFrame();

        // Set the plotarea at (55, 58) and of size 520 x 195 pixels, with white
        // background. Turn on both horizontal and vertical grid lines with light
        // grey color (0xcccccc)
        // c.setPlotArea(55, 55, 400, 250, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);
        c.setPlotArea(55, 55, 350, 250, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);//(55, 55, 500, 550, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);

        // Add a legend box at (50, 30) (top of the chart) with horizontal layout.
        // Use 9 pts Arial Bold font. Set the background and border color to
        // Transparent.
        c.addLegend(50, 30, false, "Arial Bold", 9).setBackground(Chart.Transparent);

        // Add a title box to the chart using 15 pts Times Bold Italic font, on a
        // light blue (CCCCFF) background with glass effect. white (0xffffff) on a
        // dark red (0x800000) background, with a 1 pixel 3D border.
        c.addTitle(titre, "Times New Roman Bold Italic", 15
        ).setBackground(0xccccff, 0x000000, Chart.glassEffect());

        // Add a title to the y axis
        c.yAxis().setTitle("Nombre de pas par épisodes");

        // Set the labels on the x axis.
        c.xAxis().setLabels(labels);

        // Display 1 out of 3 labels on the x-axis.
        //afficher label chaque 5 épisodes(5 10 15 ...)
        c.xAxis().setLabelStep(10);

        // Add a title to the x axis
        c.xAxis().setTitle("Nombre d'épisodes");

        // Add a line layer to the chart
        LineLayer layer = c.addLineLayer2();

        // Set the default line width to 2 pixels
        layer.setLineWidth(1);

        // Add the three data sets to the line layer. For demo purpose, we use a dash
        // line color for the last line
        layer.addDataSet(data, 0x3333ff, method);
        //layer.addDataSet(data1, 0x008800, "Server #2");
        //layer.addDataSet(data2, c.dashLineColor(0x3333ff, Chart.DashLine),"Server #3");

        // Output the chart
        viewer.setImage(c.makeImage());

    }

    //dataAntC max des robots learning_graph0()
    public void createChart1000(String titre, String method, ChartViewer viewer, int index, double[] data, String[] labels) {

        for (int k = 0; k < data.length; k++) {
            System.out.println("episode n°=" + k + "  data=" + data[k]);
        }
        System.out.println("ensemble des éléments capturés");
        for (int k = 0; k < data.length; k++) {
            System.out.print(data[k] + ", ");
        }

        //System.out.println("data0("+k+")="+data0[k]+"labels("+k+")="+labels[k]);
        // Create an XYChart object of size 600 x 300 pixels, with a light blue
        // (EEEEFF) background, black border, 1 pxiel 3D border effect and rounded
        // corners
        //XYChart c = new XYChart(500, 350, 0xeeeeff, 0x000000, 1);
        XYChart c = new XYChart(400, 350, 0xeeeeff, 0x000000, 1);//(600, 650, 0xeeeeff, 0x000000, 1);
        c.setRoundedFrame();

        // Set the plotarea at (55, 58) and of size 520 x 195 pixels, with white
        // background. Turn on both horizontal and vertical grid lines with light
        // grey color (0xcccccc)
        // c.setPlotArea(55, 55, 400, 250, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);
        c.setPlotArea(55, 55, 350, 250, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);//(55, 55, 500, 550, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);

        // Add a legend box at (50, 30) (top of the chart) with horizontal layout.
        // Use 9 pts Arial Bold font. Set the background and border color to
        // Transparent.
        c.addLegend(50, 30, false, "Arial Bold", 9).setBackground(Chart.Transparent);

        // Add a title box to the chart using 15 pts Times Bold Italic font, on a
        // light blue (CCCCFF) background with glass effect. white (0xffffff) on a
        // dark red (0x800000) background, with a 1 pixel 3D border.
        c.addTitle(titre, "Times New Roman Bold Italic", 15
        ).setBackground(0xccccff, 0x000000, Chart.glassEffect());

        // Add a title to the y axis
        c.yAxis().setTitle("Nombre d'épisodes effectués");

        // Set the labels on the x axis.
        c.xAxis().setLabels(labels);

        // Display 1 out of 3 labels on the x-axis.
        //afficher label chaque 5 épisodes(5 10 15 ...)
        c.xAxis().setLabelStep(10);

        // Add a title to the x axis
        c.xAxis().setTitle("Nombre d'itérations (x1000)");

        // Add a line layer to the chart
        LineLayer layer = c.addLineLayer2();

        // Set the default line width to 2 pixels
        layer.setLineWidth(1);

        // Add the three data sets to the line layer. For demo purpose, we use a dash
        // line color for the last line
        layer.addDataSet(data, 0x3333ff, method);
        //layer.addDataSet(data1, 0x008800, "Server #2");
        //layer.addDataSet(data2, c.dashLineColor(0x3333ff, Chart.DashLine),"Server #3");

        // Output the chart
        viewer.setImage(c.makeImage());

    }

    //dataAntC de chaque robot learning_graph1()
    public void createChartAllRobots(String titre, ChartViewer viewer, int index, double[][] data, String[] labels) {

        //System.out.println("data0("+k+")="+data0[k]+"labels("+k+")="+labels[k]);
        // Create an XYChart object of size 600 x 300 pixels, with a light blue
        // (EEEEFF) background, black border, 1 pxiel 3D border effect and rounded
        // corners
        //XYChart c = new XYChart(500, 350, 0xeeeeff, 0x000000, 1);
        XYChart c = new XYChart(400, 350, 0xeeeeff, 0x000000, 1);//(600, 650, 0xeeeeff, 0x000000, 1);
        c.setRoundedFrame();

        // Set the plotarea at (55, 58) and of size 520 x 195 pixels, with white
        // background. Turn on both horizontal and vertical grid lines with light
        // grey color (0xcccccc)
        // c.setPlotArea(55, 55, 400, 250, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);
        c.setPlotArea(55, 55, 350, 250, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);//(55, 55, 500, 550, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);

        // Add a legend box at (50, 30) (top of the chart) with horizontal layout.
        // Use 9 pts Arial Bold font. Set the background and border color to
        // Transparent.
        c.addLegend(50, 30, false, "Arial Bold", 9).setBackground(Chart.Transparent);

        // Add a title box to the chart using 15 pts Times Bold Italic font, on a
        // light blue (CCCCFF) background with glass effect. white (0xffffff) on a
        // dark red (0x800000) background, with a 1 pixel 3D border.
        c.addTitle(titre, "Times New Roman Bold Italic", 15
        ).setBackground(0xccccff, 0x000000, Chart.glassEffect());

        // Add a title to the y axis
        c.yAxis().setTitle("Nombre de pas par épisodes");

        // Set the labels on the x axis.
        c.xAxis().setLabels(labels);

        // Display 1 out of 3 labels on the x-axis.
        //afficher label chaque 5 épisodes(5 10 15 ...)
        c.xAxis().setLabelStep(10);
        // Add a title to the x axis
        c.xAxis().setTitle("Nombre d'épisodes");

        // Add a line layer to the chart
        LineLayer layer = c.addLineLayer2();

        // Set the default line width to 2 pixels
        layer.setLineWidth(1);

        // Add the three data sets to the line layer. For demo purpose, we use a dash
        // line color for the last line
        //pour 3 robots
        layer.addDataSet(data[0], 0x3399FF, "Robot 1");
        layer.addDataSet(data[1], 0xFF3399, "Robot 2");
        layer.addDataSet(data[2], 0x99FF00, "Robot 3");

        // Output the chart
        viewer.setImage(c.makeImage());

    }

    //dataAntC de tous les robots
    public void createChartAllRobots(String titre, ChartViewer viewer, int index, double[][] data1, double[][] data2, String[] labels) {

        //System.out.println("data0("+k+")="+data0[k]+"labels("+k+")="+labels[k]);
        // Create an XYChart object of size 600 x 300 pixels, with a light blue
        // (EEEEFF) background, black border, 1 pxiel 3D border effect and rounded
        // corners
        //XYChart c = new XYChart(500, 350, 0xeeeeff, 0x000000, 1);
        XYChart c = new XYChart(400, 350, 0xeeeeff, 0x000000, 1);//(600, 650, 0xeeeeff, 0x000000, 1);
        c.setRoundedFrame();

        // Set the plotarea at (55, 58) and of size 520 x 195 pixels, with white
        // background. Turn on both horizontal and vertical grid lines with light
        // grey color (0xcccccc)
        // c.setPlotArea(55, 55, 400, 250, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);
        c.setPlotArea(55, 55, 350, 250, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);//(55, 55, 500, 550, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);

        // Add a legend box at (50, 30) (top of the chart) with horizontal layout.
        // Use 9 pts Arial Bold font. Set the background and border color to
        // Transparent.
        c.addLegend(50, 30, false, "Arial Bold", 9).setBackground(Chart.Transparent);

        // Add a title box to the chart using 15 pts Times Bold Italic font, on a
        // light blue (CCCCFF) background with glass effect. white (0xffffff) on a
        // dark red (0x800000) background, with a 1 pixel 3D border.
        c.addTitle(titre, "Times New Roman Bold Italic", 15
        ).setBackground(0xccccff, 0x000000, Chart.glassEffect());

        // Add a title to the y axis
        c.yAxis().setTitle("Nombre de pas par épisodes");

        // Set the labels on the x axis.
        c.xAxis().setLabels(labels);

        // Display 1 out of 3 labels on the x-axis.
        //afficher label chaque 5 épisodes(5 10 15 ...)
        c.xAxis().setLabelStep(10);

        // Add a title to the x axis
        c.xAxis().setTitle("Nombre d'épisodes");

        // Add a line layer to the chart
        LineLayer layer = c.addLineLayer2();

        // Set the default line width to 2 pixels
        layer.setLineWidth(1);

        // Add the three data sets to the line layer. For demo purpose, we use a dash
        // line color for the last line
        //pour 3 robots
        layer.addDataSet(data1[0], 0x3399FF, "R1_M1");
        layer.addDataSet(data1[1], 0xFF3399, "R2_M1");
        layer.addDataSet(data1[2], 0x00CC00, "R3_M1");//0x99FF00
        //pour 3 robots
        layer.addDataSet(data2[0], c.dashLineColor(0x3399FF, Chart.DashLine), "R1_M2");
        layer.addDataSet(data2[1], c.dashLineColor(0xFF3399, Chart.DashLine), "R2_M2");
        layer.addDataSet(data2[2], c.dashLineColor(0x00CC00, Chart.DashLine), "R3_M2");

        // Output the chart
        viewer.setImage(c.makeImage());

    }

    void createChartAllRobots(String titre, ChartViewer viewer, int minSizeTable, ArrayList<Double>[] table) {
        double[][] data = new double[3][minSizeTable];
        String[] labels = new String[minSizeTable];
        for (int i = 0; i < minSizeTable; i++) {
            for (int k = 0; k < 3; k++) {
                data[k][i] = table[k].get(i);
            }

            labels[i] = Integer.toString(i);
        }
     //  labelsAntC[k][i]=Integer.toString(i);

        // Create an XYChart object of size 600 x 300 pixels, with a light blue
        // (EEEEFF) background, black border, 1 pxiel 3D border effect and rounded
        // corners
        //XYChart c = new XYChart(500, 350, 0xeeeeff, 0x000000, 1);
        XYChart c = new XYChart(400, 350, 0xeeeeff, 0x000000, 1);//(600, 650, 0xeeeeff, 0x000000, 1);
        c.setRoundedFrame();

        // Set the plotarea at (55, 58) and of size 520 x 195 pixels, with white
        // background. Turn on both horizontal and vertical grid lines with light
        // grey color (0xcccccc)
        // c.setPlotArea(55, 55, 400, 250, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);
        c.setPlotArea(55, 55, 350, 250, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);//(55, 55, 500, 550, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);

        // Add a legend box at (50, 30) (top of the chart) with horizontal layout.
        // Use 9 pts Arial Bold font. Set the background and border color to
        // Transparent.
        c.addLegend(50, 30, false, "Arial Bold", 9).setBackground(Chart.Transparent);

        // Add a title box to the chart using 15 pts Times Bold Italic font, on a
        // light blue (CCCCFF) background with glass effect. white (0xffffff) on a
        // dark red (0x800000) background, with a 1 pixel 3D border.
        c.addTitle(titre, "Times New Roman Bold Italic", 15
        ).setBackground(0xccccff, 0x000000, Chart.glassEffect());

        // Add a title to the y axis
        c.yAxis().setTitle("Nombre de pas par épisodes");

        // Set the labels on the x axis.
        c.xAxis().setLabels(labels);

        // Display 1 out of 3 labels on the x-axis.
        //afficher label chaque 5 épisodes(5 10 15 ...)
        c.xAxis().setLabelStep(10);
        // Add a title to the x axis
        c.xAxis().setTitle("Nombre d'épisodes");

        // Add a line layer to the chart
        LineLayer layer = c.addLineLayer2();

        // Set the default line width to 2 pixels
        layer.setLineWidth(1);

        // Add the three data sets to the line layer. For demo purpose, we use a dash
        // line color for the last line
        //pour 3 robots
        layer.addDataSet(data[0], 0x3399FF, "Robot 1");
        layer.addDataSet(data[1], 0xFF3399, "Robot 2");
        layer.addDataSet(data[2], 0x99FF00, "Robot 3");

        // Output the chart
        viewer.setImage(c.makeImage());

    }
     void createChartMaxRobots(int numExp,String titre, ChartViewer viewer2, int minSizeTable, ArrayList<Double>[] table) {
        double[] data = new double[minSizeTable];
        String[] labels = new String[minSizeTable];

        for (int i = 0; i < minSizeTable; i++) {
            data[i] = table[0].get(i);
            for (int k = 1; k < table.length; k++) {
                if (data[i] < table[k].get(i)) {
                    data[i] = table[k].get(i);
                }
            }
            System.out.println(" episode " + i + "=" + data[i]);
            labels[i] = Integer.toString(i);
        }
        //stocker les valeurs de data (max tous les robots) dans un fichier
        try {
            //initiliser files 
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("E"+numExp+"_episodes_Système.txt"))));
           /*pw.println("les épisodes du système");
            
            for (double d : data) 
            pw.print(d+",");*/
            for (double d : data) 
            pw.println(d);
           
            pw.close();
        } catch (IOException exception) {
            System.out.println("Erreur lors de la lecture : " + exception.getMessage());
        }

        // Create an XYChart object of size 600 x 300 pixels, with a light blue
        // (EEEEFF) background, black border, 1 pxiel 3D border effect and rounded
        // corners
        //XYChart c = new XYChart(500, 350, 0xeeeeff, 0x000000, 1);
        XYChart c = new XYChart(1100, 450, 0xeeeeff, 0x000000, 1);//400,350(600, 650, 0xeeeeff, 0x000000, 1);
        c.setRoundedFrame();

        // Set the plotarea at (55, 58) and of size 520 x 195 pixels, with white
        // background. Turn on both horizontal and vertical grid lines with light
        // grey color (0xcccccc)
        // c.setPlotArea(55, 55, 400, 250, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);
        c.setPlotArea(55, 55, 1050, 350, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);//350,250(55, 55, 500, 550, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);

        // Add a legend box at (50, 30) (top of the chart) with horizontal layout.
        // Use 9 pts Arial Bold font. Set the background and border color to
        // Transparent.
        c.addLegend(50, 30, false, "Arial Bold", 9).setBackground(Chart.Transparent);

        // Add a title box to the chart using 15 pts Times Bold Italic font, on a
        // light blue (CCCCFF) background with glass effect. white (0xffffff) on a
        // dark red (0x800000) background, with a 1 pixel 3D border.
        c.addTitle(titre, "Times New Roman Bold Italic", 15
        ).setBackground(0xccccff, 0x000000, Chart.glassEffect());

        // Add a title to the y axis
        c.yAxis().setTitle("Nombre de pas par épisodes");

        // Set the labels on the x axis.
        c.xAxis().setLabels(labels);

        // Display 1 out of 3 labels on the x-axis.
        //afficher label chaque 5 épisodes(5 10 15 ...)
        c.xAxis().setLabelStep(10);
        // Add a title to the x axis
        c.xAxis().setTitle("Nombre d'épisodes");

        // Add a line layer to the chart
        LineLayer layer = c.addLineLayer2();

        // Set the default line width to 2 pixels
        layer.setLineWidth(1);

        // Add the three data sets to the line layer. For demo purpose, we use a dash
        // line color for the last line
        //pour 3 robots
        layer.addDataSet(data, 0x3399FF, "Robots");

        // Output the chart
        viewer2.setImage(c.makeImage());
    }
void createChartMoyenneEpisodes(String titre, ChartViewer viewer2, double[] data) {
        
        String[] labels = new String[data.length];

        for (int i = 0; i < data.length; i++) {
            
            System.out.println(" episode " + i + "=" + data[i]);
            labels[i] = Integer.toString(i);
        }
        //stocker les valeurs de data (max tous les robots) dans un fichier
        try {
            //initiliser files 
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("episodes_Moyenne.txt"),true)));
            pw.println("les épisodes du système");
            
            for (double d : data) 
            pw.print(d+",");
           
            pw.close();
        } catch (IOException exception) {
            System.out.println("Erreur lors de la lecture : " + exception.getMessage());
        }

        // Create an XYChart object of size 600 x 300 pixels, with a light blue
        // (EEEEFF) background, black border, 1 pxiel 3D border effect and rounded
        // corners
        //XYChart c = new XYChart(500, 350, 0xeeeeff, 0x000000, 1);
        XYChart c = new XYChart(1100, 450, 0xeeeeff, 0x000000, 1);//(600, 650, 0xeeeeff, 0x000000, 1);
        c.setRoundedFrame();

        // Set the plotarea at (55, 58) and of size 520 x 195 pixels, with white
        // background. Turn on both horizontal and vertical grid lines with light
        // grey color (0xcccccc)
        // c.setPlotArea(55, 55, 400, 250, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);
        c.setPlotArea(55, 55, 1050, 350, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);//(55, 55, 500, 550, 0xffffff, -1, -1, 0xcccccc, 0xcccccc);

        // Add a legend box at (50, 30) (top of the chart) with horizontal layout.
        // Use 9 pts Arial Bold font. Set the background and border color to
        // Transparent.
        c.addLegend(50, 30, false, "Arial Bold", 9).setBackground(Chart.Transparent);

        // Add a title box to the chart using 15 pts Times Bold Italic font, on a
        // light blue (CCCCFF) background with glass effect. white (0xffffff) on a
        // dark red (0x800000) background, with a 1 pixel 3D border.
        c.addTitle(titre, "Times New Roman Bold Italic", 15
        ).setBackground(0xccccff, 0x000000, Chart.glassEffect());

        // Add a title to the y axis
        c.yAxis().setTitle("Nombre de pas par épisodes");

        // Set the labels on the x axis.
        c.xAxis().setLabels(labels);

        // Display 1 out of 3 labels on the x-axis.
        //afficher label chaque 5 épisodes(5 10 15 ...)
        c.xAxis().setLabelStep(10);
        // Add a title to the x axis
        c.xAxis().setTitle("Nombre d'épisodes");

        // Add a line layer to the chart
        LineLayer layer = c.addLineLayer2();

        // Set the default line width to 2 pixels
        layer.setLineWidth(1);

        // Add the three data sets to the line layer. For demo purpose, we use a dash
        // line color for the last line
        //pour 3 robots
        layer.addDataSet(data, 0x3399FF, "Robots");

        // Output the chart
        viewer2.setImage(c.makeImage());
    }


    }
