package com.ndl.erp.repository;

import com.ndl.erp.domain.User;
import com.ndl.erp.domain.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
//public interface UserRoleRepository  {

    @Query("select c from UserRole c  where c.user.id = :id")
	List<UserRole> findByUserId(Long id);
//
//	List<User> findAll();

    @Query("select c.user from UserRole c  where c.role.name in (?1)")
    Set<User> getUsersByRole(List<String> roles);
}
