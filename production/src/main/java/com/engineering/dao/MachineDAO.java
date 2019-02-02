package com.engineering.dao;

import java.util.List;

import com.engineering.entity.Machine;


public interface MachineDAO {
	
	public void removeAllMachines();
	
	public List<Machine> getAllMachines();
	
	
}	
