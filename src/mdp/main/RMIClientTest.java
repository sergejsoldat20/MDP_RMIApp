package mdp.main;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;

import etfbl.mdp.rmi.server.RMIReportInterface;

public class RMIClientTest {
	public static void main(String args[]) {
		System.setProperty("java.security.policy", "client_policyfile.txt");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		
		try {
			Registry registry = LocateRegistry.getRegistry(1099);
			RMIReportInterface report = (RMIReportInterface) registry.lookup("RMIReport");
			
			//report.uploadReport("Sergej", "A", 0, LocalTime.now(), "Sergej - A" , "alooooo".getBytes());
			for(String s : report.uploadedReports()) {
				System.out.println(s);
			}
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}

	}
}
