package com.engineering.dao;

import java.util.List;

import com.engineering.entity.Task;

public interface TaskDAO {

	// add one task
	public void addTask();

	public List<Task> fillWithRandomTasks(int tasksNum, int machinesNum);
	
	public List<Task> getAllTasks();
	
	public void removeAllTasks();
}
