package com.tmm.enterprise.microblog.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.tmm.enterprise.microblog.domain.enums.ObjectType;

public class ProjectTest {

	@Test
	public void testProject() {
		Project p = new Project();
		p.setName("project1");
		assertEquals("project1", p.getName());
		assertEquals(ObjectType.PROJECT, p.getObjectType());
		p.setDescription("project1");
		assertEquals("project1", p.getDescription());
		
		//set teams
		List<Team> teams = new ArrayList<Team>();
		p.setTeams(teams);
		assertEquals(teams, p.getTeams());
		assertEquals(0, p.getTeams().size());
		
		//add teams 
		Team t = new Team();
		p.addTeam(t);
		assertEquals(1, p.getTeams().size());
		assertEquals(t, p.getTeams().get(0));
		
		//remove team
		p.removeTeam(t);
		assertEquals(0, p.getTeams().size());
	}

}
