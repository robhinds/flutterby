package com.tmm.enterprise.microblog.renderer;

import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.domain.Notification;
import com.tmm.enterprise.microblog.domain.Status;

/**
 * Render PrivateMessage based Notification objects to JSON for consumption by
 * web ui
 * 
 * @author robert.hinds
 * 
 */
public class StatusNotificationRenderer implements NotificationRenderer {

	@Override
	public JsonObject render(Notification renderTarget) {

		// FIXME - just put in to get web running.. will need more thought on
		// the rendering approach

		JsonObject n = new JsonObject();
		Status s = (Status) renderTarget.getActivity();
		n.addProperty("read", renderTarget.isRead());
		String msg = s.getTitle();
		boolean isOwner = s.getRaisedBy() != null && s.getRaisedBy().getNotifications().contains(renderTarget);
		String from;
		if (isOwner) {
			from = "you posted this update";
		} else {
			from = "update posted by " + (s.getRaisedBy() == null ? "Unknown Sender" : s.getRaisedBy().getName());
		}
		n.addProperty("body", msg);
		n.addProperty("from", from);
		n.addProperty("activityId", s.getId());
		n.addProperty("activityType", "Status");

		return n;
	}

}
