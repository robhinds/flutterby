package com.tmm.enterprise.microblog.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmm.enterprise.microblog.core.exception.ButterflyException;
import com.tmm.enterprise.microblog.core.exception.ButterflyExceptionCode;
import com.tmm.enterprise.microblog.domain.Person;
import com.tmm.enterprise.microblog.domain.PrivateMessage;
import com.tmm.enterprise.microblog.security.Account;

@Service("messageService")
@Repository
@Transactional
public class MessageService {
	private EntityManager entityManager;

	@Autowired
	private AccountService accountService;
	@Autowired
	private ContactableService contactService;

	@PersistenceContext
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public void setContactService(ContactableService cService) {
		this.contactService = cService;
	}

	/**
	 * Persist a PrivateMessage object
	 * 
	 * @param s
	 */
	@Transactional
	public void createPrivateMessage(PrivateMessage pm) {
		getEntityManager().persist(pm);
	}

	@Transactional
	public PrivateMessage loadPrivateMessage(long id) {
		return getEntityManager().find(PrivateMessage.class, id);
	}

	/**
	 * Method to create and send a new email
	 * 
	 * @param senderUserName
	 * @param msg
	 *            body
	 * @param recipId
	 * @throws ButterflyException
	 */
	@Transactional
	public void sendEmail(String senderUserName, String msg, String recipId) throws ButterflyException {
		Person recipient = null;
		Account senderAcc = accountService.loadAccountByUserName(senderUserName);
		Person sender = senderAcc.getUserProfile();
		try {
			Long id = Long.parseLong(recipId);
			recipient = contactService.loadPerson(id);
		} catch (Exception e) {
			throw new ButterflyException(ButterflyExceptionCode.USER002_INVALIDUSERID, "Invalid recipient ID provided - ID must be numeric", e);
		}

		PrivateMessage pm = new PrivateMessage();
		pm.setDetails(msg);
		pm.setAssignedTo(recipient);
		pm.setRaisedBy(sender);

		createPrivateMessage(pm);
		sender.addSentMessage(pm);
		recipient.addReceivedMessage(pm);
	}

}