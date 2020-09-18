package com.example.api.repository;

import com.example.api.model.CustomerAddress;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<CustomerAddress, Long> {

}
