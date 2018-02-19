import java.io.*;
import java.net.*;
import java.util.logging.*;

public class MyServer {
    private final static int PORT = 13;
    private final static Logger errors = Logger.getLogger("errors");

    public static void main(String[] args) {
        // Try to create a new ServerSocket through PORT
        try(DatagramSocket socket = new DatagramSocket(PORT)) {
            while(true) {
                try {
                    // Catch a sent packet
                    DatagramPacket request = new DatagramPacket(new byte[4096],
                        4096);
                    socket.receive(request);

                    // Prepare output file for reconstruction
                    File fileOutput = new File(System.getProperty("user.dir") +
                        "fileOutput.png");
                    byte[] data = request.getData();

                    // Convert data to image and output to prepared output file
                    ByteArrayInputStream imageByte = new ByteArrayInputStream(data);
                    InputStream inStream = imageByte;
                    OutputStream outStream = new FileOutputStream("outputfile.png");

                    // Do response
                    String responseInfo = "- Image File Processed -";
                    DatagramPacket response = new DatagramPacket(
                        responseInfo.getBytes(), responseInfo.length(),
                        request.getAddress(), request.getPort());
                    socket.send(response);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
