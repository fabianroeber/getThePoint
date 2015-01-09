package de.hdm.getThePoint.db.mapper;

import java.util.ArrayList;
import java.util.List;

import de.hdm.getThePoint.bo.AntwortBo;
import de.hdm.getThePoint.db.dbmodel.Antwort;

public class AntwortMapper implements DbMapperInterface<AntwortBo, Antwort> {

	@Override
	public List<AntwortBo> getModelsAsList(List<Antwort> dbmodels) {
		List<AntwortBo> antworten = new ArrayList<>();
		for (Antwort antwort : dbmodels) {
			antworten.add(getModel(antwort));
		}
		return antworten;
	}

	@Override
	public AntwortBo getModel(Antwort dbmodel) {
		AntwortBo antwortBo = new AntwortBo();
		antwortBo.setId(dbmodel.getId());
		antwortBo.setText(dbmodel.getText());

		return antwortBo;
	}

	@Override
	public Antwort getDbModel(AntwortBo model) {
		Antwort antwort = new Antwort();
		antwort.setId(model.getId());
		antwort.setText(model.getText());

		return antwort;
	}

}
