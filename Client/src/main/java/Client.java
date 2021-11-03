import general.Generalities;

import java.io.*;

public class Client {

        public static void main(String[] args) {
            try(Generalities generalities = new Generalities("127.0.0.1", 8000)){
                System.out.println("Connected to server");
                generalities.writeLine(ProcessData.createRequest());

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
                        String response;

                        response = generalities.readLine();
                        while(response!=null){
                           // if(!response.equals(prev_response)){
                                System.out.println("Response: "+response);
                                System.out.println("get z from server");
                                ProcessData.size_z+=response.length();
                            //}
                            response = generalities.readLine();
                        }
//                    }
//                }).start();



            } catch (IOException e) {
                e.printStackTrace();
            }

//        }
    }

}
