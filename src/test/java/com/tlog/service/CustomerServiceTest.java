package com.tlog.service;

import com.tlog.dao.CustomerDAO;
import com.tlog.exception.DuplicateResourceException;
import com.tlog.exception.ResourceNotFoundException;
import com.tlog.model.Customer;
import com.tlog.model.CustomerRegistrationRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
    @Mock
    private CustomerDAO customerDAO;
    private CustomerService underTest;

    @BeforeEach
    void setUp() {
        underTest = new CustomerService(customerDAO);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllCustomers() {
        //Given
        //When
        underTest.getAllCustomers();
        //Then
        verify(customerDAO).selectAllCustomers();
    }

    @Test
    void getCustomer() {
        int id = 2;
        Customer customer = new Customer(id,"tlog","tlog@t.com",25);
        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.of(customer));
        Customer actual = underTest.getCustomer(id);
        assertThat(actual).isEqualTo(customer);
    }
    @Test
    void willThrowWhenGetCustomerReturnEmptyOptional(){
        int id = 10;
        when(customerDAO.selectCustomerById(id)).thenReturn(Optional.empty());
        assertThatThrownBy(()->underTest.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [%s] not found".formatted(id));
    }

    @Test
    void addCustomer() {
        //Given
        String email = "gman@shock.com";
        when(customerDAO.existsCustomerWithEmail(email)).thenReturn(false);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest("Alex",email,25);
        //When
        underTest.addCustomer(request);
        //Then
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerDAO).createCustomer(customerArgumentCaptor.capture());
        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(capturedCustomer.getId()).isNull();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getAge()).isEqualTo(request.age());
        assertThat(capturedCustomer.getEmail()).isEqualTo(request.email());

    }
    @Test
    void shouldNotAddCustomerWhenEmailAlreadyExist() {
        //Given
        String email = "gman@shock.com";
        when(customerDAO.existsCustomerWithEmail(email)).thenReturn(true);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest("Alex",email,25);
        //Then
        assertThatThrownBy(()->underTest.addCustomer(request)).isInstanceOf(DuplicateResourceException.class)
                .hasMessage("email already exists");
        verify(customerDAO,never()).createCustomer(any());
    }

    @Test
    void deleteCustomer() {
        //Given
        int id = 1;
        //When
        when(customerDAO.existsCustomerWithId(id)).thenReturn(true);
        underTest.deleteCustomer(id);
        //Then
        verify(customerDAO).deleteCustomerById(id);
    }

    @Test
    void updateCustomer() {
    }
}