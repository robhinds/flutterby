package com.tmm.enterprise.microblog.core.dao;

import java.util.List;

import com.tmm.enterprise.microblog.domain.Contactable;

public interface IContactableDAO {

	 List<Contactable> loadAllContactables();
	
}