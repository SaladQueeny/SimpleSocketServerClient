import java.util.ArrayList;
import java.util.List;

public class dataOperations {
    public static List<Double> y = new ArrayList<>();
    public static List<Double> x = new ArrayList<>();
    public static List<Double> t = new ArrayList<>();;
    public static void getData(String data) {
        double x_start=0, x_end=0, x_change=0, y_start=0, y_end=0, y_change=0, t_start=0, t_end=0, t_change=0;
        //List<Double> x = new ArrayList<>();
        double xyt = x_start;
        for(int i=0;i<Math.abs(x_start-x_end)/x_change; i++){
            x.add(xyt);
            xyt+=x_change;
        }

        //List<Double> y = new ArrayList<>();
        xyt=y_start;
        for(int i=0;i< Math.abs(y_start-y_end)/y_change; i++){
            y.add(xyt);
            xyt+=y_change;
        }

        //List<Double> t = new ArrayList<>();
        xyt = t_start;
        for(int i=0;i<Math.abs(t_start-t_end)/t_change; i++){
            y.add(xyt);
            xyt+=y_change;
        }

        List<List<Double>> z = new ArrayList<>();

        int count=t.size(), count1=x.size();
        for(int k =0; k<count;k++){
            List<Double> z1 = new ArrayList<>();
            for(int i=0; i<count1;i++){
                z1.add(x.get(i)*t.get(k)+y.get(i)*t.get(k));
            }
            z.add(z1);
        }
        System.out.println(z);
    }
    public static String workWithData(String data) {
        getData(data);
        List<List<Double>> z = ComputeFunction.calculate(x, y, t);
        String JSONString = " ";
        return JSONString;
    }
}
