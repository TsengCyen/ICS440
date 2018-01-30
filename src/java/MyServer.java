import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

public class MyServer {
    private final static int PORT = 13;
    private final static Logger audit = Logger.getLogger("requests");
    private final static Logger errors = Logger.getLogger("errors");

    public static void main(String[] args) {
        // Allow user to input a file for program to find and use
        // File MUST be inside the working project area
        Scanner userInput = new Scanner(System.in);
        String fileName = "notRealFile.name";
        File file = new File(System.getProperty("user.dir") + "/" + fileName);

        // Continue to prompt user until a valid file is found
        while(!file.exists()) {
            System.out.print("\nFilename (include extension): ");
            fileName = userInput.nextLine();
            file = new File(fileName);

            if(!file.exists()) {
                System.out.println("- File not found -");
            }
        }
        userInput.close();

        // Try to create a new ServerSocket
        try(DatagramSocket socket = new DatagramSocket(PORT)) {
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
                    audit.info("<DATA_INFO>" + " sent to " + request.getAddress());
                } catch (IOException e) {
                    errors.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            errors.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
