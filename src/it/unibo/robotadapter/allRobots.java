package it.unibo.robotadapter;

import java.util.ArrayList;
import java.util.HashMap;

import it.unibo.qactors.QActorContext;
import it.unibo.qactors.akka.QActor;

public class allRobots {

	public static final String ROBOT_SERIAL = "robotSerial";
	public static final String ROBOT_DEMO = "robotDemo";
	public static final String ROBOT_VIRTUAL = "robotVirtual";

	private static final HashMap<String, ArrayList<RobotExecutor>> executors = new HashMap<>();

	public static void setUp(QActor qa, String robotType, String args) {
		try {
			qa.println("in setUp section, all robot-robot adapter. Robot type is: " + robotType);
			RobotExecutor tmp = null;
			switch (robotType) {
			case ROBOT_SERIAL:
				qa.println("init serial configuration");
				tmp = new it.unibo.robotadapter.robotSerial.robotExecutor();
				break;
			case ROBOT_DEMO:
				qa.println("init demo configuration");
				tmp = new it.unibo.robotadapter.robotDemo.robotExecutor();
				break;
			case ROBOT_VIRTUAL:
				qa.println("init virtual configuration");
				tmp = new it.unibo.robotadapter.robotVirtual.robotExecutor();
				break;
			}
			qa.println("allRobots " + robotType + " setUp args=" + args);
			executors.putIfAbsent(robotType, new ArrayList<>());
			if (tmp != null) {
				if (args.startsWith("\'"))
					tmp.setUp(qa, args.substring(1, args.length() - 1));
				else
					tmp.setUp(qa, args);
				executors.get(robotType).add(tmp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void terminate(QActor qa) {
		qa.println("allRobots terminating");
		for (String key : executors.keySet()) {
			for (int i = executors.get(key).size() - 1; i >= 0; i--) {
				executors.get(key).get(i).terminate(qa);
				executors.get(key).remove(i);
			}
			executors.remove(key);
		}
	}

	public static void doMove(QActor qa, String cmd) { // Args MUST be String
		try {
			executors.values().forEach(l -> l.forEach(e -> e.doMove(qa, cmd)));
			sendCommandCompleted(qa, cmd);
		} catch (Exception e) {
			System.out.println("movePlanUtil ERROR:" + e.getMessage());
		}

	}

	public static void doMove(QActor qa, String cmd, String duration) { // Args MUST be String
		try {
			executors.values().forEach(l -> l.forEach(e -> e.doMove(qa, cmd)));
			Thread.sleep(Integer.parseInt(duration));
			executors.values().forEach(l -> l.forEach(e -> e.doMove(qa, "h")));
			sendCommandCompleted(qa, cmd);
		} catch (Exception e) {
			System.out.println("movePlanUtil ERROR:" + e.getMessage());
		}

	}

	private static void sendCommandCompleted(QActor qa, String cmd) throws Exception {
		switch (cmd) {
		case "a":
		case "d":
			String temporaryStr = "moveMsgCmdDone(CMD)".replace("CMD", cmd);
			qa.sendMsg("moveMsgCmdDone", "robot_advanced", QActorContext.dispatch, temporaryStr);
			break;
		default:
		}
	}
}
