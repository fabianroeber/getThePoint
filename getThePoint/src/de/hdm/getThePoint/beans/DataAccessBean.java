package de.hdm.getThePoint.beans;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import de.hdm.getThePoint.db.DataAccess;

/**
 * Dieses Bean stellt den Datenbankzugriff bereit.
 * 
 * @author Fabian
 *
 */
@ManagedBean(name = "dataAccesBean")
@ApplicationScoped
public class DataAccessBean implements Serializable {

	private static final long serialVersionUID = 6527490600640418751L;
	private DataAccess dataAccess;

	public DataAccessBean() {
		dataAccess = new DataAccess();
	}

	public DataAccess getDataAccess() {
		return dataAccess;
	}

	public void setDataAccess(DataAccess dataAccess) {
		this.dataAccess = dataAccess;
	}

}
