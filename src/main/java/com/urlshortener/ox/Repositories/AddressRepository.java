package com.urlshortener.ox.Repositories;

import com.urlshortener.ox.Entities.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends CrudRepository<Address,Integer> {

    Iterable<Address> findByUserID(Long user);

}
