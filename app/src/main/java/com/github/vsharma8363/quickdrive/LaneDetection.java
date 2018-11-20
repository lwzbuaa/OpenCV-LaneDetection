package com.github.vsharma8363.quickdrive;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

public class LaneDetection
{
    /**
     *
     * @param input Frame from camera input
     * @return Mat with all parts of image masked out in black except for the lane (in white)
     */
    public static Mat laneIsolator(Mat input)
    {
        return input;
    }

    /**
     *
     * @param x The x value for the scalar
     * @param y the y value for the scalar
     * @param tolerance value between 0.0 (100%) and 1.0 (0% accurate) - Larger it gets, lower the precision
     * @return the image with everything blurred out except the road
     */
    public static Mat roadSegmentation(Mat input, double x, double y, double tolerance)
    {
        Imgproc.blur(input, input, new Size(6,6));
        Imgproc.cvtColor(input, input, Imgproc.COLOR_RGB2HSV);
        Imgproc.blur(input, input, new Size(6,6));
        //Find scalar of target point
        double[] val = input.get((int)x,(int)y);
        Scalar target = new Scalar(val);
        Scalar upper = target.mul(new Scalar(1+tolerance, 1+tolerance, 1+tolerance));
        Scalar lower = target.mul(new Scalar(1-tolerance, 1-tolerance, 1-tolerance));
        //Apply road mask
        Core.inRange(input, lower, upper, input);
        return input;
    }
}
