package com.engineering.twotasks;

public class DoubleTask {

	private int []  firstPartMachines;
	private int []  secondPartMachines;
	private int twoMachines;
	
	public DoubleTask(int[] firstPartMachines, int[] secondPartMachines, int twoMachines) {
		super();
		this.firstPartMachines = firstPartMachines;
		this.secondPartMachines = secondPartMachines;
		this.twoMachines = twoMachines;
	}

	public int[] getFirstPartMachines() {
		return firstPartMachines;
	}

	public void setFirstPartMachines(int[] firstPartMachines) {
		this.firstPartMachines = firstPartMachines;
	}

	public int[] getSecondPartMachines() {
		return secondPartMachines;
	}

	public void setSecondPartMachines(int[] secondPartMachines) {
		this.secondPartMachines = secondPartMachines;
	}

	public int getTwoMachines() {
		return twoMachines;
	}

	public void setTwoMachines(int twoMachines) {
		this.twoMachines = twoMachines;
	}
	
	
	
}
