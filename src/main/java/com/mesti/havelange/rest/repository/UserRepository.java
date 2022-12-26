package com.mesti.havelange.rest.repository;

import com.mesti.havelange.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "todos", path = "todos")
public interface UserRepository extends JpaRepository<User, Long> {
}
