package com.engineering.servicemodeltwo;

import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.engineering.entity.Machine;
import com.engineering.entity.Task;
import com.engineering.twotasks.DoubleTask;

@Service
public class AnnealingTwoService {

	
	public int [][] getRandomSolutionTwo(Map<Integer, DoubleTask> doubleTaskMachines, List<Task> allTasks) {
		
		int [][] ranSolution = new int [2][allTasks.size()];
		
		int i = 0;
		
		// get random 
		Random random = new Random(); 
		
		for(Task theTask : allTasks) {
			
			// get task ID
			int taskID = theTask.getId();
			
			// check if the task is assigned more than one machine
			int checkDouble = doubleTaskMachines.get(taskID).getTwoMachines();
			
			if(checkDouble == 1) {
				
				int [] firstTable = doubleTaskMachines.get(taskID).getFirstPartMachines();
				int [] secondTable = doubleTaskMachines.get(taskID).getSecondPartMachines();
				
				// Get integer between >= 0 and < firstTable.length
				int ranFirst = random.nextInt(firstTable.length) + 0;
				// Get integer between >= 0 and < secondTable.length
				int ranSecond = random.nextInt(secondTable.length) + 0;
				
				// Get machines ID 
				int firstMachine = firstTable[ranFirst];
				int secondMachine = secondTable[ranSecond];
				
				ranSolution[0][i] = firstMachine;
				ranSolution[1][i] = secondMachine;
				
				i++;
				
			}
			else {
				
				List<Machine> machines = theTask.getMachines();
				
				// Get integer between >= 0 and < machine.size()
				int ranMachine = random.nextInt(machines.size()) + 0; 
				
				ranSolution[0][i] = machines.get(ranMachine).getId();
				ranSolution[1][i] = -1;
				
				i++;
			}
			
		}
		
		return ranSolution;
		
	}
	
	
	public int [][] getColorDoubleTasks(Map<Integer, DoubleTask> doubleTaskMachines, 
														List<Task> allTasks, List<Machine> allMachines){
		
		int machineOffset = allMachines.get(0).getId();
		int tasksOffset = allTasks.get(0).getId();
		
		int [][] tableColor = new int[allMachines.size()][allTasks.size()];
		
		for(Task theTask : allTasks) {
			
			// get the task id
			int taskId = theTask.getId();
			
			int doubleTask = doubleTaskMachines.get(taskId).getTwoMachines();
			
			if(doubleTask == 0) {
				
				for(int i = 0 ; i < allMachines.size() ; i++) {
					tableColor[i][theTask.getId()-tasksOffset] = 0;
				}
				
			}
			else {
				
				int [] oneArr = doubleTaskMachines.get(taskId).getFirstPartMachines();
				int [] twoArr = doubleTaskMachines.get(taskId).getSecondPartMachines();
				
				for(int i = 0 ; i < oneArr.length ; i++) {
					tableColor[oneArr[i]-machineOffset][theTask.getId()-tasksOffset] = 1; 
				}
				for(int i = 0 ; i < twoArr.length ; i++) {
					tableColor[twoArr[i]-machineOffset][theTask.getId()-tasksOffset] = 2; 
				}
				
			}
	
		}
	
		return tableColor;
	}
	
	
	
	

	public int [] countEnergyTwo(int [][] solution, double costWeight, double timeWeight,
									List<Task> allTasks, List<Machine> allMachines, 
										int[][] costMatrix, int [][] timeMatrix) {
		
		// get id of first machine
		int machineOffset = allMachines.get(0).getId();
		
		double maxTime = 0;
		
		// array of time for machines
		double [] timeArray = new double[allMachines.size()];
		
		double energyCost = 0;
		double finalEnergy = 0;
		
		int castFinalEnergy;
		int castFinalTime;
		int castFinalCost;
		
		// counting cost
		for(int i = 0 ; i < allTasks.size() ; i++) {
			if(solution[1][i] == -1) {
				energyCost +=  costMatrix[ solution[0][i] - machineOffset ][i+1] * costWeight;
			}
			else {
				energyCost +=  costMatrix[ solution[0][i] - machineOffset ][i+1] * costWeight;
				energyCost +=  costMatrix[ solution[1][i] - machineOffset ][i+1] * costWeight;
			}
		}
		
		// counting max time 
		for(int i = 0; i < allTasks.size() ; i++) {	
				
			
			if(solution[1][i] == -1) {
				timeArray[solution[0][i]-machineOffset] +=   timeMatrix[ solution[0][i] - machineOffset][i+1] * timeWeight;
			}
			else {
				timeArray[solution[0][i]-machineOffset] += timeMatrix[ solution[0][i] - machineOffset][i+1] * timeWeight;
				timeArray[solution[1][i]-machineOffset] += timeMatrix[ solution[1][i] - machineOffset][i+1] * timeWeight;
			}
			
			
		}
		
		for(int i = 0; i < timeArray.length ; i++) {
			if(maxTime < timeArray[i] ) {
				maxTime = timeArray[i];
			}
		}
		
		finalEnergy = maxTime + energyCost;
		
		castFinalEnergy = (int) finalEnergy;
		castFinalTime = (int) maxTime;
		castFinalCost = (int) energyCost;
		
		int [] finalArray = new int[3];
		finalArray[0] = castFinalEnergy;
		finalArray[1] = castFinalCost;
		finalArray[2] = castFinalTime;

		return finalArray;
		
	}
	
	
	
	
	public int [][] changeSolutionTwo(int [][] solution, int changes,
										Map<Integer, DoubleTask> doubleTaskMachines, 
										List<Task> allTasks) {
	
		Random random = new Random(); 
		
		// get two (changes) random different number >= 0 and < allTasks.size()
		final int[] ints = new Random().ints(0, allTasks.size()).distinct().limit(changes).toArray();
		
		for(int i : ints) {

			int choice = solution[1][i];
			
			if(choice == -1) {
				
				List<Machine> machines = allTasks.get(i).getMachines();
				
				// Get integer between >= 0 and < machine.size()
				int ranMachine = random.nextInt(machines.size()) + 0; 
				
				solution[0][i] = machines.get(ranMachine).getId();

			}
			else {
				
				int taskID = allTasks.get(i).getId();
				
				int [] firstTable = doubleTaskMachines.get(taskID).getFirstPartMachines();
				int [] secondTable = doubleTaskMachines.get(taskID).getSecondPartMachines();
				
				// Get integer between >= 0 and < firstTable.length
				int ranFirst = random.nextInt(firstTable.length) + 0;
				// Get integer between >= 0 and < secondTable.length
				int ranSecond = random.nextInt(secondTable.length) + 0;
				
				// Get machines ID 
				int firstMachine = firstTable[ranFirst];
				int secondMachine = secondTable[ranSecond];
				
				solution[0][i] = firstMachine;
				solution[1][i] = secondMachine;
				
			}
		
		}	
		
		return solution;
			
		
	}
	
	
	
}
