package edu.upc.news.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.upc.news.model.Comment;
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

}
