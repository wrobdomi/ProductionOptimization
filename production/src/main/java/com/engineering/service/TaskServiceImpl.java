package com.engineering.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.engineering.dao.TaskDAO;
import com.engineering.entity.Task;

@Service
public class TaskServiceImpl implements TaskService {
	
	// need to inject customer dao
	@Autowired
	private TaskDAO taskDAO;
	
	@Override
	@Transactional
	public void addTask() {
		taskDAO.addTask();
	}

	@Override
	@Transactional
	public List<Task> fillWithRandomTasks(int tasksNum, int machinesNum) {
		return taskDAO.fillWithRandomTasks(tasksNum, machinesNum);
	}
	
	
	@Override
	@Transactional
	public List<Task> getAllTasks() {
		return taskDAO.getAllTasks();
	}

	@Override
	@Transactional
	public void removeAllTasks() {
		taskDAO.removeAllTasks();
		
	}
	
	

}
