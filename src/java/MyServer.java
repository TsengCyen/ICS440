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
            // Continuously listen to port 13 until connection is made
            while(true) {
                System.out.println("Listening...");
                try {
                    DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
                    socket.receive(request);

                    // DoWork

                    // -------------------------------------------------------
                    // - Remove comments after DoWork is done                -
                    // - Change <DATA> and <DATA_INFO> to appropriate values -
                    // -------------------------------------------------------
                    //DatagramPacket response - new DatagramPacket(<DATA>, <DATA>.length,
                    //    resquest.getAddress(), request.getPort());
                    //socket.send(response);
                    //
                    //audit.info(<DATA_INFO> + " sent to " + request.getAddress());
                } catch (IOException e) {
                    errors.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            errors.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
