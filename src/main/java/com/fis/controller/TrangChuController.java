package com.fis.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.DatatypeConverter;

import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.fis.dao.TrangChuDAO;
import com.fis.entities.Product;

@ManagedBean(name = "trangChuController")
@ViewScoped
public class TrangChuController implements Serializable{
	private static final long serialVersionUID = 1L;
	private Product cate = new Product();
	private TrangChuDAO service = new TrangChuDAO();
	private List<Product> listBestSale = new ArrayList<>();
	private List<Product> listData = new ArrayList<>();
	private List<Product> searchList = new ArrayList<>();

	private UploadedFile file;
	private String keyword;
	private String bFileName;
	private byte[] bFile;
	private StreamedContent fileExport;
	private int flag;
	private int catId;
	private String name;
	private int orderStt;

	@PostConstruct
	public void init() {
		onShowListBestSale();
		onShowAll();
	}

	public String getProductImage(byte[] input) {
		try {
			return DatatypeConverter.printBase64Binary(input);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void Reset() {
		cate = new Product();
	}

	public void onShowListBestSale() {
		listBestSale = service.onShowData();
	}

	public void onShowAll() {
		listData = service.ListData();
	}

	public void onSearchProduct() {
		Product search = new Product();
		search.setName(name);
		search.setOrderName(orderStt);
		search.setCatId(catId);
		listData = service.SearchListData(search);
	}

	public void resetDialogForm() {
		cate = new Product();
		name = null;
		orderStt = 0;
		catId = 0;
	}

	public Product getCate() {
		return cate;
	}

	public void setCate(Product cate) {
		this.cate = cate;
	}

	public TrangChuDAO getService() {
		return service;
	}

	public void setService(TrangChuDAO service) {
		this.service = service;
	}

	public List<Product> getListBestSale() {
		return listBestSale;
	}

	public void setListBestSale(List<Product> listBestSale) {
		this.listBestSale = listBestSale;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public String getbFileName() {
		return bFileName;
	}

	public void setbFileName(String bFileName) {
		this.bFileName = bFileName;
	}

	public byte[] getbFile() {
		return bFile;
	}

	public void setbFile(byte[] bFile) {
		this.bFile = bFile;
	}

	public StreamedContent getFileExport() {
		return fileExport;
	}

	public void setFileExport(StreamedContent fileExport) {
		this.fileExport = fileExport;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public List<Product> getListData() {
		return listData;
	}

	public void setListData(List<Product> listData) {
		this.listData = listData;
	}

	public List<Product> getSearchList() {
		return searchList;
	}

	public void setSearchList(List<Product> searchList) {
		this.searchList = searchList;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

	public int getOrderStt() {
		return orderStt;
	}

	public void setOrderStt(int orderStt) {
		this.orderStt = orderStt;
	}

}
