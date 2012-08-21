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
@Repository(value="personDAO")
public class PersonDAO extends GenericHibernateDAO<Person, Long> implements IPersonDAO{
	
	/* (non-Javadoc)
	 * @see com.tmm.enterprise.microblog.core.dao.IPersonDAO#loadAllPersons()
	 */
	@SuppressWarnings("unchecked")
	public List<Person> loadAllPersons()
	{
		Query query = getEntityManager().createQuery("select s from Person s");
		List<Person> s = (List<Person>) query.getResultList();
		return s;
	}


	@Override
	public List<Person> loadPeople(Long[] ids) {
		if(ids.length ==0 )
		{
			return new ArrayList<Person>(); 
		}
		Query query = getEntityManager().createQuery("select c from Contactable c where c.id in ("+createSqlArgs(ids.length)+")");
		int i = 0 ; 
		for(Long id : ids)
		{
			query.setParameter(i++, id); 
		}
		return query.getResultList();
	}
	
	
	private String createSqlArgs(int length)
	{
		String[] builder = new String[length]; 
		for(int i = 0 ; i < length ;i++)
		{
			builder[i] = "?"+i; 
		}
		return StringUtils.join(builder,",");
	}
}
