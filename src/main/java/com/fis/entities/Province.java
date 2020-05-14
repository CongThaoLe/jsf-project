package com.fis.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_PROVINCE")
public class Province implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PROVINCE_ID")
	private String provinceId;

	@Column(name = "PROVINCE_NAME")
	private String provinceName;

	public Province() {
	}

	public String getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

}