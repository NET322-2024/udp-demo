/*
* UDP Simple Echo Client
* @author Ramsey Ith Njema
* @version 1.0
 */

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

public class UDPClient {
    public static void main(String[] args) {
        try (Scanner consoleScanner = new Scanner(System.in)) {
            System.out.printf("Specify server port : ");
            int serverPort = consoleScanner.nextInt();
            try (DatagramSocket udpClientSocket = new DatagramSocket();) {
                udpClientSocket.setSoTimeout(10000);
                byte[] inputBuffer = new byte[1024];
                DatagramPacket inputDatagramBuffer = new DatagramPacket(inputBuffer, inputBuffer.length);
                DatagramPacket outDatagramBuffer = new DatagramPacket(inputBuffer, inputBuffer.length);
                try {
                    System.out.print("\tMessage : ");
                    // String sentMessage = consoleScanner.next(stringLinePattern);
                    Scanner messagScanner = new Scanner(System.in);
                    String sentMessage = messagScanner.nextLine();
                    outDatagramBuffer.setData(sentMessage.getBytes("UTF-8"));
                    messagScanner.close();
                    InetSocketAddress serverSocketAddress = new InetSocketAddress("localhost", serverPort);
                    
                    // udpClientSocket.connect(serverSocketAddress);
                    outDatagramBuffer.setSocketAddress(serverSocketAddress);
                    System.out.printf("\tTo : %s\n\tFrom : %s\n",
                            udpClientSocket.getRemoteSocketAddress().toString().split("/")[1],
                            udpClientSocket.getLocalSocketAddress().toString().split("/")[1]);
                    udpClientSocket.send(outDatagramBuffer);
                    
                    // Receive echo message
                    udpClientSocket.receive(inputDatagramBuffer);
                    String receivedMessage = new String(inputDatagramBuffer.getData(), "UTF-8");
                    System.out.printf("Received %s from %s:%s", receivedMessage,
                            serverSocketAddress.getAddress().toString().split("/")[1], serverSocketAddress.getPort());

                } catch (IOException ioEx) {
                    ioEx.printStackTrace();
                } catch (IllegalArgumentException illArgEx) {
                    illArgEx.printStackTrace();
                } catch (InputMismatchException iMismatchException) {
                    System.err.println("Input does not match expected pattern. FIX IT");
                }

            } catch (SocketException sockex) {
                sockex.printStackTrace();

            }
        }
    }
}