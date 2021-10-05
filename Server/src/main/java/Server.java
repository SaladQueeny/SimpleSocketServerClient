import general.Generalities;

import java.io.*;
import java.net.ServerSocket;

import java.util.List;

public class Server {

    public static void main(String[] args)  {
        try(ServerSocket server = new ServerSocket(8080)){
            System.out.println("Server created");
            while(true){
                //вариант с многопоточностью
                Generalities generalities = new Generalities(server);
                new Thread(()->{
                    System.out.println("New thread");
                    String request = generalities.readLine();
                    System.out.println("Request: "+request);

                    //String response = dataOperations.workWithData(request);
                    String response = "Hello from server, "+request;
                    //вернуть массив z и массив t
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