/**
 * 
 */
package com.tmm.enterprise.microblog.core.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tmm.enterprise.microblog.domain.Team;


/**
 * @author robert.hinds
 *
 */
@Repository(value="teamDAO")
public class TeamDAO extends GenericHibernateDAO<Team, Long> implements ITeamDAO{
	
	/* (non-Javadoc)
	 * @see com.tmm.enterprise.microblog.core.dao.ITeamDAO#loadAllTeams()
	 */
	@SuppressWarnings("unchecked")
	public List<Team> loadAllTeams()
	{
		Query query = getEntityManager().createQuery("select s from Team s");
		List<Team> s = (List<Team>) query.getResultList();
		return s;
	}
	

	/* (non-Javadoc)
	 * @see com.tmm.enterprise.microblog.core.dao.ITeamDAO#loadTeamByName(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Team loadTeamByName(String name)
	{
		Query query = getEntityManager().createQuery("select t from Team t where t.name = ?1");
		query.setParameter(1, name);
		Team team = null;
		
		try{
			List<Team> teams = (List<Team>)query.getResultList();
			if (teams==null || teams.isEmpty())
			{
				return null;
			}
			else
			{
				return teams.get(0);
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return team;
	}


}
