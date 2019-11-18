package ChatUDP;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    
    public static void main(String args[]) throws Exception {
        try {
            DatagramSocket clientSocket = new DatagramSocket();

            Scanner scan = new Scanner(System.in);

            System.out.println("Address: ");
            String address = scan.nextLine();
            InetAddress ip = InetAddress.getByName(address);

            System.out.println("Port: ");
            int port = Integer.parseInt(scan.nextLine());

            byte[] sendData = new byte[1024];
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, port);
            clientSocket.send(sendPacket);

            InThread in = new InThread(clientSocket);
            OutThread out = new OutThread(ip, port, clientSocket);
            in.start();
            out.start();
            out.join();
        }

        catch (RuntimeException | InterruptedException ex){}
    }

}