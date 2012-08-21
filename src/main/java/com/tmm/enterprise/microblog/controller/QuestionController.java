package com.tmm.enterprise.microblog.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.domain.Activity;
import com.tmm.enterprise.microblog.domain.Answer;
import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.Question;
import com.tmm.enterprise.microblog.domain.QuestionTag;
import com.tmm.enterprise.microblog.service.AccountService;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.JsonService;
import com.tmm.enterprise.microblog.service.NotificationService;
import com.tmm.enterprise.microblog.service.QuestionService;
import com.tmm.enterprise.microblog.service.SearchService;

@Controller
@RequestMapping(value = "/question")
public class QuestionController {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ContactableService contactService;
	@Autowired
	private NotificationService notificationService;
	@Autowired
	private JsonService jsonService;
	@Autowired
	private SearchService searchService;

	public void setJsonService(JsonService jsonService) {
		this.jsonService = jsonService;
	}

	public void setSearchService(SearchService searchService) {
		this.searchService = searchService;
	}

	public void setQuestionService(QuestionService qService) {
		this.questionService = qService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void setContactService(ContactableService contactService) {
		this.contactService = contactService;
	}

	public void setNotificationService(NotificationService notificationService) {
		this.notificationService = notificationService;
	}

	/**
	 * Controller method invoked when a user attempts to create a new Question
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/createQuestion")
	public ModelAndView createQuestion(@RequestParam String title,@RequestParam String body,@RequestParam Long recipientId,@RequestParam String tags,HttpServletRequest request) throws Exception {
		String username = request.getRemoteUser();
		questionService.createQuestion(title, body, username, recipientId, tags);
		Map<String, String> model = Maps.newHashMap();
		return new ModelAndView("ajax_question", model);
	}

	@RequestMapping("/postAnswer")
	public ModelAndView postAnswer(@RequestParam String answer,@RequestParam Long questionId,HttpServletRequest request) throws Exception {
		String userName = request.getRemoteUser();
		questionService.createAnswer(questionId, answer, userName);

		Map<String, Object> model = Maps.newHashMap();
		return new ModelAndView("ajax_question", model);
	}

	/**
	 * Method to build users questions list
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Person currentUser = accountService.getPerson(request);
		Map<String, Object> model = Maps.newHashMap();
		JsonArray asked = new JsonArray();
		JsonArray toAnswer = new JsonArray();
		if (currentUser != null) {
			List<Question> questionsAsked = questionService.loadQuestionsAsked(currentUser);
			for (Question q : questionsAsked) {
				asked.add(jsonService.convertToJson(q));
			}
			List<Question> questionsToAnswer = questionService.loadQuestionsAssignedTo(currentUser);
			for (Question q : questionsToAnswer) {
				toAnswer.add(jsonService.convertToJson(q));
			}
		}

		// build list of popular tags
		List<QuestionTag> tags = questionService.getPopularTags();
		JsonArray qTags = new JsonArray();
		// TODO
		// for (QuestionTag t : tags){
		// qTags.add(jsonService.convertToJson(t));
		// }
		List<Question> latestQuestions = questionService.getLatestQuestions(8);
		JsonArray latest = new JsonArray();
		for (Question ques : latestQuestions) {
			JsonObject obj = new JsonObject();
			obj.addProperty("title", ques.getTitle());
			obj.addProperty("id", ques.getId());
			latest.add(obj);
		}

		model.put("questionsAsked", asked);
		model.put("latestQuestions", latest);
		model.put("questionsAssigned", toAnswer);
		model.put("tags", tags);

		jsonService.addUserInfoToModel(accountService.getAccount(request), model);
		List<Contactable> contacts = contactService.loadAllContactables();
		jsonService.addContactsToModel(contacts, model);
		return new ModelAndView("questionList", model);
	}

	/**
	 * Method to build the detailed question page
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/detail/{questionId}")
	public ModelAndView detail(@PathVariable("questionId") long questionId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Person currentUser = accountService.getPerson(request);
		notificationService.markAsRead(currentUser, questionId);

		Question q = questionService.loadQuestion(questionId);
		JsonObject question = jsonService.convertToJson(q);
		JsonArray answers = new JsonArray();
		for (Answer a : q.getAnswers()) {
			JsonObject jo = jsonService.convertToJson(a);
			answers.add(jo);
			notificationService.markAsRead(currentUser, a.getId());
		}

		JsonArray tags = new JsonArray();
		for (QuestionTag tag : q.getTags()) {
			tags.add(jsonService.convertToJson(tag));
		}

		List<Activity> similarQuestions = searchService.searchQuestionsByTitle(q.getTitle());
		JsonArray similar = new JsonArray();
		for (Activity ques : similarQuestions) {
			if (!ques.equals(q)) {
				JsonObject obj = new JsonObject();
				obj.addProperty("title", ques.getTitle());
				obj.addProperty("id", ques.getId());
				similar.add(obj);
			}
		}

		Map<String, Object> model = Maps.newHashMap();
		model.put("question", question);
		model.put("answers", answers);
		model.put("similar", similar);
		model.put("tags", tags);
		model.put("numAnswers", q.getAnswers().size());
		jsonService.addUserInfoToModel(accountService.getAccount(request), model);
		List<Contactable> contacts = contactService.loadAllContactables();
		jsonService.addContactsToModel(contacts, model);
		return new ModelAndView("questionDetail", model);
	}

	@RequestMapping("/updateQuestion")
	public ModelAndView updateQuestion(@RequestParam Long id,@RequestParam Boolean completed,HttpServletRequest request) throws Exception {
		questionService.updateAnswer(id, completed);
		Map<String, String> model = Maps.newHashMap();
		return new ModelAndView("ajax_question_updated", model);
	}
}