# ISS-18-ddr
Project for the course of "Laboratory of Software Systems" AA.2018/2019

https://www.unibo.it/en/teaching/course-unit-catalogue/course-unit/2019/412677

## Use Requirements

In order to use this application, download and install:
* **QActor plug-in** in Ecplise IDE (open filesystem -> eclipse folder -> dropin -> past QActor plug-in).
* XText Plug-in

## Run

### Front-end
* Read ddr-app: https://github.com/chiara-volonnino/lss-18-ddr/tree/master/srcNode/ddr-app

### Robot virtual:
* Read Soffritti: https://github.com/chiara-volonnino/lss-18-ddr/tree/master/srcNode/Soffritti

### Robot Phisical: 
* Go and load in your Arduino this files: https://github.com/chiara-volonnino/lss-18-ddr/tree/master/srcArduino/ArduinoCar
* Build project and load in your Raspberry jar file present in path: build -> distributions -> it.unibo.ctxConsoleMainCtxConsole and build -> distributions -> it.unibo.ctxRobotMainCtxRobot 

### Robot and Console

In ***local*** the system all that is needed: 


* Set in robot.qa (in src) localhost host
* Run gradle build
* Download the jar file in build -> distributions -> it.unibo.ctxConsoleMainCtxConsole and build -> distributions -> it.unibo.ctxRobotMainCtxRobot
* Open a cmd, one for each contect, and run: 
`
	java -jar iss-18-ddr-1.0.jar
`

In ***remote*** the system all that is needed: 


* Set in robot.qa (in src) the correct host
* Run gradle build
* Download the jar file in build -> distributions -> it.unibo.ctxConsoleMainCtxConsole and build -> distributions -> it.unibo.ctxRobotMainCtxRobot
* Open a cmd, one for each contect, and run: 
`
	java -jar iss-18-ddr-1.0.jar
`

## Documentation [ITA]
A brief presentation of the system can be found in [doc folder](https://github.com/chiara-volonnino/lss-18-ddr/tree/master/doc)

## Team members
Chiara Volonnino (chiara.volonnino@studio.unibo.it)  
