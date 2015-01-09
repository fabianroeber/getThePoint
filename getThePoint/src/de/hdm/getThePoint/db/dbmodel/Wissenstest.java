package de.hdm.getThePoint.db.dbmodel;

// Generated 30.12.2014 21:24:32 by Hibernate Tools 4.3.1

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Wissenstest generated by hbm2java
 */
@Entity
@Table(name = "Wissenstest", catalog = "getthepointdb")
public class Wissenstest extends HibernateModel {

	private static final long serialVersionUID = 7719728256949700400L;
	private int id;
	private Lehrender lehrender;
	private Date startzeit;
	private Date endzeit;
	private Integer zeitFrage;
	private Character random;
	private Character starttyp;
	private Character gestartet;
	private Character aktiv;
	private String bezeichnung;
	private Set<ZuordungWissenstestFrage> zuordungWissenstestFrages = new HashSet<ZuordungWissenstestFrage>(
			0);
	private Set<Ergebnis> ergebnises = new HashSet<Ergebnis>(0);

	public Wissenstest() {
	}

	public Wissenstest(int id) {
		this.id = id;
	}

	public Wissenstest(int id, Lehrender lehrender, Date startzeit,
			Date endzeit, Integer zeitFrage, Character random,
			Character starttyp, Character gestartet, Character aktiv,
			String bezeichnung,
			Set<ZuordungWissenstestFrage> zuordungWissenstestFrages,
			Set<Ergebnis> ergebnises) {
		this.id = id;
		this.lehrender = lehrender;
		this.startzeit = startzeit;
		this.endzeit = endzeit;
		this.zeitFrage = zeitFrage;
		this.random = random;
		this.starttyp = starttyp;
		this.gestartet = gestartet;
		this.aktiv = aktiv;
		this.bezeichnung = bezeichnung;
		this.zuordungWissenstestFrages = zuordungWissenstestFrages;
		this.ergebnises = ergebnises;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
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

	@Temporal(TemporalType.DATE)
	@Column(name = "startzeit", length = 10)
	public Date getStartzeit() {
		return this.startzeit;
	}

	public void setStartzeit(Date startzeit) {
		this.startzeit = startzeit;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "endzeit", length = 10)
	public Date getEndzeit() {
		return this.endzeit;
	}

	public void setEndzeit(Date endzeit) {
		this.endzeit = endzeit;
	}

	@Column(name = "zeit_frage")
	public Integer getZeitFrage() {
		return this.zeitFrage;
	}

	public void setZeitFrage(Integer zeitFrage) {
		this.zeitFrage = zeitFrage;
	}

	@Column(name = "random", length = 1)
	public Character getRandom() {
		return this.random;
	}

	public void setRandom(Character random) {
		this.random = random;
	}

	@Column(name = "starttyp", length = 1)
	public Character getStarttyp() {
		return this.starttyp;
	}

	public void setStarttyp(Character starttyp) {
		this.starttyp = starttyp;
	}

	@Column(name = "gestartet", length = 1)
	public Character getGestartet() {
		return this.gestartet;
	}

	public void setGestartet(Character gestartet) {
		this.gestartet = gestartet;
	}

	@Column(name = "aktiv", length = 1)
	public Character getAktiv() {
		return this.aktiv;
	}

	public void setAktiv(Character aktiv) {
		this.aktiv = aktiv;
	}

	@Column(name = "bezeichnung", length = 45)
	public String getBezeichnung() {
		return this.bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "wissenstest")
	public Set<ZuordungWissenstestFrage> getZuordungWissenstestFrages() {
		return this.zuordungWissenstestFrages;
	}

	public void setZuordungWissenstestFrages(
			Set<ZuordungWissenstestFrage> zuordungWissenstestFrages) {
		this.zuordungWissenstestFrages = zuordungWissenstestFrages;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "wissenstest")
	public Set<Ergebnis> getErgebnises() {
		return this.ergebnises;
	}

	public void setErgebnises(Set<Ergebnis> ergebnises) {
		this.ergebnises = ergebnises;
	}

}
