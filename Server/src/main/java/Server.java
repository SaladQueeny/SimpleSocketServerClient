import general.Generalities;

import java.io.*;
import java.net.ServerSocket;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    public static void main(String[] args)  {
        try(ServerSocket server = new ServerSocket(8080)){
            System.out.println("Server created");
            while(true){
                //вариант с многопоточностью
                Generalities generalities = new Generalities(server);
                System.out.println("generalities");
                new Thread(()->{
                    System.out.println("new thread");
                    String request = generalities.readLine();
                    System.out.println("Request: "+request);
                    String response = "Hello from server, "+request;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    generalities.writeLine(response);
                    try {
                        generalities.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();

            }
        }catch (IOException e) {
            throw  new RuntimeException(e);//непроверяемое исключение
        }

    }
}