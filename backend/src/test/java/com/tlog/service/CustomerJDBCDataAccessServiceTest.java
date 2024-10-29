package com.tlog.service;

import com.tlog.AbstractTestContainers;
import com.tlog.mapper.CustomerRowMapper;
import com.tlog.model.Customer;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerJDBCDataAccessServiceTest extends AbstractTestContainers {

    private CustomerJDBCDataAccessService serviceUnderTest;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    @BeforeEach
    void setUp() {
        serviceUnderTest = new CustomerJDBCDataAccessService(
                getJdbcTemplate(),
                customerRowMapper
        );
    }

    @Test
    void getAllCustomers() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().fullName(),
                email,
                20);
        serviceUnderTest.createCustomer(customer);
        //When
        List<Customer> customers = serviceUnderTest.selectAllCustomers();
        //Then
        assertThat(customers).isNotEmpty();
    }

    @Test
    void getCustomerById() {
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().fullName(),
                email,
                20);
        serviceUnderTest.createCustomer(customer);
        int id = serviceUnderTest.selectAllCustomers().
                stream().
                filter(c->c.getEmail().equals(email))
                .map(Customer::getId).findFirst().orElseThrow();
        Optional<Customer> actual = serviceUnderTest.selectCustomerById(id);
        assertThat(actual).isPresent().hasValueSatisfying(c->{
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(c.getName());
        });
    }

    @Test
    void willReturnEmptyWhenSelectCustomerById(){
      //Given
      int id = -1;
      //When
      Optional<Customer> actual = serviceUnderTest.selectCustomerById(id);
      //Then
      assertThat(actual).isEmpty();

    }

    @Test
    void deleteCustomerTest() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        Customer customer = new Customer(FAKER.name().fullName(),
                email,
                20);
        serviceUnderTest.createCustomer(customer);
        int id = serviceUnderTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(c -> c.getId())
                .findFirst()
                .orElseThrow();
        boolean beforeDeletion = serviceUnderTest.selectCustomerById(id).isEmpty();
        assertThat(beforeDeletion).isFalse();
        //When
        serviceUnderTest.deleteCustomerById(id);
        //Then
        boolean actual = serviceUnderTest.selectCustomerById(id).isEmpty();
        assertThat(actual).isTrue();
    }

    @Test
    void updateCustomerTest() {
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(name,
                email,
                20);
        serviceUnderTest.createCustomer(customer);
        int id = serviceUnderTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(c -> c.getId())
                .findFirst()
                .orElseThrow();
        //When
        Customer customerUpdated = new Customer(
                id,
                name,
                email,
                25);
        serviceUnderTest.updateCustomerById(customerUpdated);
        //Then
        assertThat(serviceUnderTest.selectCustomerById(id)).isPresent()
        .hasValueSatisfying(c ->{
            assertThat(c.getAge()).isEqualTo(25);
            assertThat(c.getName()).isEqualTo(name);
        } );
    }

    @Test
    void existsCustomerWithEmailTest(){
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(name,
                email,
                20);
        serviceUnderTest.createCustomer(customer);
        //When
        boolean actual = serviceUnderTest.existsCustomerWithEmail(email);
        //Then
        assertThat(actual).isTrue();
    }

    @Test
    void existsCustomerWithIdTest(){
        //Given
        String email = FAKER.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = FAKER.name().fullName();
        Customer customer = new Customer(name,
                email,
                20);
        serviceUnderTest.createCustomer(customer);
        int id = serviceUnderTest.selectAllCustomers()
                .stream()
                .filter(c -> c.getEmail().equals(email))
                .map(c -> c.getId())
                .findFirst()
                .orElseThrow();
        //When
        boolean actual = serviceUnderTest.existsCustomerWithId(id);
        //Then
        assertThat(actual).isTrue();
    }
    
}