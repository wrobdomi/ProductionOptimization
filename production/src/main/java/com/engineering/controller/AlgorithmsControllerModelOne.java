package com.engineering.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.engineering.entity.Machine;
import com.engineering.entity.Task;
import com.engineering.json.JsonReceive;
import com.engineering.json.JsonReceiveGeneticOne;
import com.engineering.json.JsonResponseGeneticOne;
import com.engineering.json.JsonResponseOne;
import com.engineering.servicemodelone.AnnealingOneService;
import com.engineering.servicemodelone.GeneticOneService;
import com.engineering.service.MachineService;
import com.engineering.servicemodelone.ModelOneService;
import com.engineering.service.TaskService;

@Controller
@RequestMapping("/annealing")
public class AlgorithmsControllerModelOne {
	
	// Tasks DAO
	@Autowired
	private TaskService taskService;
	
	// Machines DAO
	@Autowired
	private MachineService machineService;
	
	// Genetic algorithm service
	@Autowired
	private GeneticOneService geneticOneService;
	
	// Simulated annealing service
	@Autowired
	private AnnealingOneService annealingOneService;
	
	// General Model One Service
	@Autowired
	private ModelOneService modelOneService;
	
	private int[][] timeMatrix;
	
	private int[][] costMatrix;
	
	
	@GetMapping("/display")
	public String mainAnnealing(Model theModel) {
		
		
		List<Task> allTasks = taskService.getAllTasks();
		List<Machine> allMachines = machineService.getAllMachines();
	
		
		if( !allTasks.isEmpty() && !allMachines.isEmpty() ) {
			
			costMatrix = new int [allMachines.size()][allTasks.size()+1];
			timeMatrix = new int [allMachines.size()][allTasks.size()+1];
			
			
			// Get id of first task and machine
			int offsetTask = allTasks.get(0).getId();
			int offsetMachine = allMachines.get(0).getId();
			
			
			for(Task theTask : allTasks) {
				for(Machine theMachine : allMachines ) {
					List<Machine> tasksMachines = theTask.getMachines();
					boolean choice = tasksMachines.contains(theMachine);
				
					if(choice) {
						costMatrix[theMachine.getId()-offsetMachine][theTask.getId()-offsetTask+1] =
								theTask.getDetailsNumber() * theMachine.getDetailCost();
						
						timeMatrix[theMachine.getId()-offsetMachine][theTask.getId()-offsetTask+1] =
								theTask.getDetailsNumber() * theMachine.getDetailTime();
					}
					else {
						costMatrix[theMachine.getId()-offsetMachine][theTask.getId()-offsetTask+1] = 0;
						timeMatrix[theMachine.getId()-offsetMachine][theTask.getId()-offsetTask+1] = 0;
					}	
				}
			}
			
			for(int i = 0 ; i < allMachines.size() ; i++) {
				costMatrix[i][0] = allMachines.get(i).getId();
				timeMatrix[i][0] = allMachines.get(i).getId();
			}
			
			
			theModel.addAttribute("tasks", allTasks);
			theModel.addAttribute("machines", allMachines);
			theModel.addAttribute("costMatrix", costMatrix);
			theModel.addAttribute("timeMatrix", timeMatrix);
			theModel.addAttribute("rows", costMatrix.length-1);
			theModel.addAttribute("cols", costMatrix[0].length-1);
		
		}
		
		return "main-annealing";
		
	}
	
	
	
	@PostMapping("/solve")  @ResponseBody
    JsonResponseOne simulatedAnnealing(@RequestBody JsonReceive jsonReceive, Model theModel) {	
		
		List<Task> allTasks = taskService.getAllTasks();
		List<Machine> allMachines = machineService.getAllMachines();
		
		int tasksOffset = allTasks.get(0).getId();
		int machineOffset = allMachines.get(0).getId();
		int machineLength = allMachines.size();
		
//		System.out.println("## ## ## ## ## ## This is from JSON ## ## ## ## ## ##");
//		System.out.println(jsonReceive.getAlpha());
//		System.out.println(jsonReceive.getCoolingWay());
//		System.out.println(jsonReceive.getTemperature());
//		System.out.println(jsonReceive.getIterations());
//		System.out.println(jsonReceive.getAssignments());
//		System.out.println(jsonReceive.getcWeight());
//		System.out.println(jsonReceive.gettWeight());
//		System.out.println(jsonReceive.isCostConEnabled());
//		System.out.println(jsonReceive.isTimeConEnabled());
//		System.out.println(jsonReceive.getCostCon());
//		System.out.println(jsonReceive.getTimeCon());
//		System.out.println("## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ##");
		
		// Lists to be returned
		List<Integer> listLoopNum = new ArrayList<Integer>();
		List<Integer> listBestEnergy = new ArrayList<Integer>();
		List<Double> listTemperature = new ArrayList<Double>();
		List<Double> listAcceptanceProb = new ArrayList<Double>();
		List<Integer> listAcceptance = new ArrayList<Integer>();
		List<Integer> listCost = new ArrayList<Integer>();
		List<Integer> listTime = new ArrayList<Integer>();
		List<Double> listDeltaTemperature = new ArrayList<Double>();
		
		// from JSON
		double temperature = jsonReceive.getTemperature(); // set initial temperature
		double initialTemperature = jsonReceive.getTemperature(); // get initial temperature
		double alpha = jsonReceive.getAlpha(); // used while changing temperature 
		boolean cooling = jsonReceive.getCoolingWay().contains("-");
		int maxIter = jsonReceive.getIterations(); // max number of iterations
		int changes = jsonReceive.getAssignments(); // changes in solution
		double cWeight = jsonReceive.getcWeight();
		double tWeight = jsonReceive.gettWeight();
		boolean costConEnabled = jsonReceive.isCostConEnabled();
		boolean timeConEnabled = jsonReceive.isTimeConEnabled();
		int costCon = jsonReceive.getCostCon();
		int timeCon = jsonReceive.getTimeCon();
		
		int [] initialSolution = modelOneService.getRandomSolution(allTasks);
		int [] bestSolution = initialSolution; // first best solution
		
		int [] bestArr = annealingOneService.countEnergyOne(allTasks, allMachines, costMatrix, 
															timeMatrix, bestSolution, cWeight, tWeight);
		int bestEnergy = bestArr[0]; // initial energy
		int initialEnergy = bestEnergy;
		
		
		// System.out.println("Initial energy is " + bestEnergy );
		
		listBestEnergy.add(bestEnergy);
		listTemperature.add(temperature);
		listDeltaTemperature.add(0.0);
		listLoopNum.add(0);
		listAcceptanceProb.add(0.0);
		listAcceptance.add(0);
		listCost.add(bestArr[1]);
		listTime.add(bestArr[2]);
			
		// System.out.println("Initial energy is " + bestEnergy );
		
		for(int i = 0; i < maxIter; i++) {
			
			double oldTemperature = temperature;
			
			if(!cooling) {
				temperature = alpha * temperature;
			}
			else { 
				// temperature = initialTemperature / ( 1 + Math.log10( i + 1 ) );
				temperature = temperature - alpha;
			}
			
			double diffTemperature = oldTemperature - temperature;
			
			listLoopNum.add(i+1);
			listTemperature.add(temperature);
			listDeltaTemperature.add(diffTemperature);
			
					
			int [] newSolution = annealingOneService.changeSolution(bestSolution, changes, allTasks);
			int [] newArr = annealingOneService.countEnergyOne(allTasks, allMachines, costMatrix, 
					timeMatrix, newSolution, cWeight, tWeight);
			int newEnergy = newArr[0];
			
				
			// Applying time constraints -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- // 
			if(newArr[2] > timeCon && timeConEnabled && !costConEnabled ) {
				do {
					newSolution = annealingOneService.changeSolution(bestSolution, changes, allTasks);
					// newArr = this.countEnergyOne(newSolution, cWeight, tWeight);
					newArr = annealingOneService.countEnergyOne(allTasks, allMachines, costMatrix, 
							timeMatrix, newSolution, cWeight, tWeight);
					newEnergy = newArr[0];
				}while(newArr[2] > timeCon);
			}
			
			// Applying cost constraints
			if(newArr[1] > costCon && costConEnabled && !timeConEnabled ) {
				do {
					newSolution = annealingOneService.changeSolution(bestSolution, changes, allTasks);
					newArr = annealingOneService.countEnergyOne(allTasks, allMachines, costMatrix, 
							timeMatrix, newSolution, cWeight, tWeight);
					newEnergy = newArr[0];
				}while(newArr[1] > costCon);
			}
			
			// Applying time and cost constraints
			if((newArr[1] > costCon || newArr[2] > timeCon) && costConEnabled && timeConEnabled ) {
				do {
					newSolution = annealingOneService.changeSolution(bestSolution, changes, allTasks);
					newArr = annealingOneService.countEnergyOne(allTasks, allMachines, costMatrix, 
							timeMatrix, newSolution, cWeight, tWeight);
					newEnergy = newArr[0];
				}while( newArr[1] > costCon || newArr[2] > timeCon );
			}
			// -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- //
			
			
			
			int diff = Math.abs(newEnergy - bestEnergy);
			diff = -1 * diff;
			
		
			double expUp = diff/temperature;
			double probFun = Math.exp(expUp);
		
			
			System.out.println("======== probFun equals:  " + probFun);
			System.out.println("======= temperature equals:  " + temperature);
			System.out.println("==== expUp equals:  " + expUp);
			System.out.println(" ");
			
			if(newEnergy <= bestEnergy) {
				bestSolution = newSolution;
				bestEnergy = newEnergy;
				bestArr = newArr;
				
				listBestEnergy.add(bestEnergy);
				listAcceptance.add(0);
				listAcceptanceProb.add(0.0);
				listCost.add(bestArr[1]);
				listTime.add(bestArr[2]);
				
			}
			else if( probFun > Math.random() ) {
				bestSolution = newSolution;
				bestEnergy = newEnergy;
				bestArr = newArr;
				
				System.out.println(" ^^ ^^ ^^ Worse solution accepted ^^ ^^ ^^ ");
				
				listBestEnergy.add(bestEnergy);
				listAcceptance.add(1);
				listAcceptanceProb.add(probFun);
				listCost.add(bestArr[1]);
				listTime.add(bestArr[2]);
			}
			else {
				
				listBestEnergy.add(bestEnergy);
				listAcceptance.add(0);
				listAcceptanceProb.add(0.0);
				listCost.add(bestArr[1]);
				listTime.add(bestArr[2]);
			}
					
		}
		
	
		// System.out.println("End energy is  " + bestEnergy );
		
		int machineStatistics [] = new int[allMachines.size()];
		
		for(int i  = 0 ; i < allTasks.size() ; i++) {
			machineStatistics[bestSolution[i]-1]++;
		}
		
		// System.out.println("This is my statistics array: ");
		// this.printMyArray(machineStatistics);
		

		JsonResponseOne jsonResponse = new JsonResponseOne(bestSolution, 
										tasksOffset, initialEnergy, bestEnergy, 
										listLoopNum, listBestEnergy, listTemperature, 
										listAcceptanceProb, listAcceptance, listTime, listCost,
										machineOffset, machineLength, listDeltaTemperature
										);
		
		return jsonResponse;
		
	}
	
	
	@PostMapping("/solvegenetic")  @ResponseBody
    JsonResponseGeneticOne geneticAlgorithm( @RequestBody JsonReceiveGeneticOne jsonReceiveGeneticOne ) {
				
		List<Task> allTasks = taskService.getAllTasks();
		List<Machine> allMachines = machineService.getAllMachines();
		
		// -- TODO ----------------------------------- //
		// - To be returned using JSON ---------- //
		List<Integer> listPopulationFitness = new ArrayList<Integer>();
		List<Integer> listBestFromPopulationFitness = new ArrayList<Integer>();
		List<Integer> listWorstFromPopulationFitness = new ArrayList<Integer>();
		List<Integer> listMeanFromPopulationFitness = new ArrayList<Integer>();
		
		List<Integer> listCrossoverApplied = new ArrayList<Integer>();
		List<Double> listCrossoverProb = new ArrayList<Double>();
		
		List<Integer> listMutationApplied = new ArrayList<Integer>();
		List<Double> listMutationProb = new ArrayList<Double>();
		
		List<Integer> listIterationNum = new ArrayList<Integer>();
		
		List<Integer> listCost = new ArrayList<Integer>();
		List<Integer> listTime = new ArrayList<Integer>();
	
		
		int initialFitness = 0;
		int finalFitness = 0;
		int taskOffset = allTasks.get(0).getId();; 
		int [] solution = new int[allTasks.size()];
		// --     ------------------------------------ //
		
		
		// -- from JSON ------------------------------ //
		double cWeight = jsonReceiveGeneticOne.getcWeight();
		double tWeight = jsonReceiveGeneticOne.gettWeight();
		int iterNum = jsonReceiveGeneticOne.getIterNum();
		
		
	
		// -- TODO ----------------------------------------- //
		// 1. Generate random population
		int populationSize = jsonReceiveGeneticOne.getPopulationSize(); // ** from JSON
		int [] initialAge = new int [populationSize]; 
		int [][] population = new int[populationSize][allTasks.size()];
		for(int i = 0; i < populationSize; i++) {
			// population[i] = this.getRandomSolution();
			population[i] = modelOneService.getRandomSolution(allTasks);
			initialAge[i] = 0;
		}
		// -- DONE ----------------------------------------- //
		
			
		
		// -- TODO ----------------------------------------- //
		// 2. Calculate fitness function for the whole population
		// and for every individual and choose best from population
		int [] populationFitness = new int[populationSize];
		int [] populationCost = new int[populationSize];
		int [] populationTime = new int[populationSize];
		int overallFitness = 0;
		int [] tempArr = new int[3];  
		for(int i = 0; i < populationSize ; i++) {
			tempArr = geneticOneService.countEnergyOneGenetic(population[i], cWeight, tWeight, 
					allTasks, allMachines, timeMatrix, costMatrix);
			overallFitness += tempArr[0];
			populationFitness[i] = tempArr[0]; 
			populationCost[i] = tempArr[1];
			populationTime[i] = tempArr[2];
		}
		int bestFit = populationFitness[0];
		int worstFit = populationFitness[0];
		int meanFit =  populationFitness[0];
		int bestCost = 0;
		int bestTime = 0;
		for(int i = 1 ; i < populationSize ; i++) {
			if(populationFitness[i] < bestFit) {
				bestFit = populationFitness[i];
				bestCost = populationCost[i];
				bestTime = populationTime[i];
			}
			if(populationFitness[i] > worstFit) {
				worstFit = populationFitness[i];
			}
			meanFit += populationFitness[i];
		}
		meanFit = meanFit / populationSize;
		// -- DONE ----------------------------------------- //
		
		
		listPopulationFitness.add(overallFitness);
		listIterationNum.add(0);
		listBestFromPopulationFitness.add(bestFit);
		listWorstFromPopulationFitness.add(worstFit);
		listMeanFromPopulationFitness.add(meanFit);
		initialFitness = bestFit;
		listCrossoverApplied.add(0);
		listMutationApplied.add(0);
		listCrossoverProb.add(0.0);
		listMutationProb.add(0.0);
		listCost.add(bestCost);
		listTime.add(bestTime);
		
//		System.out.println("**** **** **** **** **** **** **** INITIALIZING **** **** **** **** ");
//		System.out.println(" ");
//		System.out.println("The initial population is");
//		this.printMyTwoArray(population);
//		System.out.println("The overall fitness for the population is");
//		System.out.println(overallFitness);
//		System.out.println("Fitness per genotype: ");
//		this.printMyArray(populationFitness);
//		System.out.println("The best solution is");
//		System.out.println(bestFit);
		
		Random random = new Random(); 
		
		
		for(int mainLoop = 0 ; mainLoop <  iterNum ; mainLoop++) {
			
		
			// -- TODO ----------------------------------------- //
			// 3. Choose parents from current population
			// - Tournament selection implemented
			// System.out.println(" \n\n **** **** **** **** **** **** Parent Selection **** **** **** **** **** **** ****");
			String parentSelection = jsonReceiveGeneticOne.getParentSelection(); // ** from JSON
			int tournamentMembers = jsonReceiveGeneticOne.getTournamentMembers(); // ** from JSON
			int [][] parents = new int[2][allTasks.size()];
		
			
			if(parentSelection.equals("Tournament selection")) {
				parents = geneticOneService.findParents(tournamentMembers, populationSize, population, 
						allTasks, allMachines, this.timeMatrix, this.costMatrix, cWeight, tWeight);
			}
			
			int [] parentOne = parents[0];
			int [] parentTwo = parents[1];
		
			// -- DONE ----------------------------------------- // 
		
			
			// -- TODO ----------------------------------------- //
			// 4. Crossover
			// !! ADD PROBABILITY Pc
			// - Uniform crossover implemented
			// System.out.println("\n\n **** **** **** **** **** **** Crossover **** **** **** **** **** **** ****");
			int [][] children = new int [2][parentOne.length];
			
			String crossoverSelection = jsonReceiveGeneticOne.getCrossoverSelection(); // ** from JSON
			
			double crossoverProb = jsonReceiveGeneticOne.getCrossoverProb(); // ** from JSON 
			int crossoverProb100 = (int) (crossoverProb * 100);
			int compareCrossoverProb = random.nextInt(100) + 1;  // Get integer between >= 1 and < 101
			double crossListProb = ( (double) compareCrossoverProb / 100.0 );
			
			if(compareCrossoverProb <= crossoverProb100) {
				if( crossoverSelection.equals("Uniform") ) {
					listCrossoverApplied.add(1);
					listCrossoverProb.add(crossListProb);
					children = geneticOneService.findChildrenCrossover(parentOne, parentTwo);
				}
				if( crossoverSelection.equals("Multipoint") ) {
					listCrossoverApplied.add(1);
					listCrossoverProb.add(crossListProb);
					children = geneticOneService.findChildrenCrossoverMultipoint(parentOne, parentTwo);
				}
			}
			else {
				listCrossoverApplied.add(0);
				listCrossoverProb.add(crossListProb);
				children = parents;
			}
			// -- DONE  ----------------------------------------- //
					
			
			// -- TODO ------------------------------------------ //
			// 5. Mutation
			// !! ADD PROBABILITY Pm
			// - Bit flip mutation implemented
			// System.out.println("\n\n **** **** **** **** **** **** Mutation **** **** **** **** **** **** ****");
			String mutationSelection = jsonReceiveGeneticOne.getMutationSelection(); // ** from JSON
			int genNum = jsonReceiveGeneticOne.getMutationSize(); // ** from JSON
			
			
			double mutationProb = jsonReceiveGeneticOne.getMutationProb(); // ** from JSON
			int mutationProb100 = (int) ( mutationProb * 100 );
			int compareMutationProb = random.nextInt(100) + 1;  // Get integer between >= 1 and < 101
			double mutListProb = ( (double) compareMutationProb / 100.0 );
			
			int [][] mutatedChildren = new int [2][parentOne.length];
			
			if(compareMutationProb <= mutationProb100) {
				if( mutationSelection.equals("Bit Flip") ) {
					listMutationApplied.add(1);
					listMutationProb.add(mutListProb);
					mutatedChildren = geneticOneService.mutateChildren(children[0], children[1], 
																genNum, allTasks, allMachines);
				}
			}	
			else {
				listMutationApplied.add(0);
				listMutationProb.add(mutListProb);
				mutatedChildren = children;
			}
			// -- DONE ------------------------------------------ //
			
			
	
			// -- TODO ------------------------------------------ //
			// 6. Recreating generation 
			// - Choose least fit from generation using tournament 
			// replace them by new children
			// System.out.println("\n\n **** **** **** **** **** **** Recreating Population **** **** **** **** **** **** ****");
			String recreatingSelection = jsonReceiveGeneticOne.getRecreatingSelection(); // ** from JSON
			
			
			if( recreatingSelection.equals("Tournament") ) {
				
				population = geneticOneService.recreatePopulation(population, tournamentMembers, mutatedChildren,
						 										populationSize, allTasks, allMachines, 
						 										this.timeMatrix, this.costMatrix, cWeight, tWeight);
														
			}
			if( recreatingSelection.equals("Age based") ) {
				// System.out.println("AGE ARRAY BEFORE RECREATING");
				// this.printMyArray(initialAge);
				population = geneticOneService.recreatePopulationAgeBased(population, mutatedChildren,
							populationSize, allTasks, allMachines, 
							this.timeMatrix, this.costMatrix, cWeight, tWeight, initialAge);
				// System.out.println("AGE ARRAY AFTER RECREATING");
				// this.printMyArray(initialAge);
			}
			// -- DONE ------------------------------------------ //
			
			
			int overallFitnessInside = 0;
			
			for(int i = 0; i < populationSize ; i++) {
				tempArr = geneticOneService.countEnergyOneGenetic(population[i], cWeight, tWeight, 
						allTasks, allMachines, timeMatrix, costMatrix);
				overallFitnessInside += tempArr[0];
				populationFitness[i] = tempArr[0]; 
				populationCost[i] = tempArr[1];
				populationTime[i] = tempArr[2];
			}
			
			if(mainLoop == 50) {
				System.out.println("\nThis is transformed population after iteration " + mainLoop);
				this.printMyTwoArray(population);
				System.out.println("This is fitness of this population");
				this.printMyArray(populationFitness);
				System.out.println("\n Overall new fitness function : " + overallFitnessInside);
			}
			
			
			
			int bestIterationFitness = populationFitness[0];
			int bestIterationCost = 0;
			int bestIterationTime = 0;
			int worstIterationFitness = populationFitness[0];
			int meanIterationFitness = populationFitness[0];
			
			solution = population[0];
			for(int i = 1 ; i < populationSize ; i++) {
				if(populationFitness[i] < bestIterationFitness) {
					bestIterationFitness = populationFitness[i];
					bestIterationCost = populationCost[i];
					bestIterationTime = populationTime[i];
					solution = population[i];
				}
				if(populationFitness[i] > worstIterationFitness) {
					worstIterationFitness = populationFitness[i];
				}
				meanIterationFitness += populationFitness[i];
			}
			meanIterationFitness = meanIterationFitness / populationSize;
			if(mainLoop == (iterNum - 1) ) {
				finalFitness = bestIterationFitness;
			}
			
			
			listPopulationFitness.add(overallFitnessInside);
			listBestFromPopulationFitness.add(bestIterationFitness);
			listWorstFromPopulationFitness.add(worstIterationFitness);
			listMeanFromPopulationFitness.add(meanIterationFitness);
			listIterationNum.add(mainLoop+1);
			listCost.add(bestIterationCost);
			listTime.add(bestIterationTime);
		
		}
		
		
		JsonResponseGeneticOne jsonResponseGeneticOne = new JsonResponseGeneticOne(solution,
															taskOffset, initialFitness, finalFitness,
															listPopulationFitness, listBestFromPopulationFitness,
															listWorstFromPopulationFitness, listMeanFromPopulationFitness,
															listIterationNum, listCrossoverApplied, listCrossoverProb,
															listMutationApplied, listMutationProb, listCost, listTime);
		
		
		int machineStatistics [] = new int[allMachines.size()];
		
		for(int i  = 0 ; i < allTasks.size() ; i++) {
			machineStatistics[solution[i]-1]++;
		}
		
		
		System.out.println("## ## ## ## ## ## This is from JSON ## ## ## ## ## ##");
		System.out.println(jsonReceiveGeneticOne.getPopulationSize());
		System.out.println(jsonReceiveGeneticOne.getParentSelection());
		System.out.println(jsonReceiveGeneticOne.getTournamentMembers());
		System.out.println(jsonReceiveGeneticOne.getCrossoverSelection());
		System.out.println(jsonReceiveGeneticOne.getCrossoverProb());
		System.out.println(jsonReceiveGeneticOne.getMutationSelection());
		System.out.println(jsonReceiveGeneticOne.getMutationSize());
		System.out.println(jsonReceiveGeneticOne.getMutationProb());
		System.out.println(jsonReceiveGeneticOne.getRecreatingSelection());
		System.out.println(jsonReceiveGeneticOne.getcWeight());
		System.out.println(jsonReceiveGeneticOne.gettWeight());
		System.out.println(jsonReceiveGeneticOne.getIterNum());
		System.out.println("## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ## ##");
		
	
		System.out.println("This is my statistics array: ");
		this.printMyArray(machineStatistics);
		
		return jsonResponseGeneticOne;
	}
	
	
	
	
	// ***************************************************************************** // 
	public void printMyArray(int [] arr) {
		if(arr == null)
			return;
		for(int i : arr) {
			System.out.print(" " + i );
		}
	}
	
	public void printMyTwoArray(int [][] arr) {
		if(arr == null)
			return;
		for(int i = 0 ; i < arr.length ; i++) {
			for(int j = 0 ; j < arr[0].length ; j++) {
				System.out.print(arr[i][j] + "  ");
			}
			System.out.println(" ");
		}
			
	}
	// ***************************************************************************** // 
	
	
}
