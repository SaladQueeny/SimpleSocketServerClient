import general.Generalities;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args) {
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
