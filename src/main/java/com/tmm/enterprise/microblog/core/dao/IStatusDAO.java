package com.tmm.enterprise.microblog.core.dao;

import java.util.List;

import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.Status;

public interface IStatusDAO {

	public abstract List<Status> loadAllStatus(int limit);

	public abstract List<Status> loadAllStatusByUser(Contactable user);

}