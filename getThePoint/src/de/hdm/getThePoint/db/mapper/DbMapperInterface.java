package de.hdm.getThePoint.db.mapper;

import java.util.List;

import de.hdm.getThePoint.bo.BusinessObject;
import de.hdm.getThePoint.db.dbmodel.HibernateModel;

/**
 * Generisches Interface, dass Methoden zum Umwandeln eines
 * {@link HibernateModel} in ein {@link BusinessObject} bereitstellt. Ein
 * solches Mapping ist notwendig, da GWT nicht mit Hibernate/JPA
 * Datenbankobjekten umgehen kann.
 * 
 * @author Fabian
 *
 * @param <Model>
 * @param <DbModel>
 */
public interface DbMapperInterface<Model extends BusinessObject, DbModel extends HibernateModel> {

	/**
	 * Diese Methode mappt eine Menge von {@link HibernateModel} in eine Menge
	 * von {@link BusinessObject}. von
	 * 
	 * @param dbmodels
	 * @return Liste von {@link BusinessObject}
	 */
	public List<Model> getModelsAsList(List<DbModel> dbmodels);

	/**
	 * 
	 * Diese Methode mappt ein {@link HibernateModel} in ein
	 * {@link BusinessObject}.
	 * 
	 * @param dbmodel
	 * @return {@link BusinessObject}
	 */
	public Model getModel(DbModel dbmodel);

	/**
	 * Diese Methode mappt ein {@link BusinessObject} in ein
	 * {@link HibernateModel}.
	 * 
	 * @param model
	 * @return {@link HibernateModel}
	 */
	public DbModel getDbModel(Model model);

}
