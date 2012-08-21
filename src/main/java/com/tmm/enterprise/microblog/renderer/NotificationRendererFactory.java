package com.tmm.enterprise.microblog.renderer;

import org.springframework.stereotype.Repository;

import com.tmm.enterprise.microblog.domain.Notification;
import com.tmm.enterprise.microblog.domain.enums.ObjectType;

@Repository
public class NotificationRendererFactory {

	public NotificationRenderer getNotificationRenderer(ObjectType object) {
		switch (object) {
		case PRIVATEMESSAGE: {
			return new PrivateMessageNotificationRenderer();
		}
		case QUESTION: {
			return new QuestionNotificationRenderer();
		}
		case ANSWER: {
			return new AnswerNotificationRenderer();
		}
		case TODO: {
			return new ToDoNotificationRenderer();
		}
		case TRACKER: {
			return new TrackerNotificationRenderer();
		}
		default: {
			return null;
		}
		}
	}
	
	public NotificationRenderer getNotificationRenderer(Notification notification) {
		return getNotificationRenderer(ObjectType.valueOf(notification.getDiscriminator()));
	}
}
