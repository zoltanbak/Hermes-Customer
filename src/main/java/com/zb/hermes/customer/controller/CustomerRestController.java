package com.zb.hermes.customer.controller;

import com.zb.hermes.customer.model.Customer;
import com.zb.hermes.customer.repository.CustomerRepository;
import com.zb.hermes.customer.service.CustomerService;
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
    CustomerService customerService;


    @GetMapping("customers")
    public Flux<Customer> getAllCustomer() {
        return customerService.findAll();
    }

    @GetMapping("search/findById")
    public Mono<Customer> findById(@RequestParam UUID id) {
        return customerService.findById(id);
    }

    @PostMapping("customers")
    public Mono<Customer> postCustomer(@RequestBody Customer customer) {
        return customerService.save(customer);
    }
}
