package ru.kolpakovkuleshov.helpfulClasses;

import org.json.simple.JSONObject;

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
    public static void getStartData(){
        x_start=0.1;
        x_end=0.5;
        x_change=0.1;
        y_start=0.1;
        y_end=0.5;
        y_change=0.1;
        t_start=1;
        t_end=2;
        t_change=1;
    }
    public static String createRequest(){
        getStartData();
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

}