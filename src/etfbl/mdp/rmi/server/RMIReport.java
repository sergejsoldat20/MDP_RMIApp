package etfbl.mdp.rmi.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalTime;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import etfbl.mdp.rmi.model.Report;

public class RMIReport implements RMIReportInterface{
	 
	public static ArrayList<String> uploadedReportsList = new ArrayList<>();
	
	public RMIReport() {
		
	}
	
	@Override
	public void uploadReport(String user, String station, long fileLength, LocalTime time, String filename, byte[] data) throws RemoteException {
		Report report = new Report(user, station, fileLength, time, filename);
		File reportsFile = new File("reports" + File.separator + filename);
		//dodaj svaki naziv fajla koji se uploaduje
		uploadedReportsList.add(filename);
		if(!reportsFile.exists()) {
			try {
				reportsFile.createNewFile();
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		System.out.println("upload report method");
		//serijalizuj u json i sacuvaj zapise
		try {
			File file = new File("users info" + File.separator + filename + ".out");
			FileWriter fileWriter = new FileWriter(file);
			String json = new Gson().toJson(report);
			fileWriter.write(json);
			fileWriter.flush();
			fileWriter.close();
			} catch(Exception ex) {
			ex.printStackTrace();
		}
		//upisi izvjestaje
		try {
			FileOutputStream os = new FileOutputStream(reportsFile);
			os.write(data);
			os.flush();
			os.close();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public byte[] downloadReport(String filename) throws RemoteException {
		try {
			FileInputStream fis = new FileInputStream(new File("reports" + File.separator + filename));
			byte[] data = new byte[(int)fis.getChannel().size()];
			fis.read(data);
			return data;
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public ArrayList<String> getReports() throws RemoteException{
		ArrayList<String> result = new ArrayList<>();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		File file = new File("C:\\Users\\Korisnik\\eclipse-workspace\\AZSMDP-RMI\\users info");
		File[] allFiles = file.listFiles();
		if(allFiles != null) {
			for(File f : allFiles) {
				try {
					System.out.println("aaaaa");
					BufferedReader in = new BufferedReader(new FileReader(f));
					Report report = gson.fromJson(in.readLine(), Report.class);
					System.out.println(report);
					result.add(report.toString());
				} catch(Exception ex) {
					ex.printStackTrace();
			}
			} 
		}
		return result;
	}
	
	//vrati listu naziva arhiviranih fajlova
	@Override
	public ArrayList<String> uploadedReports() throws RemoteException {
		return uploadedReportsList;
	}
	
	
	public static void main(String args[]) {
		System.setProperty("java.security.policy","server_policyfile.txt");
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			RMIReport server = new RMIReport();
			RMIReportInterface stub = (RMIReportInterface) UnicastRemoteObject.exportObject(server, 0);
			Registry registry = LocateRegistry.createRegistry(1099);
			registry.rebind("RMIReport", stub);
			System.out.println("radi server");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
		
		
}



