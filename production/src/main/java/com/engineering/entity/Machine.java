package com.engineering.entity;

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
@Table(name="machines")
public class Machine {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="detail_cost")
	private int detailCost;
	
	@Column(name="detail_time")
	private int detailTime;
	
	@ManyToMany(fetch=FetchType.EAGER, 
			cascade = {CascadeType.PERSIST, CascadeType.MERGE,
					CascadeType.DETACH, CascadeType.REFRESH})
	@JoinTable(
			name="tasks_machines",
			joinColumns=@JoinColumn(name="machine_id"),
			inverseJoinColumns=@JoinColumn(name="task_id")
			)
	
	private List<Task> tasks;

	public Machine() {
		
	}

	public Machine(int detailCost, int detailTime) {
		this.detailCost = detailCost;
		this.detailTime = detailTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDetailCost() {
		return detailCost;
	}

	public void setDetailCost(int detailCost) {
		this.detailCost = detailCost;
	}

	public int getDetailTime() {
		return detailTime;
	}

	public void setDetailTime(int detailTime) {
		this.detailTime = detailTime;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
		return "Machine [id=" + id + ", detailCost=" + detailCost + ", detailTime=" + detailTime + ", machines="
				+ tasks + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Machine other = (Machine) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
	
}
