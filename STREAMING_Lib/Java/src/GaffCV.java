import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import edu.wpi.first.wpilibj.networktables.NetworkTable;

/*A goof plus a laugh is a gaff*/
public class GaffCV {
	
	private static boolean runGaffCV = true, streamGaffCV = true;
	private static Mat output, input;
	private static long delay = 0, start = 0, frameCount = 0;
	private static Scalar[] scalars;
	
        public static void main(String[] args){
        		loadDriver();
        		scalars = IO.getScalars();
        		VideoCapture c = new VideoCapture(0);
        		output = new Mat();
        		input = new Mat();
        		
				NetworkTable.setClientMode();
        		//Set this ip to RoboRIO-670-FRC.local for the robot
        		NetworkTable.setIPAddress("127.0.0.1");
        		NetworkTable visionTable = NetworkTable.getTable("GaffCV");      
        		
        		//Image processing & data sending loop
        		new Thread("Process & Send Data") 
        		{
        		      public void run()
        		      {
        		    	  String data = "";
        		    	  while(true)
        					{
        		    		  	if(runGaffCV)
        		    		  	{
        		  					start = System.currentTimeMillis();
        		  					
        		    		  		c.read(input);
        		    		  		
        		    		  		try {
	        		    		  		frameCount++;
	        		    		  		
	        		    		  		/*Process the mat input image and create output*/
	        		    		  		output = input.clone();
	        		    		  		
	        		    		  		data = ImageProcessor.process(output, scalars);
	        		    		  		
	        		    		  		System.out.println(data);
	        		    		  		if(visionTable.isConnected())
	            		        		{
	            		        			visionTable.putString("data", data);
	            		        			streamGaffCV = visionTable.getBoolean("stream", false);
	            		        		}
        		    		  		}
        		    		  		catch(Exception e)
        		    		  		{
        		    		  			System.out.println(e.toString());
        		    		  		}
        		    		  	}
        					}
        		      }
        		}.start();
        		
        		//Stream image to a server for viewing on driver station
        		new Thread("Stream Images") 
        		{
					public void run()
					{
						while(true)
						{
							
							if(streamGaffCV)
							{
								ServerSocket server = null;
											
				        		try{
				        			server = new ServerSocket(8080);
								}
				        		catch (IOException e1) {}
				        		
				        		if(server != null)
				        		{
					        		try (Socket socket = server.accept()){
		        		  				OutputStream outputStream = socket.getOutputStream();
		        		  				outputStream.write((
		        		  					      "HTTP/1.0 200 OK\r\n" +
		        		  					      "Server: Homestead"+"\r\n" +
		        		  					      "Connection: close\r\n" +
		        		  					      "Max-Age: 0\r\n" +
		        		  					      "Expires: 0\r\n" +
		        		  					      "Cache-Control: no-cache, private\r\n" + 
		        		  					      "Pragma: no-cache\r\n" + 
		        		  					      "Content-Type: multipart/x-mixed-replace; " +
		        		  					      "boundary=--BoundaryString\r\n\r\n").getBytes());
		        		  				byte[] httpResponse = new byte[] {0};
		        		  				while (true)
		        		  				{
		        		  					httpResponse = extractBytes();
		        		  					outputStream.write((
		        		  				        "--BoundaryString\r\n" +
		        		  				        "Content-type: image/jpg\r\n" +
		        		  				        "Content-Length: " +
		        		  				        httpResponse.length +
		        		  				        "\r\n\r\n").getBytes());
		        		  				    outputStream.write(httpResponse);
		        		  				    outputStream.write("\r\n\r\n".getBytes());
		        		  				    outputStream.flush();
		        		  				    if(frameCount%5 == 0)
		        		  				    	delay = (System.currentTimeMillis() - start);
		        		  				}
					        		}catch(IOException e)
					        		{
			        		    		  continue;
					        		}
				        		}
							}
						}
					}
				}.start();
        }
        
        /*Convert a Mat image to a byte array to write to a server (mjpg server)*/
		public static byte[] extractBytes(){
				if(!output.empty())
				{
					Size s = new Size(320, 210);
					Imgproc.resize(output, output, s);
					MatOfByte bytemat = new MatOfByte();
			    	Imgcodecs.imencode(".jpg", output, bytemat);
			    	return bytemat.toArray();
				}
				else
					return new byte[] {};
    	}

        /*Dll files are driver files native to windows, this static method will load the drivers, 
        allowing the use of opencv in this java program*/
        public static void loadDriver()
        {
        	String workingDirectoryLocation = (System.getProperty("user.dir"));    
        	String opencv = (workingDirectoryLocation + "\\drivers\\x64\\opencv_java330.dll");
        	System.load(opencv);
        }
}