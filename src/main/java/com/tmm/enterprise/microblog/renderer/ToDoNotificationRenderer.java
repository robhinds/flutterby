package com.tmm.enterprise.microblog.renderer;

import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.domain.Notification;
import com.tmm.enterprise.microblog.domain.ToDo;

/**
 * Render PrivateMessage based Notification objects to JSON for consumption by
 * web ui
 * 
 * @author robert.hinds
 * 
 */
public class ToDoNotificationRenderer implements NotificationRenderer {

	@Override
	public JsonObject render(Notification renderTarget) {

		// FIXME - just put in to get web running.. will need more thought on
		// the rendering approach
		JsonObject n = new JsonObject();
		ToDo todo = (ToDo) (renderTarget.getActivity());
		n.addProperty("read", renderTarget.isRead());
		String msg = todo.getTitle() == null ? (todo.getDetails().length() > 100 ? todo.getDetails().substring(0, 100) : todo.getDetails()) : (todo
				.getTitle().length() > 100 ? todo.getTitle().substring(0, 100) : todo.getTitle());
		String from = "you created a ToDo Item " + (todo.getDeadline() == null ? "" : todo.getDeadline().toString());
		n.addProperty("body", msg);
		n.addProperty("from", from);
		n.addProperty("activityId", todo.getId());
		n.addProperty("activityType", "ToDo");

		return n;
	}

}
