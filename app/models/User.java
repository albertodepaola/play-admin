package models;

import javax.persistence.Entity;

import play.data.validation.Constraints.Required;

@Entity
public class User extends Person {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -450693349365372948L;
	
	@Required
	private String username;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
