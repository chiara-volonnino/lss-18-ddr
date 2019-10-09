#include <Arduino.h>
#include <SoftwareSerial.h>

#include "Scheduler.h"
#include "DistanceTask.h"
#include "BlinkTask.h"
#include "SerialTask.h"


#define PROXIMITY_TRIG_PIN A5
#define PROXIMITY_ECHO_PIN A4
#define LED_PIN    13


#define ENA 5
#define ENB 6
#define IN1 7
#define IN2 8
#define IN3 9
#define IN4 11
#define carSpeed 250

/*
   -----------------------------------
   Function declaration
   -----------------------------------
*/
void move(int direction);

/*
   -----------------------------------
   Hardware devices
   -----------------------------------
*/
Scheduler sched;

float* gloDistanceValue = new float(5.0);
bool* gloBlinkingState = new bool(false);

DistanceTask* distanceTask = new DistanceTask(PROXIMITY_TRIG_PIN, PROXIMITY_ECHO_PIN, gloDistanceValue);
BlinkTask* blinkingLed = new BlinkTask(LED_PIN, gloBlinkingState);
SerialTask* serialTask = new SerialTask(gloDistanceValue, gloBlinkingState, move);


void forward(){ 
  analogWrite(ENA, carSpeed);
  analogWrite(ENB, carSpeed);
  digitalWrite(IN1, HIGH);
  digitalWrite(IN2, LOW);
  digitalWrite(IN3, LOW);
  digitalWrite(IN4, HIGH);
  Serial.println("Forward");
}

void back() {
  analogWrite(ENA, carSpeed);
  analogWrite(ENB, carSpeed);
  digitalWrite(IN1, LOW);
  digitalWrite(IN2, HIGH);
  digitalWrite(IN3, HIGH);
  digitalWrite(IN4, LOW);
  Serial.println("Back");
}

void left() {
  analogWrite(ENA, carSpeed);
  analogWrite(ENB, carSpeed);
  digitalWrite(IN1, LOW);
  digitalWrite(IN2, HIGH);
  digitalWrite(IN3, LOW);
  digitalWrite(IN4, HIGH); 
  Serial.println("Left");
}

void right() {
  analogWrite(ENA, carSpeed);
  analogWrite(ENB, carSpeed);
  digitalWrite(IN1, HIGH);
  digitalWrite(IN2, LOW);
  digitalWrite(IN3, HIGH);
  digitalWrite(IN4, LOW);
  Serial.println("Right");
}

void stop() {
  digitalWrite(ENA, LOW);
  digitalWrite(ENB, LOW);
  Serial.println("Stop!");
}

void setup() {
  Serial.begin(9600);
  pinMode(IN1,OUTPUT);
  pinMode(IN2,OUTPUT);
  pinMode(IN3,OUTPUT);
  pinMode(IN4,OUTPUT);
  pinMode(ENA,OUTPUT);
  pinMode(ENB,OUTPUT);
  stop();
  sched.init(50);
  
  distanceTask->init(100);
  blinkingLed->init(200);
  serialTask->init(50);
  
  sched.addTask(distanceTask);
  sched.addTask(blinkingLed);
  sched.addTask(serialTask);
}

void loop() {
  sched.schedule();
}

void move(int direction)
{
  switch (direction) {

    case 1://forward
      forward();
      break;
    case 2: //backward
      back();
      break;
    case 3: //left
      left();
      break;
    case 4: //right
      right();
      break;
    case 5: //halt
      stop();
      break;
  }
}
