/**
 * 
 */
package com.tmm.enterprise.microblog.core.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.ToDo;


/**
 * @author robert.hinds
 *
 */
@Repository(value="todoDAO")
public class ToDoDAO extends GenericHibernateDAO<ToDo, Long> implements IToDoDAO{
	
	
	/* (non-Javadoc)
	 * @see com.tmm.enterprise.microblog.core.dao.IToDoDAO#loadAllTodosByUser(com.tmm.enterprise.microblog.domain.Contactable)
	 */
	@SuppressWarnings("unchecked")
	public List<ToDo> loadAllTodosByUser(Contactable user)
	{
		Query query = getEntityManager().createQuery("select t from ToDo t where t.raisedBy = ?1");
		query.setParameter(1, user);
		List<ToDo> todos = (List<ToDo>) query.getResultList();
		return todos;
	}


}
