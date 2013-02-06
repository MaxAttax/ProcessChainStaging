package org.maxhoffmann.dev.ProductionAnalysisAnnotation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="`order`")					// The ` signs are necessary because 'order' is a keyword in SQL...
public class Order {
	private Material material;
	private int orderId;
	private int orderNo;
	private String orderType;
	
	public Order() {
	}
	
	public Order(int orderNo, String orderType) {
		this.orderNo = orderNo;
		this.orderType = orderType;
	}
	
	@Id
	@GeneratedValue
	@Column(name="OrderId")
	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	
	@ManyToOne
	@JoinColumn(name = "MaterialId", nullable = true)
	public Material getMaterial() {
		return this.material;
	}
	
	public void setMaterial (Material material) {
		this.material = material;
	}
	
	@Column(name="OrderNo")
	public Integer getOrderNo() {
		return this.orderNo;
	}
	
	public void setOrderNo (Integer orderNo) {
		this.orderNo = orderNo;
	}
	
	@Column(name="OrderType")
	public String getOrderType() {
		return this.orderType;
	}
	
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
}




