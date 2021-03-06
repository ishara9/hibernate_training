package se.cambio.training.app;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import se.cambio.training.entities.AbstractEntity;
import se.cambio.training.util.HibernateUtil;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.Connection;
import java.util.List;

/**
 * @author MJameel
 * @since on 7/17/2017.
 */
public abstract class AbstractApp
{
	private final HibernateUtil hibernateUtil;

	public AbstractApp(final String configFile) {
		this.hibernateUtil = HibernateUtil.getInstance(configFile);
	}

	Session getCurrentSession()
	{
		return hibernateUtil.getCurrentSession();
	}

	/**
	 * see {@linkplain //www.java2blog.com/2016/07/difference-opensession-getcurrentsession-hibernate.html}
	 * Open a {@link Session}.
	 * <p/>
	 * JDBC {@link Connection connection(s} will be obtained from the
	 * configured {@link org.hibernate.engine.jdbc.connections.spi.ConnectionProvider} as needed
	 * to perform requested work.
	 *
	 * @return The created session.
	 *
	 * @throws HibernateException Indicates a problem opening the session; pretty rare here.
	 */
	Session openSession()
	{
		return hibernateUtil.openSession();
	}

	/**
	 * saves an entity
	 * @param entity to be saved
	 */
	void persist(AbstractEntity entity)
	{
		getCurrentSession().save(entity);
	}

	/**
	 * Deletes an entity
	 * @param entity to be deleted
	 */
	void delete(AbstractEntity entity)
	{
		getCurrentSession().delete(entity);
	}

	/**
	 * Updates an entity
	 * @param entity to be updated
	 */
	void update(AbstractEntity entity)
	{
		getCurrentSession().update(entity);
	}

	/**
	 * Fetch an entity from the table
	 * @param entityClass the class of the entity
	 * @param id to fetch from
	 * @param <T> type of the return object
	 * @return fetched object from database
	 */
	<T> T get (Class<T> entityClass,long id)
	{
		return getCurrentSession().get(entityClass,id);
	}

	/**
	 * Load an entity
	 * @param entityClass the class of the entity
	 * @param id to fetch from
	 * @param <T> type of the return object
	 * @return fetched object from database
	 */
	<T> T load (Class<T> entityClass,long id)
	{
		return getCurrentSession().get(entityClass,id);
	}

	/**
	 * Load an a collection of entities
	 * @param entityClass the class of the entity
	 * @param id to fetch from
	 * @param <T> type of the return object
	 * @return fetched object from database
	 */
	<T> List<T> getAll (Class<T> entityClass,long id)
	{
		CriteriaBuilder criteriaBuilder = getCurrentSession().getCriteriaBuilder();

		CriteriaQuery<T> query = criteriaBuilder.createQuery(entityClass);
		TypedQuery<T> typedQuery = getCurrentSession().createQuery(query);

		return typedQuery.getResultList();
	}
}
