package com.tmm.enterprise.microblog.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.enterprise.microblog.core.dao.QuestionDAO;
import com.tmm.enterprise.microblog.core.dao.QuestionTagDAO;
import com.tmm.enterprise.microblog.domain.Answer;
import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.Question;
import com.tmm.enterprise.microblog.domain.QuestionTag;
import com.tmm.enterprise.microblog.security.Account;

@Service("questionService")
@Repository
@Transactional
public class QuestionService {
	private EntityManager entityManager;

	@Autowired
	private AccountService accountService;
	@Autowired
	private ContactableService contactService;
	@Autowired
	private AnswerService answerService;

	@Autowired
	private QuestionDAO questionDao;
	@Autowired
	private QuestionTagDAO questionTagDao;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void setContactService(ContactableService contactService) {
		this.contactService = contactService;
	}

	public void setAnswerService(AnswerService aService) {
		this.answerService = aService;
	}

	public void setQuestionDao(QuestionDAO questionDao) {
		this.questionDao = questionDao;
	}

	public void setQuestionTagDao(QuestionTagDAO questionTagDao) {
		this.questionTagDao = questionTagDao;
	}

	/**
	 * Persist a Status object
	 * 
	 * @param s
	 */
	@Transactional
	public void createQuestion(Question q) {
		questionDao.persist(q);
	}

	@Transactional
	public Question loadQuestion(long id) {
		return getEntityManager().find(Question.class, id);
	}

	@Transactional
	public Answer loadAnswer(long id) {
		return getEntityManager().find(Answer.class, id);
	}

	@Transactional
	public void createQuestion(String title, String description, String currentUserName, long assignedToId, String tags) {
		// current user
		Account acc = accountService.loadAccountByUserName(currentUserName);
		Person currentUser = acc.getUserProfile();
		// assigning to
		if (currentUser != null) {
			Contactable contact = contactService.loadContactable(assignedToId);
			Question q = new Question();
			q.setTitle(title);
			q.setDetails(description);
			q.setRaisedBy(currentUser);
			q.setAssignedTo(contact);

			if (tags != null && !"".equals(tags)) {
				String[] listTags = tags.split(",");
				for (String t : listTags) {
					QuestionTag tag = questionTagDao.loadOrCreateTagByName(t.trim());
					q.addTag(tag);
				}
			}

			questionDao.persist(q);
		}
	}

	@Transactional
	public List<Question> loadQuestionsAsked(Person currentUser) {
		return questionDao.loadAllQuestionsAskedByUser(currentUser);
	}

	@Transactional
	public List<Question> loadQuestionsAssignedTo(Person currentUser) {
		return questionDao.loadAllQuestionsAskedToUser(currentUser);
	}

	@Transactional
	public void createAnswer(long questionId, String answer, String currentUserName) {
		Account acc = accountService.loadAccountByUserName(currentUserName);
		Person currentUser = acc.getUserProfile();
		// assigning to
		if (currentUser != null) {
			Question q = loadQuestion(questionId);
			Answer a = new Answer();
			a.setAssignedTo(currentUser);
			a.setRaisedBy(currentUser);
			a.setDetails(answer);
			a.setQuestion(q);
			q.addAnswer(a);
			answerService.createAnswer(a);
		}
	}

	@Transactional
	public List<QuestionTag> getPopularTags() {
		// TODO Auto-generated method stub
		return questionTagDao.loadmostPopular(20);
	}

	@Transactional
	public void updateAnswer(long answerId, boolean correct) {
		Answer a = loadAnswer(answerId);
		a.setCorrect(correct);
	}

	@Transactional
	public List<Question> getLatestQuestions(int limit) {
		return questionDao.loadLatestQuestions(limit);
	}

}