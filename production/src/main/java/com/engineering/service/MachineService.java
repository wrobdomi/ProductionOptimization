package com.engineering.service;

import java.util.List;

import com.engineering.entity.Machine;

public interface MachineService {
	
	public void removeAllMachines();
	
	public List<Machine> getAllMachines();
	
	
}
