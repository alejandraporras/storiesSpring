package edu.upc.news.model;

import javax.persistence.Entity;

@Entity
public class Story extends Item {
	
	// No tiene ID propia. El ID del objeto esta definido en el padre (Item)
	
	// Con este solo objeto podemos representar Story y Ask
	
	private int score;
	
	private String title;
	
	private String url;  // Un Ask es un Story con URL null
	
	public Story() {
		
	}
	
	// Ask
	public Story(User user,String title, String url, String text) {
		super(user,"ask");  // inicializa los datos minimos del padre (item)
		this.title = title;
		this.score=0;
		super.setText(text);

	}
	
	// Story
	public Story(User user,String title, String url) {
		
		
		super(user,"story");  
		this.title = title;
		this.url = url;
		this.score=0;
	}

	

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
		

}
