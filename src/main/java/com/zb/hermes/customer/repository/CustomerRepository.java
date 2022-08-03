package com.zb.hermes.customer.repository;

import com.zb.hermes.customer.model.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface CustomerRepository extends ReactiveCrudRepository<Customer, UUID> {

}
