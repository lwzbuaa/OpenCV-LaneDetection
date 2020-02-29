from flask import Flask, render_template, Response
import cv2
import threading

app = Flask(__name__)

vc = cv2.VideoCapture(0)

def processor(frame):
    frame = cv2.resize(frame, (640, 360))
    return frame

def capture2Save():
    rect, frame = vc.read()
    cv2.imwrite('temp.jpg', processor(frame))

@app.route('/')
def index():
    return render_template('index.html')

def gen():
    count = 0
    while True:
        #Slow down image capture to allow processing and prevent overlap in threads
        if count > 250:
            count = 0
            proc = threading.Thread(target=capture2Save(), args=())
            proc.start()
        else:
            count += 1
        yield (b'--frame\r\n'b'Content-Type: image/jpeg\r\n\r\n' + open('temp.jpg', 'rb').read() + b'\r\n')

@app.route('/feed')
def feed():
    return Response(gen(), mimetype='multipart/x-mixed-replace; boundary=frame')

if __name__ == '__main__':
    app.run(host='127.0.0.1', port=5151, debug=False, threaded=True)
