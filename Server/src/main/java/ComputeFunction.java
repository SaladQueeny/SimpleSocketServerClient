import java.util.*;

public class ComputeFunction {
    public static List<List<List<Double>>> calculate(List<Double> x, List<Double> y, List<Double> t){
        List<List<List<Double>>> z = new ArrayList<>();
        int count=t.size(), count1=x.size();
        for(int k =0; k<count;k++){
            List<List<Double>> zx = new ArrayList<>();
            for(int i=0; i<x.size();i++){
                List<Double> zy = new ArrayList<>();
                for(int j=0; j<y.size();j++){
                    //sin(xx+yy)/(xx+yy)
                    double value = (Math.sin(x.get(i)*x.get(i)+y.get(j)*y.get(j))/(0.1+y.get(j)*y.get(j)+x.get(i)*x.get(i)));
                    //double value = (Math.sin(x.get(i))*Math.cos(y.get(j)));
                    zy.add(value);
                }
                zx.add(zy);
            }
            z.add(zx);
        }
        return z;
    }
}
