package com.urlshortener.ox.Entities;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends CrudRepository<Address,Integer> {



    @Query("SELECT A from Address A where A.user.id=?1")
    Iterable<Address> userAddresses(Long user);



}
