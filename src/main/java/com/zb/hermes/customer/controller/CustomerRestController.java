package com.zb.hermes.customer.controller;

import com.zb.hermes.customer.model.Customer;
import com.zb.hermes.customer.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.util.UUID;

@RestController
@RequestMapping(value = "api/v1")
@Slf4j
public class CustomerRestController {

    @Autowired
    CustomerRepository customerRepository;


    @GetMapping("customers")
    public Flux<Customer> getAllCustomer() {
        return customerRepository.findAll();
    }

    @GetMapping("search/findById")
    public Mono<Customer> findById(@RequestParam UUID id) {
        return customerRepository.findById(id);
    }

    @PostMapping
    public Mono<Customer> postCustomer(@RequestBody Customer customer) {
        return customerRepository.save(
                new Customer(customer.getFirstName(),
                             customer.getLastName()));
    }

}
