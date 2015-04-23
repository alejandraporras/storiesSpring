package edu.upc.model;

import javax.persistence.Entity;

@Entity
public class Story extends Item {
	
	// No tiene ID propia. El ID del objeto esta definido en el padre (Item)
	
	// Con este solo objeto podemos representar Story y Ask
	
	private Integer descendants;
	
	private Integer score;
	
	private String title;
	
	private String url;  // Un Ask es un Story con URL null
	
	public Story() {
		//super();
	}
	
	public Story(String title, String url, User user) {
		super("story", user);  // inicializa los datos minimos del padre (item)
		this.title = title;
		this.url = url;		
	}

	public Integer getDescendants() {
		return descendants;
	}

	public void setDescendants(Integer descendants) {
		this.descendants = descendants;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
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

	@Override
	public String toString() {
		return "Story [descendants=" + descendants + ", score=" + score
				+ ", title=" + title + ", url=" + url + "]";
	}
		

}
