import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;

import javax.imageio.*;

public class MyClient {
    private final static int CLIENT_PORT = 0;
    private final static int SERVER_PORT = 13;
    private final static String HOSTNAME = "localhost";

    public static void main(String[] args) {
        // Try to create a new ServerSocket through SERVER_PORT
        try(DatagramSocket socket = new DatagramSocket(CLIENT_PORT)) {
            // Allow user to input an image file for program to find and use
            // File MUST be inside the working project area
            Scanner userInput = new Scanner(System.in);
            String fileName = "notRealFile.name";
            File file = new File(System.getProperty("user.dir") +
                "/" + fileName);

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

            // Convert image file into data
            BufferedImage bufferedImg = ImageIO.read(file);
            ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
            ImageIO.write(bufferedImg, "jpg", byteArrayOut);
            byteArrayOut.flush();
            byte[] data = byteArrayOut.toByteArray();

            // Work with Server
            socket.setSoTimeout(10000);
            InetAddress host = InetAddress.getByName(HOSTNAME);
            DatagramPacket request = new DatagramPacket(data, data.length,
                host, SERVER_PORT);

            // Log datagrams being sent to console

            /*
             *
             *
             *
             * getOffet() Not actually working. Only getting one instance.
             * Assignment requires START BYTE OFFSET and END BYTE OFFSET
             *
             *
             *
             */

            for(int i=0; i < data.length; i++) {
                System.out.println("[" + data[i] + "]-[" +
                    request.getOffset() + "]-[" + request.getOffset() + "]");
            }

            socket.send(request);

            // Retrieve Server reply
            byte[] data2 = new byte[1024];
            DatagramPacket response = new DatagramPacket(data2, data2.length,
                host, SERVER_PORT);
            socket.receive(response);

            // Output server response
            System.out.println(new String(response.getData(), StandardCharsets.UTF_8));

        } catch ( IOException e ) {
            e.printStackTrace();
        }

    }

}
