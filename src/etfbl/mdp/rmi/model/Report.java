package etfbl.mdp.rmi.model;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Objects;

public class Report implements Serializable {
	
	private String user;
	private String station;
	private long fileLength;
	private LocalTime time;
	private String filename;
	
	public Report() {
		
	}

	public Report(String user, String station, long fileLength, LocalTime time, String filename) {
		super();
		this.user = user;
		this.station = station;
		this.fileLength = fileLength;
		this.time = time;
		this.filename = filename;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public long getFileLength() {
		return fileLength;
	}

	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Override
	public String toString() {
		return "Report [user=" + user + ", station=" + station + ", fileLength=" + fileLength + ", time=" + time
				+ ", filename=" + filename + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(fileLength, filename, station, time, user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Report other = (Report) obj;
		return fileLength == other.fileLength && Objects.equals(filename, other.filename)
				&& Objects.equals(station, other.station) && Objects.equals(time, other.time)
				&& Objects.equals(user, other.user);
	}
}
