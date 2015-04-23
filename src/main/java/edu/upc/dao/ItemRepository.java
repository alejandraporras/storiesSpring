package edu.upc.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.upc.model.Item;
import edu.upc.model.User;

public interface ItemRepository extends JpaRepository<Item, Long> {
	public List<Item> findByBy(User user);
}
