package de.hdm.getThePoint.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.hdm.getThePoint.bo.FrageBo;
import de.hdm.getThePoint.db.DataAcces;
import de.hdm.getThePoint.db.mapper.FrageMapper;

@ManagedBean(name = "frageBean")
@SessionScoped
public class FrageBean implements Serializable {

	String name = "test";

	private static final long serialVersionUID = -7885565449150992040L;

	private FrageMapper frageMapper = new FrageMapper();
	
	private DataAcces dataAccess = new DataAcces();
	
	private List<FrageBo> fragen;
	
	private String antwortm1;
	private String antwortm2;
	private String antwortm3;
	private String antwortm4;
	
	
	public FrageBean() {
		fragen = getAllFragen();
	}

	public List<FrageBo> getAllFragen() {
		return frageMapper.getModelsAsList(dataAccess
				.getAllFrage());
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

	
}
