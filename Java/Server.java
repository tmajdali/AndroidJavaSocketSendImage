import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
public class ImageRec {

	public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(3003);
        Socket socket = serverSocket.accept();
        InputStream in = socket.getInputStream();

        

		BufferedOutputStream out = new BufferedOutputStream( new FileOutputStream( System.getProperty("user.dir")+"/n02.jpg" ) );
		byte[] buffer = new byte[ 4096 ];
		int cl = 0;
		
	         int bytesRead;
	          while ( (bytesRead = in.read( buffer )) !=  -1 ) {
	        		out.write( buffer, 0, bytesRead );
	            }

        serverSocket.close();
    }

}
