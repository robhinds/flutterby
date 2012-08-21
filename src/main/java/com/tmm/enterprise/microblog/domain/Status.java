/**
 * 
 */
package com.tmm.enterprise.microblog.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;

import com.tmm.enterprise.microblog.domain.enums.ObjectType;

/**
 * @author robert.hinds
 * 
 */
@Entity
@Table(name = "BF_STATUS")
@DiscriminatorValue("Status")
@Indexed
public class Status extends Activity {

	private static final long serialVersionUID = 4925606208793500282L;

	public Status(Contactable sender, String text) {
		this.title = text;
		this.assignedTo = sender;
		this.raisedBy = sender;
	}

	public Status() {
	}

	@Override
	public String getName() {
		return title;
	}

	public void setStatus(String s) {
		this.title = s;
	}

	public String getStatus() {
		return title;
	}

	@Override
	public ObjectType getObjectType() {
		return ObjectType.STATUS;
	}
}
