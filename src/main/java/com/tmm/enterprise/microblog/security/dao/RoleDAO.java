/**
 * 
 */
package com.tmm.enterprise.microblog.security.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.enterprise.microblog.core.dao.GenericHibernateDAO;
import com.tmm.enterprise.microblog.security.Role;


/**
 * @author robert.hinds
 *
 */
@Repository(value="roleDAO")
public class RoleDAO extends GenericHibernateDAO<Role, Long>{
	
	@Transactional
	public Role loadRoleByAuthority(String authority)
	{
		Query query = getEntityManager().createQuery("select u from Role u where u.role = ?1");
		query.setParameter(1, authority);
		
		try{
			return (Role)query.getSingleResult();
		}catch (Exception e)
		{
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Role> loadAllUsers()
	{
		Query query = getEntityManager().createQuery("select r from Account r");
		return (List<Role>) query.getResultList();
	}
}
