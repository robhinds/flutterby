/**
 * 
 */
package com.tmm.enterprise.microblog.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import com.tmm.enterprise.microblog.domain.enums.ObjectType;

/**
 * @author robert.hinds
 * 
 *         Class to model Question objects. These are designed to allow a user
 *         to ask questions - also allows a basic scoring system to allow users
 *         to vote for questions that they like.
 * 
 */
@Entity
@Table(name = "BF_QUESTION")
@DiscriminatorValue("Question")
@Indexed
public class Question extends Activity {

	private static final long serialVersionUID = -3937272863191270148L;

	@Column(name = "SCORE")
	private int score = 0;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "question")
	private List<Answer> answers = new ArrayList<Answer>();

	@IndexedEmbedded
	@ManyToMany
	@JoinTable(name = "QUESTION_TAGS", joinColumns = { @JoinColumn(name = "QUESTION_ID") }, inverseJoinColumns = { @JoinColumn(name = "TAG_ID") })
	private Set<QuestionTag> tags = new HashSet<QuestionTag>();

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	@Override
	public String getName() {
		return title;
	}

	@Override
	public ObjectType getObjectType() {
		return ObjectType.QUESTION;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void addAnswer(Answer a) {
		getAnswers().add(a);
	}

	public void removeAnswer(Answer a) {
		getAnswers().remove(a);
	}

	/**
	 * method to check whether the question has an answer that has been marked
	 * as correct
	 * 
	 * @return
	 */
	public boolean isAnswered() {
		for (Answer a : getAnswers()) {
			if (a.isCorrect()) {
				return true;
			}
		}
		return false;
	}

	public void setTags(Set<QuestionTag> tags) {
		this.tags = tags;
	}

	public Set<QuestionTag> getTags() {
		return tags;
	}

	public void addTag(QuestionTag t) {
		this.tags.add(t);
	}

	public void removeTag(QuestionTag t) {
		this.tags.remove(t);
	}

}