package edu.upc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="userx")  // La tabla en BD no puede llamarse User porque es un nombre reservado
public class User {


	@Id
	private String username;  // La clave deberia ser el nombre del usuario. No puede ser autogenerado sino capturado por la aplicacion
	
	private String password;
	private Integer delay;
	private Date created;
	private Integer karma;
	private String about;
	
	@OneToMany(mappedBy= "by")
	private List<Item> items =  new ArrayList<Item>();
	
	// Podemos personalizar los constructores para facilitar la creacion del objeto
	
	public User() { // necesito un constructor sin parametros si voy a usar otros
		
	}
	
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.created = new Date();   // siempre que creamos un user, le ponemos la fecha/hora
	}
	
	public User(String username, String password, Integer delay, Integer karma) {
		this(username, password); // llamamos al constructor anterior
		this.delay = delay;
		this.karma = karma;	
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Integer getDelay() {
		return delay;
	}
	public void setDelay(Integer delay) {
		this.delay = delay;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Integer getKarma() {
		return karma;
	}
	public void setKarma(Integer karma) {
		this.karma = karma;
	}

	public List<Item> getItems() {
		return items;
	}


	public void setItems(List<Item> items) {
		this.items = items;
	}


	@Override
	public String toString() {
		return "User [username=" + username + " password=" + password + ", delay=" + delay + ", created="
				+ created + ", karma=" + karma + ", about=" + about
				+ ", items=" + items + "]";
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getAbout() {
		return about;
	}


	public void setAbout(String about) {
		this.about = about;
	}
	
	
}
