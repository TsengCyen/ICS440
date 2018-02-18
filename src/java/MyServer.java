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
            while(true) {
                try {
                    DatagramPacket request = new DatagramPacket(new byte[4096],
                        4096);
                    socket.receive(request);

                    File fileOutput = new File(System.getProperty("user.dir") +
                        "fileOutput.png");
                    byte[] data = request.getData();


                    ByteArrayInputStream imageByte = new ByteArrayInputStream(data);
                    InputStream inStream = imageByte;
                    OutputStream outStream = new FileOutputStream("outputfile.png");

                    byte[] test = new byte[1024];
                    int var;
                    while((var = inStream.read(test)) > 0) {
                        outStream.write(test, 0, var);
                    }
                    /*
                    DataBuffer buffer = new DataBufferByte(data, data.length);
                    WritableRaster raster =
                        Raster.createInterleavedRaster(buffer, 100, 100, 2, 1,
                        new int[]{0, 1, 2}, (Point) null);

                    ColorModel cm = new ComponentColorModel(
                        ColorModel.getRGBdefault().getColorSpace(),
                        false, true, Transparency.OPAQUE, DataBuffer.TYPE_BYTE);

                    BufferedImage image =
                        new BufferedImage(cm, raster, true, null);

                    ImageIO.write(imageByte, "png", fileOutput); */

                    String responseText = "- Image File Processed -";
                    DatagramPacket response = new DatagramPacket(
                        responseText.getBytes(), responseText.length(),
                        request.getAddress(), request.getPort());
                    socket.send(request);
                    audit.info(data + " sent to " + request.getAddress());
                } catch (IOException e) {
                    errors.log(Level.SEVERE, e.getMessage(), e);
                }
            }
        } catch (IOException e) {
            errors.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}
