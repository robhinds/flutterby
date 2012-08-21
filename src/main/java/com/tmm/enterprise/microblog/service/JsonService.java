/**
 * 
 */
package com.tmm.enterprise.microblog.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.core.exception.ButterflyException;
import com.tmm.enterprise.microblog.core.exception.ButterflyExceptionCode;
import com.tmm.enterprise.microblog.coverter.html.IHtmlConverter;
import com.tmm.enterprise.microblog.domain.Activity;
import com.tmm.enterprise.microblog.domain.Answer;
import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.PrivateMessage;
import com.tmm.enterprise.microblog.domain.Question;
import com.tmm.enterprise.microblog.domain.QuestionTag;
import com.tmm.enterprise.microblog.domain.Status;
import com.tmm.enterprise.microblog.domain.Team;
import com.tmm.enterprise.microblog.domain.TicketUpdate;
import com.tmm.enterprise.microblog.domain.ToDo;
import com.tmm.enterprise.microblog.domain.WorkTask;
import com.tmm.enterprise.microblog.domain.enums.ObjectType;
import com.tmm.enterprise.microblog.helper.DateHelper;
import com.tmm.enterprise.microblog.helper.HtmlHelper;
import com.tmm.enterprise.microblog.security.Account;

/**
 * @author robert.hinds
 * 
 *         JsonHelper has a consistent, central API for serialising domain
 *         objects in to a JSON format.
 * 
 *         This class should always be used when dealing with JSON to send to
 *         the UI to ensure a consistent approach to object serialisation
 * 
 */
@Service("jsonService")
public class JsonService {

	@Autowired
	private IHtmlConverter htmlConverter;

	// static strings declared to help ensure all objects use a common
	// nomenclature
	private static final String CREATED_AT = "createdAt";
	private static final String DISPLAY_DATE = "displayDate";
	private static final String CREATED_BY = "createdBy";
	private static final String ID = "id";
	private static final String TICKET_NUMBER = "ticketNumber";
	private static final String STATE = "state";
	private static final String PRIORITY = "priority";
	private static final String STATUS = "status";
	private static final String DESCRIPTION = "description";
	private static final String OBJET_TYPE = "objectType";
	private static final String TAGS = "tags";
	private static final String TRACKER_ID = "trackerId";
	private static final String BODY = "body";
	private static final String TITLE = "title";
	private static final String SCORE = "score";
	private static final String TOOL_TIP = "tooltip";
	private static final String MARKED_CORRECT = "isCorrect";
	private static final String COMPLETED = "completed";
	private static final String DEADLINE = "deadline";
	private static final String NAME = "name";
	private static final String TEAM_NAME = "teamName";
	private static final String ROLE = "role";
	private static final String RECIPIENTS = "recipients";
	private static final String QUESTION_ID = "questionId";
	private static final String QUESTION_TITLE = "questionTitle";
	private static final String NUM_ANSWERS = "numAnswers";
	private static final String NUM_UPDATES = "numUpdates";
	private static final String MEMBERS = "members";
	private static final String ASSIGNED_TO = "assignedTo";

	private static final String UNKNOWN_AUTHOR = "UNKNOWN AUTHOR";

	public void setHtmlConverter(IHtmlConverter htmlConverter) {
		this.htmlConverter = htmlConverter;
	}

	/**
	 * Creates a standard JSON object representing a Status object
	 * 
	 * @param s
	 * @return
	 */
	public JsonObject convertToJson(Status status) {
		JsonObject s = new JsonObject();
		s.addProperty(BODY, status.getStatus());
		s.addProperty(TITLE, status.getStatus());
		s.addProperty(CREATED_AT, status.getCreationDate().toString());
		s.addProperty(CREATED_BY, status.getRaisedBy() != null ? status.getRaisedBy().getName() : UNKNOWN_AUTHOR);
		s.addProperty(ID, status.getId());
		s.addProperty(DISPLAY_DATE, DateHelper.getTimeAgo(status.getCreationDate()));
		s.addProperty(OBJET_TYPE, status.getObjectType().toString());
		return s;
	}

	/**
	 * Creates a standard JSON object representing a ToDo item
	 * 
	 * @param todo
	 * @return
	 */
	public JsonObject convertToJson(ToDo todo) {
		JsonObject s = new JsonObject();
		s.addProperty(BODY, htmlConverter.convertToHtml(todo.getDetails()));
		s.addProperty(TITLE, todo.getTitle());
		s.addProperty(COMPLETED, todo.isCompleted());
		s.addProperty(DEADLINE, DateHelper.getTimeAgo(todo.getDeadline()));
		s.addProperty(CREATED_AT, todo.getCreationDate().toString());
		s.addProperty(CREATED_BY, todo.getRaisedBy() != null ? todo.getRaisedBy().getName() : UNKNOWN_AUTHOR);
		s.addProperty(ID, todo.getId());
		s.addProperty(DISPLAY_DATE, DateHelper.getTimeAgo(todo.getCreationDate()));
		s.addProperty(OBJET_TYPE, todo.getObjectType().toString());
		return s;
	}

	/**
	 * Creates a standard JSON object representing a Question item
	 * 
	 * @param todo
	 * @return
	 */
	public JsonObject convertToJson(Question q) {
		JsonObject s = new JsonObject();
		s.addProperty(BODY, htmlConverter.convertToHtml(q.getDetails()));
		s.addProperty(TITLE, q.getTitle());
		s.addProperty(SCORE, q.getScore());
		s.addProperty(MARKED_CORRECT, q.isAnswered());
		s.addProperty(CREATED_AT, q.getCreationDate().toString());
		s.addProperty(CREATED_BY, q.getRaisedBy() != null ? q.getRaisedBy().getName() : UNKNOWN_AUTHOR);
		s.addProperty(ID, q.getId());
		s.addProperty(DISPLAY_DATE, DateHelper.getTimeAgo(q.getCreationDate()));
		s.addProperty(OBJET_TYPE, q.getObjectType().toString());
		s.addProperty(NUM_ANSWERS, q.getAnswers().size());
		JsonArray array = new JsonArray();
		for (QuestionTag tag : q.getTags()) {
			array.add(convertToJson(tag));
		}
		s.add(TAGS, array);
		s.addProperty(TOOL_TIP, q.getDetails() != null && q.getDetails().length() > 255 ? q.getDetails().substring(0, 255) : q.getDetails());
		return s;
	}

	/**
	 * Creates a standard JSON object representing a ToDo item
	 * 
	 * @param WorkTask
	 * @return
	 */
	public JsonObject convertToJson(WorkTask w) {
		JsonObject s = new JsonObject();
		s.addProperty(BODY, htmlConverter.convertToHtml(w.getDetails()));
		s.addProperty(TITLE, w.getTitle());
		s.addProperty(COMPLETED, w.isCompleted());
		s.addProperty(CREATED_AT, w.getCreationDate().toString());
		s.addProperty(CREATED_BY, w.getRaisedBy() != null ? w.getRaisedBy().getName() : UNKNOWN_AUTHOR);
		s.addProperty(ID, w.getId());
		s.addProperty(TICKET_NUMBER, w.getTicketNumber());
		s.addProperty(STATE, w.getState().toString());
		s.addProperty(PRIORITY, w.getPriority().toString());
		s.addProperty(DISPLAY_DATE, DateHelper.getTimeAgo(w.getCreationDate()));
		s.addProperty(OBJET_TYPE, w.getObjectType().toString());
		s.addProperty(ASSIGNED_TO, w.getAssignedTo() != null ? w.getAssignedTo().getName() : UNKNOWN_AUTHOR);
		s.addProperty(TOOL_TIP, w.getDetails() != null && w.getDetails().length() > 255 ? w.getDetails().substring(0, 255) : w.getDetails());
		JsonArray updates = new JsonArray();
		for (TicketUpdate t : w.getUpdates()) {
			updates.add(convertToJson(t));
		}

		s.add("updates", updates);
		s.addProperty(NUM_UPDATES, w.getUpdates().size());
		return s;
	}

	/**
	 * Creates a standard JSON object representing a ToDo item
	 * 
	 * @param WorkTask
	 * @return
	 */
	public JsonObject convertToJson(TicketUpdate t) {
		JsonObject s = new JsonObject();
		s.addProperty(BODY, htmlConverter.convertToHtml(t.getDetails()));
		s.addProperty(TITLE, t.getTicket() == null ? "Unknown Work Ticket" : t.getTicket().getId() + ": " + t.getTicket().getTitle());
		s.addProperty(CREATED_BY, t.getRaisedBy() != null ? t.getRaisedBy().getName() : UNKNOWN_AUTHOR);
		s.addProperty(DISPLAY_DATE, DateHelper.getTimeAgo(t.getCreationDate()));
		s.addProperty(OBJET_TYPE, t.getObjectType().toString());
		s.addProperty(TRACKER_ID, t.getTicket() == null ? "Unknown Work Task" : "" + t.getTicket().getId());
		return s;
	}

	/**
	 * Creates a standard JSON Object representing a QuestionTag item
	 * 
	 * @param tag
	 * @return
	 */
	public JsonObject convertToJson(QuestionTag tag) {
		JsonObject jo = new JsonObject();
		jo.addProperty(TITLE, tag.getTag());
		jo.addProperty(OBJET_TYPE, "TAG");
		return jo;
	}

	/**
	 * Creates a standard JSON object representing a ToDo item
	 * 
	 * @param todo
	 * @return
	 */
	public JsonObject convertToJson(Answer a) {
		JsonObject s = new JsonObject();
		s.addProperty(BODY, htmlConverter.convertToHtml(a.getDetails()));
		s.addProperty(TITLE, a.getTitle() == null ? (a.getDetails().length() > 60 ? a.getDetails().substring(0, 60) + ".." : a.getDetails()) : a
				.getTitle());
		s.addProperty(SCORE, a.getScore());
		s.addProperty(MARKED_CORRECT, a.isCorrect());
		s.addProperty(CREATED_AT, a.getCreationDate().toString());
		s.addProperty(CREATED_BY, a.getRaisedBy() != null ? a.getRaisedBy().getName() : UNKNOWN_AUTHOR);
		s.addProperty(ID, a.getId());
		s.addProperty(QUESTION_ID, a.getQuestion() != null ? a.getQuestion().getId().toString() : "");
		s.addProperty(QUESTION_TITLE, a.getQuestion() != null ? a.getQuestion().getTitle() : "Unknown Question");
		s.addProperty(DISPLAY_DATE, DateHelper.getTimeAgo(a.getCreationDate()));
		s.addProperty(OBJET_TYPE, a.getObjectType().toString());
		return s;
	}

	/**
	 * Creates a standard JSON object representing an Activity
	 * 
	 * TODO - Complete all Activity types
	 * 
	 * @param act
	 * @return
	 */
	public JsonObject convertToJson(Activity act) {
		if (act instanceof Status) {
			return convertToJson((Status) act);
		}
		if (act instanceof ToDo) {
			return convertToJson((ToDo) act);
		}
		if (act instanceof Question) {
			return convertToJson((Question) act);
		}
		if (act instanceof Answer) {
			return convertToJson((Answer) act);
		}
		if (act instanceof PrivateMessage) {
			return convertToJson((PrivateMessage) act);
		}
		if (act instanceof WorkTask) {
			return convertToJson((WorkTask) act);
		}
		if (act instanceof TicketUpdate) {
			return convertToJson((TicketUpdate) act);
		}
		return null;
	}

	/**
	 * Creates a standard JSON object representing a PrivateMessage object
	 * 
	 * @param s
	 * @return
	 */
	public JsonObject convertToJson(PrivateMessage pm) {
		JsonObject p = new JsonObject();
		p.addProperty(BODY, pm.getDetails());
		p.addProperty(CREATED_BY, pm.getRaisedBy() != null ? pm.getRaisedBy().getName() : UNKNOWN_AUTHOR);
		p.addProperty(CREATED_AT, pm.getCreationDate().toString());
		p.addProperty(DISPLAY_DATE, DateHelper.getTimeAgo(pm.getCreationDate()));
		p.addProperty(ID, pm.getId());
		String subject = pm.getName();
		if (subject == null || subject.isEmpty()) {
			if (pm.getDetails().length() > 100) {
				subject = pm.getDetails().substring(0, 60) + "...";
			} else {
				subject = pm.getDetails();
			}
		}
		p.addProperty(TITLE, subject);
		JsonArray recipients = new JsonArray();
		if (pm.getAssignedTo() instanceof Person) {
			recipients.add(convertToJson((Person) pm.getAssignedTo()));
		}
		p.add(RECIPIENTS, recipients);
		p.addProperty(OBJET_TYPE, pm.getObjectType().toString());
		return p;
	}

	/**
	 * Creates a standard JSON object representing a Person object
	 * 
	 * @param s
	 * @return
	 */
	public JsonObject convertToJson(Person p) {
		JsonObject j = new JsonObject();
		j.addProperty(ID, p.getId());
		j.addProperty(NAME, p.getName());
		j.addProperty(ROLE, p.getRole().toString());
		j.addProperty(TEAM_NAME, p.getTeam() != null ? p.getTeam().getName() : "");
		j.addProperty(OBJET_TYPE, p.getObjectType().toString());
		j.addProperty(STATUS, p.getStatuses().isEmpty() ? "No Status Set Yet" : p.getStatuses().get(0).getTitle());
		return j;
	}

	/**
	 * creates a standard JSON object representing a team
	 * 
	 * @param t
	 * @return
	 */
	public JsonObject convertToJson(Team t) {
		JsonObject j = new JsonObject();
		j.addProperty(ID, t.getId());
		j.addProperty(NAME, t.getName());
		j.addProperty(DESCRIPTION, t.getDescription() == null ? "No Description Provided" : t.getDescription());
		j.addProperty(OBJET_TYPE, t.getObjectType().toString());
		return j;
	}

	public JsonObject deepConvertToJson(Team t) {
		JsonObject j = convertToJson(t);
		JsonArray members = new JsonArray();
		for (Person p : t.getMembers()) {
			members.add(convertToJson(p));
		}
		j.add(MEMBERS, members);
		return j;
	}

	/**
	 * creates a standard JsonObject from a contactable
	 * 
	 * @param c
	 * @return
	 * @throws ButterflyException
	 */
	public JsonObject convertToJson(Contactable c) throws ButterflyException {
		if (c instanceof Team) {
			return convertToJson((Team) c);
		} else if (c instanceof Person) {
			return convertToJson((Person) c);
		}

		throw new ButterflyException(ButterflyExceptionCode.CONTACT003_UNSUPPORTEDCONTACTABLE, "unsupported Contactable type found");
	}

	/**
	 * Method to build the user profile model for the site
	 * 
	 * @param user
	 *            - profile being built for
	 * @return
	 */
	public Map<String, Object> buildUserProfile(Account user) {
		Map<String, Object> model = Maps.newHashMap();
		addUserInfoToModel(user, model);

		return model;
	}

	/**
	 * Method takes in an Account object and a Map and then proceeds to add the
	 * necessary user info to the map required for the normal user control left
	 * panel.
	 * 
	 * @param user
	 * @param model
	 */
	public void addUserInfoToModel(Account user, Map<String, Object> model) {
		Person profile = user.getUserProfile();
		String name = profile == null ? user.getUserName() : profile.getName();
		model.put("username", name);

		String teamName = profile == null ? "N/A" : (profile.getTeam() == null ? "N/A" : profile.getTeam().getName());
		model.put("teamname", teamName);

		String status = (profile == null || profile.getStatuses().isEmpty()) ? "You do not currently have any status set" : profile.getLatestStatus()
				.getStatus();
		model.put("currentStatus", HtmlHelper.italics(status));
	}

	/**
	 * Method takes a list of all the Contacts (people/teams) and add to the
	 * model - this is needed to populate the Contacts list when creating
	 * questions etc
	 * 
	 * @param contacts
	 * @param model
	 */
	public void addContactsToModel(List<? extends Contactable> contacts, Map<String, Object> model) {
		JsonArray teamArray = new JsonArray();
		JsonArray personArray = new JsonArray();
		for (Contactable c : contacts) {
			if (c instanceof Person) {
				personArray.add(convertToJson((Person) c));
			}
			if (c instanceof Team) {
				teamArray.add(convertToJson((Team) c));
			}
		}

		model.put("contactsTeams", teamArray);
		model.put("contactsPersons", personArray);
	}

	/**
	 * Method to add the current users connections to a Model
	 * 
	 * @param user
	 * @param model
	 * @throws ButterflyException
	 */
	public void addConnectionsToModel(Account user, Map<String, Object> model) throws ButterflyException {
		Person p = user.getUserProfile();
		if (p != null) {
			JsonArray peopleArray = new JsonArray();
			JsonArray teamArray = new JsonArray(); 
			for (Contactable c : p.getFollowing()) {
				if(c.getObjectType() == ObjectType.PERSON)
				{
					peopleArray.add(convertToJson(c));
				}
				else if(c.getObjectType() == ObjectType.TEAM)
				{
					teamArray.add(convertToJson(c)); 
				}
				
			}
			model.put("connections", peopleArray);
			model.put("teamConnections", teamArray);
		}
	}

}
