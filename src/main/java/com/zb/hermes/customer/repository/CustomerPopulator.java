package com.zb.hermes.customer.repository;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;

@Component
public class CustomerPopulator {

    @Bean
    ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {

        var initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
//        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ByteArrayResource((
//                "create extension if not exists \"uuid-ossp\";" +
//                        "select uuid_generate_v4();" +
//                        "create table if not exists customer (\n" +
//                        "    id uuid default uuid_generate_v4(),\n" +
//                        "    first_name varchar(20) NOT NULL,\n" +
//                        "    last_name varchar(20) NOT NULL,\n" +
//                        "    registered_on timestamp NOT NULL,\n" +
//                        "    primary key(id)\n" +
//                        ");")
//                .getBytes())));
        initializer.setDatabasePopulator(
                new ResourceDatabasePopulator(
                new ClassPathResource("db/migration/V1_InitTables.sql")));

        return initializer;
    }

}
