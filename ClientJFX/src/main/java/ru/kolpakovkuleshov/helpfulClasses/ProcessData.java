package ru.kolpakovkuleshov.helpfulClasses;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;

public class ProcessData {
    public static double x_start;
    public static double x_end;
    public static double x_change;
    public static double y_start;
    public static double y_end;
    public static double y_change;
    public static double t_start;
    public static double t_end;
    public static double t_change;
    public static List<List<List<Double>>> z;
    public static List<Double> t;
    public static List<Double> x;
    public static List<Double> y;

    public static void getStartData(double xstart, double xend, double xchange, double ystart, double yend, double ychange, double tstart, double tend, double tchange) {
        x_start = xstart;
        x_end = xend;
        x_change = xchange;
        y_start = ystart;
        y_end = yend;
        y_change = ychange;
        t_start = tstart;
        t_end = tend;
        t_change = tchange;
    }

    public static String createRequest() {
        JSONObject obj = new JSONObject();
        obj.put("x_start", x_start);
        obj.put("x_end", x_end);
        obj.put("x_change", x_change);
        obj.put("y_start", y_start);
        obj.put("y_end", y_end);
        obj.put("y_change", y_change);
        obj.put("t_start", t_start);
        obj.put("t_end", t_end);
        obj.put("t_change", t_change);
        return obj.toJSONString();
    }

    public static void getDataFromJson(String jsonString) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonString);
            z = (List<List<List<Double>>>) obj.get("z");
            System.out.println("z=" + z);
            t = (List<Double>) obj.get("t");
            System.out.println("t=" + t);
            x = (List<Double>) obj.get("x");
            System.out.println("x=" + x);
            y = (List<Double>) obj.get("y");
            System.out.println("y=" + y);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
