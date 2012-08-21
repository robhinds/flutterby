/**
 * 
 */
package com.tmm.enterprise.microblog.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.tmm.enterprise.microblog.domain.enums.ObjectType;

/**
 * @author robert.hinds Project Team persistent entity
 */
@Entity
@DiscriminatorValue("Team")
@Table(name = "BF_TEAM")
// @Indexed
public class Team extends Contactable {

	private static final long serialVersionUID = 2621515235293172595L;

	@Column(name = "NAME")
	// @Field(index=Index.TOKENIZED, store=Store.NO)
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
	private List<Person> members = new ArrayList<Person>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_TEAM")
	private Team parentTeam;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "parentTeam")
	private List<Team> subTeams = new ArrayList<Team>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJET")
	private Project project;

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setMembers(List<Person> members) {
		this.members = members;
	}

	public List<Person> getMembers() {
		return members;
	}

	public void addMember(Person p) {
		members.add(p);
	}

	public void removeMember(Person p) {
		members.remove(p);
	}

	public void setParentTeam(Team parentTeam) {
		this.parentTeam = parentTeam;
	}

	public Team getParentTeam() {
		return parentTeam;
	}

	public void setSubTeams(List<Team> subTeams) {
		this.subTeams = subTeams;
	}

	public List<Team> getSubTeams() {
		return subTeams;
	}

	public void addSubTeam(Team t) {
		subTeams.add(t);
	}

	public void removeSubTeam(Team t) {
		subTeams.remove(t);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * @return the project (if current team project is not set then iterate
	 *         upwards through team stucture to find project
	 */
	public Project getProject() {
		if (project == null) {
			Team pTeam = getParentTeam();
			while (pTeam != null) {
				if (pTeam.getProject() != null) {
					return pTeam.getProject();
				}
				pTeam = pTeam.getParentTeam();
			}
		}
		return project;
	}

	@Override
	public ObjectType getObjectType() {
		return ObjectType.TEAM;
	}

}
