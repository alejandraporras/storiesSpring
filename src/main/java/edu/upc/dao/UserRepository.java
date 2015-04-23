package edu.upc.dao;

import org.springframework.data.repository.CrudRepository;

import edu.upc.model.User;

public interface UserRepository extends CrudRepository<User,String> {
	
	/*
	 * Para los Users no necesitamos tantas funciones de acceso a datos por lo tanto lo derivamos de CRUD solamente
	 * 
	 * Observad que este repositorio va a acceder objetos User con una Primary Key que es String
	 * 
	 */

}
