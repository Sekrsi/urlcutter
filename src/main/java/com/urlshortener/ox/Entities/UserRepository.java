package com.urlshortener.ox.Entities;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {


    @Query("select u from User u where u.username = ?1")
    User findByUsername(String username);


    @Query("select u from User u where u.email = ?1")
    User findByEmail(String email);



}
