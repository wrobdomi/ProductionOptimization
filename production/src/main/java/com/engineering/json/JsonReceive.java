package com.engineering.json;

public class JsonReceive {

	private int temperature;
	private String coolingWay;
	private double alpha;
	private int iterations;
	private int assignments;
	private double cWeight;
	private double tWeight;
	private boolean costConEnabled;
	private boolean timeConEnabled;
	private int costCon;
	private int timeCon;
	
	public JsonReceive() {
	
	}


	public JsonReceive(int temperature, String coolingWay, double alpha, int iterations, int assignments,
			double cWeight, double tWeight, boolean costConEnabled, boolean timeConEnabled, int costCon, int timeCon) {
		super();
		this.temperature = temperature;
		this.coolingWay = coolingWay;
		this.alpha = alpha;
		this.iterations = iterations;
		this.assignments = assignments;
		this.cWeight = cWeight;
		this.tWeight = tWeight;
		this.costConEnabled = costConEnabled;
		this.timeConEnabled = timeConEnabled;
		this.costCon = costCon;
		this.timeCon = timeCon;
	}


	public int getCostCon() {
		return costCon;
	}

	public void setCostCon(int costCon) {
		this.costCon = costCon;
	}

	public int getTimeCon() {
		return timeCon;
	}

	public void setTimeCon(int timeCon) {
		this.timeCon = timeCon;
	}

	public boolean isCostConEnabled() {
		return costConEnabled;
	}


	public void setCostConEnabled(boolean costConEnabled) {
		this.costConEnabled = costConEnabled;
	}

	public boolean isTimeConEnabled() {
		return timeConEnabled;
	}

	public void setTimeConEnabled(boolean timeConEnabled) {
		this.timeConEnabled = timeConEnabled;
	}

	public double getcWeight() {
		return cWeight;
	}

	public void setcWeight(double cWeight) {
		this.cWeight = cWeight;
	}

	public double gettWeight() {
		return tWeight;
	}

	public void settWeight(double tWeight) {
		this.tWeight = tWeight;
	}

	public int getAssignments() {
		return assignments;
	}

	public void setAssignments(int assignments) {
		this.assignments = assignments;
	}

	public int getIterations() {
		return iterations;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public int getTemperature() {
		return temperature;
	}

	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}

	public String getCoolingWay() {
		return coolingWay;
	}

	public void setCoolingWay(String coolingWay) {
		this.coolingWay = coolingWay;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	
}
