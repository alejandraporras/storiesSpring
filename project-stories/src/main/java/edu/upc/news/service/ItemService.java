package edu.upc.news.service;

import java.util.List;

import edu.upc.news.model.Item;
import edu.upc.news.model.User;

public interface ItemService {
	
	public List<Item> getItemsByUser(User user);
	
	public List<Item> getCommentsByUser(User user);
	
	public List<Item> getStoriesByUser(User user);
	
	public List<Item> getAllStories();
	
	public List<Item> getAllAsk();
	
	public Item findItem(Long id);
	
	public void newStory(String title, String url, User user);
	
	public void newAsk(String title,  User user, String url, String text);
	
	public void newComment(User user, String text, Item parentItem );

}
