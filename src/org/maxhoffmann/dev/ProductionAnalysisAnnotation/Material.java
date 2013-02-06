package org.maxhoffmann.dev.ProductionAnalysisAnnotation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="material")
public class Material {
	private Source source;
	private int materialId;
	private int materialNo;
	private String description;
	
	public Material(int materialNo, String description) {
		super();
		this.materialNo = materialNo;
		this.description = description;
	}
	
	public Material() {
	}
	
	@Id
	@GeneratedValue
	@Column(name="MaterialId")
	public int getId() {
		return materialId;
	}
	
	public void setId(int materialId) {
		this.materialId = materialId;
	}
	
	@ManyToOne
	@JoinColumn(name = "SourceId", nullable = true)
	public Source getSource() {
		return this.source;
	}
	
	public void setSource(Source source) {
		this.source = source;
	}
	
	@Column(name="MaterialNo")
	public int getMaterialNo() {
		return materialNo;
	}
	
	public void setMaterialNo(int materialNo) {
		this.materialNo = materialNo;
	}
	
	@Column(name="Description")
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
}
