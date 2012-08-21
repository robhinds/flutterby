/**
 * 
 */
package com.tmm.enterprise.microblog.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.tmm.enterprise.microblog.domain.enums.ObjectType;

/**
 * @author robert.hinds
 * 
 */
@Entity
@Table(name = "BF_PRIVATE_MESSAGES")
@DiscriminatorValue("Message")
public class PrivateMessage extends Activity {

	private static final long serialVersionUID = -3991384143664978994L;

	@Column(name = "UNREAD")
	private boolean read = true;

	@Override
	public ObjectType getObjectType() {
		return ObjectType.PRIVATEMESSAGE;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	@Override
	public String getName() {
		return this.getTitle();
	}
}
