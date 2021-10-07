
import java.io.*;

public class Client {
    public void goTo(){
//        public static void main(String[] args) {
            try(GeneralThings generalThings = new GeneralThings("127.0.0.1", 8080)){
                System.out.println("Connected to server");
                generalThings.writeLine(ProcessData.createRequest());
                String response = generalThings.readLine();
                System.out.println("Response: "+response);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            try(Generalities generalities = new Generalities("127.0.0.1", 8080)){
//                System.out.println("Connected to server");
//                generalities.writeLine(ProcessData.createRequest());
//                String response = generalities.readLine();
//                System.out.println("Response: "+response);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//        }
    }

}
