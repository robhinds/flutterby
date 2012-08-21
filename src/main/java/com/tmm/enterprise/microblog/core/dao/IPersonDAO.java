package com.tmm.enterprise.microblog.core.dao;

import java.util.List;

import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.Person;

public interface IPersonDAO {

	List<Person> loadAllPersons();

	 List<Person> loadPeople(Long[]ids);
}