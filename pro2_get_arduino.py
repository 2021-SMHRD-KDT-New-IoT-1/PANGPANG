import serial
import re

ser = serial.Serial("/dev/ttyACM0", 9600)
# ser.flushInput()

def co_ult():
    while True : 
        result = ser.readline()
        result = result.decode('utf-8')
        data1 = ''
        data2 = ''
        check = False

        # data1에 , 전까지 데이터를 누적
        for i in result:
            if i == ',':
                break
            data1 += i
            
        # ,를 만난 순간 check가 True로 바뀌고 그 때 부터 누적
        for i in result:
            if i == ',':
                check = True
            
            if check:
                data2 += i
        # ,부터 저장이 되니 슬라이싱을 통해 데이터 전처리
        data2 = data2[2:-2]
        # print(data1)
        # print(data2)
        return (data1, data2)
