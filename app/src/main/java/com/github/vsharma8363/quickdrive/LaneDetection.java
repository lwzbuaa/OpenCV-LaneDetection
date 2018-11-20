package com.github.vsharma8363.quickdrive;

import org.opencv.core.Mat;

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
}
