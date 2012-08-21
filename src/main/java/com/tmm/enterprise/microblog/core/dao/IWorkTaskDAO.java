package com.tmm.enterprise.microblog.core.dao;

import java.util.List;

import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.WorkTask;

public interface IWorkTaskDAO {

	public abstract List<WorkTask> loadAllWorkTasksRaisedByUser(Contactable user);

	public abstract List<WorkTask> loadAllWorkTasksRaisedToUser(Contactable user);

	public abstract List<WorkTask> loadLatestWorktTasks(int limit);

}