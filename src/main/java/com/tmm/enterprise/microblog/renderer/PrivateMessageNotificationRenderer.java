package com.tmm.enterprise.microblog.renderer;

import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.domain.Notification;
import com.tmm.enterprise.microblog.domain.PrivateMessage;

/**
 * Render PrivateMessage based Notification objects to JSON for consumption by
 * web ui
 * 
 * @author robert.hinds
 * 
 */
public class PrivateMessageNotificationRenderer implements NotificationRenderer {

	@Override
	public JsonObject render(Notification renderTarget) {

		// FIXME - just put in to get web running.. will need more thought on
		// the rendering approach
		JsonObject n = new JsonObject();
		PrivateMessage pm = (PrivateMessage) renderTarget.getActivity();
		n.addProperty("read", renderTarget.isRead());
		String msg = pm.getTitle() == null ? (pm.getDetails().length() > 100 ? pm.getDetails().substring(0, 100) : pm.getDetails()) : (pm.getTitle()
				.length() > 100 ? pm.getTitle().substring(0, 100) : pm.getTitle());
		String from = pm.getRaisedBy() == null ? "Unknown Sender" : pm.getRaisedBy().getName();
		n.addProperty("body", msg);
		n.addProperty("from", from);
		n.addProperty("activityId", pm.getId());
		n.addProperty("activityType", "PrivateMessage");

		return n;
	}

}
