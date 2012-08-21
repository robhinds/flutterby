package com.tmm.enterprise.microblog.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.Status;

public class StatusServiceDbTest extends AbstractServiceTest {

	@Autowired
	StatusService service;

	@Test
	public void testCreateStatusStatus() {
		//
		// Status s = createTestsStatus("being sent!");
		// Status loaded = service.loadStatus(s.getId());
		// assertNotNull(loaded);
		// assertEquals("test status being sent!", loaded.getStatus());
	}

	private Status createTestsStatus(String identifier) {
		Person sender = new Person();
		Status s = new Status(sender, "test status " + identifier);
		service.createStatus(s);
		return s;
	}

}
