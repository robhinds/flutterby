package com.tmm.enterprise.microblog.renderer;

import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.domain.Notification;
import com.tmm.enterprise.microblog.domain.WorkTask;

/**
 * Render Work Ticket based Notification objects to JSON for consumption by web
 * ui
 * 
 * @author robert.hinds
 * 
 */
public class TrackerNotificationRenderer implements NotificationRenderer {

	@Override
	public JsonObject render(Notification renderTarget) {

		// FIXME - just put in to get web running.. will need more thought on
		// the rendering approach

		JsonObject n = new JsonObject();
		WorkTask wt = (WorkTask) renderTarget.getActivity();
		n.addProperty("read", renderTarget.isRead());
		String msg = wt.getTitle() == null ? (wt.getDetails().length() > 100 ? wt.getDetails().substring(0, 100) : wt.getDetails()) : (wt.getTitle()
				.length() > 100 ? wt.getTitle().substring(0, 100) : wt.getTitle());
		boolean isOwner = wt.getRaisedBy() != null && wt.getRaisedBy().getNotifications().contains(renderTarget);
		String from;
		if (isOwner) {
			from = "you raised this Work Request";
		} else {
			from = "Work Ticket raised by " + (wt.getRaisedBy() == null ? "Unknown Sender" : wt.getRaisedBy().getName());
		}
		n.addProperty("body", msg);
		n.addProperty("from", from);
		n.addProperty("activityId", wt.getId());
		n.addProperty("activityType", "Question");

		return n;
	}

}
