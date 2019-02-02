package com.engineering.servicemodelone;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.engineering.entity.Machine;
import com.engineering.entity.Task;

@Service
public class ModelOneService {

	/**
	 * The method generates random initial solution for SA algorithm
	 * 
	 * @return arrays of tasks with assigned machines
	 */

	public int [] getRandomSolution(List<Task> allTasks) {
		
		int [] ranSolution = new int[allTasks.size()];
		
		int i = 0;
		
		Random random = new Random(); 
		
		for(Task theTask : allTasks) {
			
			List<Machine> machines = theTask.getMachines();
			
			// Get integer between >= 0 and < machine.size()
			int ranMachine = random.nextInt(machines.size()) + 0; 
			
			ranSolution[i] = machines.get(ranMachine).getId();
			
			i++;
		}

		return ranSolution;
		
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
