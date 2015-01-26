package de.hdm.getThePoint.beans;

import java.io.Serializable;
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
public class WissenstestBean implements Serializable {

	private static final long serialVersionUID = 4292557396761753803L;
	List<WissenstestBo> wissenstests;

	public List<WissenstestBo> getWissenstests() {
		return wissenstests;
	}

	public void setWissenstests(List<WissenstestBo> wissenstests) {
		this.wissenstests = wissenstests;
	}

}
