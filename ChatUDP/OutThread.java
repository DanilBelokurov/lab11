package ChatUDP;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class OutThread extends  Thread{
    private DatagramSocket socket;
    private int port;
    private InetAddress ip;
    private Scanner scanner = new Scanner(System.in);

    public OutThread(InetAddress ipAddress, int port, DatagramSocket socket){
        this.socket = socket;
        this.ip = ipAddress;
        this.port = port;
    }

    @Override
    public void run(){
        try{
            String name = "You";
            while (true) {
                System.out.println(name+": ");

                byte[] sendData = new byte[1024];
                String message = scanner.nextLine();

                if (message.equals("@name")) {
                    System.out.println("Type your new name: ");
                    name = scanner.nextLine();
                    DatagramPacket sendPacket = new DatagramPacket("@name".getBytes(),"@name".getBytes().length, ip, port);
                    socket.send(sendPacket);
                    sendPacket = new DatagramPacket(name.getBytes(),name.getBytes().length, ip, port);
                    socket.send(sendPacket);
                    continue;
                }else if (message.equals("@quit")) {
                    String quitMessage = "You typed QUIT, our dialog is over! :(";
                    sendData = quitMessage.getBytes();
                    DatagramPacket quitPacket = new DatagramPacket(sendData, sendData.length, ip, port);
                    socket.send(quitPacket);
                    System.exit(0);
                    socket.close();
                }

                sendData = message.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);

                socket.send(sendPacket);
            }
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        finally{
            scanner.close();
        }
    }
}
