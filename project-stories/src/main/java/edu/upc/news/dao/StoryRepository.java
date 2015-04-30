package edu.upc.news.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.upc.news.model.Story;

public interface StoryRepository extends JpaRepository<Story,Long> {
	
	/*
	 * Un repositorio para manejar las historias. Similar a CommentRepository, se deriva de JpaRepository
	 */
	

	
	/*
	 * Aqui viene lo interesante y practico. En este momento esta interface dispone de metodos de acceso a datos como:
	 * 
	 * 		- save()
	 * 		- findOne()
	 * 		- findAll()
	 * 		- count()
	 * 		- delete()
	 */
	
	
	 /*
	  * Pero ademas un repositorio Spring puede definir metodos adicionales cuya implementacion las generara el propio framework Spring Data
	  */
	
	
	// Podriamos necesitar un metodo para buscar todas las historias relacionadas a un score dado
	
	// La clave para que Spring Data pueda crear estos metodos es que nuestro repositorio los defina con un nombre dado:
	//
	//				findByXXXXX : donde XXXX es un atributo del objeto relacionado al repositorio (Story en este caso)
	
	public List<Story> findByScore(Integer score);  // Con este nombre SpringData sabe que para implementar este metodo
													// necesita lanzar una Query sobre la entidad 'Story' con la columna 'score'
	
	
	// otro mas
	public List<Story> findByTitle(String title);
	
	
	// y otro mas
	public List<Story> findByUrl(String url);
	
	
}
