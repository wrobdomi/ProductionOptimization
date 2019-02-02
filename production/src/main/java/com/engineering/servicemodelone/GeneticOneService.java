package com.engineering.servicemodelone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.engineering.entity.Machine;
import com.engineering.entity.Task;

@Service
public class GeneticOneService {

	public int [][] findParents(int tournamentMembers, int populationSize, int [][] population,
									List<Task> allTasks, List<Machine> allMachines, 
										int [][] timeMatrix, int [][] costMatrix, 
										double cWeight, double tWeight){
		
		int [] parentOne;
		int [] parentTwo;
		// choose two random int arrays of indexes 
		// get two ( tournamentMembers) random different number >= 0 and < populationSize
		int [] indexes = new Random().ints(0, populationSize).distinct().limit(tournamentMembers).toArray();
		
		parentOne = population[indexes[0]];
			
		int [] fitnessArray = this.countEnergyOneGenetic(population[indexes[0]], cWeight, tWeight, 
												allTasks, allMachines, timeMatrix, costMatrix); // ** cWeight, tWeight hardcoded for now
		//System.out.println("\nIts fitness equals ");
		int fitness = fitnessArray[0];
		//System.out.println(fitness);
		
		for(int i = 1 ; i < tournamentMembers ; i++) {
			
			int [] parentCandidate = population[indexes[i]];
			int [] tempFitnessArray = this.countEnergyOneGenetic(population[indexes[i]], cWeight, tWeight, 
												allTasks, allMachines, timeMatrix, costMatrix); 
			int tempFitness = tempFitnessArray[0];
			if( tempFitness < fitness ) {
				fitness = tempFitness;
				parentOne = parentCandidate;
			}
		}	
		
		indexes = new Random().ints(0, populationSize).distinct().limit(tournamentMembers).toArray();
		
		parentTwo = population[indexes[0]];
		fitnessArray = this.countEnergyOneGenetic(population[indexes[0]], cWeight, tWeight, 
						allTasks, allMachines, timeMatrix, costMatrix); // ** cWeight, tWeight hardcoded for now
		fitness = fitnessArray[0];
		
		if(parentTwo.equals(parentOne)) {
			parentTwo = population[indexes[1]];
			fitnessArray = this.countEnergyOneGenetic(population[indexes[1]], cWeight, tWeight, 
						allTasks, allMachines, timeMatrix, costMatrix);  // ** cWeight, tWeight hardcoded for now
			fitness = fitnessArray[1];
		}
		
		
		for(int i = 1 ; i < tournamentMembers ; i++) {
	
			int [] parentCandidate = population[indexes[i]];
			if(parentCandidate.equals(parentOne)) {
				continue;
			}
			int [] tempFitnessArray = this.countEnergyOneGenetic(population[indexes[i]], cWeight, tWeight, 
					allTasks, allMachines, timeMatrix, costMatrix); 
			int tempFitness = tempFitnessArray[0];
			if( tempFitness < fitness ) {
				fitness = tempFitness;
				parentTwo = parentCandidate;
			}
			
	}
	
			
	int [][] parents = new int[2][parentOne.length];  
	parents[0] = parentOne;
	parents[1] = parentTwo;
	
	return parents;
	
}
	
	
public int [] countEnergyOneGenetic(int [] solution, double costWeight, double timeWeight,
										List<Task> allTasks, List<Machine> allMachines, 
										int [][] timeMatrix, int [][] costMatrix ) {
		
		
		// get id of first machine
		int machineOffset = allMachines.get(0).getId();
		
		double maxTime = 0;
		
		// array of time for machines
		double [] timeArray = new double[allMachines.size()];
		
		double energyCost = 0;
		double finalEnergy = 0;
		
		// counting cost
		for(int i = 0; i < allTasks.size() ; i++) {
			//System.out.println(costMatrix[ solution[i] - machineOffset ][i+1]);
			energyCost +=  costMatrix[ solution[i] - machineOffset ][i+1] * costWeight;
		}
		
		// counting max time 
		for(int i = 0; i < allTasks.size() ; i++) {	
			timeArray[solution[i]-machineOffset] +=   timeMatrix[ solution[i] - machineOffset][i+1] * timeWeight;	
		}
		
		for(int i = 0; i < timeArray.length ; i++) {
			if(maxTime < timeArray[i] ) {
				maxTime = timeArray[i];
			}
		}
		
		finalEnergy = maxTime + energyCost;
		
		
		int castFinalEnergy = (int) finalEnergy;
		int castFinalTime = (int) maxTime;
		int castFinalCost = (int) energyCost;
		
		
		int [] finalArray = new int[3];
		finalArray[0] = castFinalEnergy;
		finalArray[1] = castFinalCost;
		finalArray[2] = castFinalTime;

		return finalArray;
		
	}
	
	
	public int[][] findChildrenCrossover(int[] parentOne, int[] parentTwo) {
		
		int [] childrenOne = new int[parentOne.length];
		int [] childrenTwo = new int[parentTwo.length];
		int [][] children = new int[2][parentOne.length]; 
		
		Random random = new Random();
		
		for(int i = 0 ; i < parentOne.length ; i++) {
			// Get integer between >= 0 and < 2
			int ranValue = random.nextInt(2) + 0;
			if(ranValue == 1) {
				childrenOne[i] = parentOne[i];
			}
			else {
				childrenOne[i] = parentTwo[i];
			}
		}
		
		for(int i = 0 ; i < parentOne.length ; i++) {
			// Get integer between >= 0 and < 2
			int ranValue = random.nextInt(2) + 0;
			// System.out.println("Random is: " + ranValue);
			if(ranValue == 1) {
				childrenTwo[i] = parentOne[i];
			}
			else {
				childrenTwo[i] = parentTwo[i];
			}
		}
		
		
		children[0] = childrenOne;
		children[1] = childrenTwo;
		
		return children;
		
	}
	
	
	
	public int [][] findChildrenCrossoverMultipoint(int[] parentOne, int[] parentTwo){
		
		int [] childrenOne = new int[parentOne.length];
		int [] childrenTwo = new int[parentTwo.length];
		int [][] children = new int[2][parentOne.length]; 
		
		
		int divider =  (int) ( parentOne.length / 2 ) ;
		
		Random random = new Random();
		
		// Get integer between >= 0 and < divider
		int indexOne = random.nextInt(divider);
		// Get integer between >= divider and < parentOne.length
		int indexTwo = random.nextInt(divider) + divider;
		
		
		for(int i =  0 ; i < parentOne.length ; i++) {
			childrenOne[i] = parentOne[i];
			childrenTwo[i] = parentTwo[i];
		}
		
		for(int i = indexOne; i <= indexTwo ; i++) {
			childrenOne[i] = parentTwo[i];
			childrenTwo[i] = parentOne[i];
		}
		
		children[0] = childrenOne;
		children[1] = childrenTwo;
		
		return children;
		
	}
	
	
	public int[][] mutateChildren(int[] childOne, int[] childTwo, int genNum, 
									List<Task> allTasks, List<Machine> allMachines) {
		
		int [][] mutatedChildren = new int [2][childOne.length];
		int [] mutatedOne = childOne;
		int [] mutatedTwo = childTwo;
		
		// get two ( genNum ) random different numbers >= 0 and < childOne.length
		int [] indexes = new Random().ints(0, childOne.length ).distinct().limit(genNum).toArray();
		
		Random random = new Random();
		
		for(int i = 0 ; i < genNum ; i++) {
			
			List<Machine> tasksMachines = allTasks.get(indexes[i]).getMachines();
			
			// Get integer between >= 0 and < tasksMachines.size
			int ranValue = random.nextInt(tasksMachines.size()) + 0;
			int machId = tasksMachines.get(ranValue).getId();
			mutatedOne[indexes[i]] = machId;
		
		}
		
		// get two ( genNum ) random different numbers >= 0 and < childOne.length
		indexes = new Random().ints(0, childOne.length ).distinct().limit(genNum).toArray();
				
		for(int i = 0 ; i < genNum ; i++) {
			
			List<Machine> tasksMachines = allTasks.get(indexes[i]).getMachines();
			
			// Get integer between >= 0 and < tasksMachines.size
			int ranValue = random.nextInt(tasksMachines.size()) + 0;
			int machId = tasksMachines.get(ranValue).getId();
			mutatedTwo[indexes[i]] = machId;
		
		}
	
		mutatedChildren[0] = mutatedOne;
		mutatedChildren[1] = mutatedTwo;
		
		return mutatedChildren;
		
	}
	

	
	
	public int[][] recreatePopulation(int[][] population, int tournamentMembers, int[][] mutatedChildren, 
			int populationSize, List<Task> allTasks, List<Machine> allMachines, int[][] timeMatrix, int[][] costMatrix,
			double cWeight, double tWeight) {
		
		int [] outOne;
		int [] outTwo;
		
		// choose two random int arrays of indexes 
		// get two ( tournamentMembers) random different number >= 0 and < populationSize
		int [] indexes = new Random().ints(0, populationSize).distinct().limit(tournamentMembers).toArray();
		
		outOne = population[indexes[0]];
		
		int [] fitnessArray = this.countEnergyOneGenetic(population[indexes[0]], cWeight, tWeight, 
												allTasks, allMachines, timeMatrix, costMatrix); // ** cWeight, tWeight hardcoded for now
		int fitness = fitnessArray[0];
		
		for(int i = 1 ; i < tournamentMembers ; i++) {
			
			int [] outCandidate = population[indexes[i]];
			int [] tempFitnessArray = this.countEnergyOneGenetic(population[indexes[i]], cWeight, tWeight, 
												allTasks, allMachines, timeMatrix, costMatrix); 
			int tempFitness = tempFitnessArray[0];
			if( tempFitness > fitness ) {
				fitness = tempFitness;
				outOne = outCandidate;
			}
		}
		
		indexes = new Random().ints(0, populationSize).distinct().limit(tournamentMembers).toArray();
		
		outTwo = population[indexes[0]];
		fitnessArray = this.countEnergyOneGenetic(population[indexes[0]], cWeight, tWeight, 
						allTasks, allMachines, timeMatrix, costMatrix); // ** cWeight, tWeight hardcoded for now
		fitness = fitnessArray[0];
		
		if(outTwo.equals(outOne)) {
			outTwo = population[indexes[1]];
			fitnessArray = this.countEnergyOneGenetic(population[indexes[1]], cWeight, tWeight, 
						allTasks, allMachines, timeMatrix, costMatrix);  // ** cWeight, tWeight hardcoded for now
			fitness = fitnessArray[1];
		}
		
		for(int i = 1 ; i < tournamentMembers ; i++) {
	
			int [] outCandidate = population[indexes[i]];
			if(outCandidate.equals(outOne)) {
				continue;
			}
			int [] tempFitnessArray = this.countEnergyOneGenetic(population[indexes[i]], cWeight, tWeight, 
					allTasks, allMachines, timeMatrix, costMatrix); 
			int tempFitness = tempFitnessArray[0];
			if( tempFitness > fitness ) {
				fitness = tempFitness;
				outTwo = outCandidate;
			}
			
	}
	
		for(int i = 0 ; i < population.length ; i++ ) {
				
				if(population[i].equals(outOne)) {
					population[i] = mutatedChildren[0];
				}
				
				if(population[i].equals(outTwo)) {
					population[i] = mutatedChildren[1];
				}
				
		}
		

		
		return population;
		
	}
	
	
	public int[][] recreatePopulationAgeBased(int[][] population, int[][] mutatedChildren,
			int populationSize, List<Task> allTasks, List<Machine> allMachines, int[][] timeMatrix, int[][] costMatrix,
			double cWeight, double tWeight, int [] initialAge) {
		
		
		// find maximum from initialAge
		int [] helpArr = new int[populationSize];
		int maxAge = -1;
		int maxIndex = 0;
		List<Integer> listZerosIndexes = new ArrayList<>();
	
		
		
		for(int i = 0; i < populationSize; i++) {
			if(initialAge[i] > maxAge) {
				maxAge = initialAge[i];
				maxIndex = i;
			}
			helpArr[i] = initialAge[i];
		}
		
		for(int i = 0; i < populationSize; i++) {
			helpArr[i] = helpArr[i] - maxAge;
			if(helpArr[i] == 0) {
				listZerosIndexes.add(i);
			}
		}
		
		if(listZerosIndexes.size() >= 2) {
			// get two random different numbers >= 0 and < allTasks.size()
			int[] ints = new Random().ints(0, listZerosIndexes.size()).distinct().limit(2).toArray();
			population[listZerosIndexes.get(ints[0])] = mutatedChildren[0];
			initialAge[listZerosIndexes.get(ints[0])] = -1; 
			population[listZerosIndexes.get(ints[1])] = mutatedChildren[1];
			initialAge[listZerosIndexes.get(ints[1])] = -1; 
		}
		
		if(listZerosIndexes.size() == 1) {
			
			listZerosIndexes = new ArrayList<>();
			initialAge[listZerosIndexes.get(0)] = -1;
			population[listZerosIndexes.get(0)] = mutatedChildren[0];
			maxAge = 0;
			maxIndex = 0;
			
			for(int i = 0; i < populationSize; i++) {
				if(initialAge[i] > maxAge) {
					maxAge = initialAge[i];
					maxIndex = i;
				}
				helpArr[i] = initialAge[i];
			}
			
			for(int i = 0; i < populationSize; i++) {
				helpArr[i] = helpArr[i] - maxAge;
				if(helpArr[i] == 0) {
					listZerosIndexes.add(i);
				}
			}
			
			if(listZerosIndexes.size() >= 2) {
				// get 1 random different numbers >= 0 and < listZerosIndexes.size()
				int[] ints = new Random().ints(0, listZerosIndexes.size()).distinct().limit(1).toArray();
				population[listZerosIndexes.get(ints[0])] = mutatedChildren[1];
				initialAge[listZerosIndexes.get(ints[0])] = -1; 
			}
			
			if(listZerosIndexes.size() == 1) {
				initialAge[listZerosIndexes.get(0)] = -1;
				population[listZerosIndexes.get(0)] = mutatedChildren[1];
			}
			
		}
		
			
		for( int i = 0; i < populationSize ; i++) {
			initialAge[i]++;
		}
		
		return population;
		
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
