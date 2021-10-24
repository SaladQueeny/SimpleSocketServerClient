import general.Logs;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class DataOperations {
    private List<Double> y = new ArrayList<>();
    private List<Double> x = new ArrayList<>();
    private List<Double> t = new ArrayList<>();
    private String classText;
    private boolean checksize = true;

    public List<List<List<Double>>> ComputeFunction() {
        List<List<List<Double>>> result=null;
        try {
            Class my = null;
            my = test.getClassObject(classText);
            Method m = my.getMethod("test", new Class[] { List.class, List.class,List.class });
            Object o = my.newInstance();
            result= (List<List<List<Double>>>) m.invoke(o, new Object[] { x, y, t });
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public void getAndSetData(String JSONdata) {
        System.out.println("start get and set data");
        try {
            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(JSONdata);
            double x_start = (double) obj.get("x_start"),
                    x_end = (double) obj.get("x_end"),
                    x_change = (double) obj.get("x_change"),
                    y_start = (double) obj.get("y_start"),
                    y_end = (double) obj.get("y_end"),
                    y_change = (double) obj.get("y_change"),
                    t_start = (double) obj.get("t_start"),
                    t_end = (double) obj.get("t_end"),
                    t_change = (double) obj.get("t_change");
            classText = (String) obj.get("classText");
            System.out.println(classText);
            for (double xyt = x_start; xyt <= x_end; xyt += x_change) {
                x.add(Math.round(xyt * 10000) / 10000.0);
            }
            for (double xyt = y_start; xyt <= y_end; xyt += y_change) {
                y.add(Math.round(xyt * 10000) / 10000.0);
            }
            for (double xyt = t_start; xyt <= t_end; xyt += t_change) {
                t.add(Math.round(xyt * 10000) / 10000.0);
            }
            if (x.size() != y.size()) {
                checksize=false;
            }
            Logs.writeLog(this.getClass(), new Throwable().getStackTrace()[0].getMethodName(),
                    "Compute all lists (x,y,t)", Level.INFO, true);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public String workWithData(String data)  {
        getAndSetData(data);
        List<List<List<Double>>> z = ComputeFunction();
        JSONArray resultMain = new JSONArray();
        for (int i = 0; i < z.size(); i++) {
            JSONArray result = new JSONArray();
            for (int j = 0; j < z.get(i).size(); j++) {
                result.add(z.get(i).get(j));
            }
            resultMain.add(result);
        }
        JSONObject object = new JSONObject();
        object.put("z", resultMain);

        resultMain = new JSONArray();
        for (int i = 0; i < t.size(); i++) {
            resultMain.add(t.get(i));
        }
        object.put("t", resultMain);

        resultMain = new JSONArray();
        for (int i = 0; i < x.size(); i++) {
            resultMain.add(x.get(i));
        }
        object.put("x", resultMain);

        resultMain = new JSONArray();
        for (int i = 0; i < y.size(); i++) {
            resultMain.add(y.get(i));
        }
        object.put("y", resultMain);
        object.put("checkSize", checksize);
        Logs.writeLog(this.getClass(), new Throwable().getStackTrace()[0].getMethodName(),
                "Create request to client with lists of x, y, t, z", Level.INFO, true);
        return object.toJSONString();
    }
}
