package com.engineering.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.engineering.entity.Machine;
import com.engineering.entity.Task;

@Repository
public class MachineDAOImpl implements MachineDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Machine> getAllMachines() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// query production
		List<Machine> allMachines = currentSession.createQuery("from Machine").getResultList();
		
		return allMachines;	
	}
	
	
	@Override
	public void removeAllMachines() {
		
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		List<Machine> allMachines = this.getAllMachines();
		
		for(Machine theMachine : allMachines) {
			currentSession.delete(theMachine);
		}
	}
	
}
