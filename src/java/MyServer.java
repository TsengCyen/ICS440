import java.io.*;
import java.net.*;

public class MyServer {
    public final static int PORT = 13;

    public static void main(String[] args) {
        // Try to create a new ServerSocket
        try(ServerSocket server = new ServerSocket(PORT)) {
            // Continuously listen to port 13 until connection is made
            while(true) {

                System.out.println("[WORKING] - NoPort");

                try(Socket connection = server.accept()) {
                    // DO WORK
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
