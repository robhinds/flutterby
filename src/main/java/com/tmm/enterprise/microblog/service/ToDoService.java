package com.tmm.enterprise.microblog.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.enterprise.microblog.core.dao.ToDoDAO;
import com.tmm.enterprise.microblog.core.exception.ButterflyException;
import com.tmm.enterprise.microblog.core.exception.ButterflyExceptionCode;
import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.ToDo;
import com.tmm.enterprise.microblog.security.Account;


@Service("todoService")
@Repository
@Transactional
public class ToDoService
{
	private EntityManager entityManager;
	
	@Autowired
	private AccountService accountService;


	@Autowired
	private ToDoDAO todoDao;


	@PersistenceContext
	public void setEntityManager(EntityManager em)
	{
		this.entityManager = em;
	}

	public EntityManager getEntityManager()
	{
		return entityManager;
	}
	
	
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}


	public void setTodoDao(ToDoDAO todoDao) {
		this.todoDao = todoDao;
	}

	/**
	 * Persist a Status object
	 * @param s
	 */
	@Transactional
	public void createToDo(ToDo s){
		todoDao.persist(s);
	}
	
	
	@Transactional
	public ToDo loadToDo(long id){
		return getEntityManager().find(ToDo.class, id);
	}
	
	@Transactional
	public List<ToDo> loadToDos(String userName){
		Account acc = accountService.loadAccountByUserName(userName);
		Person currentUser = acc.getUserProfile();
		
		if (currentUser!=null){
			return currentUser.getTodoItems();
		}
		
		return new ArrayList<ToDo>();
	}

	@Transactional
	public ToDo createToDo(String title, String description, String userName) throws ButterflyException {
		Account acc = accountService.loadAccountByUserName(userName);
		Person currentUser = acc.getUserProfile();
		if (currentUser!=null){
			ToDo todo = new ToDo();
			todo.setTitle(title);
			todo.setDetails(description);
			todo.setRaisedBy(currentUser);
			todo.setAssignedTo(currentUser);
			currentUser.addTodoItem(todo);
			todoDao.persist(todo);
			return todo;
		}
		throw new ButterflyException(ButterflyExceptionCode.USER003_INVALIDUSER, "Unable to create new ToDo - Account does not have associated Person");
	}
	
	@Transactional
	public void updateToDoCompletion(long id, boolean completed){		
		ToDo todo = getEntityManager().find(ToDo.class, id);
		if (todo!=null){
			todo.setCompleted(completed);
		}
	}

	@Transactional
	public void removeTodo(long id) {
		ToDo todo = getEntityManager().find(ToDo.class, id);
		Contactable raiser = todo.getRaisedBy();
		List<ToDo> todos = raiser.getTodoItems();
		todos.remove(todo);
		todo.setRaisedBy(null);
	}


}