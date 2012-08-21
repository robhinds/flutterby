package com.tmm.enterprise.microblog.core.dao;

import java.util.List;

import com.tmm.enterprise.microblog.domain.Team;

public interface ITeamDAO {

	public abstract List<Team> loadAllTeams();

	public abstract Team loadTeamByName(String name);

}