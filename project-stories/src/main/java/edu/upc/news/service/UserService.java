package edu.upc.news.service;

import edu.upc.news.model.User;

public interface UserService {
	User findByName(String name);
	void saveUser(User user);
}
