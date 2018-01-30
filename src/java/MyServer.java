import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.*;

import javax.imageio.*;

public class MyServer {
    private final static int PORT = 13;
    private final static Logger audit = Logger.getLogger("requests");
    private final static Logger errors = Logger.getLogger("errors");

    public static void main(String[] args) {
        // Allow user to input an image file for program to find and use
        // File MUST be inside the working project area
        Scanner userInput = new Scanner(System.in);
        String fileName = "notRealFile.name";
        File file = new File(System.getProperty("user.dir") + "/" + fileName);

        // Continue to prompt user until a valid file is found
        while(!file.exists()) {
            System.out.print("\nImage Filename (include extension): ");
            fileName = userInput.nextLine();
            file = new File(fileName);

            if(!file.exists()) {
                System.out.println("- File not found -");
            }
        }
        userInput.close();

        // Try to create a new ServerSocket
        try(DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("Datagram Socket Created");
            while(true) {
                try {
                    DatagramPacket request = new DatagramPacket(new byte[1024],
                        1024);

                    // PROGRAM STOPS HERE
                    socket.receive(request);

                    System.out.println("Converted Image Data");
                    // Convert image file into data
                    BufferedImage bufferedImage = ImageIO.read(file);
                    WritableRaster rasterImage = bufferedImage.getRaster();
                    DataBufferByte imageData = (DataBufferByte) rasterImage.getDataBuffer();
                    byte[] data = imageData.getData();

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
