package edu.upc.news.service;

import org.springframework.stereotype.Service;

import edu.upc.news.model.User;

public interface UserService {
	User findByName(String name);
}
