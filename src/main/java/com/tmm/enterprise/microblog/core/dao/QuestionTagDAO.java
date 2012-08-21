/**
 * 
 */
package com.tmm.enterprise.microblog.core.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tmm.enterprise.microblog.domain.QuestionTag;


/**
 * @author robert.hinds
 *
 */
@Repository(value="questionTagDAO")
public class QuestionTagDAO extends GenericHibernateDAO<QuestionTag, Long> {
	

	@SuppressWarnings("unchecked")
	public QuestionTag loadOrCreateTagByName(String name)
	{
		Query query = getEntityManager().createQuery("select t from QuestionTag t where t.tag = ?1");
		query.setParameter(1, name);
		QuestionTag tag = null;
		
		try{
			List<QuestionTag> tags = (List<QuestionTag>)query.getResultList();
			if (tags==null || tags.isEmpty())
			{
				tag = new QuestionTag();
				tag.setTag(name);
				persist(tag);
			}
			else
			{
				tag = tags.get(0);
			}
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return tag;
	}

	public List<QuestionTag> loadmostPopular(int i) {
		StringBuilder q = new StringBuilder("select * from BF_TAGS where id in ()");
		Query query = getEntityManager().createNativeQuery("", QuestionTag.class);
		//TODO Native query using groupBy to collate the most popular tags..
		//TODO Auto-generated method stub
		return null;
	}
}