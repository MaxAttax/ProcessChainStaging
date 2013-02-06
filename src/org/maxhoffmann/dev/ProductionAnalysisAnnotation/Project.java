package org.maxhoffmann.dev.ProductionAnalysisAnnotation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="project")
public class Project {
	
	private int projectId;
	private String status;
	
	public Project(String status) {
		super();
		this.status = status;
		
	}
	
	public Project() {
	}

	@Id
	@GeneratedValue
	@Column(name="ProjectId")
	public int getId() {
		return projectId;
	}
	
	public void setId(int projectId) {
		this.projectId = projectId;
	}
	
	@Column(name="Status", nullable=false)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

}
