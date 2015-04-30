package edu.upc.news.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
public class Comment extends Item {
	
	// No tiene ID propia. El ID del objeto esta definido en el padre (Item)
	
	@ManyToOne
	private Item parent;
	private String text;
	
	public Comment() {
		
	}
	
	public Comment(User user, String text ) {
		// Inicializamos los datos de Item
		super(user,"comment");
		this.setText(text);
	}
	
	public Item getParent() {
		return parent;
	}
	public Long getId() {
		return super.getId();
	}
	public void setParent(Item parent) {
		parent.incrementDescendants();
		this.parent = parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
