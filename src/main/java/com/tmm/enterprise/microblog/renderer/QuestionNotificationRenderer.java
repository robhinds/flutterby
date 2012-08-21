package com.tmm.enterprise.microblog.renderer;

import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.domain.Notification;
import com.tmm.enterprise.microblog.domain.Question;

/**
 * Render PrivateMessage based Notification objects to JSON for consumption by
 * web ui
 * 
 * @author robert.hinds
 * 
 */
public class QuestionNotificationRenderer implements NotificationRenderer {

	@Override
	public JsonObject render(Notification renderTarget) {

		// FIXME - just put in to get web running.. will need more thought on
		// the rendering approach

		JsonObject n = new JsonObject();
		Question q = (Question) renderTarget.getActivity();
		n.addProperty("read", renderTarget.isRead());
		String msg = q.getTitle() == null ? (q.getDetails().length() > 100 ? q.getDetails().substring(0, 100) : q.getDetails()) : (q.getTitle()
				.length() > 100 ? q.getTitle().substring(0, 100) : q.getTitle());
		boolean isOwner = q.getRaisedBy() != null && q.getRaisedBy().getNotifications().contains(renderTarget);
		String from;
		if (isOwner) {
			from = "you asked this question";
		} else {
			from = "asked by " + (q.getRaisedBy() == null ? "Unknown Sender" : q.getRaisedBy().getName());
		}
		n.addProperty("body", msg);
		n.addProperty("from", from);
		n.addProperty("activityId", q.getId());
		n.addProperty("activityType", "Question");

		return n;
	}

}
