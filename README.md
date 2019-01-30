# QuickDrive

## Requirements
  - Lane detection
  - Object detection
    - Sign detection (coupled with object detection)
  - Wheel movement decisioning (angle, speed data + acceleration data) PID?
  
##### Setting up the Android project

setup OpenCV for Android Studio:
1) https://sourceforge.net/projects/opencvlibrary/files/opencv-android/ <-- Download
2) File > New > Import Module
3) Choose the OpenCV-Version-android-sdk/sdk/java
4) go to build.gradle, and change compileSdkVersion, buildToolsVersion and targetSdkVersion to latest API you are using
5) Right click on 'app', and in module settings, add module dependency "openCVLibrary"
https://medium.com/@rdeep/android-opencv-integration-without-opencv-manager-c259ef14e73b <-- How to install OpenCV native libraries for java

##### Lane Detection (Py - Working Example)

1) Using canny transformation to isolate objects

'''python
def laneAngle(frame):
    height = np.size(frame, 0)
    width = np.size(frame, 1)
    edges = cv2.Canny(frame, 100, 200)
'''

2) Isolating the region of interest using polygon morph (native to OpenCV) and cropping the image

'''python
    rows, cols = edges.shape[:2]
    bottom_left = [cols * 0.1, rows * 0.9]
    top_left = [cols * 0.25, rows * 0.55]
    bottom_right = [cols * 0.9, rows * 0.9]
    top_right = [cols * 0.75, rows * 0.55]

    # the vertices are an array of polygons (i.e array of arrays) and the data type must be integer
    vertices = np.array([[bottom_left, top_left, top_right, bottom_right]], dtype=np.int32)
    cropped_edges = filter_region(edges, vertices)
'''

3) Using the hough_lines transformation to isolate lanes [More info](https://opencv-python-tutroals.readthedocs.io/en/latest/py_tutorials/py_imgproc/py_houghlines/py_houghlines.html)

'''python
    hough_lines = cv2.HoughLinesP(cropped_edges, rho=1, theta=np.pi / 180, threshold=20, minLineLength=50,
                                  maxLineGap=50)
    if hough_lines is not None:
        list_of_lines = list(hough_lines)
    totalM = 0
    m = 0
    xCenter = width / 2
    yCenter = height * 0.75
    leftLane = [-1, -1, -1, -1]
    rightLane = [-1, -1, -1, -1]
'''


