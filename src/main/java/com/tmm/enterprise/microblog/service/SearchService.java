package com.tmm.enterprise.microblog.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.enterprise.microblog.core.exception.ButterflyException;
import com.tmm.enterprise.microblog.core.exception.ButterflyExceptionCode;
import com.tmm.enterprise.microblog.domain.Activity;
import com.tmm.enterprise.microblog.domain.Question;
import com.tmm.enterprise.microblog.domain.Status;
import com.tmm.enterprise.microblog.domain.ToDo;
import com.tmm.enterprise.microblog.domain.WorkTask;
import com.tmm.enterprise.microblog.domain.enums.ObjectType;

@Service("searchService")
@Repository
@Transactional
public class SearchService {
	private EntityManager entityManager;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	@Transactional
	public void indexAllObjects() {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(getEntityManager());
		try {
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/*
	 * core object search methods - takes a string for field name to be queried
	 * and the searchterm
	 */

	private List<Activity> searchQuestions(String fields, String searchTerm) {
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(getEntityManager());
		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Question.class).get();
		org.apache.lucene.search.Query query = qb.keyword().onFields(fields).matching(searchTerm).createQuery();
		javax.persistence.Query persistenceQuery = fullTextEntityManager.createFullTextQuery(query, Question.class);
		persistenceQuery.setMaxResults(10);
		return (List<Activity>) persistenceQuery.getResultList();
	}

	private List<Activity> searchWorkTasks(String fields, String searchTerm) {
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(getEntityManager());
		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(WorkTask.class).get();
		org.apache.lucene.search.Query query = qb.keyword().onFields(fields).matching(searchTerm).createQuery();
		javax.persistence.Query persistenceQuery = fullTextEntityManager.createFullTextQuery(query, WorkTask.class);
		persistenceQuery.setMaxResults(10);
		return (List<Activity>) persistenceQuery.getResultList();
	}

	private List<Activity> searchToDos(String fields, String searchTerm) {
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(getEntityManager());
		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(ToDo.class).get();
		org.apache.lucene.search.Query query = qb.keyword().onFields(fields).matching(searchTerm).createQuery();

		javax.persistence.Query persistenceQuery = fullTextEntityManager.createFullTextQuery(query, ToDo.class);
		persistenceQuery.setMaxResults(10);
		return (List<Activity>) persistenceQuery.getResultList();
	}

	private List<Activity> searchStatus(String fields, String searchTerm) {
		FullTextEntityManager fullTextEntityManager = org.hibernate.search.jpa.Search.getFullTextEntityManager(getEntityManager());
		QueryBuilder qb = fullTextEntityManager.getSearchFactory().buildQueryBuilder().forEntity(Status.class).get();
		org.apache.lucene.search.Query query = qb.keyword().onFields(fields).matching(searchTerm).createQuery();
		javax.persistence.Query persistenceQuery = fullTextEntityManager.createFullTextQuery(query, Status.class);
		persistenceQuery.setMaxResults(10);
		return (List<Activity>) persistenceQuery.getResultList();
	}

	public List<Activity> searchQuestionsByTitle(String searchTerm) {
		return searchQuestions("title", searchTerm);
	}

	public List<Activity> searchTasksByTitle(String searchTerm) {
		return searchWorkTasks("title", searchTerm);
	}

	public List<Activity> searchTodosByTitle(String searchTerm) {
		return searchToDos("title", searchTerm);
	}

	public List<Activity> searchStatusByTitle(String searchTerm) {
		return searchStatus("title", searchTerm);
	}

	public List<Activity> searchActivityByTitle(String searchTerm) {
		List<Activity> acts = new ArrayList<Activity>();
		acts.addAll(searchQuestionsByTitle(searchTerm));
		acts.addAll(searchTodosByTitle(searchTerm));
		acts.addAll(searchTasksByTitle(searchTerm));
		// acts.addAll(searchStatusByTitle(searchTerm));
		return acts;
	}

	/**
	 * Method to perform search for a specific activity type
	 * 
	 * @param searchTerm
	 * @param activityType
	 * @return
	 * @throws ButterflyException
	 */
	public List<Activity> searchActivityByTitle(String searchTerm, ObjectType activityType) throws ButterflyException {
		switch (activityType) {
		case QUESTION:
			return searchQuestionsByTitle(searchTerm);
		case TODO:
			return searchTodosByTitle(searchTerm);
		case TRACKER:
			return searchTasksByTitle(searchTerm);
		case PROJECT:
			return searchActivityByTitle(searchTerm);
		}
		throw new ButterflyException(ButterflyExceptionCode.SEARCH001_INVALIDSEARCHCONTEXT,
				"Error attempting to search - invalid Activity search context");
	}
}