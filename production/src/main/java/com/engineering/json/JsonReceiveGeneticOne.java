package com.engineering.json;

public class JsonReceiveGeneticOne {
	
	private int populationSize;
	private String parentSelection;
	private int tournamentMembers;
	private String crossoverSelection;
	private double crossoverProb;
	private String mutationSelection;
	private int mutationSize;
	private double mutationProb;
	private String recreatingSelection;
	private double cWeight;
	private double tWeight;
	private int iterNum;
	
	public JsonReceiveGeneticOne() {
	}
	
	public JsonReceiveGeneticOne(int populationSize, String parentSelection, int tournamentMembers,
			String crossoverSelection, double crossoverProb, String mutationSelection, int mutationSize,
			double mutationProb, String recreatingSelection, double cWeight, double tWeight, int iterNum) {
		super();
		this.populationSize = populationSize;
		this.parentSelection = parentSelection;
		this.tournamentMembers = tournamentMembers;
		this.crossoverSelection = crossoverSelection;
		this.crossoverProb = crossoverProb;
		this.mutationSelection = mutationSelection;
		this.mutationSize = mutationSize;
		this.mutationProb = mutationProb;
		this.recreatingSelection = recreatingSelection;
		this.cWeight = cWeight;
		this.tWeight = tWeight;
		this.iterNum = iterNum;
	}


	public int getIterNum() {
		return iterNum;
	}

	public void setIterNum(int iterNum) {
		this.iterNum = iterNum;
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


	public int getPopulationSize() {
		return populationSize;
	}

	public void setPopulationSize(int populationSize) {
		this.populationSize = populationSize;
	}

	public String getParentSelection() {
		return parentSelection;
	}

	public void setParentSelection(String parentSelection) {
		this.parentSelection = parentSelection;
	}

	public int getTournamentMembers() {
		return tournamentMembers;
	}

	public void setTournamentMembers(int tournamentMembers) {
		this.tournamentMembers = tournamentMembers;
	}

	public String getCrossoverSelection() {
		return crossoverSelection;
	}

	public void setCrossoverSelection(String crossoverSelection) {
		this.crossoverSelection = crossoverSelection;
	}

	public double getCrossoverProb() {
		return crossoverProb;
	}

	public void setCrossoverProb(double crossoverProb) {
		this.crossoverProb = crossoverProb;
	}

	public String getMutationSelection() {
		return mutationSelection;
	}

	public void setMutationSelection(String mutationSelection) {
		this.mutationSelection = mutationSelection;
	}

	public int getMutationSize() {
		return mutationSize;
	}

	public void setMutationSize(int mutationSize) {
		this.mutationSize = mutationSize;
	}

	public double getMutationProb() {
		return mutationProb;
	}

	public void setMutationProb(double mutationProb) {
		this.mutationProb = mutationProb;
	}

	public String getRecreatingSelection() {
		return recreatingSelection;
	}

	public void setRecreatingSelection(String recreatingSelection) {
		this.recreatingSelection = recreatingSelection;
	}

		
}
