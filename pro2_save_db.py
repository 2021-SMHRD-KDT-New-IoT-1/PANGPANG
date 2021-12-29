import pymysql
import time
import threading

from pro2_cap_movement import detect

import pro2_get_arduino as p2ga

conn = pymysql.connect(host='project-db-stu2.ddns.net',
                     port=3307,
                     user='pang',
                     passwd='pang',
                     db='campus_a_2_1218',
                     charset='utf8')

cursor = conn.cursor()
isRecording = False
try :
    with cursor :
  
        while True :
            result = p2ga.co_ult()
            co, dis = result
            co = float(co)
            dis = int(dis)
            dis_re = 0
            sense_num = 0
            print(f'co : {co}')
            print(f'dis : {dis}')
            if dis <= 100 :
                dis_re = 1
            if dis_re == 1 :
                sense_num = 200
            else :
                sense_num = 100
                
            print(f'sense : {sense_num}')
            
            nb_nick = "장팡수"
            sql = "insert into tbl_sensing(mb_nickname, sensing_num, sensing_gasvalue, sensing_detected) values (%s, %s, %s, %s)"
            values = [(nb_nick, sense_num, co, dis_re)]
            cursor.executemany(sql, values)
            conn.commit()
            global th1
            if dis <= 100 :
                if isRecording == False:
                    isRecording = True
                    print('100이하임')
                    th1 = threading.Thread(target = detect)
                    th1.start()
            if isRecording == True :
                if th1.is_alive() == False :
                    isRecording = False
            time.sleep(1)
            
except KeyboardInterrupt :
    exit()
finally :
    cursor.close()
    conn.close()
