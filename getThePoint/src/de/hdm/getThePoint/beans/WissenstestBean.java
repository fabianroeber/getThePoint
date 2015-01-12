package de.hdm.getThePoint.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import de.hdm.getThePoint.db.dbmodel.Wissenstest;

@ManagedBean(name = "wissenstestBean")
@ViewScoped
public class WissenstestBean {

	private List<Wissenstest> wissenstests;

	public List<Wissenstest> getWissenstests() {
		return wissenstests;
	}

	public void setWissenstests(List<Wissenstest> wissenstests) {
		this.wissenstests = wissenstests;
	}

}
