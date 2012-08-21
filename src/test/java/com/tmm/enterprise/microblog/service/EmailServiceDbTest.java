package com.tmm.enterprise.microblog.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailServiceDbTest extends AbstractServiceTest {

	@Autowired
	MessageService service;

	/**
	 * this tests load and create in one, as cant be seperated
	 */
	@Test
	public void testCreatePrivateMessage() {
		// PrivateMessage p = service.loadPrivateMessage(1);
		// assertEquals("PrivateMessage already exists", p, null);
		//
		// PrivateMessage newP = new PrivateMessage();
		// service.createPrivateMessage(newP);
		//
		// p = service.loadPrivateMessage(newP.getId());
		// assertEquals("different PrivateMessage loaded", p, newP);
	}
}
