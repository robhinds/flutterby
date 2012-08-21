package com.tmm.enterprise.microblog.core.dao;

import java.util.List;

import com.tmm.enterprise.microblog.domain.Contactable;
import com.tmm.enterprise.microblog.domain.Question;

public interface IQuestionDAO {

	public abstract List<Question> loadAllQuestionsAskedByUser(Contactable user);

	public abstract List<Question> loadAllQuestionsAskedToUser(Contactable user);

	public abstract List<Question> loadLatestQuestions(int limit);

}