/**
 * 
 */
package com.tmm.enterprise.microblog.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Where;

/**
 * @author robert.hinds
 * 
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "contactableType")
@Table(name = "BF_CONTACT")
public abstract class Contactable extends PersistableObject {

	private static final long serialVersionUID = -8904416991717314873L;

	@ManyToMany(mappedBy = "raisedBy", targetEntity = Activity.class, fetch = FetchType.LAZY)
	@Cascade(CascadeType.DELETE_ORPHAN)
	@Where(clause = "activity_type='Todo'")
	private List<ToDo> todoItems = new ArrayList<ToDo>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "BF_NOTIFICATION_USERS", joinColumns = @JoinColumn(name = "CONTACTABLE_ID", referencedColumnName = "ID"), inverseJoinColumns = @JoinColumn(name = "NOTIFICATION_ID", referencedColumnName = "ID"))
	private List<Notification> notifications = new ArrayList<Notification>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PERSON_CONNECTIONS", joinColumns = { @JoinColumn(name = "CONNECTION_ID") }, inverseJoinColumns = { @JoinColumn(name = "PERSON_ID") })
	private Set<Person> followers = new HashSet<Person>();

	public void setTodoItems(List<ToDo> todoItems) {
		this.todoItems = todoItems;
	}

	public List<ToDo> getTodoItems() {
		return todoItems;
	}

	public void removeTodoItem(ToDo td) {
		todoItems.remove(td);
	}

	public void addTodoItem(ToDo td) {
		todoItems.add(td);
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

	public void setFollowers(Set<Person> followers) {
		this.followers = followers;
	}

	public Set<Person> getFollowers() {
		return followers;
	}
}
