package com.urlshortener.Repositories;

import com.urlshortener.Entities.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address,Integer> {

    Iterable<Address> findByUserID(Long user);

}
