#ifndef __SERIALTASK__
#define __SERIALTASK__

#include "Task.h"
#include "Arduino.h"
#include "SoftwareSerial.h"

class SerialTask: public Task {

public:
  SerialTask(float* gloDistanceValue, bool* gloBlinkingState, void (*move)(int));
  void init(int period);
  void tick();

private:
	void remoteCmdExecutor();

  float* gloDistanceValue;
  bool* gloBlinkingState;
  void (*move)(int);

	int input;
  unsigned int c;
};

#endif
