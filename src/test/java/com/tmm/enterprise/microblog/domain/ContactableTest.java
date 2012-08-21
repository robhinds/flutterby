package com.tmm.enterprise.microblog.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

public class ContactableTest {

	@Test
	public void testContactablePerson() {
		Person p = new Person();
		List<PrivateMessage> sent = new ArrayList<PrivateMessage>();
		List<PrivateMessage> recieved = new ArrayList<PrivateMessage>();
		
		//set messages
		p.setReceivedMessages(recieved);
		p.setSentMessages(sent);
		assertEquals(0, p.getReceivedMessages().size());
		assertEquals(recieved, p.getReceivedMessages());
		assertEquals(0, p.getSentMessages().size());
		assertEquals(sent, p.getSentMessages());
		
		//add messages
		PrivateMessage pm = new PrivateMessage();
		p.addReceivedMessage(pm);
		p.addSentMessage(pm);
		assertEquals(1, p.getReceivedMessages().size());
		assertEquals(pm, p.getReceivedMessages().get(0));
		assertEquals(1, p.getSentMessages().size());
		assertEquals(pm, p.getSentMessages().get(0));
		
		//remove messages
		p.removeReceivedMessage(pm);
		p.removeSentMessage(pm);
		assertEquals(0, p.getReceivedMessages().size());
		assertEquals(0, p.getSentMessages().size());
		

		//set status
		Status s = new Status();
		List<Status> statuses = new ArrayList<Status>();
		p.setStatuses(statuses);
		assertEquals(0, p.getStatuses().size());
		assertEquals(statuses, p.getStatuses());
		
		//add status
		p.addStatus(s);
		assertEquals(1, p.getStatuses().size());
		assertEquals(s, p.getStatuses().get(0));
		
		//remove status
		p.removeStatus(s);
		assertEquals(0, p.getStatuses().size());
		
		//get latest status
		p.addStatus(s);
		Status latest = p.getLatestStatus();
		assertEquals(s, latest);

		//check creation date
		Date now = new Date();
		p.setCreationDate(now);
		assertEquals(now, p.getCreationDate());
	
		
		//check modification date
		Date later = new Date();
		p.setModifiedDate(later);
		assertEquals(now, p.getModifiedDate());
	
	}

}
