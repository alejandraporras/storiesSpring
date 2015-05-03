package edu.upc.news.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="userx")  // La tabla en BD no puede llamarse User porque es un nombre reservado
public class User {


	@Id
	private String username;  // La clave deberia ser el nombre del usuario. No puede ser autogenerado sino capturado por la aplicacion
	
	private int delay;
	private Date created;
	private int karma;
	private String password;
	
	private String socialName;
	
	// Un User tiene varios items (historias, comments, etc)
	// el mappedBy tiene que indicar el atributo en el objeto 'Item' que hace referencia a 'User'
	// CascadeType.REMOVE : si elimino un User, se eliminan TODOS sus items
	@OneToMany(mappedBy = "by", cascade = CascadeType.REMOVE)
	private List<Item> items;  
	
	// Podemos personalizar los constructores para facilitar la creacion del objeto
	
	public User() { // necesito un constructor sin parametros si voy a usar otros
		
	}
	
	
	public User(String username,String password) {
		this.username = username;
		this.password = password;
		this.created = new Date();   // siempre que creamos un user, le ponemos la fecha/hora
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public int getKarma() {
		return karma;
	}
	public void setKarma(int karma) {
		this.karma = karma;
	}
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getSocialName() {
		return socialName;
	}


	public void setSocialName(String socialName) {
		this.socialName = socialName;
	}


	@Override
	public String toString() {
		return "UserTable [username=" + username + ", delay="
				+ delay + ", created=" + created + ", karma=" + karma + "]";
	}
	
}
