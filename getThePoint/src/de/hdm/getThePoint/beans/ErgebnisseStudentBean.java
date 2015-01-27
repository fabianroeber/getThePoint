package de.hdm.getThePoint.beans;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import de.hdm.getThePoint.bo.ErgebnisBo;
import de.hdm.getThePoint.bo.WissenstestBo;
import de.hdm.getThePoint.db.DataAccess;
import de.hdm.getThePoint.db.mapper.ErgebnisMapper;
import de.hdm.getThePoint.db.mapper.StudentMapper;
import de.hdm.getThePoint.db.mapper.WissenstestMapper;

@ManagedBean(name = "ergebnisseStudentBean")
@ViewScoped
public class ErgebnisseStudentBean implements Serializable {

	private static final long serialVersionUID = 3371866323292299244L;

	private List<WissenstestBo> wissenstests;
	private DataAccess dataAccess;
	private WissenstestBo selectedWissenstest;

	private WissenstestMapper wissenstestMapper;
	private ErgebnisMapper ergebnisMapper;
	private StudentMapper studentMapper;

	private List<ErgebnisBo> ergebnisse;

	@ManagedProperty(value = "#{userBean}")
	private UserBean userBean;

	@ManagedProperty(value = "#{dataAccesBean}")
	public DataAccessBean dataAccessBean;

	public ErgebnisseStudentBean() {
		wissenstestMapper = new WissenstestMapper();
		ergebnisMapper = new ErgebnisMapper();
		studentMapper = new StudentMapper();
	}

	@PostConstruct
	public void init() {
		if (userBean.getStudent() != null) {
			getAllWissenstests();
		}
	}

	public void getAllWissenstests() {

		wissenstests = wissenstestMapper.getModelsAsList(dataAccess
				.getWissentestsByStudentWithErgebnis(studentMapper
						.getDbModel(userBean.getStudent())));

	}

	public void loadErgebnisse() {

	}

	public List<WissenstestBo> getWissenstests() {
		return wissenstests;
	}

	public void setWissenstests(List<WissenstestBo> wissenstests) {
		this.wissenstests = wissenstests;
	}

	public WissenstestBo getSelectedWissenstest() {
		return selectedWissenstest;
	}

	public void setSelectedWissenstest(WissenstestBo selectedWissenstest) {
		this.selectedWissenstest = selectedWissenstest;
	}

	public List<ErgebnisBo> getErgebnisse() {
		return ergebnisse;
	}

	public void setErgebnisse(List<ErgebnisBo> ergebnisse) {
		this.ergebnisse = ergebnisse;
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
