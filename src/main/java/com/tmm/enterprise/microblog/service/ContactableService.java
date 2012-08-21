package com.tmm.enterprise.microblog.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tmm.enterprise.microblog.core.dao.IContactableDAO;
import com.tmm.enterprise.microblog.core.dao.PersonDAO;
import com.tmm.enterprise.microblog.core.dao.TeamDAO;
import com.tmm.enterprise.microblog.core.exception.ButterflyException;
import com.tmm.enterprise.microblog.core.exception.ButterflyExceptionCode;
import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.Team;
import com.tmm.enterprise.microblog.domain.enums.UserRole;
import com.tmm.enterprise.microblog.security.Account;

@Service("contactableService")
@Repository
@Transactional
public class ContactableService {
	private EntityManager entityManager;

	@Autowired
	private AccountService accountService;
	@Autowired
	private JsonService jsonService;

	@Autowired
	private PersonDAO personDao;
	@Autowired
	private TeamDAO teamDao;
	@Autowired
	private IContactableDAO contactableDao;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setPersonDao(PersonDAO personDao) {
		this.personDao = personDao;
	}

	public void setTeamDao(TeamDAO teamDao) {
		this.teamDao = teamDao;
	}

	public void setContactableDao(IContactableDAO contactableDao) {
		this.contactableDao = contactableDao;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void setJsonService(JsonService jsonService) {
		this.jsonService = jsonService;
	}

	@Transactional
	public void createPerson(Person p) {
		personDao.persist(p);
	}

	@Transactional
	public void mergePerson(Person p) {
		personDao.merge(p);
	}

	@Transactional
	public Person loadPerson(long id) {
		return getEntityManager().find(Person.class, id);
	}

	@Transactional
	public List<Person> loadAllPersons() {
		return personDao.loadAllPersons();
	}

	@Transactional
	public List<Contactable> loadAllContactables() {
		return contactableDao.loadAllContactables();
	}

	@Transactional
	public void createTeam(Team t) {
		teamDao.persist(t);
	}

	@Transactional
	public Team loadTeam(long id) {
		return getEntityManager().find(Team.class, id);
	}

	@Transactional
	public List<Team> loadAllTeams() {
		return teamDao.loadAllTeams();
	}

	@Transactional
	public Contactable loadContactable(long id) {
		return getEntityManager().find(Contactable.class, id);
	}

	@Transactional
	public Contactable loadContactableByName(String contactName) throws ButterflyException {
		Account acc = accountService.loadAccountByUserName(contactName);
		if (acc != null) {
			Person user = acc.getUserProfile();
			if (user != null) {
				return user;
			}
		}

		// not found user by name so try looking for teams
		Team team = teamDao.loadTeamByName(contactName);
		if (team != null) {
			return team;
		}

		// if nothing returned now then lets throw an exception
		throw new ButterflyException(ButterflyExceptionCode.CONTACT001_CONTACTABLENOTFOUND, "No User/Team found matching name: " + contactName);
	}

	/**
	 * method to connect a target contactable to the current user
	 * 
	 * @param userName
	 *            - current users username
	 * @param connectId
	 *            - target Contactable ID
	 * @throws ButterflyException
	 */
	@Transactional
	public void connect(String userName, long connectId) throws ButterflyException {
		Account acc = accountService.loadAccountByUserName(userName);
		if (acc != null) {
			Person user = acc.getUserProfile();
			if (user != null) {
				Contactable c = loadContactable(connectId);
				user.addFollowing(c);
			} else {
				throw new ButterflyException(ButterflyExceptionCode.CONTACT002_ERRORMAKINGCONNECTION, "Error making connection: ");
			}
		} else {
			throw new ButterflyException(ButterflyExceptionCode.CONTACT002_ERRORMAKINGCONNECTION, "Error making connection: ");
		}

	}

	/**
	 * Build the JSON required for the org chart
	 * 
	 * @param t
	 * @return
	 */
	public JsonObject buildOrgChart(Team t) {
		JsonObject team = new JsonObject();
		team.addProperty("teamName", t.getName());
		team.addProperty("teamDesc", t.getDescription());

		JsonArray members = new JsonArray();
		for (Person p : t.getMembers()) {
			members.add(jsonService.convertToJson(p));
		}
		team.add("members", members);

		JsonArray subTeams = new JsonArray();
		for (Team t1000 : t.getSubTeams()) {
			subTeams.add(buildOrgChart(t1000));
		}
		team.add("subTeams", subTeams);

		return team;
	}

	@Transactional
	public void createTeam(String teamName, long parentTeamId, String teamDesc) {
		Team t = new Team();
		t.setName(teamName);
		t.setDescription(teamDesc);
		if (parentTeamId != 0) {
			Team pTeam = loadTeam(parentTeamId);
			if (pTeam != null) {
				t.setParentTeam(t);
				pTeam.addSubTeam(t);
			}
		}

		createTeam(t);
	}

	@Transactional
	public void updateTeam(long selectedTeamId, long parentTeamId, long teamLeadId ,Long[]teamIds) {
		Team t = loadTeam(selectedTeamId);

		if (parentTeamId != 0) {
			Team pTeam = loadTeam(parentTeamId);
			t.setParentTeam(pTeam);
		}

		List<Person> teamMembers = personDao.loadPeople(teamIds);
		for(Person p : teamMembers)
		{
			p.setTeam(t); 
		}
		t.setMembers(teamMembers); 
		
		if (teamLeadId != 0) {
			for (Person p : t.getMembers()) {
				if (p.getId() == teamLeadId) {
					p.setRole(UserRole.MANAGER);
				} else {
					p.setRole(UserRole.MEMBER);
				}
			}
		}
	}
}