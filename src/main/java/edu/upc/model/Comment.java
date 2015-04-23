package edu.upc.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Comment extends Item {
	
	// No tiene ID propia. El ID del objeto esta definido en el padre (Item)
	
	@ManyToOne		
	private Item parent; // representa la relacion: Comments*     1 Items

	private String text;
	
	public Comment(){
		
	}
	public Comment(User user, String text, Item parent){
		super("Comment", user);
		this.parent = parent;
		this.text = text;
	}
	public Item getParent() {
		return parent;
	}

	public void setParent(Item parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "Comment [parent=" + parent + "]";
	}
	
	
	
}
