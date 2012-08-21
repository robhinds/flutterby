package com.tmm.enterprise.microblog.renderer;

import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.domain.Notification;

public interface NotificationRenderer {

	/**
	 * Renders a JSON Notification object for use by the web.
	 * 
	 * Web expects a set format for the JSON object, needs the following
	 * details:
	 * 
	 * - body - the message to be displayed in dropdown etc - from - the OP of
	 * the activity - activityId - ID of the underlying activity (not the
	 * notification) - activityType - Type of underlying activity
	 * 
	 * @param renderTarget
	 * @return
	 */
	JsonObject render(Notification renderTarget);
}
