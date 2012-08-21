package com.tmm.enterprise.microblog.core.dao;

import java.util.List;

import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.ToDo;

public interface IToDoDAO {

	public abstract List<ToDo> loadAllTodosByUser(Contactable user);

}