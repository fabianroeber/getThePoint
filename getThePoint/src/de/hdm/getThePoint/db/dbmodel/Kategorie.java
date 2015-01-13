package de.hdm.getThePoint.db.dbmodel;

// Generated 30.12.2014 21:24:32 by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Kategorie generated by hbm2java
 */
@Entity
@Table(name = "Kategorie", catalog = "getthepointdb")
public class Kategorie extends HibernateModel {

	private static final long serialVersionUID = -8572549950770548904L;
	private Integer id;
	private Lehrender lehrender;
	private String bezeichnung;
	private Set<Frage> frages = new HashSet<Frage>(0);

	public Kategorie() {
	}

	public Kategorie(Integer id) {
		this.id = id;
	}

	public Kategorie(Integer id, Lehrender lehrender, String bezeichnung,
			Set<Frage> frages) {
		this.id = id;
		this.lehrender = lehrender;
		this.bezeichnung = bezeichnung;
		this.frages = frages;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lehrender_id")
	public Lehrender getLehrender() {
		return this.lehrender;
	}

	public void setLehrender(Lehrender lehrender) {
		this.lehrender = lehrender;
	}

	@Column(name = "bezeichnung", length = 45)
	public String getBezeichnung() {
		return this.bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "kategorie")
	public Set<Frage> getFrages() {
		return this.frages;
	}

	public void setFrages(Set<Frage> frages) {
		this.frages = frages;
	}

}
