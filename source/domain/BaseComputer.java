package domain;

import java.io.Serializable;

public class BaseComputer implements Serializable {

	static final long serialVersionUID = 1L;

	private String cpuType;
	private int ramSize;
	private int diskSize;

	public static BaseComputer getInstance(String cpuType, int ramSize, int diskSize) {
		return new BaseComputer (cpuType, ramSize, diskSize);
	}

	public BaseComputer(String cpuType, int ramSize, int diskSize) {
 		this.cpuType = cpuType;
		this.ramSize = ramSize;
		this.diskSize = diskSize;
	}

	public String getCpuType() {
		return cpuType;
	}

	public void setCpuType(String cpuType) {
		this.cpuType = cpuType;
	}

	public int getRamSize() {
		return ramSize;
	}

	public void setRamSize(int ramSize) {
		this.ramSize = ramSize;
	}

	public int getDiskSize() {
		return diskSize;
	}

	public void setDiskSize(int diskSize) {
		this.diskSize = diskSize;
	}
	
	@Override
	public String toString( ) {
		return cpuType;
		
	}

}

