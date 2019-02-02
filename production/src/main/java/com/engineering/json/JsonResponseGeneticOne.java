package com.engineering.json;

import java.util.ArrayList;
import java.util.List;

public class JsonResponseGeneticOne {

	private int [] solution;
	private int taskOffset;
	private int initialFitness;
	private int finalFitness;
	private List<Integer> listPopulationFitness;
	private List<Integer> listBestFromPopulationFitness;
	private List<Integer> listWorstFromPopulationFitness;
	private List<Integer> listMeanFromPopulationFitness;
	private List<Integer> listIterationNum;
	private List<Integer> listCrossoverApplied;
	private List<Double> listCrossoverProb;
	private List<Integer> listMutationApplied;
	private List<Double> listMutationProb;
	private List<Integer> listCost;
	private List<Integer> listTime;
	
	
	public JsonResponseGeneticOne() {
	}


	public JsonResponseGeneticOne(int[] solution, int taskOffset, int initialFitness, int finalFitness,
			List<Integer> listPopulationFitness, List<Integer> listBestFromPopulationFitness,
			List<Integer> listWorstFromPopulationFitness, List<Integer> listMeanFromPopulationFitness,
			List<Integer> listIterationNum, List<Integer> listCrossoverApplied, List<Double> listCrossoverProb,
			List<Integer> listMutationApplied, List<Double> listMutationProb, List<Integer> listCost,
			List<Integer> listTime) {
		super();
		this.solution = solution;
		this.taskOffset = taskOffset;
		this.initialFitness = initialFitness;
		this.finalFitness = finalFitness;
		this.listPopulationFitness = listPopulationFitness;
		this.listBestFromPopulationFitness = listBestFromPopulationFitness;
		this.listWorstFromPopulationFitness = listWorstFromPopulationFitness;
		this.listMeanFromPopulationFitness = listMeanFromPopulationFitness;
		this.listIterationNum = listIterationNum;
		this.listCrossoverApplied = listCrossoverApplied;
		this.listCrossoverProb = listCrossoverProb;
		this.listMutationApplied = listMutationApplied;
		this.listMutationProb = listMutationProb;
		this.listCost = listCost;
		this.listTime = listTime;
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


	public int getInitialFitness() {
		return initialFitness;
	}


	public void setInitialFitness(int initialFitness) {
		this.initialFitness = initialFitness;
	}


	public int getFinalFitness() {
		return finalFitness;
	}


	public void setFinalFitness(int finalFitness) {
		this.finalFitness = finalFitness;
	}


	public List<Integer> getListPopulationFitness() {
		return listPopulationFitness;
	}


	public void setListPopulationFitness(List<Integer> listPopulationFitness) {
		this.listPopulationFitness = listPopulationFitness;
	}


	public List<Integer> getListBestFromPopulationFitness() {
		return listBestFromPopulationFitness;
	}


	public void setListBestFromPopulationFitness(List<Integer> listBestFromPopulationFitness) {
		this.listBestFromPopulationFitness = listBestFromPopulationFitness;
	}


	public List<Integer> getListWorstFromPopulationFitness() {
		return listWorstFromPopulationFitness;
	}


	public void setListWorstFromPopulationFitness(List<Integer> listWorstFromPopulationFitness) {
		this.listWorstFromPopulationFitness = listWorstFromPopulationFitness;
	}


	public List<Integer> getListMeanFromPopulationFitness() {
		return listMeanFromPopulationFitness;
	}


	public void setListMeanFromPopulationFitness(List<Integer> listMeanFromPopulationFitness) {
		this.listMeanFromPopulationFitness = listMeanFromPopulationFitness;
	}


	public List<Integer> getListIterationNum() {
		return listIterationNum;
	}


	public void setListIterationNum(List<Integer> listIterationNum) {
		this.listIterationNum = listIterationNum;
	}


	public List<Integer> getListCrossoverApplied() {
		return listCrossoverApplied;
	}


	public void setListCrossoverApplied(List<Integer> listCrossoverApplied) {
		this.listCrossoverApplied = listCrossoverApplied;
	}


	public List<Double> getListCrossoverProb() {
		return listCrossoverProb;
	}


	public void setListCrossoverProb(List<Double> listCrossoverProb) {
		this.listCrossoverProb = listCrossoverProb;
	}


	public List<Integer> getListMutationApplied() {
		return listMutationApplied;
	}


	public void setListMutationApplied(List<Integer> listMutationApplied) {
		this.listMutationApplied = listMutationApplied;
	}


	public List<Double> getListMutationProb() {
		return listMutationProb;
	}


	public void setListMutationProb(List<Double> listMutationProb) {
		this.listMutationProb = listMutationProb;
	}


	public List<Integer> getListCost() {
		return listCost;
	}


	public void setListCost(List<Integer> listCost) {
		this.listCost = listCost;
	}


	public List<Integer> getListTime() {
		return listTime;
	}


	public void setListTime(List<Integer> listTime) {
		this.listTime = listTime;
	}

	
	
	
}



