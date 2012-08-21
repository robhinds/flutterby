/**
 * 
 */
package com.tmm.enterprise.microblog.core.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.Person;


/**
 * @author robert.hinds
 *
 */
@Repository(value="contactableDAO")
public class ContactableDAO extends GenericHibernateDAO<Contactable, Long> implements IContactableDAO{
	
	/* (non-Javadoc)
	 * @see com.tmm.enterprise.microblog.core.dao.IContactableDAO#loadAllContactables()
	 */
	@SuppressWarnings("unchecked")
	public List<Contactable> loadAllContactables()
	{
		Query query = getEntityManager().createQuery("select c from Contactable c");
		List<Contactable> s = (List<Contactable>) query.getResultList();
		return s;
	}

	
}
