package edu.upc;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.upc.dao.CommentRepository;
import edu.upc.dao.ItemRepository;
import edu.upc.dao.StoryRespository;
import edu.upc.dao.UserRepository;
import edu.upc.model.Comment;
import edu.upc.model.Item;
import edu.upc.model.Story;
import edu.upc.model.User;



/**
 * Standalone application with Spring Data JPA, Hibernate and Maven
 */
public class App {
	
	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		
	
		
		UserRepository userRepo = context.getBean(UserRepository.class);
		StoryRespository storyRepo = context.getBean(StoryRespository.class);
		CommentRepository commentRepo = context.getBean(CommentRepository.class);
		
		
		// En realidad este repositorio no lo necesito creo. Cada vez que grabo un Comment o una Story se crea un Item
		ItemRepository itemRepo = context.getBean(ItemRepository.class);
		
		// Creamos un user
		User user = new User("Ale", "XXX");
		userRepo.save(user);
		
		
		Story story = new Story("Este es el titulo - Primera Historia", "www.yahoo.com", user);
		storyRepo.save(story);
		
		// Creamos un comentario a esta historia
		Comment comment = new Comment(user,"Comentario de la primera historia de Ale", story);
		commentRepo.save(comment);
		
		// Creamos un reply del comentario anterior
		
		Comment reply = new Comment(user,"Vaya mierda de comentario!", comment);
		commentRepo.save(reply);
		
		// Observa que en la clase Comment el atributo 'parent' es de tipo Item
		// y un Comment o una Story son Item
	
		
		// Ahora vamos a recuperar los datos grabados
		// 1ro el user
		User userLeido = userRepo.findOne(user.getUsername());
		
		// a continuacion los items del user (ahora si que necesito ItemRepository)
		// Los List<Item> items de User no se recuperan de forma magica
		// Recordar lo de LAZY init
		// por lo tanto tengo que recuperalos via un metodo que he creado en ItemRepository
		// En realidad el metodo esta vacio. Spring Data se encarga de generarlo
		
		List<Item> items = itemRepo.findByBy(userLeido);
		userLeido.setItems(items);
		
		
		// Listamos todos los items de Alejita:
		for (Item item: userLeido.getItems())  {
			System.out.println("Item Id:"+ item.getId() + " Tyep="+ item.getType() + " Date:"+item.getTime());
		}		
		
		
		
		context.close();
	}
}
