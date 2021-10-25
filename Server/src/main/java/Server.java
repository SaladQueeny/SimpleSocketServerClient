import general.Generalities;

import java.io.*;
import java.net.ServerSocket;

public class Server {
    //Egor
    public static void main(String[] args)  {
        try(ServerSocket server = new ServerSocket(8000)){
            System.out.println("Server created");
            while(true){
                Generalities generalities = new Generalities(server);
                new Thread(()->{
                    System.out.println("New thread");
                    String request = generalities.readLine();
                    System.out.println("Request: "+request);
                    DataOperations dataOperations = new DataOperations();
                    String response = dataOperations.workWithData(request);
                    System.out.println("Response: "+response);
                    try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
                    generalities.writeLine(response);
                    try { generalities.close(); } catch (IOException e) { e.printStackTrace(); }
                }).start();
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}