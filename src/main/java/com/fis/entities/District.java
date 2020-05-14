package com.fis.entities;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "TBL_DISTRICT")
public class District implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "DISTRICT_ID")
	private String districtId;

	@Column(name = "DISTRICT_NAME")
	private String districtName;

	@Column(name = "PROVINCE_ID")
	private String provinceId;

	public District() {
	}

	public String getDistrictId() {
		return this.districtId;
	}

	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}

	public String getDistrictName() {
		return this.districtName;
	}

	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}

	public String getProvinceId() {
		return this.provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

}