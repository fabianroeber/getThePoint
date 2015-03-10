package de.hdm.getThePoint.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

import de.hdm.getThePoint.bo.GTPMenuItem;

@ManagedBean(name = "menuBean")
@RequestScoped
public class MenuBean implements Serializable {

	private static final long serialVersionUID = 5919604358733105104L;

	private MenuModel menu = new DefaultMenuModel();

	@ManagedProperty(value = "#{userBean}")
	UserBean userBean;

	private List<GTPMenuItem> menuVorlage;

	@PostConstruct
	public void init() {
		fillMenu();

		for (GTPMenuItem item : menuVorlage) {

			DefaultMenuItem defaultMenuItem = new DefaultMenuItem();
			defaultMenuItem.setOutcome(item.getOutcome());
			defaultMenuItem.setValue(item.getLabel());
			defaultMenuItem.setParam("i", item.getId());

			menu.addElement(defaultMenuItem);
		}

	}

	/**
	 * Methode, um das Menu aufzubauen.
	 */
	private void fillMenu() {

		menuVorlage = new ArrayList<GTPMenuItem>();

		if (hasStudenRole()) {
			menuVorlage.add(new GTPMenuItem(0, "/secured/wissenstest.xhtml",
					"Wissenstest"));
			menuVorlage.add(new GTPMenuItem(1,
					"/secured/ergebnisse_student.xhtml", "Ergebnisse"));
		}
		if (hasLehrenderRole()) {
			
			menuVorlage.add(new GTPMenuItem(0,
					"/secured/wissenstestVerwaltung.xhtml",
					"Verwaltung"));
			menuVorlage.add(new GTPMenuItem(1,
					"/secured/wissenstests_monitor.xhtml",
					"Monitor"));
			menuVorlage.add(new GTPMenuItem(2, "/secured/fragenpool.xhtml",
					"Fragenpool"));
			menuVorlage.add(new GTPMenuItem(3, "/secured/auswertungen.xhtml",
					"Auswertungen"));

		}
		if (hasAdminRole()) {
			menuVorlage.add(new GTPMenuItem(2, "/secured/lehrende.xhtml",
					"Lehrende"));
		}
	}

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

	public MenuModel getMenu() {
		return menu;
	}

	public void setMenu(MenuModel menu) {
		this.menu = menu;
	}

	public UserBean getUserBean() {
		return userBean;
	}

	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}

}
