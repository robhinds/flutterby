package com.tmm.enterprise.microblog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.core.exception.ButterflyException;
import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.ToDo;
import com.tmm.enterprise.microblog.helper.DateHelper;
import com.tmm.enterprise.microblog.service.AccountService;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.JsonService;
import com.tmm.enterprise.microblog.service.NotificationService;
import com.tmm.enterprise.microblog.service.ToDoService;

@Controller
@RequestMapping(value = "/todo")
public class TodoController {

	@Autowired
	private ToDoService todoService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ContactableService contactService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private JsonService jsonService;

	public void setJsonService(JsonService jsonService) {
		this.jsonService = jsonService;
	}

	public void setTodoService(ToDoService todoService) {
		this.todoService = todoService;
	}

	public void setAccountService(AccountService accService) {
		this.accountService = accService;
	}

	public void setContactService(ContactableService contactService) {
		this.contactService = contactService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	/**
	 * Controller method invoked when a user attempts to create a new ToDo
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/createTodo")
	public ModelAndView createTodo(HttpServletRequest request, HttpServletResponse response)  {
		String title = request.getParameter("title");
		String body = request.getParameter("body");
		String userName = request.getRemoteUser();
		ToDo todo = null;
		try {
			todo = todoService.createToDo(title, body, userName);
		} catch (ButterflyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, String> model = Maps.newHashMap();
		model.put("id", "" + todo.getId());
		model.put("title", todo.getTitle());
		model.put("body", todo.getDetails());
		model.put("displayDate", DateHelper.getTimeAgo(todo.getCreationDate()));
		return new ModelAndView("ajax_todo_created", model);
	}

	/**
	 * Method to build users to-do pile
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Transactional
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Person currentUser = accountService.getPerson(request);
		Map<String, Object> model = Maps.newHashMap();
		JsonArray array = new JsonArray();
		if (currentUser != null) {
			List<ToDo> todos = currentUser.getTodoItems();
			for (ToDo todo : todos) {
				array.add(jsonService.convertToJson(todo));
			}
		}
		model.put("todos", array);
		jsonService.addUserInfoToModel(accountService.getAccount(request), model);
		List<Contactable> contacts = contactService.loadAllContactables();
		jsonService.addContactsToModel(contacts, model);

		return new ModelAndView("todoList", model);
	}

	/**
	 * update the completion status of a todo item
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateTodo")
	public ModelAndView updateTodo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String idStr = request.getParameter("id");
		boolean completed = Boolean.parseBoolean(request.getParameter("completed"));
		long id = Long.parseLong(idStr);

		todoService.updateToDoCompletion(id, completed);
		Map<String, String> model = Maps.newHashMap();
		return new ModelAndView("ajax_todo_updated", model);
	}

	/**
	 * update the completion status of a todo item
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteTodo")
	public ModelAndView deleteTodo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String idStr = request.getParameter("id");
		long id = Long.parseLong(idStr);
		todoService.removeTodo(id);
		Map<String, String> model = Maps.newHashMap();
		return new ModelAndView("ajax_todo_removed", model);
	}

	/**
	 * @param todoId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/detail/{todoId}")
	public ModelAndView detail(@PathVariable("todoId") long todoId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Person currentUser = accountService.getPerson(request);

		notificationService.markAsRead(currentUser, todoId);

		Map<String, Object> model = Maps.newHashMap();
		JsonArray array = new JsonArray();
		if (currentUser != null) {
			List<ToDo> todos = currentUser.getTodoItems();
			for (ToDo todo : todos) {
				JsonObject t = jsonService.convertToJson(todo);
				if (todo.getId() == todoId) {
					t.addProperty("display", true);
				}
				array.add(t);
			}
		}
		model.put("todos", array);
		jsonService.addUserInfoToModel(accountService.getAccount(request), model);
		List<Contactable> contacts = contactService.loadAllContactables();
		jsonService.addContactsToModel(contacts, model);

		return new ModelAndView("todoList", model);
	}
}