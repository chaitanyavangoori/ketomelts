package com.ketomelts.main.repository;

import com.ketomelts.main.domain.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Chaitanya on 2/20/17.
 */
public interface CustomerRepository extends MongoRepository<Customer, String>{
    Customer findByEmail(String email);
}
