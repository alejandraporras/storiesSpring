package edu.upc.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;









import edu.upc.news.dao.ItemRepository;
import edu.upc.news.dao.StoryRepository;
import edu.upc.news.model.Comment;
import edu.upc.news.model.Item;
import edu.upc.news.model.Story;
import edu.upc.news.model.User;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private StoryRepository storyRepository;
	
	public List<Item> getItemsByUser(User user) {
		return itemRepository.findByBy(user);		
	}
	
	public List<Item> getCommentsByUser(User user) {
		return itemRepository.findByByAndType(user,"comment");		
	}
	
	public List<Item> getStoriesByUser(User user) {
		return itemRepository.findByByAndType(user,"story");		
	}
	
	public List<Item> getAllStories(){
		return itemRepository.findByType("story");		
	}
	
	public List<Item> getAllAsk(){
		return itemRepository.findByType("ask");		
	}

	@Override
	public Item findItem(Long id) {
		// TODO Auto-generated method stub
		return itemRepository.findOne(id);
	}

	@Override
	public void newStory(String title, String url, User user) {
		Story story = new Story(user, url, title);
		storyRepository.save(story);
	}

	@Override
	public void newAsk(String title, User user, String url, String text) {
		Story story = new Story(user, title, "", text);
		storyRepository.save(story);
	}

	




}
