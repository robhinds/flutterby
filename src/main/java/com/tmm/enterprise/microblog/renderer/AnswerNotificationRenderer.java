package com.tmm.enterprise.microblog.renderer;

import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.domain.Answer;
import com.tmm.enterprise.microblog.domain.Notification;
import com.tmm.enterprise.microblog.domain.Question;

/**
 * Render PrivateMessage based Notification objects to JSON for consumption by
 * web ui
 * 
 * @author robert.hinds
 * 
 */
public class AnswerNotificationRenderer implements NotificationRenderer {

	@Override
	public JsonObject render(Notification renderTarget) {

		// FIXME - just put in to get web running.. will need more thought on
		// the rendering approach
		JsonObject n = new JsonObject();
		Answer a = (Answer) renderTarget.getActivity();
		n.addProperty("read", renderTarget.isRead());
		String msg;

		Question q = a.getQuestion();
		if (q != null) {
			msg = "Answer added for: "
					+ (q.getTitle() == null ? (q.getDetails().length() > 100 ? q.getDetails().substring(0, 100) : q.getDetails()) : (q.getTitle()
							.length() > 100 ? q.getTitle().substring(0, 100) : q.getTitle()));
		} else {
			msg = a.getTitle() == null ? (a.getDetails().length() > 100 ? a.getDetails().substring(0, 100) : a.getDetails())
					: (a.getTitle().length() > 100 ? a.getTitle().substring(0, 100) : a.getTitle());
		}

		boolean isOwner = a.getRaisedBy() != null && a.getRaisedBy().getNotifications().contains(renderTarget);
		String from;
		if (isOwner) {
			from = "you answered this question";
		} else {
			from = "answered by " + (a.getRaisedBy() == null ? "Unknown Sender" : a.getRaisedBy().getName());
		}

		n.addProperty("body", msg);
		n.addProperty("from", from);
		n.addProperty("activityId", a.getId());
		n.addProperty("activityType", "Answer");

		return n;
	}

}
