package com.tlog.repository;

import com.tlog.AbstractTestContainers;
import com.tlog.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestContainers {

    @Autowired
    private CustomerRepository repoUnderTest;

    @Autowired
    private ApplicationContext context;


    @BeforeEach
    void setUp() {
        System.out.println(context.getBeanDefinitionCount());
    }

    @Test
    void existsCustomerByEmail() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().fullName(),
                email,
                20);
        repoUnderTest.save(customer);
        int id = repoUnderTest.findAll().
                stream().
                filter(c->c.getEmail().equals(email))
                .map(Customer::getId).findFirst().orElseThrow();
        var actual = repoUnderTest.existsCustomerById(id);
        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerById() {
    }
}