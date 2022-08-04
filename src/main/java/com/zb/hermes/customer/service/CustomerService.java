package com.zb.hermes.customer.service;

import com.zb.hermes.customer.model.Customer;
import com.zb.hermes.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Flux<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Mono<Customer> findById(UUID id) {
        return customerRepository.findById(id);
    }

    public Mono<Customer> save(Customer customer) {
        return customerRepository.save(
                new Customer(customer.getFirstName(),
                        customer.getLastName()));
    }
}
