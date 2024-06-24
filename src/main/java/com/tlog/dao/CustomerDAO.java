package com.tlog.dao;

import com.tlog.model.Customer;
import com.tlog.model.CustomerRegistrationRequest;
import com.tlog.model.CustomerUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface CustomerDAO {
    List<Customer> selectAllCustomers();

    Optional<Customer> selectCustomerById(Integer id);

    void createCustomer(Customer customer);

    boolean existsCustomerWithEmail(String email);

    boolean existsCustomerWithId(Integer id);

    void deleteCustomerById(Integer id);

    void updateCustomerById(Customer updatedCustomer);
}
