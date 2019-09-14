package it.unibo.robotadapter.robotSerial;

import java.util.ArrayList;

import it.unibo.robotadapter.IRobotSerialObserver;
import it.unibo.supports.serial.JSSCSerialComm;
import it.unibo.supports.serial.SerialPortConnSupport;

public class RobotSerialCommunication {

	private SerialPortConnSupport conn = null;
	private JSSCSerialComm serialConn;
	private double dataSonar = 0;
	private String curDataFromArduino;
	private ArrayList<IRobotSerialObserver> observer = new ArrayList<>();
	private Logger logger;
	private String port;

	public RobotSerialCommunication(String port, Logger logger) {
		this.logger = logger;
		this.logger.setOwner("RobotSerialCommunication");
		this.port = port;
		try {
			logger.log("start");
			serialConn = new JSSCSerialComm(null);
			conn = serialConn.connect(port); // returns a SerialPortConnSupport
			if (conn == null)
				return;
			curDataFromArduino = conn.receiveALine();
			logger.log("received: " + dataSonar);
			getDataFromArduino();
		} catch (Exception e) {
			logger.log("ERROR " + e.getMessage());
		}
	}

	public void close() {
		this.logger.log("closing communication on port " + port);
		try {
			conn.closeConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void executeTheCommand(String cmd) {
		logger.log("executeTheCommand " + cmd + " conn=" + conn);
		try {
			serialConn.writeData(cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addObserverToSensors(IRobotSerialObserver observer) {
		this.observer.add(observer);
	}

	private void getDataFromArduino() {
		new Thread() {
			public void run() {
				try {
					logger.log("getDataFromArduino STARTED");
					while (true) {
						try {
							curDataFromArduino = conn.receiveALine();
// 	 						logger.log("mbotConnArduinoObj received:" + curDataFromArduino );
							double v = Double.parseDouble(curDataFromArduino);
							// handle too fast change
							double delta = Math.abs(v - dataSonar);
							if (delta < 7 && delta > 0.5) {
								dataSonar = v;
								observer.forEach(o -> o.notify("" + dataSonar));
//								QActorUtils.raiseEvent(curActor, curActor.getName(), "realSonar", 
//										"sonar( DISTANCE )".replace("DISTANCE", ""+dataSonar ));
							}
						} catch (Exception e) {
							logger.log("ERROR:" + e.getMessage());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.start();
	}

	public interface Logger {
		void setOwner(String owner);

		void log(String msg);
	}
}
