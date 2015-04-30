package edu.upc.news.model;

import javax.persistence.Entity;

@Entity
public class Story extends Item {
	
	// No tiene ID propia. El ID del objeto esta definido en el padre (Item)
	
	// Con este solo objeto podemos representar Story y Ask
	

	private Integer score;
	
	private String title;
	
	private String url;  // Un Ask es un Story con URL null
	
	public Story() {
		
	}
	
	// Creadora de un ask
	
	public Story(User user,String title) {
		super(user,"ask");  // inicializa los datos minimos del padre (item)
		this.title = title;

		score = 0;

	}
	// Creadora de un story
	public Story(User user,String title, String url) {
		super(user,"story");  // inicializa los datos minimos del padre (item)
		this.title = title;

		score = 0;
		this.url = "http://"+url;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
	public Long getId() {
		return super.getId();
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
		this.url = "http://"+url;
	}
		

}
