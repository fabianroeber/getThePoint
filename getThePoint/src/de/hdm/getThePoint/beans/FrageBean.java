package de.hdm.getThePoint.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import de.hdm.getThePoint.bo.AntwortBo;
import de.hdm.getThePoint.bo.FrageBo;
import de.hdm.getThePoint.bo.KategorieBo;
import de.hdm.getThePoint.db.DataAcces;
import de.hdm.getThePoint.db.mapper.FrageMapper;
import de.hdm.getThePoint.db.mapper.KategorieMapper;

@ManagedBean(name = "frageBean")
@ViewScoped
public class FrageBean implements Serializable {

	String name = "test";

	private static final long serialVersionUID = -7885565449150992040L;

	private FrageMapper frageMapper;
	private KategorieMapper kategorieMapper;

	private DataAcces dataAccess;

	private List<FrageBo> fragen;
	private String antwortm1;
	private String antwortm2;
	private String antwortm3;
	private String antwortm4;

	private List<KategorieBo> kategorien;
	private String bezeichnung;

	public FrageBean() {
		dataAccess = new DataAcces();
		frageMapper = new FrageMapper();
		kategorieMapper = new KategorieMapper();
		getAllFragen();
		getAllKategorien();

	}

	public void getAllFragen() {
		fragen = frageMapper.getModelsAsList(dataAccess.getAllFrage());
	}

	public void addFrage() {
		FrageBo frage = new FrageBo();
		frage.setText("Hier Frage eingeben");
		frage.setAntwortmoeglichkeiten(new ArrayList<AntwortBo>());
		fragen.add(frage);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAntwortm1() {
		return fragen.get(0).getText();
	}

	public void setAntwortm1(String antwortm1) {
		this.antwortm1 = antwortm1;
	}

	public String getAntwortm2() {
		return fragen.get(1).getText();
	}

	public void setAntwortm2(String antwortm2) {
		this.antwortm2 = antwortm2;
	}

	public String getAntwortm3() {
		return fragen.get(2).getText();
	}

	public void setAntwortm3(String antwortm3) {
		this.antwortm3 = antwortm3;
	}

	public String getAntwortm4() {
		return fragen.get(3).getText();
	}

	public void setAntwortm4(String antwortm4) {
		this.antwortm4 = antwortm4;
	}

	public List<FrageBo> getFragen() {
		return fragen;
	}

	public void setFragen(List<FrageBo> fragen) {
		this.fragen = fragen;
	}

	public List<KategorieBo> getKategorien() {
		return kategorien;
	}

	public void setKategorien(List<KategorieBo> kategorien) {
		this.kategorien = kategorien;
	}

	public void getAllKategorien() {
		kategorien = kategorieMapper.getModelsAsList(dataAccess
				.getAllKategorie());
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

}
