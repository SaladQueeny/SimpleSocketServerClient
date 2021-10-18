import java.util.*;

public class ComputeFunction {
    public static List<List<Double>> calculate(List<Double> x, List<Double> y, List<Double> t){
        List<List<Double>> z = new ArrayList<>();
        int count=t.size(), count1=x.size();
        for(int k =0; k<count;k++){
            List<Double> z1 = new ArrayList<>();
            for(int i=0; i<x.size();i++){
                for(int j=0; j<y.size();j++){
                    z1.add(x.get(i)*t.get(k)+y.get(i)*t.get(k));
                }
            }
            z.add(z1);
        }
        return z;
    }
}
