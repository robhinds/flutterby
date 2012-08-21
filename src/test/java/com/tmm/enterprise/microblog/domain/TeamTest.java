package com.tmm.enterprise.microblog.domain;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.tmm.enterprise.microblog.domain.enums.ObjectType;

public class TeamTest {

	@Test
	public void testTeam() {
		Team t = new Team();
		t.setName("team1");
		assertEquals("team1", t.getName());
		assertEquals(ObjectType.TEAM, t.getObjectType());
		
		List<Person> members = new ArrayList<Person>();
		t.setMembers(members);
		assertEquals(members, t.getMembers());
		assertEquals(0, t.getMembers().size());
		
		//add member
		Person p = new Person();
		t.addMember(p);
		assertEquals(1, t.getMembers().size());
		assertEquals(p,t.getMembers().get(0));
		
		//remove member
		t.removeMember(p);
		assertEquals(0, t.getMembers().size());
		
		//set parent
		Team parent = new Team();
		t.setParentTeam(parent);
		assertEquals(parent, t.getParentTeam());
		
		Team sub = new Team();
		List<Team> subTeams = new ArrayList<Team>();
		t.setSubTeams(subTeams);
		assertEquals(0, t.getSubTeams().size());
		assertEquals(subTeams, t.getSubTeams());
		
		t.addSubTeam(sub);
		assertEquals(1, t.getSubTeams().size());
		assertEquals(sub, t.getSubTeams().get(0));
		
		t.removeSubTeam(sub);
		assertEquals(0, t.getSubTeams().size());
		
		t.setDescription("team2");
		assertEquals("team2", t.getDescription());
		
		Project proj = new Project();
		t.setProject(proj);
		assertEquals(proj, t.getProject());
	}
}
