package com.ndl.erp.repository;


import com.ndl.erp.domain.Role;
import com.ndl.erp.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

@Component
public interface RoleRepository extends JpaRepository<Role, Long> {

	Role getRoleById(long id);

	Role findByName(String name);

	@Query(value = "select c from Role c where c.name like %?1% ")
	Page<Role> findUsingFilterPageable(String filter, Pageable pageable);

	@Query(value = "select count(c.id) from Role c where c.name like %?1% ")
	public Integer countAllByFilter(String filter);
}
