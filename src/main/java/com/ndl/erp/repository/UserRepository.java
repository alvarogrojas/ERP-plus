package com.ndl.erp.repository;

import com.ndl.erp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserRepository extends JpaRepository<User, Long> {


	User findByName(String name);

	User findByUsername(String username);

	User findById(long id);

	List<User> findAll();

	@Query(value = "select u from User u where u.status='Activo' order by name asc")
	List<User> findUsersActive();

}
