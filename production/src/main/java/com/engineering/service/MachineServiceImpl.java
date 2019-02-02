package com.engineering.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.engineering.dao.MachineDAO;
import com.engineering.entity.Machine;


@Service
public class MachineServiceImpl implements MachineService {
	
	@Autowired
	private MachineDAO machineDAO;
	
	@Override
	@Transactional
	public void removeAllMachines() {
		machineDAO.removeAllMachines();
	}

	@Override
	@Transactional
	public List<Machine> getAllMachines() {
		return machineDAO.getAllMachines();
	}

}

