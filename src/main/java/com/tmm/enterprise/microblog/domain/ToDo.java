/**
 * 
 */
package com.tmm.enterprise.microblog.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;

import com.tmm.enterprise.microblog.domain.enums.ObjectType;

/**
 * @author robert.hinds
 * 
 *         Class to model ToDo Items or notes for a given user. These are
 *         designed to allow a user to make notes or simple todo items for
 *         themselves. It does not support creation of ToDos or notes for other
 *         users - workItems/Trackers should be used to assign work to others.
 * 
 */
@Entity
@Table(name = "BF_TODO")
@DiscriminatorValue("Todo")
@Indexed
public class ToDo extends Activity {

	private static final long serialVersionUID = -2760225496623271902L;
	@Column(name = "COMPLETED")
	private boolean completed;

	@Override
	public String getName() {
		return title;
	}

	@Override
	public ObjectType getObjectType() {
		return ObjectType.TODO;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public boolean isCompleted() {
		return completed;
	}

}