/*
* UDP Simple Echo Server
* @author Ramsey Ith Njema
* @version 1.0
 */

import java.io.*;
import java.net.*;

public class UDPServer {
    public static void main(String[] args) {
        // Binds to a wildcard address [available on all interfaces] and dynamic port.
        try (DatagramSocket udpServerSocket = new DatagramSocket(0);) {
            System.out.printf("UDP server bound on %s\n",
                    udpServerSocket.getLocalSocketAddress().toString().split("/")[1]);
            byte[] inputBuffer = new byte[8192];
            DatagramPacket inputDatagramBuffer = 
            new DatagramPacket(inputBuffer, inputBuffer.length);
            try {
                boolean serverAlive = true;
                while (serverAlive) {
                    // Receives incoming message
                    udpServerSocket.receive(inputDatagramBuffer);
                    String message = new String(inputDatagramBuffer.getData(), 
                    0, inputDatagramBuffer.getLength(),
                            "UTF-8");
                    SocketAddress clientAddressInfo = inputDatagramBuffer.getSocketAddress();
                    System.out.printf("Received %s from %s\n", message,
                            clientAddressInfo.toString().split("/")[1]);
                    inputDatagramBuffer.setLength(inputBuffer.length);
                    // Echo received message
                    udpServerSocket.send(inputDatagramBuffer);
                }

            } catch (IOException ioex) {
                ioex.printStackTrace();
            }

        } catch (SocketException sockex) {
            sockex.printStackTrace();
        }
    }
}