package models;

import javax.persistence.Entity;

import play.data.validation.Constraints.Required;

@Entity
public class Person extends BasePojo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7615870894576532893L;
	
	@Required
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
