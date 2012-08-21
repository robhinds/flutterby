/**
 * 
 */
package com.tmm.enterprise.microblog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * @author robert.hinds
 * 
 *         Object to capture notifications of new Activities - this allows us to
 *         tie activity events (such as new message, work ticket, etc) to an
 *         individual)
 * 
 */
@Entity
@Table(name = "BF_NOTIFICATION")
public class Notification {

	private static final long serialVersionUID = -8935712447903901463L;

	@Id
	@TableGenerator(name = "TABLE_GEN", table = "SEQUENCE", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PO_SEQ")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LINKED_ACTIVITY", nullable = false)
	private Activity activity;

	@Column(name = "notificationRead")
	private boolean read = false;

	@Column(name = "NOTIFICATION_TYPE")
	private String discriminator;

	public void setActivity(Activity activity) {
		this.activity = activity;
		this.discriminator = (activity.getObjectType().toString());
	}

	public Activity getActivity() {
		return activity;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public boolean isRead() {
		return read;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getDiscriminator() {
		return discriminator;
	}

}
