package de.hdm.getThePoint.db.mapper;

import java.util.ArrayList;
import java.util.List;

import de.hdm.getThePoint.bo.ErgebnisBo;
import de.hdm.getThePoint.db.dbmodel.Ergebnis;

public class ErgebnisMapper implements DbMapperInterface<ErgebnisBo, Ergebnis> {

	private StudentMapper studentMapper;
	private FrageMapper frageMapper;
	private AntwortMapper antwortMapper;

	public ErgebnisMapper() {
		studentMapper = new StudentMapper();
		frageMapper = new FrageMapper();
		antwortMapper = new AntwortMapper();
	}

	@Override
	public List<ErgebnisBo> getModelsAsList(List<Ergebnis> dbmodels) {
		List<ErgebnisBo> ergebnisse = new ArrayList<ErgebnisBo>();
		for (Ergebnis ergebnis : dbmodels) {
			ergebnisse.add(getModel(ergebnis));
		}
		return ergebnisse;
	}

	@Override
	public ErgebnisBo getModel(Ergebnis dbmodel) {
		ErgebnisBo ergebnisBo = new ErgebnisBo();
		ergebnisBo.setId(dbmodel.getId());
		ergebnisBo.setAntwort(antwortMapper.getModel(dbmodel.getAntwort()));
		ergebnisBo.setFrage(frageMapper.getModel(dbmodel.getFrage()));
		ergebnisBo.setStudent(studentMapper.getModel(dbmodel.getStudent()));
		if (dbmodel.getRichtig() == 'y') {
			ergebnisBo.setRichtig(true);
		} else {
			ergebnisBo.setRichtig(false);
		}
		return ergebnisBo;
	}

	@Override
	public Ergebnis getDbModel(ErgebnisBo model) {
		Ergebnis ergebnis = new Ergebnis(model.getId());
		ergebnis.setAntwort(antwortMapper.getDbModel(model.getAntwort()));
		ergebnis.setFrage(frageMapper.getDbModel(model.getFrage()));
		ergebnis.setStudent(studentMapper.getDbModel(model.getStudent()));
		if (model.isRichtig()) {
			ergebnis.setRichtig('y');
		} else {
			ergebnis.setRichtig('n');
		}
		return ergebnis;
	}

}
