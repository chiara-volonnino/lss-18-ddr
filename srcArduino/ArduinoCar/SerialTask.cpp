#include "SerialTask.h"

SerialTask::SerialTask(float* gloDistanceValue, bool* gloBlinkingState, void (*move)(int)) {
  this->gloDistanceValue = gloDistanceValue;
  this->gloBlinkingState = gloBlinkingState;
  this->move = move;
}

void SerialTask::init(int period) {
  Task::init(period);
  c = 0;
  Serial.begin(9600);
  while (!Serial){}
}

/*
   -----------------------------------
   Interpreter
   -----------------------------------
*/
void SerialTask::remoteCmdExecutor()
{
  if ((Serial.available()) > (0  )) {
    input = Serial.read();
    //Serial.println(input);
    switch ( input ) {
      case 'w' : move(1); break;  //w
      case 's' : move(2); break;  //s
      case 'a'  : move(3); break;  //a
      case 'd' : move(4); break;  //d
      case 'h' : move(5); break;  //h
      case 'b' : *gloBlinkingState=true; break;   //b
      case 'n' : *gloBlinkingState=false; break; //n
      default  : move(5);
    }
  }
}

void SerialTask::tick() {
	remoteCmdExecutor();
	
  if (c++ == 3) {
    c = 0;
    Serial.println(*gloDistanceValue);
  }
}
