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
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import com.tmm.enterprise.microblog.domain.enums.ObjectType;

/**
 * 
 * Class to model Task/Work ticket/Tracker Items for a given user. These are
 * designed to create formal tasks that users can assign to other users/teams
 * for completion. This is used as a more formal work tracking system.
 * 
 * @author robert.hinds
 * 
 */
@Entity
@Table(name = "BF_TASK")
@DiscriminatorValue("WorkTask")
@Indexed
public class WorkTask extends Activity {

	private static final long serialVersionUID = 8645771574430866151L;

	public enum Priorities {
		LOW, MEDIUM, HIGH
	};

	public enum State {
		ASSIGNED, ONHOLD, CLOSED
	};

	@Column(name = "COMPLETED")
	private boolean completed;

	@Column(name = "PRIORITY")
	private String priority = "MEDIUM";

	@Column(name = "STATE")
	private String state = "ASSIGNED";

	@ManyToMany
	@JoinTable(name = "TASK_DEPENDENCIES", joinColumns = { @JoinColumn(name = "TASK_ID") }, inverseJoinColumns = { @JoinColumn(name = "DEPENDENCY") })
	private Set<WorkTask> dependencies = new HashSet<WorkTask>();

	@IndexedEmbedded
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CATEGORY_ID")
	private CategoryDropDown category;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "ticket")
	private List<TicketUpdate> updates = new ArrayList<TicketUpdate>();

	public Set<WorkTask> getDependencies() {
		return dependencies;
	}

	public void setDependencies(Set<WorkTask> dependencies) {
		this.dependencies = dependencies;
	}

	public void addDependency(WorkTask task) {
		dependencies.add(task);
	}

	public void removeDependency(WorkTask task) {
		dependencies.remove(task);
	}

	@Override
	public String getName() {
		return title;
	}

	@Override
	public ObjectType getObjectType() {
		return ObjectType.TRACKER;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setPriority(Priorities priority) {
		this.priority = priority.toString();
	}

	public Priorities getPriority() {
		if (this.priority != null) {
			return Priorities.valueOf(priority);
		}
		return Priorities.MEDIUM;
	}

	public void setCategory(CategoryDropDown category) {
		this.category = category;
	}

	public CategoryDropDown getCategory() {
		return category;
	}

	public void setUpdates(List<TicketUpdate> updates) {
		this.updates = updates;
	}

	public List<TicketUpdate> getUpdates() {
		return updates;
	}

	public void addUpdate(TicketUpdate update) {
		this.updates.add(update);
	}

	public void removeUpdate(TicketUpdate update) {
		this.updates.remove(update);
	}

	public void setState(State state) {
		this.state = state.toString();
	}

	public State getState() {
		if (this.state != null) {
			return State.valueOf(this.state);
		}
		return State.ASSIGNED;
	}

	public Long getTicketNumber() {
		return getId();
	}

}