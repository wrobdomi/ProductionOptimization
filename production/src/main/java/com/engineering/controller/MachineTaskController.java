package com.engineering.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.engineering.entity.Machine;
import com.engineering.entity.Task;
import com.engineering.service.MachineService;
import com.engineering.service.TaskService;

@Controller
@RequestMapping("/production")
public class MachineTaskController {
	
	// Tasks dao 
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private MachineService machineService;
	
	private int[][] timeMatrix;
	
	private int[][] costMatrix;
	
	public void removeAll() {
		
		taskService.removeAllTasks();
		machineService.removeAllMachines();
		
	}

	
	@PostMapping("/gettasks")
	public String listProduction(Model theModel, @RequestParam int tasksNum, @RequestParam int machinesNum) {
				
		this.removeAll();
		
		taskService.fillWithRandomTasks(tasksNum, machinesNum);	
		return "redirect:/production/display";
	}
	
	
	
	@GetMapping("/display")
	public String displayTasks(Model theModel) {
		
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
		
		return "tasks-list";
	}
	
}
