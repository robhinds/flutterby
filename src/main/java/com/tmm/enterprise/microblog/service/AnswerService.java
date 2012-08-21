package com.tmm.enterprise.microblog.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.enterprise.microblog.domain.Answer;


@Service("answerService")
@Repository
@Transactional
public class AnswerService
{
	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager em)
	{
		this.entityManager = em;
	}

	public EntityManager getEntityManager()
	{
		return entityManager;
	}


	/**
	 * Persist a Status object
	 * @param s
	 */
	@Transactional
	public void createAnswer(Answer a){
		getEntityManager().persist(a);
	}
	
	
	@Transactional
	public Answer loadAnswer(long id){
		return getEntityManager().find(Answer.class, id);
	}

}