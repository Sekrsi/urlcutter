package com.urlshortener.ox.Repositories;

import com.urlshortener.ox.Entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {


  //  @Query("select u from User u where u.username = ?1")
    User findByUsername(String username);


  //  @Query("select u from User u where u.email = ?1")
    User findByEmail(String email);



}
