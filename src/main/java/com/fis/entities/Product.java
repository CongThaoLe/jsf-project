package com.fis.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TBL_PRODUCTS")
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "CATID")
	private int catId;

	@Column(name = "NAME")
	private String name;

	@Column(name = "ALIAS")
	private String alias;

	@Column(name = "IMG")
	private byte[] image;

	@Column(name = "DETAIL")
	private String detail;

	@Column(name = "IDPRODUCER")
	private Integer idProducer;

	@Column(name = "NUMBER")
	private Integer number;

	@Column(name = "NUMBER_BUY")
	private Integer numberBuy;

	@Column(name = "SALE")
	private Integer sale;

	@Column(name = "PRICE")
	private Integer price;

	@Column(name = "PRICE_SALE")
	private Integer priceSale;

	@Column(name = "CREATED")
	private Date created;

	@Column(name = "STATUS")
	private Integer status;

	@Transient
	private Integer orderName;

	@Transient
	private int monthOfYear;

	@Transient
	private int count;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public Integer getIdProducer() {
		return idProducer;
	}

	public void setIdProducer(Integer idProducer) {
		this.idProducer = idProducer;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getNumberBuy() {
		return numberBuy;
	}

	public void setNumberBuy(Integer numberBuy) {
		this.numberBuy = numberBuy;
	}

	public Integer getSale() {
		return sale;
	}

	public void setSale(Integer sale) {
		this.sale = sale;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getPriceSale() {
		return priceSale;
	}

	public void setPriceSale(Integer priceSale) {
		this.priceSale = priceSale;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
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

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Integer getOrderName() {
		return orderName;
	}

	public void setOrderName(Integer orderName) {
		this.orderName = orderName;
	}

}
