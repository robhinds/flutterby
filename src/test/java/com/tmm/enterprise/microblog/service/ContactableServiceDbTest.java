package com.tmm.enterprise.microblog.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.Team;

public class ContactableServiceDbTest extends AbstractServiceTest{
	
	@Autowired
	ContactableService service;

	/**
	 * this tests load and create in one, as cant be seperated
	 */
	@Test
	public void testLoadPerson() {
		Person p = service.loadPerson(1);
		assertEquals("person already exists", p, null);
		
		Person newP = new Person();
		service.createPerson(newP);

		p = service.loadPerson(newP.getId());
		assertEquals("different guy loaded", p, newP);
	}

	
	@Test
	public void testLoadAllPersons() {
		List<Person> people = service.loadAllPersons();
		assertEquals("Check DB is empty first", 0, people.size());
		Person p = new Person();
		service.createPerson(p);
		people = service.loadAllPersons();
		assertEquals("check person has been created", 1, people.size());
	}

	@Test
	public void testLoadTeam() {
		Team t = service.loadTeam(1);
		assertEquals("person already exists", t, null);
		
		Team newT = new Team();
		service.createTeam(newT);
		
		t = service.loadTeam(newT.getId());
		assertEquals("different guy loaded", t, newT);
	}

	@Test
	public void testLoadAllTeams() {
		List<Team> team = service.loadAllTeams();
		assertEquals("Check DB is empty first", 0, team.size());
		Team t = new Team();
		service.createTeam(t);
		team = service.loadAllTeams();
		assertEquals("check person has been created", 1, team.size());
	}

}
