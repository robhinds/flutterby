/**
 * 
 */
package com.tmm.enterprise.microblog.core.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.Question;
import com.tmm.enterprise.microblog.domain.Team;

/**
 * @author robert.hinds
 * 
 */
@Repository(value = "questionDAO")
public class QuestionDAO extends GenericHibernateDAO<Question, Long> implements IQuestionDAO {

	@SuppressWarnings("unchecked")
	public List<Question> loadAllQuestionsAskedByUser(Contactable user) {
		Query query = getEntityManager().createQuery("select q from Question q where q.raisedBy = ?1");
		query.setParameter(1, user);
		List<Question> questions = (List<Question>) query.getResultList();
		return questions;
	}

	@SuppressWarnings("unchecked")
	public List<Question> loadAllQuestionsAskedToUser(Contactable user) {
		if (user instanceof Person && ((Person) user).getTeam() != null) {
			Team team = ((Person) user).getTeam();
			Query query = getEntityManager().createQuery("select q from Question q where q.assignedTo = ?1 or q.assignedTo = ?2");
			query.setParameter(1, user);
			query.setParameter(2, team);
			List<Question> questions = (List<Question>) query.getResultList();
			return questions;
		} else {
			Query query = getEntityManager().createQuery("select q from Question q where q.assignedTo = ?1");
			query.setParameter(1, user);
			List<Question> questions = (List<Question>) query.getResultList();
			return questions;
		}
	}

	@SuppressWarnings("unchecked")
	public List<Question> loadLatestQuestions(int limit) {
		Query query = getEntityManager().createQuery("select q from Question q order by q.creationDate asc");
		query.setFirstResult(0);
		query.setMaxResults(limit);
		List<Question> q = (List<Question>) query.getResultList();
		return q;
	}

}
