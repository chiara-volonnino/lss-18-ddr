package it.unibo.systemstate;

import it.unibo.qactors.akka.QActor;
import it.unibo.utils.jsonUtil;

public class systemStateUtil {
	
	private static systemStateUtil singletonStateUtil;
	private static SystemState systemState = new SystemState(new WorldState(null, null), new RobotState(new Position(), null, null), new State());

	public static systemStateUtil getSystemStateUtil() {
		if (singletonStateUtil == null)
			singletonStateUtil = new systemStateUtil();
		return singletonStateUtil;
	}
	
	public static SystemState getSystemState() {
		return systemState;
	}
	
	private systemStateUtil() {
		super();
	}
	
	public static void updateTemperature(QActor qa, String value) {
		systemState.getWorld().setTemperature(value);
		notifyUpdateState(qa);
	}
	
	public static void updateMap(QActor qa, String[][] map) {
		systemState.getWorld().setMap(map);
		notifyUpdateState(qa);
	}
	
	public static void updateRobotMovement(QActor qa, RobotState robotMovement) {
		systemState.setRobot(robotMovement);
		notifyUpdateState(qa);
	}
	
	public static void updateRobotState(QActor qa, State state) {
		if (state.getMessage() == null) {
			state.setMessage(systemState.getState().getMessage());
		}
		if (state.getActions() == null) {
			state.setActions(systemState.getState().getActions());
		}
		systemState.setState(state);
		notifyUpdateState(qa);
	}

	private static void notifyUpdateState(QActor qa) {
		String payload = jsonUtil.encode(systemState);
		
		/* PublishEvent */
		try {
			String parg = "frontendRobotState(P)".replace("P", payload);
			/* PublishEventMove */
//			parg =  qa.updateVars( Term.createTerm("state(T,P)"), Term.createTerm("state(T,P)"), 
//				    		  Term.createTerm(payload), parg);
//			if( parg != null ) qa.sendMsgMqtt(  "unibo/frontendRobotState", "frontendRobotState", "none", parg );
			it.unibo.utils.mqttUtil.sendMsgMqtt(qa, "unibo/frontendRobotState", "frontendRobotState", "none", parg);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
