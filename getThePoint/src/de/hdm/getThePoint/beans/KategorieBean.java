package de.hdm.getThePoint.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.hdm.getThePoint.bo.KategorieBo;
import de.hdm.getThePoint.db.DataAcces;
import de.hdm.getThePoint.db.mapper.KategorieMapper;

@ManagedBean(name = "kategorieBean")
@SessionScoped
public class KategorieBean implements Serializable {

	private static final long serialVersionUID = -7885565449150992040L;

	private KategorieMapper kategorieMapper = new KategorieMapper();
	
	private DataAcces dataAccess = new DataAcces();
	
	private List<KategorieBo> kategorien;
	
	private String bezeichnung;
	
	
	public KategorieBean() {
		kategorien = getAllKategorien();
	}
	
	public List<KategorieBo> getKategorien() {
		return kategorien;
	}

	public void setKategorien(List<KategorieBo> kategorien) {
		this.kategorien = kategorien;
	}

	public List<KategorieBo> getAllKategorien() {
		return kategorieMapper.getModelsAsList(dataAccess.getAllKategorie());
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	

	
}
