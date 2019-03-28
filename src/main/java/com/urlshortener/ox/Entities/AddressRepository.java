package com.urlshortener.ox.Entities;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address,Integer> {

    @Query("SELECT MAX(id) FROM Address")
    int getMAXID();



}
