import general.Generalities;
import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try(Generalities generalities = new Generalities("127.0.0.1", 8080)){
            System.out.println("connected");
            String request = "Alena lox";
            generalities.writeLine(request);
            String response = generalities.readLine();
            System.out.println("response: "+response);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        try(Socket socket = new Socket("127.0.0.1", 8080);
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            BufferedReader reader = new BufferedReader(new InputStreamReader((socket.getInputStream())));

//        ) {
//            System.out.println("connected");
//            String request = "Alena lox";
//            writer.write(request);
//            writer.newLine();
//            writer.flush();
//            String response = reader.readLine();
//            System.out.println("response: "+response);
//        } catch (IOException e) {
//           e.printStackTrace();
//        }
    }
}
