/**
 * 
 */
package com.tmm.enterprise.microblog.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tmm.enterprise.microblog.domain.enums.ObjectType;

/**
 * Class to model response to WorkTickets objects. These are designed to allow a
 * user to respond/interact witht the original poster of a Workticket
 * 
 * @author robert.hinds
 * 
 */
@Entity
@Table(name = "BF_TICKETUPDATE")
@DiscriminatorValue("TicketUpdate")
public class TicketUpdate extends Activity {

	private static final long serialVersionUID = 3721440741665145341L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TICKET_ID")
	private WorkTask ticket;

	@Override
	public String getName() {
		return title;
	}

	@Override
	public ObjectType getObjectType() {
		return ObjectType.TICKETUPDATE;
	}

	public void setTicket(WorkTask ticket) {
		this.ticket = ticket;
	}

	public WorkTask getTicket() {
		return ticket;
	}
}