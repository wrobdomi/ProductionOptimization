package com.engineering.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="tasks")
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="details")
	private int detailsNumber;
	
	@ManyToMany(fetch=FetchType.EAGER, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(
			name="tasks_machines",
			joinColumns=@JoinColumn(name="task_id"),
			inverseJoinColumns=@JoinColumn(name="machine_id")
			)
	
	private List<Machine> machines;
	
	public Task() {
		
	}

	public Task(int detailsNumber) {
		this.detailsNumber = detailsNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDetailsNumber() {
		return detailsNumber;
	}

	public void setDetailsNumber(int detailsNumber) {
		this.detailsNumber = detailsNumber;
	}

	public List<Machine> getMachines() {
		return machines;
	}

	public void setMachines(List<Machine> machines) {
		this.machines = machines;
	}

	
	// add a convenience method
	public void addMachine(Machine theMachine){
		
		if(machines == null) {
			machines = new ArrayList<>();
		}
		
		machines.add(theMachine);
	}
	
	
	@Override
	public String toString() {
		return "Task [id=" + id + ", detailsNumber=" + detailsNumber + "]";
	}
	
	
}
