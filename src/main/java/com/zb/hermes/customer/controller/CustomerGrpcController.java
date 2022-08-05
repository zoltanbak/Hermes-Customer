package com.zb.hermes.customer.controller;

import com.google.protobuf.Empty;
import com.zb.hermes.customer.model.Customer;
import com.zb.hermes.customer.service.CustomerService;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;
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

        log.debug("New CreateCustomerRequest received with firstName: " + firstName + ", lastName: " + lastName);

        Customer customer = Customer.
                builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        Mono<Customer> resultCustomer = customerService.save(customer);

        // TODO: Examines this. Have to subscribe to be actually written to the DB
        resultCustomer.subscribe(
                result -> {
                    log.debug(result.toString());
                }
        );

        log.debug("CreateNewCustomerRequest onNext");
        responseObserver.onNext(Empty.newBuilder().build());
        log.debug("CreateNewCustomerRequest onCompleted");
        responseObserver.onCompleted();
    }

    @Override
    public void readAll(Empty request, StreamObserver<ReadCustomerResponse> responseObserver) {

        log.info("New ReadAllRequest received");

        Flux<Customer> customer = customerService.findAll();

        customer.subscribe(
                result -> {
                    log.debug("ReadAllRequest onNext");
                    responseObserver.onNext(ReadCustomerResponse.newBuilder()
                            .setId(result.getId().toString())
                            .setFirstName(result.getFirstName())
                            .setLastName(result.getLastName())
                            .setRegisteredOn(result.getRegisteredOn().toInstant(ZoneOffset.UTC).toEpochMilli())
                            .build());
                },
                throwable -> {
                    log.error("ReadCustomerRequest onError: " + throwable);
                    responseObserver.onError(throwable);
                },
                () -> {
                    log.debug("ReadAllRequest onCompleted");
                    responseObserver.onCompleted();
                }
        );

        log.debug("ReadAllRequest returns");
    }

    @Override
    public void read(ReadCustomerRequest request, StreamObserver<ReadCustomerResponse> responseObserver) {
        final String id = request.getId();

        log.info("New ReadCustomerRequest received with id: " + id);

        Mono<Customer> customer = customerService.findById(UUID.fromString(id));

        customer.subscribe(
                result -> {
                    log.debug("ReadCustomerRequest onNext");
                    responseObserver.onNext(ReadCustomerResponse.newBuilder()
                            .setId(result.getId().toString())
                            .setFirstName(result.getFirstName())
                            .setLastName(result.getLastName())
                            .setRegisteredOn(result.getRegisteredOn().toInstant(ZoneOffset.UTC).toEpochMilli())
                            .build());
                },
                throwable -> {
                    log.error("ReadCustomerRequest onError: " + throwable);
                    responseObserver.onError(throwable);
                },
                () -> {
                    log.debug("ReadCustomerRequest onCompleted");
                    responseObserver.onCompleted();
                }
        );

        log.debug("ReadCustomerRequest returns");
    }
}
