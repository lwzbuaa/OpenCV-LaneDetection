import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class IO 
{	
	public static Scalar[] getScalars()
	{
		Scalar[] hsvs = new Scalar[2];
		String s = getText("scalars.txt");
    	
    	String lower = s.substring(s.indexOf("{") + 1, s.indexOf("}"));
    	String[] larray = lower.split(",");
    	//lower hsv
    	hsvs[0] = new Scalar(Integer.parseInt(larray[0]), Integer.parseInt(larray[1]), Integer.parseInt(larray[2]));
    	
    	String upper = s.substring(s.lastIndexOf("{") + 1, s.lastIndexOf("}"));
    	String[] uarray = upper.split(",");
    	//upper hsv
    	hsvs[1] = new Scalar(Integer.parseInt(uarray[0]), Integer.parseInt(uarray[1]), Integer.parseInt(uarray[2]));
    	
    	return hsvs;
	}
	
	public static String getText(String file)
    {
    	String everything = "";
		    try{
		    	BufferedReader br = new BufferedReader(new FileReader(file));
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();
		
		        while (line != null) {
		            sb.append(line);
		            sb.append(System.lineSeparator());
		            line = br.readLine();
		        }
		        everything = sb.toString();
		    }catch(IOException e){}
		    
    	return everything;
    }
}
