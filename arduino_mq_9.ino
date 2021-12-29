#include <MQUnifiedsensor.h>

#define         Board                   ("Arduino UNO")
#define         Pin                     (A0)  //Analog input 4 of your arduino
#define         Type                    ("MQ-9") //MQ9
#define         Voltage_Resolution      (5)
#define         ADC_Bit_Resolution      (10) // For arduino UNO/MEGA/NANO
#define         RatioMQ9CleanAir        (9.6) //RS / R0 = 60 ppm 

MQUnifiedsensor MQ9(Board, Voltage_Resolution, ADC_Bit_Resolution, Pin, Type);

int trig = 2; //TRIG 핀 설정 (초음파 보내는 핀)
int echo = 3; //ECHO 핀 설정 (초음파 받는 핀)

void setup() {  
  pinMode(trig, OUTPUT);
  pinMode(echo, INPUT);
  Serial.begin(9600); //Init serial port

  MQ9.setRegressionMethod(1); //_PPM =  a*ratio^b

  MQ9.init(); 
  
  // Serial.print("Calibrating please wait.");
  float calcR0 = 0;
  for(int i = 1; i<=10; i ++)
  {
    MQ9.update(); // Update data, the arduino will be read the voltage on the analog pin
    calcR0 += MQ9.calibrate(RatioMQ9CleanAir);
    // Serial.print(".");
  }
  MQ9.setR0(calcR0/10);
  // Serial.println("  done!.");
  
  if(isinf(calcR0)) {Serial.println("Warning: Conection issue founded, R0 is infite (Open circuit detected) please check your wiring and supply"); while(1);}
  if(calcR0 == 0){Serial.println("Warning: Conection issue founded, R0 is zero (Analog pin with short circuit to ground) please check your wiring and supply"); while(1);}

  // Serial.println("** Lectures from MQ-9 ****");
  // Serial.println("|    LPG   |  CH4 |   CO  |");   
}

void loop() {
  MQ9.update(); // Update data, the arduino will be read the voltage on the analog pin
  MQ9.setA(599.65); MQ9.setB(-2.244); // Configurate the ecuation values to get LPG concentration
  float CO = MQ9.readSensor(); // Sensor will read PPM concentration using the model and a and b values setted before or in the setup

  // Serial.print("|  LPG :  "); Serial.print(LPG);
  // Serial.print("   CH4 :   "); Serial.print(CH4);
  // Serial.print("   CO  :   "); 
  Serial.print(CO); 
  Serial.print(", ");
  ultrawave();
  Serial.println("");  
  delay(500); //Sampling frequency
}

void ultrawave() {
  digitalWrite(trig, 1); // HIGN, 1 - 초음파를 쏜다 
  delayMicroseconds(10); // 1micro초 = 0.000001초
  digitalWrite(trig, 0); // LOW, 0 - 초음파를 멈춘다

  int duration = pulseIn(echo, 1);
  // pulseIn - 전압이 0 or 1이 될때까지 걸린시간을 재는 함수
  
  int distance = duration / 2 / 29;
  // 1cm 이동시 29.1microsecond가 걸린다.

  Serial.print(distance);  
}
