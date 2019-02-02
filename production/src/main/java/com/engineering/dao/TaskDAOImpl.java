package com.engineering.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.engineering.entity.Machine;
import com.engineering.entity.Task;

@Repository
public class TaskDAOImpl implements TaskDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private int tasksNumber;
	private int machinesNumber; 
	
	@Override
	public void addTask() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
				
				
		Task taskToAdd = new Task(100);
		currentSession.save(taskToAdd);
		
		Machine machine1 = new Machine(12,12);
		Machine machine2 = new Machine(10,101);
		
		taskToAdd.addMachine(machine1);
		taskToAdd.addMachine(machine2);
		
		currentSession.save(machine1);
		currentSession.save(machine2);
		
		currentSession.save(taskToAdd);
	}

	
	/**
	 * Fill SQL DB with Random Tasks and Machines and set up ManyToMany relationship
	 * 
	 * @Return returns list of newly created tasks from DB 
	 */
	@Override
	public List<Task> fillWithRandomTasks(int tasksNum, int machinesNum ) {
		
		tasksNumber = tasksNum;
		machinesNumber = machinesNum;
		
		Random random = new Random(); 
		
		List<Task> addedTasks = new ArrayList<>();
		List<Machine> addedMachines = new ArrayList<>();
			
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		
		// Create random tasks and save to the DB 
		for(int i = 0 ; i < tasksNumber;  i++) {
			
			// Get integer between >= 1 and < 101
			int ranDetails = random.nextInt(100) + 1; 
			
			// Create task with random number of details
			Task tempTask = new Task(ranDetails);
			
			// Add task to return list
			addedTasks.add(tempTask);
			
			// Add task to the database
			currentSession.save(tempTask);
		}
		
		
		// Create random machines and save to the DB 
		for(int i = 0 ; i < machinesNumber;  i++) {
			
			// Get integer between >= 10 and < 100
			int ranCost = random.nextInt(90) + 10; 
			
			// Get integer between >= 10 and < 100
			int ranTime = random.nextInt(90) + 10; 
			
			// Create task with random number of details
			Machine tempMachine = new Machine(ranCost, ranTime);
			
			addedMachines.add(tempMachine);
			
			// Add task to the database
			currentSession.save(tempMachine);
		}
		
			
		// Create connections ( ManyToMany ) beetween tasks and machines
		for(Task task : addedTasks ) {
		
			Set<Integer> randomIndexes = new HashSet<Integer>();
			
			// Get random number of machines for newly created task
			int ranCon = random.nextInt(machinesNumber-1) + 8; 
			
			for ( int i = 0; i < ranCon ; i++) {
					
					int ranInd = random.nextInt(machinesNumber) + 0; 
					randomIndexes.add(ranInd);
					
				}
			
			// Set connection between machine and task
			for ( int i : randomIndexes ) {
					task.addMachine(addedMachines.get(i));		
				}
		}
		
			
		return addedTasks;
		
	}


	/**
	 * Get all Tasks from SQL DB
	 * 
	 * @return returns list of all tasks from DB
	 */
	@Override
	public List<Task> getAllTasks() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// query production
		List<Task> allTasks = currentSession.createQuery("from Task").getResultList();
		
		return allTasks;	
	}


	
	@Override
	public void removeAllTasks() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		List<Task> allTasks = this.getAllTasks();
		
		for(Task theTask : allTasks) {
			currentSession.delete(theTask);
		}
		
	}

	
}
