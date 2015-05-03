package edu.upc.news.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.upc.news.dao.UserRepository;
import edu.upc.news.model.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public User findByName(String name) {
		return userRepository.findOne(name);
	}

	@Override
	public void saveUser(User user) {
		userRepository.save(user);
		
	}

}
