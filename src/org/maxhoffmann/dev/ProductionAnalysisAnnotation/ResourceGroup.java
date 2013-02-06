package org.maxhoffmann.dev.ProductionAnalysisAnnotation;

import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="resourcegroup")
public class ResourceGroup {
	private Source source;
	private Integer resourceGroupId;
	private String label;
	private String description;
	
	public ResourceGroup() {		
	}
	
	public ResourceGroup(Integer projectId, String label, String description) {
		this.label = label;
		this.description = description;	
	}
	
	@Id
	@GeneratedValue
	@Column(name = "ResourceGroupId")
	public Integer getResourceGroupId() {
		return this.resourceGroupId;
	}
	
	public void setResourceGroupId(Integer resourceGroupId) {
		this.resourceGroupId = resourceGroupId;
	}
	
	@ManyToOne
	@JoinColumn(name = "SourceId", nullable = true)
	public Source getSource() {
		return this.source;
	}
	
	public void setSource(Source source) {
		this.source = source;
	}
	
	/*
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	*/
	
	/*
	@ManyToOne(cascade = CascadeType.ALL)
	public long getProjectId() {
		return this.projectId;
	}
	
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}
	*/
	
	@Column(name = "Label")
	public String getLabel() {
		return this.label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	@Column(name = "Description")
	public String getDescription() {
		return this.description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
