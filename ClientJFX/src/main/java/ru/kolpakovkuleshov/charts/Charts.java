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
import ru.kolpakovkuleshov.helpfulClasses.Logs;
import ru.kolpakovkuleshov.helpfulClasses.ProcessData;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class Charts {

    private List<Double> x;
    private List<Double> y;
    private List<Double> t;
    private List<List<List<Double>>> z;

    public List<AWTChart> getAWTCharts(JavaFXChartFactory factory, List<Double> x, List<Double> y, List<Double> t, List<List<List<Double>>> z) {

        this.x = x;
        this.y = y;
        this.t = t;
        this.z = z;

        List<AWTChart> chart = new ArrayList<>();
        System.out.println(t.size() + " " + z.size() + " " + z.get(0).size() + " " + x.size() + " " + y.size());
        for (int i = 0; i < t.size(); i++) {
            System.out.println("create charts");
            AWTChart awtchart = getDemoChart(factory, t.get(i), "offscreen");
            factory.resetSize(awtchart, 620, 590);
            chart.add(awtchart);
        }
        Logs.writeLog(this.getClass(), new Throwable().getStackTrace()[0].getMethodName(),
                "Create charts list", Level.INFO, true);
        return chart;
    }

    private AWTChart getDemoChart(JavaFXChartFactory factory, double current_t, String toolkit) {

        Mapper mapper = new Mapper() {
            @Override
            public double f(double xx, double yy) {
                yy = Math.round(yy * 1000) / 1000.0;
                xx = Math.round(xx * 1000) / 1000.0;
                double value = z.get(t.indexOf(current_t)).get(x.indexOf(xx)).get(y.indexOf(yy));
                return value;
            }
        };


        Range range ;//= new Range(x.get(0).floatValue(), x.get(x.size() - 1).floatValue());
        int steps ;
        if (x.size()<y.size()){
            steps = x.size();
            range = new Range(x.get(0).floatValue(), x.get(x.size() - 1).floatValue());
        }else{
            steps = y.size();
            range = new Range(y.get(0).floatValue(), y.get(y.size() - 1).floatValue());
        }

        final Shape surface = Builder.buildOrthonormal(mapper, range, steps);
        surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
        surface.setFaceDisplayed(true);
        surface.setWireframeDisplayed(false);

        Quality quality = Quality.Advanced;

        AWTChart chart = (AWTChart) factory.newChart(quality, toolkit);
        chart.getScene().getGraph().add(surface);
        Logs.writeLog(this.getClass(), new Throwable().getStackTrace()[0].getMethodName(),
                "Create chart", Level.INFO, true);
        return chart;
    }
}