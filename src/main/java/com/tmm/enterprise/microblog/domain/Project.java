package com.tmm.enterprise.microblog.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tmm.enterprise.microblog.domain.enums.ObjectType;

/**
 * @author robert.hinds
 * 
 */
@Entity
@Table(name = "BF_PROJECT")
public class Project extends PersistableObject {

	private static final long serialVersionUID = -7502564699357671926L;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
	private List<Team> teams = new ArrayList<Team>();

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setTeams(List<Team> t) {
		this.teams = t;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void addTeam(Team t) {
		teams.add(t);
	}

	public void removeTeam(Team t) {
		teams.remove(t);
	}

	@Override
	public ObjectType getObjectType() {
		return ObjectType.PROJECT;
	}

}
