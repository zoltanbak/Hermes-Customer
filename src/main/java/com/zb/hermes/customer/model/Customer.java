package com.zb.hermes.customer.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    private UUID id;

    private String firstName;

    private String lastName;

    private LocalDateTime registeredOn;

    public Customer(String firstName,
                    String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.registeredOn = LocalDateTime.now();
    }
}

