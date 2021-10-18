package ru.kolpakovkuleshov.charts;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import org.jzy3d.chart.AWTChart;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class Charts  {


    public List<AWTChart> getAWTCharts(JavaFXChartFactory factory) {

        List<AWTChart> chart = new ArrayList<>();
        // Jzy3d

        for(int i =0; i< 10;i++){
            System.out.println("create charts");
            chart.add(getDemoChart(factory, "offscreen"));
        }
        System.out.println(chart);

        //imageView = factory.bindImageView(chart.get(Ivan.get()));

        return chart;
    }

    private AWTChart getDemoChart(JavaFXChartFactory factory, String toolkit) {
        List<Double> xx;
        List<Double> yy;
        List<List<Double>> zz;
        xx = new ArrayList<>();
        yy = new ArrayList<>();
        zz = new ArrayList<>();
        for(int i=0; i <= 10; i++){
            List<Double> z1 = new ArrayList<>();
            yy.add((double) i);
            xx.add((double) i);
            for(int j=0; j<=10; j++){

                z1.add((Math.sin(j)*Math.cos(i)));
            }
            zz.add(z1);
        }
        System.out.println(zz);
        System.out.println(yy);
        System.out.println(xx);


        // -------------------------------
        // Define a function to plot
        Mapper mapper = new Mapper() {
            @Override
            public double f(double x, double y) {
                return zz.get(yy.indexOf(y)).get(xx.indexOf(x));
            }
        };

        // Define range and precision for the function to plot
        Range range = new Range(0, 10);
        int steps = 11;

        // Create the object to represent the function over the given range.
        final Shape surface = Builder.buildOrthonormal(mapper, range, steps);
        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);

        // -------------------------------
        // Create a chart
        Quality quality = Quality.Advanced;
        //quality.setSmoothPolygon(true);
        //quality.setAnimated(true);

        // let factory bind mouse and keyboard controllers to JavaFX node
        AWTChart chart = (AWTChart) factory.newChart(quality, toolkit);
        chart.getScene().getGraph().add(surface);
        System.out.println();
        return chart;
    }
}