/**
 * 
 */
package com.tmm.enterprise.microblog.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

import com.tmm.enterprise.microblog.domain.enums.ObjectType;
import com.tmm.enterprise.microblog.security.Account;
import com.tmm.enterprise.microblog.service.ApplicationService;

/**
 * @author robert.hinds
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "activityType", discriminatorType = DiscriminatorType.STRING)
@Table(name = "BF_ACTIVITY")
public abstract class Activity extends PersistableObject {

	private static final long serialVersionUID = 8369092238968491166L;

	@ManyToOne
	@JoinColumn(name = "ASSIGNED_TO")
	protected Contactable assignedTo;

	@ManyToOne
	@JoinColumn(name = "RAISED_BY")
	protected Contactable raisedBy;

	@Column(name = "TITLE")
	@Field(index = Index.YES, store = Store.NO)
	protected String title;

	@Column(name = "DEADLINE")
	private Date deadline;

	@Column(name = "DETAILS")
	@Lob
	protected String details;

	@OneToMany(mappedBy = "activity", fetch = FetchType.LAZY, cascade = {})
	@Cascade( { CascadeType.PERSIST, CascadeType.DELETE })
	private List<Notification> notifications = new ArrayList<Notification>();

	public Contactable getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(Contactable assignedTo) {
		this.assignedTo = assignedTo;
	}

	public Contactable getRaisedBy() {
		return raisedBy;
	}

	public void setRaisedBy(Contactable raisedBy) {
		this.raisedBy = raisedBy;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void addNotification(Notification n) {
		this.notifications.add(n);
	}

	public void removeNotification(Notification n) {
		this.notifications.remove(n);
	}

	@PrePersist
	public void distributeNotifications() {
		Account acc = ApplicationService.getInstance().getModifyingAccount();
		Person user = acc.getUserProfile();
		List<Person> notified = new ArrayList<Person>();

		if (this.getObjectType().equals(ObjectType.TODO)) {
			createNotification(user, notified);

		} else if (this.getObjectType().equals(ObjectType.PRIVATEMESSAGE)) {
			createNotification(user, notified);
			createNotificationAssignedTo(notified);

		} else if (this.getObjectType().equals(ObjectType.ANSWER)) {
			createNotification(user, notified);
			Answer a = (Answer) this;
			Question q = a.getQuestion();
			createNotification((Person) q.getRaisedBy(), notified);
			createNotificationFollowers(user, notified);

		} else if (this.getObjectType().equals(ObjectType.ANSWER)) {
			createNotification(user, notified);
			createNotificationAssignedTo(notified);

		} else {
			createNotification(user, notified);
			createNotificationAssignedTo(notified);
			createNotificationFollowers(user, notified);
		}

	}

	private void createNotificationFollowers(Person user, List<Person> notified) {
		for (Person p : user.getFollowers()) {
			createNotification(p, notified);
		}
	}

	private void createNotificationAssignedTo(List<Person> notified) {
		if (this.getAssignedTo() instanceof Team) {
			for (Person p : ((Team) this.getAssignedTo()).getMembers()) {
				createNotification(p, notified);
			}
		} else {
			createNotification((Person) this.getAssignedTo(), notified);
		}
	}

	private void createNotification(Person p, List<Person> notified) {
		if (!notified.contains(p)) {
			Notification n = new Notification();

			n.setActivity(this);
			this.addNotification(n);
			p.addNotification(n);
			notified.add(p);
		}
	}

}
