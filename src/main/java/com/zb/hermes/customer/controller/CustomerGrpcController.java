package com.zb.hermes.customer.controller;

import com.google.protobuf.Empty;
import com.zb.hermes.customer.model.Customer;
import com.zb.hermes.customer.service.CustomerService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;
import zb.hermes.database.*;

import java.time.ZoneOffset;
import java.util.UUID;

@GrpcService
@Slf4j
public class CustomerGrpcController extends CustomerDatabaseServiceGrpc.CustomerDatabaseServiceImplBase {

    @Autowired
    CustomerService customerService;

    @Override
    public void create(CreateCustomerRequest request, StreamObserver<Empty> responseObserver) {
        final String firstName = request.getFirstName();
        final String lastName = request.getLastName();

        log.info("New CreateCustomerRequest with firstName: " + firstName + ", lastName: " + lastName);

        Customer customer = Customer.
                builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        // TODO: Check if the returning is the same
        customerService.save(customer);

        log.info("CreateNewCustomerRequest onNext");
        responseObserver.onNext(Empty.newBuilder().build());
        log.info("CreateNewCustomerRequest onCompleted");
        responseObserver.onCompleted();
    }

    @Override
    public void read(ReadCustomerRequest request, StreamObserver<ReadCustomerResponse> responseObserver) {
        final String id = request.getId();

        log.info("New ReadCustomerRequest with id: " + id);

        Mono<Customer> customer = customerService.findById(UUID.fromString(id));

        customer.subscribe(
            result -> {
                log.info("ReadCustomerRequest onNext");
                responseObserver.onNext(ReadCustomerResponse.newBuilder()
                        .setId(result.getId().toString())
                        .setFirstName(result.getFirstName())
                        .setLastName(result.getLastName())
                        .setRegisteredOn(result.getRegisteredOn().toInstant(ZoneOffset.UTC).toEpochMilli())
                        .build());

                log.info("ReadCustomerRequest onCompleted");
                responseObserver.onCompleted();
            }
        );
    }
}
