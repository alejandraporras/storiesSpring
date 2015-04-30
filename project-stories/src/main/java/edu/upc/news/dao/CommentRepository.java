package edu.upc.news.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.upc.news.model.Comment;
import edu.upc.news.model.Item;
import edu.upc.news.model.User;


public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	/* Al heredar de JpaRepository nuestro repositorio particular dispone de muchisimos metodos de acceso a base de datos
	 * 
	 * Heredamos de JpaRepository para tener mas funcionalidad. Podriamos tambien heredar de:
	 * 
	 * 		CrudRepository
	 * 		PagingAndSortingRepository
	 * 
	 * 
	 * JpaRepository se deriva de los anteriores, por lo tanto tiene capacidad CRUD y paginacion y ordenamiento.
	 *
	 * 
	 */
	public List<Item> findByParent(Item parent);
}
