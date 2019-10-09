package it.unibo.robotadapter.robotDemo;

import it.unibo.qactors.akka.QActor;
import it.unibo.robotadapter.RobotExecutor;

public class robotExecutor implements RobotExecutor {

	@Override
	public void setUp(QActor qa, String args) {
		qa.println("Started demo executor");
	}

	@Override
	public void terminate(QActor qa) {
		qa.println("Closing demo executor");
	}

	@Override
	public void doMove(QActor qa, String cmd) {
		qa.println("Executing commmand " + cmd);
	}

}
