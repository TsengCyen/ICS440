import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.*;

import javax.imageio.*;

public class MyClient {
    private final static int CLIENT_PORT = 0;
    private final static int SERVER_PORT = 13;
    private final static String HOSTNAME = "localhost";

    public static void main(String[] args) {
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
            byte[] dataNew = byteArrayOut.toByteArray();

            /* OLD METHOD
            BufferedImage bufferedImage = ImageIO.read(file);
            WritableRaster rasterImage = bufferedImage.getRaster();
            DataBufferByte imageData =
                (DataBufferByte) rasterImage.getDataBuffer();

            byte[] data = imageData.getData();
            */

            // Work with Server
            socket.setSoTimeout(10000);
            InetAddress host = InetAddress.getByName(HOSTNAME);
            DatagramPacket request = new DatagramPacket(dataNew, dataNew.length,
                host, SERVER_PORT);
            socket.send(request);

            byte[] data2 = new byte[1024];
            DatagramPacket response = new DatagramPacket(data2, data2.length,
                host, SERVER_PORT);
            socket.receive(response);

            System.out.println(response.getData());

        } catch ( IOException e ) {
            e.printStackTrace();
        }

    }

}
