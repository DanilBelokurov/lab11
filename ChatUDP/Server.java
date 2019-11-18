package ChatUDP;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Server {
    public static void main(String args[]) throws Exception {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the port: ");
        String serverPort = scanner.nextLine();

        DatagramSocket socket = new DatagramSocket(Integer.parseInt(serverPort));
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);

        String entering = "Server is running!";
        InetAddress ip = receivePacket.getAddress();
        int port = receivePacket.getPort();
        DatagramPacket sendPacket = new DatagramPacket(entering.getBytes(),entering.getBytes().length, ip, port);
        socket.send(sendPacket);
        InThread in = new InThread(socket);
        OutThread out = new OutThread(ip,port, new DatagramSocket());
        in.start();
        out.start();
    }
}