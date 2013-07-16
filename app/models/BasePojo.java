package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import play.db.ebean.Model;

@MappedSuperclass
@Entity
public class BasePojo extends Model {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1022357615946897996L;
	
	@Id
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
