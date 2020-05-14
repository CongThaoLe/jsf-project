package com.fis.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TBL_ORDERS")
public class Order implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "ORDERCODE")
	private String orderCode;

	@Column(name = "CUSTOMERID")
	private Integer customerId;

	@Column(name = "ORDERDATE")
	private Date orderDate;

	@Column(name = "FULLNAME")
	private String fullName;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "MONEY")
	private BigDecimal money;

	@Column(name = "PROVINCE")
	private Integer tinhTp;

	@Column(name = "DISTRICT")
	private Integer quanHuyen;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "STATUS")
	private Integer status;
	
	@Transient
	private int monthOfYear;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public Integer getTinhTp() {
		return tinhTp;
	}

	public void setTinhTp(Integer tinhTp) {
		this.tinhTp = tinhTp;
	}

	public Integer getQuanHuyen() {
		return quanHuyen;
	}

	public void setQuanHuyen(Integer quanHuyen) {
		this.quanHuyen = quanHuyen;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public int getMonthOfYear() {
		return monthOfYear;
	}

	public void setMonthOfYear(int monthOfYear) {
		this.monthOfYear = monthOfYear;
	}

}
