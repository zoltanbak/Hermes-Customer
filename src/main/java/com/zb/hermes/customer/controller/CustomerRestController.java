package com.zb.hermes.customer.controller;

import com.zb.hermes.customer.model.Customer;
import com.zb.hermes.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1/customers")
public class CustomerRestController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping
    public Flux<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @PostMapping
    public Mono<Customer> postCustomer(@RequestBody Customer customer) {
        return customerRepository.save(
                new Customer(customer.getFirstName(),
                             customer.getLastName()));
    }

}
