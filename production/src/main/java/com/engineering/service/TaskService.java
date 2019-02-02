package com.engineering.service;

import java.util.List;

import com.engineering.entity.Task;

public interface TaskService {
	
	// add one task
	public void addTask();
	
	/**
	 * The method creates Tasks and Machines with random parameters and insert them into SQL DB
	 * 
	 * @return returns list of all newly created Tasks in DB 
	 */
	public List<Task> fillWithRandomTasks(int tasksNum, int machinesNum);
		
	
	/**
	 * Retrieve all Tasks from SQL DB
	 * 
	 * @return returns list of all tasks from SQL DB
	 */
	public List<Task> getAllTasks();
	
	/**
	 * Remove all Tasks from SQL DB
	 */
	public void removeAllTasks();
	
}
