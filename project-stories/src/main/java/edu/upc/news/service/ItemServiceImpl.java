package edu.upc.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;





import edu.upc.news.dao.ItemRepository;
import edu.upc.news.model.Comment;
import edu.upc.news.model.Item;
import edu.upc.news.model.User;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemRepository itemRepository;
	
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

	




}
