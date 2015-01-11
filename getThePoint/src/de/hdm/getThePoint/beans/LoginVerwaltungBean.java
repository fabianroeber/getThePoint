package de.hdm.getThePoint.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import de.hdm.getThePoint.bo.AntwortBo;
import de.hdm.getThePoint.bo.FrageBo;
import de.hdm.getThePoint.bo.KategorieBo;
import de.hdm.getThePoint.bo.LehrenderBo;
import de.hdm.getThePoint.bo.StudentBo;
import de.hdm.getThePoint.db.DataAcces;
import de.hdm.getThePoint.db.dbmodel.Admin;
import de.hdm.getThePoint.db.dbmodel.Lehrender;
import de.hdm.getThePoint.db.dbmodel.Student;
import de.hdm.getThePoint.db.mapper.FrageMapper;
import de.hdm.getThePoint.db.mapper.KategorieMapper;
import de.hdm.getThePoint.db.mapper.LehrenderMapper;
import de.hdm.getThePoint.db.mapper.StudentMapper;

@ManagedBean(name = "loginBean")
@RequestScoped @Named
public class LoginVerwaltungBean implements Serializable {

	private LehrenderBo userLehrender;
	@Current Credentials credentials;

	private DataAcces dataAccess;
	private LehrenderMapper lehrenderMapper;
	private List<LehrenderBo> lehrende;

	public void login() {

		dataAccess = new DataAcces();
		lehrenderMapper = new LehrenderMapper();
		getAllLehrende();

		for (LehrenderBo lehrender : lehrende) {
			if (lehrender.getNachname() == credentials.getUsername())
				;
			userLehrender = lehrender;
		}
	}

	public void logout() {
		userLehrender = null;
	}

	public boolean isLoggedIn() {
		return userLehrender != null;
	}

	@Produces
	@LoggedIn
	User getCurrentUser() {
		return userLehrender;
	}

	public void getAllLehrende() {
		lehrende = lehrenderMapper.getModelsAsList(dataAccess.getAllLehrende());
	}

}
