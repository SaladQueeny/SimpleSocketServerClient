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


public class Charts extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }
    private Button btnGenerate;
    private double a,b;
    private Pane pane;
    private ImageView imageView;
    //private List<AWTChart> chart;
    private JavaFXChartFactory factory;
    private Scene scene;
    public static List<Double> xx;
    public static List<Double> yy;
    public static List<List<Double>> zz;
    @Override
    public void start(Stage stage) {
        stage.setTitle(Charts.class.getSimpleName());
        AtomicInteger Ivan = new AtomicInteger();
        a=Math.random();
        b=Math.random();
        List<AWTChart> chart = new ArrayList<>();
        // Jzy3d
        factory = new JavaFXChartFactory();

        for(int i =0; i< 10;i++){
            a=i;
            b=i;
            chart.add(getDemoChart(factory, "offscreen"));
        }
        System.out.println(chart);

        imageView = factory.bindImageView(chart.get(Ivan.get()));

        // JavaFX
        pane = new Pane();
        scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
        pane.getChildren().add(imageView);

        factory.addSceneSizeChangedListener(chart.get(Ivan.get()), scene);

        btnGenerate = new Button("Generate");
        btnGenerate.setLayoutX(50);
        btnGenerate.setLayoutY(50);
        btnGenerate.setOnAction(event -> {
            Ivan.getAndIncrement();
            pane.getChildren().remove(imageView);
            pane.getChildren().remove(btnGenerate);

            imageView = factory.bindImageView(chart.get(Ivan.get()));

            pane.getChildren().add(imageView);
            pane.getChildren().add(btnGenerate);
            factory.addSceneSizeChangedListener(chart.get(Ivan.get()), scene);
            factory.resetSize(chart.get(Ivan.get()), scene.getWidth(), scene.getHeight());
        });
        pane.getChildren().add(btnGenerate);

        stage.setWidth(500);
        stage.setHeight(500);
    }

    private AWTChart getDemoChart(JavaFXChartFactory factory, String toolkit) {
        xx = new ArrayList<>();
        yy = new ArrayList<>();
        zz = new ArrayList<>();
        for(int i=0; i <= 10; i++){
            List<Double> z1 = new ArrayList<>();
            yy.add((double) i);
            xx.add((double) i);
            for(int j=0; j<=10; j++){

                z1.add((double) (Math.sin(j)*Math.cos(i)));
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