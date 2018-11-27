package com.github.vsharma8363.quickdrive;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class Isolator {

    public static Mat laneProcessor(Mat input) {
        Mat edges = new Mat();
        Imgproc.Canny(input, edges, 100, 200, 5, true);

        return edges;
    }
}
