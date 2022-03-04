import general.Generalities;

import java.io.*;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(8000)) {
            System.out.println("Server created");
            while (true) {
                Generalities generalities = new Generalities(server);
                new Thread(() -> {
                    System.out.println("New thread");
//                    try {
//                        Files.walk(Paths.get(""), 1)
//                                .forEach(file -> {
//                                    if (file.toFile().isFile() && file.toFile().getPath().endsWith(".class")) {
//                                        System.out.println(file.getFileName());
//                                        file.toFile().delete();
//                                        System.out.println("deleted");
//                                    }
//                                });
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    String request = generalities.readLine();
                    String response = null;
                    DataOperations dataOperations = new DataOperations(generalities);
                    System.out.println("Request: " + request);
                    if (request.equals("check connection")) {
                        response = dataOperations.checkConnection();
                    } else {
                        response = dataOperations.workWithData(request);
                    }
                    //System.out.println("Response: " + response);

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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
