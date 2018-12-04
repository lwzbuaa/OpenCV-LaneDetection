# QuickDrive

## Requirements
  - Lane detection
  - Object detection
    - Sign detection (coupled with object detection)
  - Wheel movement decisioning (angle, speed data + acceleration data) PID?
  
##### Step 1: Setting up the Android project

setup OpenCV for Android Studio:
1) https://sourceforge.net/projects/opencvlibrary/files/opencv-android/ <-- Download
2) File > New > Import Module
3) Choose the OpenCV-Version-android-sdk/sdk/java
4) go to build.gradle, and change compileSdkVersion, buildToolsVersion and targetSdkVersion to latest API you are using
5) Right click on 'app', and in module settings, add module dependency "openCVLibrary"
https://medium.com/@rdeep/android-opencv-integration-without-opencv-manager-c259ef14e73b <-- How to install OpenCV native libraries for java

##### Step 2: Image processing
OpenCV img proc template

##### Step 3: Lane detection
Copy from laneDetection.py

##### Testing & Tuning
tweaking the OpenCV HSV and RGB filters in order to isolate the lane


