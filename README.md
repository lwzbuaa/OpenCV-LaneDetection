# QuickDrive

## Inspiration
It's a little weird my phone has several thousand times the processing power of the Apollo computer but struggles to pre-order my Cold Brew from Starbucks

## Concept
Using OpenCV object recognition, as well as TensorFlow by Google to teach the average Android phone to drive

## Requirements
The goal is to use the least amount of sensor input as well as the least amount of processing power to create a system that works relatively well (doesn't crash in the simulations more than a few dozen times and the P around 70).
Parts of the project:
  - Lane detection
  - Object detection
  - Sign detection (coupled with object detection)
  - Wheel movement decisioning
  - Speed (Optional, speed measurements is more of a LIDAR/RADAR type thing, using the camera for this might be an impracticality)
  
DISCLAIMER (for those who need one): Obviously this ought not to be used directly with a car, so don't do it, I am not going to take responsibility for someone who takes this code (free to use btw) and accidentally crashes their dad's Porsche

## Step 0: Setting up the Android project
This isn't really a tutorial, more of a scratchpad to take notes on my project if possible, and as such, attached to each of these steps is a short explaination but not a line by line analysis of the code. The first step is really to create the project and setup the TensorFlow and OpenCV Android libraries.

#### How to setup OpenCV for Android Studio:
1) https://sourceforge.net/projects/opencvlibrary/files/opencv-android/ <-- Download
2) File > New > Import Module
3) Choose the OpenCV-Version-android-sdk/sdk/java
4) go to build.gradle, and change compileSdkVersion, buildToolsVersion and targetSdkVersion to latest API you are using
5) Right click on 'app', and in module settings, add module dependency "openCVLibrary"

Add Native libraries:
1) Go to File>Project Structure.
2) When window opens, under Modules, select app.
3) Click Dependencies.
4) Click +.
5) Select Module Dependencies.
6) Select :openCVLibrary343.

Source: https://blog.codeonion.com/2015/11/25/creating-a-new-opencv-project-in-android-studio/ <-- Literally the best guide to OpenCV for Android

#### How to setup TensorFlow for Android Studio:
Google is slowly phasing out the old TensorFlow mobile and is switching to TensorFlow Lite. It is basically TensorFlow mobile but institutes standardization between the various other (non PC/Desktop) platforms so code can be ported with minimal effort (interchangable with Web, Mobile Device, Servers probably?). Meaning that this is for TensorFlow Lite, not mobile.

## Step 2: Building a project, and image processing
The next step is to get all of this actually working with a base. I plan on just using the template OpenCV provided for android that will let us perform some really awesome Computer Vision stuff to the image without having to spend the time to work on Android Image Capture, conversion to Bitmap, etc. Basically, I am going to use the camera capture template to speed up my work.

## Step 3: Lane detection
A while ago, I wrote a fairly simply python program to perform lane detection (code saved as laneClassify.py), so the first step is to completely repurpose this and rewrite it in java. The procedure is fairly simple
