import general.Generalities;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        // x/шаг_x = y/шаг_y
//        List<Double> y = new ArrayList<>();
//        y.add(1.0);
//        y.add(2.0);
//        y.add(3.0);
//        y.add(4.0);
//        y.add(5.0);
//        y.add(6.0);
//        List<Double> x = new ArrayList<>();
//        x.add(1.0);
//        x.add(2.0);
//        x.add(3.0);
//        x.add(4.0);
//        x.add(5.0);
//        x.add(6.0);
//        List<Double> t = new ArrayList<>();
//        t.add(1.0);
//        t.add(2.0);
//        t.add(3.0);
//        t.add(4.0);
//        t.add(5.0);
//        t.add(6.0);
//        List<List<Double>> z = new ArrayList<>();
//        int count=t.size(), count1=x.size();
//        for(int k =0; k<count;k++){
//            List<Double> z1 = new ArrayList<>();
//            for(int i=0; i<count1;i++){
//                z1.add(x.get(i)*t.get(k)+y.get(i)*t.get(k));
//            }
//            z.add(z1);
//        }
//        System.out.println(z);
        try(Generalities generalities = new Generalities("127.0.0.1", 8080)){
            System.out.println("connected");
            String request = "Alena lox";
            System.out.println(request);
            generalities.writeLine(request);
            String response = generalities.readLine();
            System.out.println("response: "+response);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
