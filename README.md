# ISS-18-ddr
Project for the course of "Laboratorio dei Sistemi Software" AA.2018/2019

https://www.unibo.it/en/teaching/course-unit-catalogue/course-unit/2019/412677

## Use Requirements

In order to use this application, download and install:
* **QActor plug-in** in Ecplise IDE (open filesystem -> eclipse folder -> dropin -> past QActor plug-in).
* XText Plug-in

## Run

### Front-end
* ddr-app read: https://bitbucket.org/chiara-volonnino/iss-18-ddr/src/master/srcNode/ddr-app/

### Robot virtual:
* Soffritti read: https://bitbucket.org/chiara-volonnino/iss-18-ddr/src/master/srcNode/Soffritti/

### Robot Phisical: 
* Go and load in Arduino this file: https://bitbucket.org/chiara-volonnino/iss-18-ddr/src/master/srcArduino/ArduinoCar/

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

## Documentation
A brief presentation of the system can be found in doc folder or in downloads: 
https://bitbucket.org/chiara-volonnino/iss-18-ddr/downloads/

## Team members
Chiara Volonnino (chiara.volonnino@studio.unibo.it)  
