package de.hdm.getThePoint.db.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.hdm.getThePoint.bo.ErgebnisBo;
import de.hdm.getThePoint.bo.FrageZuordung;
import de.hdm.getThePoint.bo.WissenstestBo;
import de.hdm.getThePoint.db.dbmodel.Ergebnis;
import de.hdm.getThePoint.db.dbmodel.Wissenstest;
import de.hdm.getThePoint.db.dbmodel.ZuordungWissenstestFrage;

public class WissenstestMapper implements
		DbMapperInterface<WissenstestBo, Wissenstest> {

	LehrenderMapper lehrenderMapper;
	ErgebnisMapper ergebnisMapper;
	FrageMapper frageMapper;

	public WissenstestMapper() {
		lehrenderMapper = new LehrenderMapper();
		ergebnisMapper = new ErgebnisMapper();
		frageMapper = new FrageMapper();
	}

	@Override
	public List<WissenstestBo> getModelsAsList(List<Wissenstest> dbmodels) {
		List<WissenstestBo> models = new ArrayList<WissenstestBo>();
		for (Wissenstest wissenstest : dbmodels) {
			models.add(getModel(wissenstest));
		}
		return models;
	}

	@Override
	public WissenstestBo getModel(Wissenstest dbmodel) {
		WissenstestBo wissenstestBo = new WissenstestBo();
		wissenstestBo.setBearbeitungszeit(dbmodel.getZeitFrage());
		wissenstestBo.setBezeichnung(dbmodel.getBezeichnung());

		switch (dbmodel.getAktiv()) {
		case 'y':
			wissenstestBo.setAktiv(true);
			break;
		case 'n':
			wissenstestBo.setAktiv(false);
		}

		wissenstestBo.setStartzeit(dbmodel.getStartzeit());
		wissenstestBo.setEndzeit(dbmodel.getEndzeit());

		switch (dbmodel.getGestartet()) {
		case 'y':
			wissenstestBo.setGestartet(true);
			break;
		case 'n':
			wissenstestBo.setGestartet(false);
		}

		switch (dbmodel.getRandom()) {
		case 'y':
			wissenstestBo.setRandom(true);
			break;
		case 'n':
			wissenstestBo.setRandom(false);
		}

		wissenstestBo.setId(dbmodel.getId());
		wissenstestBo.setLehrender(lehrenderMapper.getModel(dbmodel
				.getLehrender()));
		List<ErgebnisBo> ergebnisse = new ArrayList<ErgebnisBo>();
		for (Ergebnis ergebnis : dbmodel.getErgebnises()) {
			ergebnisse.add(ergebnisMapper.getModel(ergebnis));
		}

		List<FrageZuordung> fragezuordnungen = new ArrayList<FrageZuordung>();
		for (ZuordungWissenstestFrage zuordungWissenstestFrage : dbmodel
				.getZuordungWissenstestFrages()) {
			fragezuordnungen.add(new FrageZuordung(zuordungWissenstestFrage
					.getId(), frageMapper.getModel(zuordungWissenstestFrage
					.getFrage())));
		}

		wissenstestBo.setFrageZuordungen(fragezuordnungen);

		return wissenstestBo;
	}

	@Override
	public Wissenstest getDbModel(WissenstestBo model) {
		Wissenstest wissenstest = new Wissenstest(model.getId());
		wissenstest.setBezeichnung(model.getBezeichnung());
		wissenstest.setEndzeit(model.getEndzeit());
		wissenstest.setStartzeit(model.getStartzeit());
		wissenstest.setZeitFrage(model.getBearbeitungszeit());
		wissenstest.setLehrender(lehrenderMapper.getDbModel(model
				.getLehrender()));

		if (model.isAktiv()) {
			wissenstest.setAktiv('y');
		} else
			wissenstest.setAktiv('n');

		if (model.isGestartet()) {
			wissenstest.setGestartet('y');
		} else {
			wissenstest.setGestartet('n');
		}
		if (model.isRandom()) {
			wissenstest.setRandom('y');
		} else {
			wissenstest.setRandom('n');
		}

		Set<Ergebnis> ergebnisse = new HashSet<Ergebnis>();

		for (ErgebnisBo ergebnisBo : model.getErgebnisse()) {
			ergebnisse.add(ergebnisMapper.getDbModel(ergebnisBo));
		}

		Set<ZuordungWissenstestFrage> zuordungWissenstestFrages = new HashSet<ZuordungWissenstestFrage>();

		for (FrageZuordung frageZuordung : model.getFrageZuordungen()) {
			zuordungWissenstestFrages.add(new ZuordungWissenstestFrage(
					frageZuordung.getId(), frageMapper.getDbModel(frageZuordung
							.getFrage()), wissenstest));
		}
		return wissenstest;
	}
}
