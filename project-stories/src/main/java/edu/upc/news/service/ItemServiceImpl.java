package edu.upc.news.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.upc.news.dao.CommentRepository;
import edu.upc.news.dao.ItemRepository;
import edu.upc.news.dao.StoryRepository;
import edu.upc.news.model.Comment;
import edu.upc.news.model.Item;
import edu.upc.news.model.Story;
import edu.upc.news.model.User;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	public List<Item> getItemsByUser(User user) {
		return itemRepository.findByBy(user);		
	}
	
	@Override
	public List<Item> getCommentsByUser(User user) {
		return itemRepository.findByByAndType(user,"comment");		
	}
	
	@Override
	public List<Item> getStoriesByUser(User user) {
		return itemRepository.findByByAndType(user,"story");		
	}
	
	@Override
	public List<Item> getAllStories(){
		return itemRepository.findByType("story");		
	}
	
	@Override
	public List<Item> getAllAsk(){
		return itemRepository.findByType("ask");		
	}

	@Override
	public Item findItem(Long id) {
		return itemRepository.findOne(id);
	}

	@Override
	public void saveStory(String title, String url, User user) {
		System.out.println("CREANDO HISTORIA: " + title + "  URL: " + url);
		Story story = new Story(user, title, url);
		storyRepository.save(story);
	}

	@Override
	public void saveAsk(String title, User user, String url, String text) {
		Story story = new Story(user, title, "", text);
		storyRepository.save(story);
	}

	@Override
	public void newComment(User user, String text, Item parentItem) {
		Comment comment = new Comment(user,text, parentItem);
		commentRepository.save(comment);
		Item parent = itemRepository.findOne(parentItem.getId());
		parent.incrementDescendants();
		itemRepository.save(parent);
	}

	@Override
	public String rateStory(Long idItem) {
		Story story = (Story) itemRepository.findOne(idItem);
		story.incrementLikes();
		System.out.println("SCORE: " +story.getScore());
		itemRepository.save(story);
		return story.getType();
	}





}
