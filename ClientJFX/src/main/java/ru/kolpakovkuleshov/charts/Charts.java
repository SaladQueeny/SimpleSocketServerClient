package ru.kolpakovkuleshov.charts;

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

public class Charts  {

    private List<Double> x;
    private List<Double> y;
    private List<Double> t;
    private List<List<List<Double>>> z;
    public List<AWTChart> getAWTCharts(JavaFXChartFactory factory, List<Double> x, List<Double> y, List<Double> t, List<List<List<Double>>> z) {

        this.x=x;
        this.y=y;
        this.t=t;
        this.z=z;

        List<AWTChart> chart = new ArrayList<>();
        System.out.println(t.size()+" "+ z.size()+" "+z.get(0).size()+" "+x.size()+" "+y.size());
        for(int i =0; i< t.size(); i++){
            System.out.println("create charts");
            AWTChart awtchart = getDemoChart(factory,t.get(i), "offscreen");
            factory.resetSize(awtchart, 620, 590);
            chart.add(awtchart);
        }

        return chart;
    }

    private AWTChart getDemoChart(JavaFXChartFactory factory,double current_t,  String toolkit) {
        // -------------------------------
        // Define a function to plot
        Mapper mapper = new Mapper() {
            @Override
            public double f(double xx, double yy) {
                yy=Math.round(yy*100000000)/100000000;
                xx=Math.round(xx*100000000)/100000000;
                double value =z.get(t.indexOf(current_t)).get(y.indexOf(yy)).get(x.indexOf(xx));
                //System.out.println(value);
                return value;
            }
        };

        // Define range and precision for the function to plot
        Range range = new Range(x.get(0).floatValue(), x.get(x.size()-1).floatValue());
        int steps = x.size();

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

        return chart;
    }
}