# Vandroid

### Concept
Using OpenCV object recognition, as well as TensorFlow by Google to teach the average Android phone to drive

#### Requirements
The goal is to use the least amount of sensor input as well as the least amount of processing power to create a system that works relatively well (doesn't crash in the simulations more than a few dozen times and the P around 70).
Parts of the project:
  - Lane detection
  - Object detection
  - Sign detection (coupled with object detection)
  - Wheel movement decisioning
  - Speed (Optional, speed measurements is more of a LIDAR/RADAR type thing, using the camera for this might be an impracticality)
  
DISCLAIMER (for those who need one): Obviously this ought not to be used directly with a car, so don't do it

### Step 0: Setting up the Android project
This isn't really a tutorial, more of a scratchpad to take notes on my project if possible, and as such, attached to each of these steps is a short explaination but not a line by line analysis of the code. The first step is really to create the project and setup the TensorFlow and OpenCV Android libraries.

##### How to setup TensorFlow for Android Studio:
  

### Step 2: Lane detection
A while ago, I wrote a fairly simply python program to perform lane detection (code saved as laneClassify.py), so the first step is to completely repurpose this and rewrite it in java. The procedure is fairly simple
