package com.fis.controller;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.xml.bind.DatatypeConverter;

import org.apache.poi.util.IOUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.fis.dao.QLSanPhamDAO;
import com.fis.entities.Product;
import com.fis.utils.BaseController;
import com.fis.utils.Constants;
import com.fis.utils.ExecuteObjectController;
import com.fis.utils.Utils;

@ManagedBean(name = "qlSanPhamController")
@ViewScoped
public class QLSanPhamController extends BaseController implements Serializable, ExecuteObjectController<Product> {
	private static final long serialVersionUID = 1L;
	private Product cate = new Product();
	private QLSanPhamDAO service = new QLSanPhamDAO();
	private List<Product> list = new ArrayList<Product>();
	private List<Product> listBestSale = new ArrayList<Product>();
	private UploadedFile file;
	private String bFileName;
	private byte[] bFile;
	private StreamedContent fileExport;
	private int flag;

	@PostConstruct
	public void init() {
		checkUserIsLogin();
		onShowData();
	}

	@Override
	public void onShowData() {
		list = service.onShowData();
	}

	@Override
	public void onSearch() {
		list = service.onSearchData(cate);
	}

	@Override
	public void prepareInsert() {
		flag = 1;
		cate = new Product();
	}

	@Override
	public void onInsert() {
		cate.setImage(bFile);
		if (service.InsertData(cate)) {
			Utils.addMessage(Constants.INS_SUCCESS);
			onShowData();
			RequestContext.getCurrentInstance().execute("PF('dlgAddAcc').hide()");
		} else {
			Utils.errMessage(Constants.INS_FAIL);
		}
	}

	public void onUpdate(int entity) {
		flag = 2;
		cate = service.reloadProduct(entity);
		RequestContext.getCurrentInstance().update("dlgAddAcc");
	}

	@Override
	public void onComfirmUpdate() {
		cate.setImage(bFile);
		if (service.UpdateData(cate)) {
			Utils.addMessage(Constants.UPD_SUCCESS);
			onShowData();
			RequestContext.getCurrentInstance().execute("PF('dlgAddAcc').hide()");
			cate = new Product();
		} else {
			Utils.errMessage(Constants.UPD_FAIL);
		}
	}

	@Override
	public void onDelete(Product entity) {
		if (service.DeleteData(entity)) {
			Utils.addMessage(Constants.DEL_SUCCESS);
			onShowData();
		} else {
			Utils.errMessage(Constants.INS_FAIL);
		}
	}

	@Override
	public void resetDialogForm() {
		onShowData();
		cate = new Product();
	}

	public void handleUploadFile(FileUploadEvent event) {
		try {
			InputStream ins = event.getFile().getInputstream();
			bFileName = event.getFile().getFileName();
			bFile = IOUtils.toByteArray(ins);
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getProductImage(byte[] input) {
		try {
			return DatatypeConverter.printBase64Binary(input);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Product getCate() {
		return cate;
	}

	public void setCate(Product cate) {
		this.cate = cate;
	}

	public List<Product> getList() {
		return list;
	}

	public void setList(List<Product> list) {
		this.list = list;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
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

	public List<Product> getListBestSale() {
		return listBestSale;
	}

	public void setListBestSale(List<Product> listBestSale) {
		this.listBestSale = listBestSale;
	}

	@Override
	public void onUpdate(Product entity) {
		// TODO Auto-generated method stub

	}

}
