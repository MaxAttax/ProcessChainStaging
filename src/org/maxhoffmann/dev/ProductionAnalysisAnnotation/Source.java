package org.maxhoffmann.dev.ProductionAnalysisAnnotation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="source")
public class Source {
	
	private int sourceId;
	private String identifier;
	private String file;
	private String status;
	
	public Source(String identifier, String file, String status) {
		super();
		this.identifier = identifier;
		this.file = file;
		this.status = status;
	}
	
	public Source() {
	}

	@Id
	@GeneratedValue
	@Column(name="SourceId")
	public int getId() {
		return sourceId;
	}
	
	public void setId(int sourceId) {
		this.sourceId = sourceId;
	}
	
	@Column(name="Identifier", nullable=false)
	public String getIdentifier() {
		return identifier;
	}
	
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	
	@Column(name="File", nullable=false)
	public String getfile() {
		return file;
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	@Column(name="Status", nullable = false)
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}

}