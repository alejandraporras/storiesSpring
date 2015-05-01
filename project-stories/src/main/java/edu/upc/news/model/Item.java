package edu.upc.news.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)  // Esto es lo que faltaba burrita!!  Este tipo de herencia asegura que cada subclase tendra su propia tabla

public class Item {  // Esta clase podria definirse como abstracta. Depende de tus intereses

	@Id
	@GeneratedValue
	private Long id;
	
	private boolean deleted;
	private String type;
	
	// Esta simpatica anotacion va a ligar varios items a un user determinado. Ademas crea en base de datos la columna de enlace necesario.
	// El 'by' en lugar de ser un String seria preferible que sea el objeto User
	@ManyToOne
	private User by; 
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy="parent")
	private List<Comment> comments ;
	
	private Date time;
	private boolean dead;
	private String text;
	
	private int descendants;
	
	// Algunos constructores
	public Item() {
		
	}
	
	public Item(User user, String type) { // los datos basicos/minimos
		this.by = user;
		this.type = type;
		this.time = new Date();
		descendants = 0;
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
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public int getDescendants() {
		return descendants;
	}

	public void setDescendants(int descendants) {
		this.descendants = descendants;
	}
	
	
	public void incrementDescendants(){
		descendants += 1;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", deleted=" + deleted + ", type=" + type
				+ ", by=" + by + ", time=" + time + ", dead=" + dead + ", User=" + by.getUsername() + "]";
	}
	
	
}
