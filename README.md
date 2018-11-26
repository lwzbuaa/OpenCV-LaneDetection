# QuickDrive

## Concept
Using OpenCV object recognition to show that the average Android phone can drive

## Requirements
The goal is to use the camera as well as a minimum on processing power to create a system that works relatively well
Parts of the project:
  - Lane detection
  - Object detection
    - Sign detection (coupled with object detection)
  - Wheel movement decisioning (returning what angle to turn)
  
## Step 1: Setting up the Android project
The first step is really to create the project and setup OpenCV Android libraries.

#### How to setup OpenCV for Android Studio:
1) https://sourceforge.net/projects/opencvlibrary/files/opencv-android/ <-- Download
2) File > New > Import Module
3) Choose the OpenCV-Version-android-sdk/sdk/java
4) go to build.gradle, and change compileSdkVersion, buildToolsVersion and targetSdkVersion to latest API you are using
5) Right click on 'app', and in module settings, add module dependency "openCVLibrary"

##### This next part I cited from CodeOnion

Add Native libraries:
1) Go to File>Project Structure.
2) When window opens, under Modules, select app.
3) Click Dependencies.
4) Click +.
5) Select Module Dependencies.
6) Select :openCVLibrary343.

Source: https://blog.codeonion.com/2015/11/25/creating-a-new-opencv-project-in-android-studio/ <-- Literally the best guide to OpenCV for Android

https://medium.com/@rdeep/android-opencv-integration-without-opencv-manager-c259ef14e73b <-- How to install OpenCV native libraries for java

## Step 2: Building a project, and image processing
The next step is to get all of this actually working with a base. I plan on just using the template OpenCV provided for android that will let me perform some really awesome Computer Vision stuff

## Step 3: Lane detection
Writing the actual code to detect lanes and print the angle of shift that is read from the phone

## Testing & Tuning
Now it's just a matter of tweaking the OpenCV HSV and RGB filters in order to isolate the lane and eventually hooking the angle output up to a test system and seeing the code in action


