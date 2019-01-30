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

Using canny transformation to isolate objects

'''python

def laneAngle(frame):
    height = np.size(frame, 0)
    width = np.size(frame, 1)
    edges = cv2.Canny(frame, 100, 200)
'''

Isolating the region of interest using polygon morph (native to OpenCV) and cropping the image

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

'''python
    # INSERT CODE HERE
    # Find all lines in the image
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

    for i in list_of_lines:
        arr = i[0]
        x1 = arr[0]
        y1 = arr[1]
        x2 = arr[2]
        y2 = arr[3]

        cv2.line(frame, (x1,y1),(x2,y2), (255, 0, 0), 5)
        # Check if the line you are looking at has a slope near 0
        isNotFlatStraight = True
        if abs(float(x2) - float(x1)) < 50:
            isNotFlatStraight = False
            # if lane line is on the left side
        if (x1 < xCenter and x2 < xCenter) and isNotFlatStraight:
            if calculateDistance(leftLane[0], leftLane[1], leftLane[2], leftLane[3]) < calculateDistance(x1, y1, x2,
                                                                                                         y2):
                leftLane = [x1, y1, x2, y2]
            # if lane line is on the right side
        elif (x1 > xCenter and x2 > xCenter) and isNotFlatStraight:
            if calculateDistance(rightLane[0], rightLane[1], rightLane[2], rightLane[3]) < calculateDistance(x1, y1, x2,
                                                                                                             y2):
                rightLane = [x1, y1, x2, y2]

    vertices = [(rightLane[0], rightLane[1]), (rightLane[2], rightLane[3]), (leftLane[0], leftLane[1]),
                (leftLane[2], leftLane[3])]

    xView = midpoint(midpoint(vertices[0], vertices[2]), midpoint(vertices[1], vertices[3]))[0]

    #cv2.line(frame, vertices[0], vertices[1], (255, 0, 0), 5)
    #cv2.line(frame, vertices[1], vertices[2], (255, 0, 0), 5)
    #cv2.line(frame, vertices[2], vertices[3], (255, 0, 0), 5)
    #cv2.line(frame, vertices[3], vertices[0], (255, 0, 0), 5)

    angle = 0

    # check if right lane is valid
    if rightLane[0] != -1 and (float(rightLane[2]) - float(rightLane[0])) != 0:
        # right lane is valid
        right = True
        totalM += (float(rightLane[1]) - float(rightLane[3])) / (float(rightLane[2]) - float(rightLane[0]))
        m += 1
    else:
        right = False
    if leftLane[0] != -1 and (float(leftLane[2]) - float(leftLane[0])) != 0:
        # right lane is valid
        left = True
        totalM += (float(leftLane[1]) - float(leftLane[3])) / (float(leftLane[2]) - float(leftLane[0]))
        m += 1
    else:
        left = False

    offSet = xView - xCenter

    run = 10
    rise = 100

    if m != 0:
        rise = 100
        run = rise * totalM / m
        if offSet < 0:
            run = -abs(run)
        else:
            run = abs(run)
        angle = atan(run / rise) * 57.2958
    else:
        angle = 0

    if left == False and right != False:
        slope = (float(rightLane[1]) - float(rightLane[3])) / (float(rightLane[2]) - float(rightLane[0]))
        if slope != 0:
            angle = atan(1 / slope) * 57.2958
        else:
            angle = 0
    elif right == False and left != False:
        slope = (float(leftLane[1]) - float(leftLane[3])) / (float(leftLane[2]) - float(leftLane[0]))
        if slope != 0:
            angle = atan(1 / slope) * 57.2958
        else:
            angle = 0

    cv2.line(frame, (width / 2, int(height * 0.75)), (int(width / 2 + run), int(height * 0.75 - rise)), (0, 255, 0), 5)

    return frame, angle
'''

##### Step 3: Lane detection
Copy from laneDetection.py

##### Testing & Tuning
tweaking the OpenCV HSV and RGB filters in order to isolate the lane


