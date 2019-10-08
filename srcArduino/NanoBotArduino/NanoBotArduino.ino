#include <Arduino.h>
#include <SoftwareSerial.h>
#include "L298N.h"       // Motor H-bridge (L298N)

#include "Scheduler.h"
#include "DistanceTask.h"
#include "BlinkTask.h"
#include "SerialTask.h"


#define PROXIMITY_TRIG_PIN A5
#define PROXIMITY_ECHO_PIN A4
#define LED_PIN1 13
#define LED_PIN2 10

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
#define ENA 5
#define ENB 6
#define IN1 7
#define IN2 8
#define IN3 9
#define IN4 11

#define carSpeed 250

L298N motor1(7, 8);
L298N motor2(9, 11);

Scheduler sched;

float* gloDistanceValue = new float(5.0);
bool* gloBlinkingState = new bool(false);

DistanceTask* distanceTask = new DistanceTask(PROXIMITY_TRIG_PIN, PROXIMITY_ECHO_PIN, gloDistanceValue);
BlinkTask* blinkingLed1 = new BlinkTask(LED_PIN1, gloBlinkingState);
BlinkTask* blinkingLed2 = new BlinkTask(LED_PIN2, gloBlinkingState);
SerialTask* serialTask = new SerialTask(gloDistanceValue, gloBlinkingState, move);

/*
   -----------------------------------
   setup
   -----------------------------------
*/
void setup() {
  sched.init(50);
  
  distanceTask->init(100);
  blinkingLed1->init(200);
  blinkingLed2->init(200);
  serialTask->init(50);
  
  sched.addTask(distanceTask);
  sched.addTask(blinkingLed1);
  sched.addTask(blinkingLed2);
  sched.addTask(serialTask);
}

void loop() {
  sched.schedule();
}

/*
   -----------------------------------
   Moving
   -----------------------------------
*/

void forward(){ 
  analogWrite(ENA, carSpeed);
  analogWrite(ENB, carSpeed);
  digitalWrite(IN1, HIGH);
  digitalWrite(IN2, LOW);
  digitalWrite(IN3, LOW);
  digitalWrite(IN4, HIGH);
  Serial.println("Forward");
}

void backward() {
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

void move(int direction)
{
  switch (direction) {

    case 1://forward
      forward();
      break;
    case 2: //backward
      backward();
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
