package de.hdm.getThePoint.beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import de.hdm.getThePoint.bo.WissenstestBo;

/**
 * Diese Bean stellt Daten f&uuml;r ein Quiz bereit und verarbeitet die
 * Benutzerangaben.
 * 
 * @author Fabian
 *
 */
@ManagedBean(name = "wissenstestBean")
@ViewScoped
public class WissenstestBean {

	List<WissenstestBo> wissenstests;

	public List<WissenstestBo> getWissenstests() {
		return wissenstests;
	}

	public void setWissenstests(List<WissenstestBo> wissenstests) {
		this.wissenstests = wissenstests;
	}

}
