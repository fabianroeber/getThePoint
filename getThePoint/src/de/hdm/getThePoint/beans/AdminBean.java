package de.hdm.getThePoint.beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import de.hdm.getThePoint.bo.LehrenderBo;
import de.hdm.getThePoint.db.mapper.LehrenderMapper;

/**
 * Diese Bean stellt alle notwendigen Daten f&uuml;r die Verwaltung der
 * Lehrenden bereit.
 * 
 * @author Fabian
 *
 */
@ManagedBean(name = "adminBean")
@ViewScoped
public class AdminBean {

	private List<LehrenderBo> lehrende;

	private LehrenderMapper lehrenderMapper;

	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	@ManagedProperty(value = "#{dataAccessBean}")
	private DataAccessBean dataAccessBean;

	public AdminBean() {
		lehrenderMapper = new LehrenderMapper();
	}

	@PostConstruct
	public void init() {

	}

	public void getAllLehrende() {
		lehrende = lehrenderMapper.getModelsAsList(dataAccessBean
				.getDataAccess().getAllLehrende());
	}

	public void saveLehrender() {

	}

	public void deleteLehrender() {

	}

	public List<LehrenderBo> getLehrende() {
		return lehrende;
	}

	public void setLehrende(List<LehrenderBo> lehrende) {
		this.lehrende = lehrende;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

	public DataAccessBean getDataAccessBean() {
		return dataAccessBean;
	}

	public void setDataAccessBean(DataAccessBean dataAccessBean) {
		this.dataAccessBean = dataAccessBean;
	}

}
