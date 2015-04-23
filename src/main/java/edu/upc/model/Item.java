package edu.upc.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)  // Este tipo de herencia asegura que cada subclase tendra su propia tabla

public class Item {  // Esta clase podria definirse como abstracta. Depende de tus intereses

	@Id
	@GeneratedValue
	private Long id;
	
	private boolean deleted;
	private String type;
	

	@ManyToOne		// Esta  anotacion liga varios items a un user determinado. Ademas crea en base de datos la columna de enlace necesario.
	private User by;  // El 'by' en lugar de ser un String seria preferible que sea el objeto User

	@OneToMany(mappedBy="parent")
	private List<Comment> comments = new ArrayList<Comment>();
	private Date time;
	private boolean dead;

	
	// Algunos constructores
	public Item() {
		
	}
	
	public Item(String type, User user) { 
		this.type = type;
		this.by = user;
		this.time = new Date();

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User getBy() {
		return by;
	}

	public void setBy(User by) {
		this.by = by;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", deleted=" + deleted + ", type=" + type
				+ ", by=" + by + ", comments=" + comments + ", time=" + time
				+ ", dead=" + dead ;
	}
		
}
