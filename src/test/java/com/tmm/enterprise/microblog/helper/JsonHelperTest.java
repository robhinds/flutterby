package com.tmm.enterprise.microblog.helper;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.coverter.html.BBCodeConverter;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.PrivateMessage;
import com.tmm.enterprise.microblog.domain.Status;
import com.tmm.enterprise.microblog.domain.Team;
import com.tmm.enterprise.microblog.domain.enums.UserRole;
import com.tmm.enterprise.microblog.service.JsonService;

public class JsonHelperTest {

	private JsonService jsonService;

	@Before
	public void init() {
		jsonService = new JsonService();
		BBCodeConverter bbc = new BBCodeConverter();
		jsonService.setHtmlConverter(bbc);
	}

	@Test
	public void testConvertToJsonStatus() {
		Status s = new Status();
		s.setStatus("test status!");
		Date now = new Date();
		s.setCreationDate(now);
		s.setId(1l);
		JsonObject job = jsonService.convertToJson(s);
		assertEquals(
				"{\"body\":\"test status!\",\"title\":\"test status!\",\"createdAt\":\""
						+ now.toString()
						+ "\",\"createdBy\":\"UNKNOWN AUTHOR\",\"id\":1,\"displayDate\":\"just now\",\"objectType\":\"STATUS\"}",
				job.toString());
	}

	@Test
	public void testConvertToJsonPrivateMessage() {
		Date now = new Date();
		PrivateMessage pm = new PrivateMessage();
		pm.setDetails("test pm body text");
		pm.setCreationDate(now);
		pm.setId(1l);
		pm.setTitle("pm subject");
		JsonObject job = jsonService.convertToJson(pm);
		assertEquals(
				"{\"body\":\"test pm body text\",\"createdBy\":\"UNKNOWN AUTHOR\",\"createdAt\":\""
						+ now.toString()
						+ "\",\"displayDate\":\"just now\","
						+ "\"id\":1,\"title\":\"pm subject\",\"recipients\":[],\"objectType\":\"PRIVATEMESSAGE\"}",
				job.toString());
	}

	@Test
	public void testConvertToJsonPerson() {
		Person p = new Person();
		p.setRole(UserRole.MEMBER);
		p.setId(1l);
		JsonObject job = jsonService.convertToJson(p);
		assertEquals(
				"{\"id\":1,\"name\":\"No Account Found\",\"role\":\"MEMBER\",\"teamName\":\"\",\"objectType\":\"PERSON\",\"status\":\"No Status Set Yet\"}",
				job.toString());
	}

	@Test
	public void testConvertToJsonTeam() {
		Team t = new Team();
		t.setId(1l);
		t.setName("test team");
		t.setDescription("dummy team");
		JsonObject job = jsonService.convertToJson(t);
		assertEquals(
				"{\"id\":1,\"name\":\"test team\",\"description\":\"dummy team\",\"objectType\":\"TEAM\"}",
				job.toString());
	}

}
