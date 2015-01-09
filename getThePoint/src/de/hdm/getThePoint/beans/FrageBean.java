package de.hdm.getThePoint.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name = "frageBean")
@SessionScoped
public class FrageBean implements Serializable {

	String name = "test";

	private static final long serialVersionUID = -7885565449150992040L;

	public List<String> getList() {
		List<String> list = new ArrayList<String>();
		list.add("hallo");
		list.add("test");
		list.add("funktioniert");

		return list;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
