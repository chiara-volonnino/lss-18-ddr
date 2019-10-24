package it.unibo.robotadapter.robotVirtual;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import it.unibo.qactors.akka.QActor;
import it.unibo.robotadapter.RobotExecutor;

public class robotExecutor implements RobotExecutor {

	private Process process;
	private boolean soffrittiStarted = false;

	@Override
	public void setUp(QActor qa, String args) { // hostname,startWithSoffritti
		String[] splitted = args.split(",");
		if (splitted.length == 2) {
			try {
				qa.println("robotVirtual setUp " + splitted[0]);
				if (Boolean.parseBoolean(splitted[1])) {
					startSoffritti(qa);
				}
				qa.println("opening connection...");
				clientTcpForVirtualRobot.initClientConn(qa, splitted[0], "8999");
			} catch (Exception e) {
				e.printStackTrace();
				terminate(qa);
			}
		}
	}

	@Override
	public void terminate(QActor qa) {
		clientTcpForVirtualRobot.terminate();
		terminateSoffritti(qa);
	}

	private void printProcessOutput(Process proc, Logger log) throws IOException {
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		System.out.println("Here is the standard output of the command:\n");
		String s = null;
		while ((s = stdInput.readLine()) != null) {
			log.line(s);
		}
	}

	private void startSoffritti(QActor qa) throws Exception {
		qa.println("starting server...");
		process = Runtime.getRuntime().exec("./startServer.bat", null, new File("srcNode/Soffritti"));
		//process = Runtime.getRuntime().exec("cmd /c startServer.bat", null, new File("srcNode\\Soffritti"));
		new Thread() {
			@Override
			public void run() {
				try {
					printProcessOutput(process, line -> {
						System.out.println(line);
						if (line.contains("listening")) {
							qa.println("Soffritti started!");
							soffrittiStarted = true;
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();
		if (!process.isAlive() && process.exitValue() != 0) {
			throw new Exception("Error during execution, install node and its modules!");
		} else {
			while (!soffrittiStarted) {
				Thread.sleep(500);
			}
		}
	}

	private void terminateSoffritti(QActor qa) {
		qa.println("closing server...");
		if (process != null)
			process.destroy();
	}

	@Override
	public void doMove(QActor qa, String cmd) {
		switch (cmd) {
		case "h":
			clientTcpForVirtualRobot.sendMsg(qa, "{'type': 'alarm', 'arg': 0 }");
			break;
		case "w":
			clientTcpForVirtualRobot.sendMsg(qa, "{'type': 'moveForward', 'arg': -1 }");
			break;
		case "a":
			clientTcpForVirtualRobot.sendMsg(qa, "{'type': 'turnLeft', 'arg': 400 }");
			break;
		case "s":
			clientTcpForVirtualRobot.sendMsg(qa, "{'type': 'moveBackward', 'arg': -1 }");
			break;
		case "d":
			clientTcpForVirtualRobot.sendMsg(qa, "{'type': 'turnRight', 'arg': 400 }");
			break;
		case "blinkStart":
			qa.println("Started blinking");
			break;
		case "blinkStop":
			qa.println("Stopped blinking");
			break;
		default:
			// todo
		}
	}

	private interface Logger {
		void line(String s);
	}
}
