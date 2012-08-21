package com.tmm.enterprise.microblog.util;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.Team;
import com.tmm.enterprise.microblog.service.ContactableService;

@Service("batchService")
@Repository
@Transactional
public class BatchService {
	private EntityManager entityManager;

	@Autowired
	private ContactableService contactableService;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setContactableService(ContactableService contactableService) {
		this.contactableService = contactableService;
	}

	@Transactional
	public void setAllTeams(String teamName) {
		List<Person> peeps = contactableService.loadAllPersons();
		Team t = new Team();
		t.setName(teamName);
		for (Person p : peeps) {
			if (p.getTeam() != null) {
				continue;
			}
			System.out.println("Person assigned to team! " + teamName);
			t.addMember(p);
			p.setTeam(t);
		}
		contactableService.createTeam(t);
	}
}