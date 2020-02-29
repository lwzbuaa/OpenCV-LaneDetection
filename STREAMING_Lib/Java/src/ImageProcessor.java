import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class ImageProcessor 
{
	public static String process(Mat output, Scalar[] scalars)
	{
		ArrayList<Rect> r = ImageProcessor.getBoundingRectangle(output, scalars[0], scalars[1]);
  		ImageProcessor.drawRectangle(output, r.get(0));
  		ImageProcessor.drawRectangle(output, r.get(1));
  		
  		double angle = ImageProcessor.getAngle(r.get(0), r.get(1));
  		
  		return (r.get(0).toString() + " ||| " + r.get(1).toString()+ " ||| Angle: " + angle);
	}
	
	public static ArrayList<Rect> getBoundingRectangle(Mat frame, Scalar lowerHSV, Scalar upperHSV)
	{
		Mat processed = new Mat();
		Mat mHierarchy = new Mat();

		ArrayList<Rect> boxes = new ArrayList<Rect>();

		Core.inRange(frame, lowerHSV, upperHSV, processed);
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Imgproc.findContours(processed, contours, mHierarchy, Imgproc.RETR_LIST,Imgproc.CHAIN_APPROX_SIMPLE);
		MatOfPoint2f approxCurve = new MatOfPoint2f();

		for (int i = 0; i < contours.size(); i++)
		{
			MatOfPoint2f contour2f = new
					MatOfPoint2f(contours.get(i).toArray());
			double approxDistance = Imgproc.arcLength(contour2f, true)*0.02;
			Imgproc.approxPolyDP(contour2f, approxCurve, approxDistance, true);
			MatOfPoint points = new MatOfPoint( approxCurve.toArray() );
			boxes.add(Imgproc.boundingRect(points));
		}

		if(boxes.size() < 2)
		{
			boxes.add(new Rect(0, 0, 0, 0));
		}
		else if(boxes.size() < 1)
		{
			boxes.add(new Rect(0, 0, 0, 0));
			boxes.add(new Rect(0, 0, 0, 0));
		}

		return boxes;
	}

	public double getDistance(Rect input, Rect ideal)
	{
		double width = input.width - ideal.width;
		double height = input.height - ideal.height;
		return width;
	}
	
	//Two largest Rectangles by area
	public static Rect[] twoLargestRects(ArrayList<Rect> rects)
	{
		for(int i = 0; i < rects.size(); i++)
		{
			if(((rects.get(i))).area() >= 220000)
				rects.remove(i);
		}

		if(rects.size() == 0)
		{
			rects.add(new Rect(0, 0, 0, 0));
		}
		Rect[] result = new Rect[2];

		Rect prevMVal = new Rect(0,0,0,0);
		Rect currVal = new Rect(0,0,0,0);
		Rect currMVal = new Rect(0,0,0,0);
		int currMIndex = 0;
		int prevMIndex = 0;

		for (int i = 0; i < rects.size(); i++) {
			currVal = (Rect) rects.get(i);
			if (currVal.area() >= currMVal.area()) {
				prevMVal = currMVal;
				currMVal = currVal;
				prevMIndex = currMIndex;
				currMIndex = i;
			} else if (currVal.area() >= prevMVal.area()) {
				prevMVal = currVal;
				prevMIndex = i;
			}
		}

		result[0] = (Rect) rects.get(currMIndex);
		result[1] = (Rect) rects.get(prevMIndex);

		return result;
	}

	//Finds the angle to the wall from the center of the camera 
	//Will be negative if left, positive if to the right
	//using two bounding Rectangles
	//(Should be the Rectangles created by the reflecting tape)
	public static double getAngle(Rect left, Rect right) {
		//Camera is 1280x720, view angle is 61 degrees horizontal
		//1280/61 = 0.04765625
		double degreesPerPixel = 0.04765625; //Should be a final field (Degrees per pixel for the camera, see above)
		double midPointX = 640; //Should be a final field (The midpoint x value of the camera, see above)
		double r;
		double leftX = left.x + left.width;
		double rightX = right.x;
		double rectMidpointX = leftX + rightX / 2;

		r = degreesPerPixel * (midPointX - rectMidpointX);
		return r;
	}


	public static Mat drawRectangle(Mat frame, Rect boundingBox) {
		if(!boundingBox.equals(new Rect(0,0,0,0)) && boundingBox != null)
			Imgproc.rectangle(frame, new Point(boundingBox.x, boundingBox.y), new Point(boundingBox.x+boundingBox.width, boundingBox.y+boundingBox.height), new Scalar(255, 0, 0));
		return frame;
	}
}
