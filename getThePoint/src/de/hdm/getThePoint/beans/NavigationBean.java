package de.hdm.getThePoint.beans;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Bean f&uuml;r Navigationsaktionen
 * 
 * @author Fabian
 *
 */
@ManagedBean(name = "navigationBean")
@SessionScoped
public class NavigationBean implements Serializable {

	private static final long serialVersionUID = -8846272727588806000L;

	/**
	 * Redirect to login page.
	 * 
	 * @return Login page name.
	 */
	public String redirectToLogin() {
		return "/login.xhtml?faces-redirect=true";
	}

	/**
	 * Go to login page.
	 * 
	 * @return Login page name.
	 */
	public String toLogin() {
		return "/login.xhtml";
	}

	/**
	 * Redirect to welcome page.
	 * 
	 * @return Welcome page name.
	 */
	public String redirectToWelcome() {
		return "/secured/wissenstest.xhtml?faces-redirect=true";
	}

	public String redirectToAdmin() {
		return "/secured/lehrende.xhtml?faces-redirect=true";
	}

	public String toAdmin() {
		return "/secured/lehrende.xhtml";
	}

	public String redirectToMobileLogin() {
		return "login_mobile.xhtml?faces-redirect=true";
	}

	public String redirectToMobileLogout() {
		return "login_mobile.xhtml?faces-redirect=true";
	}

	public String redirectToMobileWelcome() {

		return "/secured/wissenstest_mobile.xhtml?faces-redirect=true";
	}

	/**
	 * Go to welcome page.
	 * 
	 * @return Welcome page name.
	 */
	public String toWelcome() {
		return "/secured/fragenpool.xhtml";
	}

	/**
	 * Go to logout page.
	 * 
	 * @return
	 */
	public String redirectToLogout() {
		return "/logout.xhtml?faces-redirect=true";
	}

	public String redirectToWelcomeVeraltung() {
		return "/secured/wissenstestVerwaltung.xhtml?faces-redirect=true";
	}
}
