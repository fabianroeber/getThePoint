package de.hdm.getThePoint.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.PersistenceException;

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

	private String kuerzel;

	private String name;

	private String vorname;

	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	@ManagedProperty(value = "#{dataAccesBean}")
	private DataAccessBean dataAccessBean;

	public AdminBean() {
		lehrenderMapper = new LehrenderMapper();
	}

	@PostConstruct
	public void init() {
		getAllLehrende();
	}

	public void getAllLehrende() {
		lehrende = lehrenderMapper.getModelsAsList(dataAccessBean
				.getDataAccess().getAllLehrende());
	}

	public void addLehrender() {
		LehrenderBo lehrender = new LehrenderBo();
		lehrender.setKuerzel(kuerzel);
		lehrender.setVorname(vorname);
		lehrender.setNachname(name);
		if (lehrende == null) {
			lehrende = new ArrayList<LehrenderBo>();
		}
		lehrende.add(lehrender);
		name = null;
		kuerzel = null;
		vorname = null;
	}

	public void saveLehrende() {
		try {
			for (LehrenderBo lehrenderBo : lehrende) {
				dataAccessBean.getDataAccess().saveLehrender(
						lehrenderMapper.getDbModel(lehrenderBo));
			}

		} catch (PersistenceException e) {
			e.printStackTrace();
			System.out.print("Fehler beim Speichern der Lehrenden");
			// TODO FacesMessages
		}

	}

	public void deleteLehrender(int index) {
		lehrende.remove(index);
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

	public String getKuerzel() {
		return kuerzel;
	}

	public void setKuerzel(String kuerzel) {
		this.kuerzel = kuerzel;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public DataAccessBean getDataAccessBean() {
		return dataAccessBean;
	}

	public void setDataAccessBean(DataAccessBean dataAccessBean) {
		this.dataAccessBean = dataAccessBean;
	}

}
