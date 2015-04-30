package edu.upc.news.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.upc.news.model.Comment;
import edu.upc.news.model.Item;
import edu.upc.news.model.User;

public interface ItemRepository extends JpaRepository<Item, Long> {
	
	// Ademas de los findXXX generales que hay en JpaRepository
	
	// Necesitamos un find que nos devuelva los items de un User dado
	// Eso no existe en JpaRepository, por eso lo creamos nosotros
	
	public List<Item> findByBy(User user);
	
	
	// Este metodo hace una busqueda compuesta por el atributo 'by' y por el atributo 'type'
	// esto me permitira separar los items que son 'story' y 'comments'
	public List<Item> findByByAndType(User user, String type);
	
	public List<Item> findByType(String type);
	
	

}
