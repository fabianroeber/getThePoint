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
import de.hdm.getThePoint.db.DataAcces;
import de.hdm.getThePoint.db.mapper.FrageMapper;
import de.hdm.getThePoint.db.mapper.KategorieMapper;

@ManagedBean(name = "lehrenderStudierenderBean")
@RequestScoped
public class Credentials implements Serializable {

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
