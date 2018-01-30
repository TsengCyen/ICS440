import java.io.*;
import java.net.*;
import java.util.logging.*;

public class MyServer {
    private final static int PORT = 13;
    private final static Logger audit = Logger.getLogger("requests");
    private final static Logger errors = Logger.getLogger("errors");

    public static void main(String[] args) {
        // Try to create a new ServerSocket
        try(DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("Datagram Socket Created");
            while(true) {
                try {
                    DatagramPacket request = new DatagramPacket(new byte[1024],
                        1024);

                    // PROGRAM STOPS HERE
                    socket.receive(request);

                    // DEFINE DATA
                    // DoThings

                    // Send imageData as Packets
                    DatagramPacket response = new DatagramPacket(data,
                        data.length, request.getAddress(), request.getPort());
                    socket.send(response);

                    System.out.println("Data packets sent");

                    audit.info(data + " sent to " + request.getAddress());
                } catch (IOException e) {
                    errors.log(Level.SEVERE, e.getMessage(), e);
                    System.out.println("Bad things 2");
                }
            }
        } catch (IOException e) {
            errors.log(Level.SEVERE, e.getMessage(), e);
            System.out.println("Bad things 1");
        }
    }
}
