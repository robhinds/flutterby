/**
 * 
 */
package com.tmm.enterprise.microblog.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.tmm.enterprise.microblog.domain.enums.ObjectType;
import com.tmm.enterprise.microblog.domain.enums.UserRole;
import com.tmm.enterprise.microblog.security.Account;

/**
 * @author robert.hinds
 * 
 */
@Entity
@DiscriminatorValue("Person")
@Table(name = "BF_PERSON")
// @Indexed
public class Person extends Contactable {

	private static final long serialVersionUID = 3458607287170514439L;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LINKED_ACCOUNT")
	private Account linkedAccount;

	@Column(name = "ROLE")
	private UserRole role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEAM")
	private Team team;

	@ManyToMany(mappedBy = "assignedTo", targetEntity = Activity.class, fetch = FetchType.LAZY)
	@OrderBy("creationDate desc")
	@Where(clause = "activity_type='Message'")
	private List<PrivateMessage> receivedMessages = new ArrayList<PrivateMessage>();

	@OneToMany(mappedBy = "raisedBy", targetEntity = Activity.class, fetch = FetchType.LAZY)
	@OrderBy("creationDate desc")
	@Where(clause = "activity_type='Message'")
	private List<PrivateMessage> sentMessages = new ArrayList<PrivateMessage>();

	@OneToMany(mappedBy = "raisedBy", targetEntity = Activity.class, fetch = FetchType.LAZY)
	@OrderBy("creationDate desc")
	@Where(clause = "activity_type='Status'")
	private List<Status> statuses = new ArrayList<Status>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "PERSON_CONNECTIONS", joinColumns = { @JoinColumn(name = "PERSON_ID") }, inverseJoinColumns = { @JoinColumn(name = "CONNECTION_ID") })
	private Set<Contactable> following = new HashSet<Contactable>();

	@Override
	public String getName() {
		if (linkedAccount == null) {
			return "No Account Found";
		} else {
			return linkedAccount.getUserName();
		}

	}

	public void setLinkedAccount(Account linkedAccount) {
		this.linkedAccount = linkedAccount;
	}

	public Account getLinkedAccount() {
		return linkedAccount;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public UserRole getRole() {
		return role;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Team getTeam() {
		return team;
	}

	/**
	 * Method to return all direct reportees to the current person (returns an
	 * empty list if the person is not the team manager)
	 * 
	 * @return {@link List} of {@link Person}
	 */
	public List<Person> getDirectReportees() {
		switch (getRole()) {
		case MANAGER:
			return getTeam().getMembers();
		}

		return new ArrayList<Person>();
	}

	@Override
	public ObjectType getObjectType() {
		return ObjectType.PERSON;
	}

	public void setReceivedMessages(List<PrivateMessage> inbox) {
		this.receivedMessages = inbox;
	}

	public List<PrivateMessage> getReceivedMessages() {
		return receivedMessages;
	}

	public void setSentMessages(List<PrivateMessage> sentMessages) {
		this.sentMessages = sentMessages;
	}

	public List<PrivateMessage> getSentMessages() {
		return sentMessages;
	}

	public void addSentMessage(PrivateMessage m) {
		sentMessages.add(m);
	}

	public void removeSentMessage(PrivateMessage m) {
		sentMessages.remove(m);
	}

	public void addReceivedMessage(PrivateMessage m) {
		receivedMessages.add(m);
	}

	public void removeReceivedMessage(PrivateMessage m) {
		receivedMessages.remove(m);
	}

	public void setStatuses(List<Status> statuses) {
		this.statuses = statuses;
	}

	public List<Status> getStatuses() {
		return statuses;
	}

	public void addStatus(Status s) {
		statuses.add(s);
	}

	public void removeStatus(Status s) {
		statuses.remove(s);
	}

	public Status getLatestStatus() {
		return statuses.isEmpty() ? null : statuses.get(0);
	}

	public void setFollowing(Set<Contactable> following) {
		this.following = following;
	}

	public Set<Contactable> getFollowing() {
		return following;
	}

	public void addFollowing(Contactable c) {
		following.add(c);
	}

	public void removeFollowing(Contactable c) {
		following.remove(c);
	}
}
