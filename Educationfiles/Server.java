import general.Generalities;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.net.ServerSocket;
import java.util.Scanner;

public class Server {
    public static void main(String[] args)  {
        try(ServerSocket server = new ServerSocket(8080)){
            System.out.println("Server created");
            while(true){

                //вариант с многопоточностью
                Generalities generalities = new Generalities(server);
                new Thread(()->{
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


                //новый вариант с классом Generalities
//                try(Generalities generalities = new Generalities(server)){
//                    String request = generalities.readLine();
//                    System.out.println("Request: "+request);
//                    String response = "Hello from server, "+request;
//                    Thread.sleep(2000);
//                    generalities.writeLine(response);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }


                //старый вариант без класса Generalities
//                try(Socket socket = server.accept();//принимает подключение
//                    //System.out.println("Client connected");
//                    OutputStream stream = socket.getOutputStream();
//                    OutputStreamWriter streamWriter = new OutputStreamWriter(stream);
//                    BufferedWriter writer = new BufferedWriter(streamWriter);
//                    BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                ) {
//                    String request = reader.readLine();
//                    System.out.println(request);
//                    writer.write("Request:" + request);
//                    writer.write("Hello from server, " + request.length());
//                    writer.newLine();
//                    writer.flush();
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }

            }
        }catch (IOException e) {
            throw  new RuntimeException(e);//непроверяемое исключение
        }

    }
}
