package edu.upc.news.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
public class Comment extends Item {
	
	// No tiene ID propia. El ID del objeto esta definido en el padre (Item)
	
	@ManyToOne
	private Item parent;
	
	public Comment() {
		
	}
	
	public Comment(User user, String text, Item parentItem ) {
		// Inicializamos los datos de Item
		super(user,"comment");
		super.setText(text);
		parent = parentItem;
		parent.incrementDescendants();
	
	}
	
	public Item getParent() {
		return parent;
	}

	public void setParent(Item parent) {
		this.parent = parent;
	}

}
