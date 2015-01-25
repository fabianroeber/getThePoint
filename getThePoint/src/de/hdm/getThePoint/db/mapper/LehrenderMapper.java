package de.hdm.getThePoint.db.mapper;

import java.util.ArrayList;
import java.util.List;

import de.hdm.getThePoint.bo.LehrenderBo;
import de.hdm.getThePoint.db.dbmodel.Lehrender;

public class LehrenderMapper implements
		DbMapperInterface<LehrenderBo, Lehrender> {

	@Override
	public List<LehrenderBo> getModelsAsList(List<Lehrender> dbmodels) {
		List<LehrenderBo> lehrende = new ArrayList<>();
		for (Lehrender lehrender : dbmodels) {
			lehrende.add(getModel(lehrender));
		}
		return lehrende;
	}

	@Override
	public LehrenderBo getModel(Lehrender dbmodel) {
		LehrenderBo lehrenderBo = new LehrenderBo();
		lehrenderBo.setId(dbmodel.getId());
		lehrenderBo.setVorname(dbmodel.getVorname());
		lehrenderBo.setNachname(dbmodel.getName());
		lehrenderBo.setKuerzel(dbmodel.getKuerzel());

		return lehrenderBo;
	}

	@Override
	public Lehrender getDbModel(LehrenderBo model) {
		Lehrender lehrender = new Lehrender();
		lehrender.setId(model.getId());
		lehrender.setVorname(model.getVorname());
		lehrender.setName(model.getNachname());
		lehrender.setKuerzel(model.getKuerzel());
		return lehrender;
	}

}
