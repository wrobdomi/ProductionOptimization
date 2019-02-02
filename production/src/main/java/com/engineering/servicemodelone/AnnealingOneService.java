package com.engineering.servicemodelone;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.engineering.entity.Machine;
import com.engineering.entity.Task;

@Service
public class AnnealingOneService {


	public int [] countEnergyOne(List<Task> allTasks, List<Machine> allMachines, 
			int[][] costMatrix, int [][] timeMatrix, int [] solution, double costWeight, double timeWeight) {
		
		
		// get id of first machine
		int machineOffset = allMachines.get(0).getId();
		
		double maxTime = 0;
		
		// array of time for machines
		double [] timeArray = new double[allMachines.size()];
		
		double energyCost = 0;
		double finalEnergy = 0;
		
		// counting cost
		for(int i = 0; i < allTasks.size() ; i++) {
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
	
	

	public int [] changeSolution(int [] solution, int changes, List<Task> allTasks) {
	
		Random random = new Random(); 
	
		final int[] ints = new Random().ints(0, allTasks.size()).distinct().limit(changes).toArray();
		
		for(int i : ints) {
			
			
			List<Machine> machines = allTasks.get(i).getMachines();
			
			// Get integer between >= 0 and < machine.size()
			int ranMachine = random.nextInt(machines.size()) + 0; 
			
			solution[i] = machines.get(ranMachine).getId();
		}
			
		
		return solution;
			
		
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
