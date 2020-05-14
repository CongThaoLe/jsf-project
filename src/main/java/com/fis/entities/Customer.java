package com.fis.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_CUSTOMERS")
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "FULLNAME")
	private String fullName;

	@Column(name = "USERNAME")
	private String userName;

	@Column(name = "PASSWORD")
	private String passWord;

	@Column(name = "GENDER")
	private Integer gender;

	@Column(name = "BIRTHDAY")
	private Date birthDay;

	@Column(name = "ADDRESS")
	private String address;

	@Column(name = "PHONE")
	private String phone;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "CREATED")
	private Date created;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "PROVINCE")
	private Integer tinhTp;

	@Column(name = "DISTRICT")
	private Integer quanHuyen;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getEmail() {
		return email;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

}
