
package edu.upc.news;


import org.springframework.context.support.FileSystemXmlApplicationContext;

import edu.upc.news.dao.CommentRepository;
import edu.upc.news.dao.ItemRepository;
import edu.upc.news.dao.StoryRepository;
import edu.upc.news.dao.UserRepository;
import edu.upc.news.model.Comment;
import edu.upc.news.model.Story;
import edu.upc.news.model.User;




/**
 * Standalone application with Spring Data JPA, Hibernate and Maven
 *
 */
public class App2 {
	
	public static void main(String[] args) {
		
		FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/applicationContext.xml");
	
	
		
		UserRepository userRepo = context.getBean(UserRepository.class);
		StoryRepository storyRepo = context.getBean(StoryRepository.class);
		CommentRepository commentRepo = context.getBean(CommentRepository.class);
		
		
		// En realidad este repositorio no lo necesito creo. Cada vez que grabo un Comment o una Story se crea un Item
		ItemRepository itemRepo = context.getBean(ItemRepository.class);
		
		// Creamos un user
		User user = new User("Ale", "pass");
		userRepo.save(user);
		
		
		Story story = new Story(user,"Este es el titulo - Primera Historia", "http://www.google.com");

		
		storyRepo.save(story);
		// Creamos varios coments a esta historia
		
		for (int i=1; i <=10; ++i) {
			Comment comment = new Comment(user,"Comentarioooooooooo",story);
			//comment.setParent(story); // esta es la clave: el comentario correponde a la historia de Alejita
			commentRepo.save(comment);
		}
		
		
	
		storyRepo.save(story);
		// Creamos un reply del comentario anterior
		
		Comment aux = new Comment(user,"Comentario base!",story);
		commentRepo.save(aux);
		Comment reply = new Comment(user,"Replica a comentario base",aux);
		commentRepo.save(reply);
		
	
		// Creamos 5 historias mas
		for (int i=1; i <=5; ++i) {
			story = new Story(user,"titulo de la Story "+i, "http://www.google.com");
			storyRepo.save(story);
		}
		// Creamos 3 aks mas
		for (int i=1; i <=3; ++i) {
			story = new Story(user,"Preguntaa "+i, "", "textoASK");
			storyRepo.save(story);
			Comment com = new Comment(user,"Comentario a la pregunta!",story);
			commentRepo.save(com);
			storyRepo.save(story);
		}
		
		
	
		context.close();
		
	}
}