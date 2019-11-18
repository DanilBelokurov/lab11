package ChatUDP;

import java.net.*;
import java.io.*;

public class InThread extends Thread{
    private DatagramSocket socket;

    public InThread(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run(){
        try{
            String name = "Server";
            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(receivePacket);

                int length;
                for(length = 0; receiveData[length] != 0; length++);
                byte[] correctReceive = new byte[length];
                for(int j = 0; j < length; j++)
                    correctReceive[j] = receiveData[j];

                String sentence = new String(correctReceive);
                    if(sentence.equals("@name")){
                        byte[] receiveName = new byte[1024];
                        DatagramPacket receivePacketName = new DatagramPacket(receiveName, receiveName.length);
                        socket.receive(receivePacketName);

                        int check;
                        for(check = 0; receiveName[check] != 0; check++);
                        byte[] correctNewName = new byte[check];
                        for(int j = 0; j < check; j++)
                            correctNewName[j] = receiveName[j];
                        String newName = new String(correctNewName);
                        name = newName;
                    }
                    else{
                        System.out.println(name + ": " + sentence);
                    }
            }
        }
        catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

}