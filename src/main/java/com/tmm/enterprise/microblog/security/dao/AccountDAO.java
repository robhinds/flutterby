/**
 * 
 */
package com.tmm.enterprise.microblog.security.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.enterprise.microblog.core.dao.GenericHibernateDAO;
import com.tmm.enterprise.microblog.security.Account;


/**
 * @author robert.hinds
 *
 */
@Repository(value="accountDAO")
public class AccountDAO extends GenericHibernateDAO<Account, Long>{
	
	@Transactional
	public Account loadAccountByUserName(String userName)
	{
		Query query = getEntityManager().createQuery("select u from Account u where u.userName = ?1");
		query.setParameter(1, userName);
		Account user = null;
		
		try{
			List<Account> users = (List<Account>)query.getResultList();
			if (users==null || users.isEmpty())
			{
				return null;
			}
			else
			{
				return users.get(0);
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return user;
	}
	
	@SuppressWarnings("unchecked")
	public List<Account> loadAllUsers()
	{
		Query query = getEntityManager().createQuery("select r from Account r");
		return (List<Account>) query.getResultList();
	}


}
