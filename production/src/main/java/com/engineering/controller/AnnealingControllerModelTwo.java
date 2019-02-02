package com.engineering.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.engineering.json.JsonResponse;
import com.engineering.json.JsonReceive;
import com.engineering.service.MachineService;
import com.engineering.service.TaskService;
import com.engineering.servicemodeltwo.AnnealingTwoService;
import com.engineering.twotasks.DoubleTask;

@Controller
@RequestMapping("/annealingtwo")
public class AnnealingControllerModelTwo {
	
	Map<Integer, DoubleTask> doubleTaskMachines;
	
	// Tasks DAO
	@Autowired
	private TaskService taskService;
	
	// Machines DAO
	@Autowired
	private MachineService machineService;
	
	// Simulated annealing service
	@Autowired
	private AnnealingTwoService annealingTwoService;
	
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
			
	
			int [][] colors = annealingTwoService.getColorDoubleTasks(doubleTaskMachines, allTasks, allMachines);
			
			
			theModel.addAttribute("tasks", allTasks);
			theModel.addAttribute("machines", allMachines);
			theModel.addAttribute("costMatrix", costMatrix);
			theModel.addAttribute("timeMatrix", timeMatrix);
			theModel.addAttribute("rows", costMatrix.length-1);
			theModel.addAttribute("cols", costMatrix[0].length-1);
			theModel.addAttribute("colors", colors);
		
		}
		
		return "main-annealing-two";
		
	}
	

	
	@GetMapping("/getassign")
	public String TestController() {
		
		this.getRandomDoubleTasks();
		return "redirect:/annealingtwo/display";
		
	}
	
	
	
	/**
	 * get random assignments between tasks and machines
	 * 
	 * @return
	 */
	public String getRandomDoubleTasks() {
		
		// get new HashMap
		doubleTaskMachines = new HashMap<Integer, DoubleTask>();
		
		// get All Tasks
		List<Task> allTasks = taskService.getAllTasks();
		
		// loop through all tasks
		for(Task theTask : allTasks) {
			
			// get all machines for a given task
			List<Machine> machines = theTask.getMachines();
			if(machines.size() == 1) { // if only one machine assigned to theTask then doubleTask must be 0
				doubleTaskMachines.put(theTask.getId(), new DoubleTask(null, null, 0));
			}
			else {
				
				Random random = new Random(); 
				
				// Get integer between >= 0 and < 2
				int randomChoice = random.nextInt(2) + 0; 
				System.out.println("Random is " + randomChoice);
				
				// 50 % chance for a task to need two machines to be completed
				if(randomChoice == 0) {
					doubleTaskMachines.put(theTask.getId(), new DoubleTask(null, null, 0));
				}
				else { // if theTask needs two machines to be completed
					
					int [] one;
					int [] two;
					
					// Get integer between >= 1 and < size 
					int randomNum = random.nextInt(machines.size()-1) + 1; 
					
					// Get randomNum different random numbers >= 0 and < machines.size()
					final int[] ints = new Random().ints(0, machines.size()).distinct().limit(randomNum).toArray();
					
					one = new int[ints.length];
					two = new int[machines.size() - ints.length];
					
					for(int i = 0 ; i < ints.length ; i++) {
						one[i] = machines.get(ints[i]).getId();
					}
					
					int ind = 0;
					
					for(int i = 0 ; i < machines.size() ; i++) {
						
						int id = machines.get(i).getId();
						
						boolean choice = true;
						
						for(int j : one) {
							if(id == j) {
								choice = false;
								break;
							}
						}
						
						if(choice) {
							two[ind] = id;
							ind++;
						}
					}
					
					DoubleTask doubleTask = new DoubleTask(one, two, 1); 
					doubleTaskMachines.put(theTask.getId(), doubleTask );
				}
				
				
			}
			
		}
	
	return "redirect:/annealingtwo/display";
		
	}
	
	

	@PostMapping("/solve")
	public @ResponseBody
    JsonResponse simulatedAnnealingTwo(@RequestBody JsonReceive jsonReceive, Model theModel) {	
		
		long startTime = System.currentTimeMillis();
		 
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
		
			
		List<Integer> listLoopNum = new ArrayList<Integer>();
		List<Integer> listBestEnergy = new ArrayList<Integer>();
		List<Double> listTemperature = new ArrayList<Double>();
		List<Double> listAcceptanceProb = new ArrayList<Double>();
		List<Integer> listAcceptance = new ArrayList<Integer>();
		List<Integer> listCost = new ArrayList<Integer>();
		List<Integer> listTime = new ArrayList<Integer>();
				
		
		
		double temperature = jsonReceive.getTemperature(); // set initial temperature
		double initialTemperature = jsonReceive.getTemperature(); // get initial temperature
		double alpha = jsonReceive.getAlpha(); // used while changing temperature 
		boolean cooling = jsonReceive.getCoolingWay().contains("T(i)");
		int maxIter = jsonReceive.getIterations(); // max number of iterations
		int changes = jsonReceive.getAssignments(); // changes in solution
		double cWeight = jsonReceive.getcWeight();
		double tWeight = jsonReceive.gettWeight();
		boolean costConEnabled = jsonReceive.isCostConEnabled();
		boolean timeConEnabled = jsonReceive.isTimeConEnabled();
		int costCon = jsonReceive.getCostCon();
		int timeCon = jsonReceive.getTimeCon();
		
		double expUp;
		
		

		int [][] initialSolution = annealingTwoService.getRandomSolutionTwo(doubleTaskMachines, allTasks); // get initial solution
		int [][] bestSolution = initialSolution; // first best solution
		
		int [] bestArr = annealingTwoService.countEnergyTwo(bestSolution, cWeight, tWeight,
														allTasks, allMachines, costMatrix, timeMatrix);
		int bestEnergy = bestArr[0]; // initial energy
		int initialEnergy = bestEnergy;
		
		
		listBestEnergy.add(bestEnergy);
		listTemperature.add(temperature);
		listLoopNum.add(0);
		listAcceptanceProb.add(0.0);
		listAcceptance.add(0);
		listCost.add(bestArr[1]);
		listTime.add(bestArr[2]);
		
		for(int i = 0; i < maxIter; i++) {
			
			
			if(cooling) {
				temperature = alpha * temperature;
			}
			else { 
				temperature = initialTemperature / ( 1 + Math.log10( i + 1 ) );
			}
				
			
			listLoopNum.add(i+1);
			listTemperature.add(temperature);
			
			int [][] newSolution = annealingTwoService.changeSolutionTwo(bestSolution, changes,
															doubleTaskMachines, allTasks);
			int [] newArr = annealingTwoService.countEnergyTwo(newSolution, cWeight, tWeight,
													allTasks, allMachines, costMatrix, timeMatrix);
			int newEnergy = newArr[0];

			
			
			
			// Applying time constraints -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- // 
			if(newArr[2] > timeCon && timeConEnabled && !costConEnabled ) {
				do {
					newSolution = annealingTwoService.changeSolutionTwo(bestSolution, changes,
															doubleTaskMachines, allTasks);
					newArr = annealingTwoService.countEnergyTwo(newSolution, cWeight, tWeight,
													allTasks, allMachines, costMatrix, timeMatrix);
					newEnergy = newArr[0];
				}while(newArr[2] > timeCon);
			}
			
			// Applying cost constraints
			if(newArr[1] > costCon && costConEnabled && !timeConEnabled ) {
				do {
					newSolution = annealingTwoService.changeSolutionTwo(bestSolution, changes,
																	doubleTaskMachines, allTasks);
					newArr = annealingTwoService.countEnergyTwo(newSolution, cWeight, tWeight,
													allTasks, allMachines, costMatrix, timeMatrix);
					newEnergy = newArr[0];
				}while(newArr[1] > costCon);
			}
			
			// Applying time and cost constraints
			if((newArr[1] > costCon || newArr[2] > timeCon) && costConEnabled && timeConEnabled ) {
				do {
					newSolution = annealingTwoService.changeSolutionTwo(bestSolution, changes,
																	doubleTaskMachines, allTasks);
					newArr = annealingTwoService.countEnergyTwo(newSolution, cWeight, tWeight,
													allTasks, allMachines, costMatrix, timeMatrix);
					newEnergy = newArr[0];
				}while( newArr[1] > costCon || newArr[2] > timeCon );
			}
			// -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- //
			
			
			int diff = Math.abs(newEnergy - bestEnergy);
			diff = -1 * diff;
			
			
			expUp = diff/temperature;
			double probFun = Math.exp(expUp);
			
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
		
	
//		System.out.println("End energy is  " + bestEnergy );
//		System.out.println("Best found solution is  ");
//		this.printMyTwoArray(bestSolution);
		
		theModel.addAttribute("energyList", listBestEnergy);
		theModel.addAttribute("foundSolution", bestSolution);
		
		
		int [] histogramData = new int[25];
		
		for(int i = 0 ; i < bestSolution[0].length ; i ++) {
			for(int j = 0; j < bestSolution.length ; j++) {
				if(bestSolution[j][i] != -1) {
					histogramData[ bestSolution[j][i]-1 ] += 1; 
				}
			}
		}
		
//		System.out.println("Histogram data: ");
//		this.printMyArray(histogramData);
		
		JsonResponse jsonResponse = new JsonResponse(bestSolution, 
										tasksOffset, initialEnergy, bestEnergy, 
										listLoopNum, listBestEnergy, listTemperature, 
										listAcceptanceProb, listAcceptance, listTime, listCost,
										machineOffset, machineLength
										);
		
		long stopTime = System.currentTimeMillis();
	    long elapsedTime = stopTime - startTime;
	    System.out.println( "\n"  + elapsedTime);
	      
	    System.out.println(initialEnergy + " & " + bestEnergy + " & " + Collections.min(listCost) + " & " + 
				Collections.min(listTime) + " & " + Collections.max(listCost) + 
										" & " + Collections.max(listTime) + " & " + elapsedTime);
	    
		return jsonResponse;
		
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
