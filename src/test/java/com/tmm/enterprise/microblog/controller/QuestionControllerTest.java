package com.tmm.enterprise.microblog.controller;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.Question;
import com.tmm.enterprise.microblog.service.AccountService;
import com.tmm.enterprise.microblog.service.ContactableService;
import com.tmm.enterprise.microblog.service.JsonService;
import com.tmm.enterprise.microblog.service.NotificationService;
import com.tmm.enterprise.microblog.service.QuestionService;
import com.tmm.enterprise.microblog.service.SearchService;

import edu.emory.mathcs.backport.java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class QuestionControllerTest {

	private static final String QUESTION = "QUESTION";
	private static final String CONTACT_NAME_1 = "contact1";
	@Mock
	private QuestionService mockQuestionService;
	@Mock
	private AccountService mockAccountService;
	@Mock
	private ContactableService mockContactService;
	@Mock
	private NotificationService mockNotificationService;
	@Mock
	private JsonService mockJsonService;
	@Mock
	private SearchService mockSearchService;
	@Mock
	private Person mockPerson;
	@Mock
	private Question mockQuestion1, mockQuestion2, mockQuestion3;

	private MockHttpServletRequest mockRequest;
	private MockHttpServletResponse mockResponse;
	private QuestionController questionController;

	@Before
	public void setUp() throws Exception {
		questionController = new QuestionController();
		questionController.setNotificationService(mockNotificationService);
		questionController.setContactService(mockContactService);
		questionController.setQuestionService(mockQuestionService);
		questionController.setAccountService(mockAccountService);
		questionController.setJsonService(mockJsonService);
		questionController.setSearchService(mockSearchService);
		mockRequest = new MockHttpServletRequest();
		mockResponse = new MockHttpServletResponse();
	}

	@Test
	public void createQuestion() throws Exception {
		mockRequest.setRemoteUser(CONTACT_NAME_1);
		String title =  "new question title";
		String body = "new question body";
		Long recipientId = 999l;
		String tags ="test, junit";
		ModelAndView actualMAV = questionController.createQuestion(title, body, recipientId, tags, mockRequest);
		verify(mockQuestionService).createQuestion("new question title", "new question body", CONTACT_NAME_1, 999, "test, junit");
		assertEquals("ajax_question", actualMAV.getViewName());
	}

	@Test
	public void postAnswer() throws Exception {
		mockRequest.setRemoteUser(CONTACT_NAME_1);
		String answer ="answer details";
		Long questionId = 99l;
		ModelAndView actualMAV = questionController.postAnswer(answer, questionId, mockRequest);
		verify(mockQuestionService).createAnswer(99, "answer details", CONTACT_NAME_1);
		assertEquals("ajax_question", actualMAV.getViewName());
	}

	@Test
	public void list() throws Exception {
		mockRequest.setRemoteUser(CONTACT_NAME_1);
		when(mockAccountService.getPerson(mockRequest)).thenReturn(mockPerson);
		when(mockQuestionService.loadQuestionsAsked(mockPerson)).thenReturn(Arrays.asList(new Object[] { mockQuestion1 }));
		when(mockQuestionService.loadQuestionsAssignedTo(mockPerson)).thenReturn(Arrays.asList(new Object[] { mockQuestion2 }));
		when(mockQuestionService.getLatestQuestions(8)).thenReturn(Arrays.asList(new Object[] { mockQuestion3 }));
		JsonObject q1 = new JsonObject();
		JsonObject q2 = new JsonObject();
		q1.addProperty("title", "question 1");
		q2.addProperty("title", "question 2");
		when(mockJsonService.convertToJson(mockQuestion1)).thenReturn(q1);
		when(mockJsonService.convertToJson(mockQuestion2)).thenReturn(q2);
		when(mockQuestion3.getTitle()).thenReturn("q3 title");
		when(mockQuestion3.getId()).thenReturn(99l);

		ModelAndView actualMAV = questionController.list(mockRequest, mockResponse);
		assertEquals("questionList", actualMAV.getViewName());
		assertEquals(
				"Expected model return different.",
				"{tags=[], latestQuestions=[{\"title\":\"q3 title\",\"id\":99}], questionsAssigned=[{\"title\":\"question 2\"}], questionsAsked=[{\"title\":\"question 1\"}]}",
				actualMAV.getModel().toString());
	}

	@Test
	public void detail() throws Exception {
		mockRequest.setRemoteUser(CONTACT_NAME_1);
		when(mockAccountService.getPerson(mockRequest)).thenReturn(mockPerson);
		when(mockQuestionService.loadQuestion(999l)).thenReturn(mockQuestion1);
		when(mockQuestion1.getTitle()).thenReturn("q1 title");
		when(mockSearchService.searchQuestionsByTitle("q1 title")).thenReturn(Arrays.asList(new Object[] { mockQuestion3 }));

		JsonObject q1 = new JsonObject();
		q1.addProperty("title", "question 1");
		when(mockJsonService.convertToJson(mockQuestion1)).thenReturn(q1);
		when(mockQuestion3.getTitle()).thenReturn("q3 title");
		when(mockQuestion3.getId()).thenReturn(99l);

		ModelAndView actualMAV = questionController.detail(999l, mockRequest, mockResponse);
		assertEquals("questionDetail", actualMAV.getViewName());
		assertEquals("Expected model return different.",
				"{tags=[], numAnswers=0, answers=[], question={\"title\":\"question 1\"}, similar=[{\"title\":\"q3 title\",\"id\":99}]}", actualMAV
						.getModel().toString());
	}

	@Test
	public void updateQuestion() throws Exception {
		mockRequest.setRemoteUser(CONTACT_NAME_1);
		Long id = 99l;
		boolean completed = true; 
		ModelAndView actualMAV = questionController.updateQuestion(id, completed, mockRequest);
		verify(mockQuestionService).updateAnswer(99l, true);
		assertEquals("ajax_question_updated", actualMAV.getViewName());
	}
}
