package it.unibo.robotadapter.robotSerial;

import it.unibo.qactors.akka.QActor;
import it.unibo.robotadapter.IRobotSerialObserver;
import it.unibo.robotadapter.RobotExecutor;

public class robotExecutor implements RobotExecutor {

	private RobotSerialCommunication robotSupport; // singleton
	private int printCount = 0;

	@Override
	public void setUp(QActor qa, String port) {
		if (robotSupport == null) {
			robotSupport = new RobotSerialCommunication(port, new RobotSerialCommunication.Logger() {
				private String owner = "";

				@Override
				public void setOwner(String owner) {
					this.owner = owner + " ";
				}

				@Override
				public void log(String msg) {
					qa.println(owner + msg);
				}
			});
			robotSupport.addObserverToSensors(new IRobotSerialObserver() {
				@Override
				public void notify(String data) {
					double distance = Double.parseDouble(data);
					if (distance < 20.0 || printCount++ == 5) {
						printCount = 0;
						qa.println("\t sonar: " + distance);
						qa.emit("robotSonar", "robotSonar(distance(" + data + "))");
						if (distance < 7.0) {
							qa.println("\t FISICAL COLLISION: " + distance);
							qa.emit("collisionDispatch", "collisionDispatch(obstacle(fisico))");
						}
					}
				}
			});
		}
	}

	@Override
	public void terminate(QActor qa) {
		robotSupport.close();
	}

	@Override
	public void doMove(QActor qa, String cmd) {
		switch (cmd) {
		case "h":
		case "w":
		case "a":
		case "s":
		case "d":
			robotSupport.executeTheCommand(cmd);
			break;
		case "blinkStart":
			robotSupport.executeTheCommand("b");
			break;
		case "blinkStop":
			robotSupport.executeTheCommand("n");
			break;
		default:
			// todo
		}
	}
}
