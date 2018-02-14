import java.awt.image.*;
import java.io.*;
import java.util.*;

import javax.imageio.*;

public class MyClient {

    public static void main(String[] args) throws IOException {
      DatagramPacket reqServer = new DatagramPacket(new byte[1024],1024); //the initial packet to connect to server
        //*add* put a connect block to the server_address here
        //*add* put a receive packet to our socket here for server_confirmation on connection

        //once connected to server then worry about the image attachment

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

        System.out.println("Converted Image Data");

        // Convert image file into data
        BufferedImage bufferedImage = ImageIO.read(file);
        WritableRaster rasterImage = bufferedImage.getRaster();
        DataBufferByte imageData = (DataBufferByte) rasterImage.getDataBuffer();
        byte[] data = imageData.getData();
        //*add*divide data into multipul parts, so divide the data into 12 slices
        //while < 11 try
        //*add*create a new Datagrampacket to send to server and add 'data_Slices' to DatagramPacket

        //once all data_Slices are sent, disconnect from server 

    }

}
