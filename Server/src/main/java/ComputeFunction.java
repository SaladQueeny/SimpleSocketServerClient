import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ComputeFunction {
    public List<List<List<Double>>> calculate(List<Double> x, List<Double> y, List<Double> t, Method callback) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Object  o = this.getClass().getDeclaredConstructor().newInstance();
        List<List<List<Double>>> z = new ArrayList<>();
        for (int k = 0; k < t.size(); k++) {
            List<List<Double>> zx = new ArrayList<>();
            for (int i = 0; i < x.size(); i++) {
                List<Double> zy = new ArrayList<>();
                for (int j = 0; j < y.size(); j++) {
                    double value = (Math.sin(x.get(i) * x.get(i) + y.get(j) * y.get(j)) / (0.1 + y.get(j) * y.get(j) + x.get(i) * x.get(i)));
                    zy.add(value);
                }
                zx.add(zy);
            }
            z.add(zx);
            if (k % 2 == 1) {
                try {
                    callback.invoke(o, new Object[]{z, x, y, t});
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return z;
    }
}
