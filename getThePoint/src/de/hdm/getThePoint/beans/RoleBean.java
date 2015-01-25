package de.hdm.getThePoint.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "roleBean")
@SessionScoped
public class RoleBean {

	@ManagedProperty(value = "#{userBean}")
	UserBean userBean;

	public boolean hasStudenRole() {
		if (getUserBean().isLoggedIn() && getUserBean().getStudent() != null) {
			return true;
		}
		return false;
	}

	public boolean hasLehrenderRole() {
		if (getUserBean().isLoggedIn() && getUserBean().getLehrender() != null) {
			return true;
		}
		return false;
	}

	public boolean hasAdminRole() {
		if (getUserBean().isLoggedIn() && getUserBean().getAdmin() != null) {
			return true;
		}
		return false;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

}
