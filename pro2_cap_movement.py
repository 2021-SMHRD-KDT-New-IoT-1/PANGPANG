import pro2_get_arduino as p2ga
import time
import datetime
import os
import requests
import picamera

def detect() :
    while True:
        #print(f'sssssss2')
        #result = p2ga.co_ult()

        # co, dis = result
        # dis = int(dis)
        #print(f'sssssss3')
        # print(f'co : {co}')
        # print(f'dis : {dis}')
        picam = picamera.PiCamera()
        with picam :       
            picam.resolution = (640, 480)
            now = datetime.datetime.now()
            filename = now.strftime('%Y-%m-%d-%H-%M-%S')
            picam.start_recording(output = '/home/pi/Videos/' + filename + '.h264')
            picam.wait_recording(10)
            picam.stop_recording()
            os.system(f"MP4Box -add {'/home/pi/Videos/'+filename + '.h264'} {'/home/pi/Videos/'+filename + '.mp4'}")
            print(f"MP4Box -add {'/home/pi/Videos/'+filename + '.h264'} {'/home/pi/Videos/'+filename + '.mp4'}")
            requests.post('http://172.30.1.6:8087/app_server_test/getVideo',
                               files={'video':open('/home/pi/Videos/' + filename + '.mp4','rb')})
        break

if __name__=='__main__':
    while True:
        detect()
