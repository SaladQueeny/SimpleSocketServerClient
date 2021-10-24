package ru.kolpakovkuleshov.helpfulClasses;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;

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
    public static boolean checksize;
    public static StringBuilder classText;
    public static List<List<List<Double>>> z;
    public static List<Double> t;
    public static List<Double> x;
    public static List<Double> y;

    public static void getClassText(StringBuilder class_text){

        classText =class_text;
        Logs.writeLog(ProcessData.class, new Throwable().getStackTrace()[0].getMethodName(),
                "Get class text", Level.INFO, true);
    }

    public static void getStartData(double xstart, double xend, double xchange, double ystart, double yend, double ychange, double tstart, double tend, double tchange) {
        File f = new File("C:\\Users\\kolpa\\IdeaProjects\\test1.java");
        Scanner scaner = null;
        try {
            scaner = new Scanner(f);
            StringBuilder classstr=new StringBuilder();
            classstr.append('"');
            while(scaner.hasNextLine()){
                String line = scaner.nextLine();
                classstr.append(line);
            }
            classstr.append('"');
            getClassText(classstr);
            System.out.println(classstr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        x_start = xstart;
        x_end = xend;
        x_change = xchange;
        y_start = ystart;
        y_end = yend;
        y_change = ychange;
        t_start = tstart;
        t_end = tend;
        t_change = tchange;
        Logs.writeLog(ProcessData.class, new Throwable().getStackTrace()[0].getMethodName(),
                "Get start data", Level.INFO, true);
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
        obj.put("classText",classText);
        Logs.writeLog(ProcessData.class, new Throwable().getStackTrace()[0].getMethodName(),
                "Create request to server", Level.INFO, true);
        System.out.println(obj.toJSONString());
        return obj.toJSONString();
    }

    public static void getDataFromJson(String jsonString) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(jsonString);
            z = (List<List<List<Double>>>) obj.get("z");
            //System.out.println("z=" + z);
            t = (List<Double>) obj.get("t");
            //System.out.println("t=" + t);
            x = (List<Double>) obj.get("x");
            //System.out.println("x=" + x);
            y = (List<Double>) obj.get("y");
            //System.out.println("y=" + y);
            checksize = (boolean) obj.get("checkSize");
            Logs.writeLog(ProcessData.class, new Throwable().getStackTrace()[0].getMethodName(),
                    "set start data from json", Level.INFO, true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
