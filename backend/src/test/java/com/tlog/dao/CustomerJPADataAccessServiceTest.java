package com.tlog.dao;

import com.tlog.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class CustomerJPADataAccessServiceTest {

    private CustomerJPADataAccessService underTest;
    private AutoCloseable autoCloseable;
    @Mock
    private CustomerRepository repository;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerJPADataAccessService(repository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void selectAllCustomers() {
        underTest.selectAllCustomers();
        verify(repository).findAll();
    }

    @Test
    void selectCustomerById() {
        int id = 1;
        underTest.selectCustomerById(id);
        verify(repository).findById(id);
    }

    @Test
    void createCustomer() {
    }

    @Test
    void existsCustomerWithEmail() {
    }

    @Test
    void existsCustomerWithId() {
    }

    @Test
    void deleteCustomerById() {
    }

    @Test
    void updateCustomerById() {
    }
}