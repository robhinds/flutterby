package com.tmm.enterprise.microblog.core.dao;

import java.util.List;

import com.tmm.enterprise.microblog.domain.Activity;
import com.tmm.enterprise.microblog.domain.Contactable;

public interface IActivityDAO {

	public abstract List<Activity> loadAllPublicActivitiesByUser(Contactable user, int limit);

}