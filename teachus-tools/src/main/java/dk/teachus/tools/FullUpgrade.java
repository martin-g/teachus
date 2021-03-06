package dk.teachus.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import dk.teachus.tools.actions.GitCommandScmClient;
import dk.teachus.tools.actions.MainTeachUsInstance;
import dk.teachus.tools.actions.ScmClient;
import dk.teachus.tools.actions.UpgradeTeachUsInstancesAction;
import dk.teachus.tools.config.Configuration;
import dk.teachus.tools.config.GitNode;
import dk.teachus.tools.config.MainDeploymentNode;
import dk.teachus.tools.config.MavenNode;
import dk.teachus.tools.config.TomcatNode;
import dk.teachus.tools.config.WorkingDirectoryNode;

public class FullUpgrade {
	
	public static void main(String[] args) throws Exception {
		String version = getInput("Version number: ");
		
		File preferenceFile = new File(System.getProperty("user.home"), ".teachus/upgrade.xml");
		
		Configuration configuration = new Configuration();
		configuration.add(new WorkingDirectoryNode());
		configuration.add(new MavenNode());
		configuration.add(new GitNode());
		configuration.add(new TomcatNode());
		configuration.add(new MainDeploymentNode());
		configuration.initialize(preferenceFile);
		
		MavenNode maven = configuration.getNode(MavenNode.class);
		GitNode git = configuration.getNode(GitNode.class);
		
		TomcatNode tomcat = configuration.getNode(TomcatNode.class);
		
		MainDeploymentNode mainDeployment = configuration.getNode(MainDeploymentNode.class);
		
		WorkingDirectoryNode workingDirectoryNode = configuration.getNode(WorkingDirectoryNode.class);
		File workingDirectory = workingDirectoryNode.getWorkingDirectoryFile();

		ScmClient scmClient = new GitCommandScmClient(git.getRemoteGitUrl(), git.getCommitterName(), git.getCommitterEmail());
		
		/*
		 * Build up workflow
		 */
		Workflow workflow = new Workflow();
		
		UpgradeTeachUsInstancesAction upgradeTeachUsInstances = new UpgradeTeachUsInstancesAction(tomcat);
		upgradeTeachUsInstances.addTeachUsInstance(new MainTeachUsInstance(maven, workingDirectory, mainDeployment, tomcat.getHost(), version, scmClient));
		workflow.addAction(upgradeTeachUsInstances);
	
		/*
		 * Start work flow
		 */
		workflow.start();
	}
	
	private static String getInput(String label) {
		System.out.print(label);
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		String line = null;
		
		try {
			line = br.readLine();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		return line;
	}

}
