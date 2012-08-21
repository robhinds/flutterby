/**
 * 
 */
package com.tmm.enterprise.microblog.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.tmm.enterprise.microblog.domain.enums.ObjectType;

/**
 * Class to model Question or Awnser objects. These are designed to allow a user
 * to ask or answer questions - also allows a basic scoring system to allow
 * users to vote for questions or answers that they like.
 * 
 * @author robert.hinds
 * 
 */
@Entity
@Table(name = "BF_ANSWER")
@DiscriminatorValue("Answer")
public class Answer extends Activity {

	private static final long serialVersionUID = 3721440741665145341L;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "QUESTION_ID")
	private Question question;

	@Column(name = "CORRECT")
	private boolean correct = false;

	@Column(name = "SCORE")
	private int score = 0;

	public boolean isCorrect() {
		return correct;
	}

	public void setCorrect(boolean correct) {
		this.correct = correct;
	}

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
		return ObjectType.ANSWER;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public Question getQuestion() {
		return question;
	}
}