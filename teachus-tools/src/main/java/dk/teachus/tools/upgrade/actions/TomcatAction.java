package dk.teachus.tools.upgrade.actions;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ch.ethz.ssh2.Session;
import dk.teachus.tools.upgrade.config.TomcatNode;

public class TomcatAction implements Action {
	private static final Log log = LogFactory.getLog(TomcatAction.class);

	public static enum ProcessAction {
		START, STOP
	}

	private TomcatNode tomcat;

	private ProcessAction processAction;

	public TomcatAction(TomcatNode tomcat, ProcessAction processAction) {
		this.tomcat = tomcat;
		this.processAction = processAction;
	}

	public void execute() throws Exception {
		if (processAction == ProcessAction.START) {
			log.info("Starting tomcat on: "+tomcat.getHost().getHost());
		} else {
			log.info("Stopping tomcat on: "+tomcat.getHost().getHost());			
		}
		
		StringBuilder command = new StringBuilder();
		command.append("export JAVA_HOME=/usr/lib/jvm/java-1.5.0-sun;");
		command.append(tomcat.getHome());
		command.append("/bin/");
		switch (processAction) {
		case START:
			command.append("startup.sh");
			break;
		case STOP:
			command.append("shutdown.sh");
			break;
		}

		SshAction ssh = new SshAction(tomcat.getHost());
		ssh.execute();

		Session session = ssh.openSession();
		session.execCommand(command.toString());
		session.close();
	}

}