package de.hdm.getThePoint.db.mapper;

import java.util.ArrayList;
import java.util.List;

import de.hdm.getThePoint.bo.ErgebnisBo;
import de.hdm.getThePoint.db.dbmodel.Ergebnis;

public class ErgebnisMapper implements DbMapperInterface<ErgebnisBo, Ergebnis> {

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
		//TODO
		return null;
	}

	@Override
	public Ergebnis getDbModel(ErgebnisBo model) {
		// TODO Auto-generated method stub
		return null;
	}

}
