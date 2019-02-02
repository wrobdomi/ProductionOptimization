package com.engineering.json;

import java.util.List;


public class JsonResponseOne {
	
	private int [] solution;
	private int taskOffset;
	private int initialEnergy;
	private int finalEnergy;
	private List<Integer> listLoopNum;
	private List<Integer> listEnergy;
	private List<Double> listTemperature;
	private List<Double> listAcceptanceProb;
	private List<Integer> listAcceptance;
	private List<Integer> listTime;
	private List<Integer> listCost;
	private int machineOffset;
	private int machineLength;
	private List<Double> listDeltaTemperature;
	
	
	public JsonResponseOne() {}
	

	

	public JsonResponseOne(int[] solution, int taskOffset, int initialEnergy, int finalEnergy,
			List<Integer> listLoopNum, List<Integer> listEnergy, List<Double> listTemperature,
			List<Double> listAcceptanceProb, List<Integer> listAcceptance, List<Integer> listTime,
			List<Integer> listCost, int machineOffset, int machineLength, List<Double> listDeltaTemperature) {
		super();
		this.solution = solution;
		this.taskOffset = taskOffset;
		this.initialEnergy = initialEnergy;
		this.finalEnergy = finalEnergy;
		this.listLoopNum = listLoopNum;
		this.listEnergy = listEnergy;
		this.listTemperature = listTemperature;
		this.listAcceptanceProb = listAcceptanceProb;
		this.listAcceptance = listAcceptance;
		this.listTime = listTime;
		this.listCost = listCost;
		this.machineOffset = machineOffset;
		this.machineLength = machineLength;
		this.listDeltaTemperature = listDeltaTemperature;
	}

	
	public List<Double> getListDeltaTemperature() {
		return listDeltaTemperature;
	}


	public void setListDeltaTemperature(List<Double> listDeltaTemperature) {
		this.listDeltaTemperature = listDeltaTemperature;
	}


	public int[] getSolution() {
		return solution;
	}

	public void setSolution(int[] solution) {
		this.solution = solution;
	}

	public int getTaskOffset() {
		return taskOffset;
	}

	public void setTaskOffset(int taskOffset) {
		this.taskOffset = taskOffset;
	}

	public int getInitialEnergy() {
		return initialEnergy;
	}

	public void setInitialEnergy(int initialEnergy) {
		this.initialEnergy = initialEnergy;
	}

	public int getFinalEnergy() {
		return finalEnergy;
	}

	public void setFinalEnergy(int finalEnergy) {
		this.finalEnergy = finalEnergy;
	}

	public List<Integer> getListLoopNum() {
		return listLoopNum;
	}

	public void setListLoopNum(List<Integer> listLoopNum) {
		this.listLoopNum = listLoopNum;
	}

	public List<Integer> getListEnergy() {
		return listEnergy;
	}

	public void setListEnergy(List<Integer> listEnergy) {
		this.listEnergy = listEnergy;
	}

	public List<Double> getListTemperature() {
		return listTemperature;
	}

	public void setListTemperature(List<Double> listTemperature) {
		this.listTemperature = listTemperature;
	}

	public List<Double> getListAcceptanceProb() {
		return listAcceptanceProb;
	}

	public void setListAcceptanceProb(List<Double> listAcceptanceProb) {
		this.listAcceptanceProb = listAcceptanceProb;
	}

	public List<Integer> getListAcceptance() {
		return listAcceptance;
	}

	public void setListAcceptance(List<Integer> listAcceptance) {
		this.listAcceptance = listAcceptance;
	}

	public List<Integer> getListTime() {
		return listTime;
	}

	public void setListTime(List<Integer> listTime) {
		this.listTime = listTime;
	}

	public List<Integer> getListCost() {
		return listCost;
	}

	public void setListCost(List<Integer> listCost) {
		this.listCost = listCost;
	}

	public int getMachineOffset() {
		return machineOffset;
	}

	public void setMachineOffset(int machineOffset) {
		this.machineOffset = machineOffset;
	}

	public int getMachineLength() {
		return machineLength;
	}

	public void setMachineLength(int machineLength) {
		this.machineLength = machineLength;
	}

	
	
}
