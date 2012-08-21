/**
 * 
 */
package com.tmm.enterprise.microblog.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.tmm.enterprise.microblog.domain.Person;

/**
 * @author robert.hinds
 * 
 *         Class that represents an individual user account. Contains user
 *         details such as username, password, name, email and the set of Roles
 *         that the person has.
 * 
 */
@Entity
@Table(name = "BF_ACCOUNT")
public class Account implements Serializable {

	public static final long serialVersionUID = 42L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "USERNAME", unique = true, nullable = false)
	private String userName;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "linkedAccount", cascade = { CascadeType.ALL })
	private Person userProfile;

	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Role> roles;

	/**
	 * @return the roles
	 */
	public Set<Role> getRoles() {
		return roles;
	}

	/**
	 * @param roles
	 *            the roles to set
	 */
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName
	 *            the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName
	 *            the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * this method takes a plain string password and encodes it before setting
	 * it for the account
	 * 
	 * @param password
	 *            - decoded password string
	 */
	public void setAndEncodePassword(String password) {
		// create the encoder object
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		encoder.setEncodeHashAsBase64(true);

		// encode the password using the unique user ID as the salt
		String encodedPassword = encoder.encodePassword(password, new Long(this.getId()));

		// set the password
		setPassword(encodedPassword);
	}

	public String getDecodedPassword() {
		// TODO
		return getPassword();
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	public void addRole(Role role) {
		if (getRoles() != null && getRoles().size() > 0) {
			getRoles().add(role);
		} else {
			Set<Role> r = new HashSet<Role>();
			r.add(role);
			setRoles(r);
		}
	}

	/**
	 * @param userProfile
	 *            the userProfile to set
	 */
	public void setUserProfile(Person userProfile) {
		this.userProfile = userProfile;
	}

	/**
	 * @return the userProfile
	 */
	public Person getUserProfile() {
		return userProfile;
	}

}
