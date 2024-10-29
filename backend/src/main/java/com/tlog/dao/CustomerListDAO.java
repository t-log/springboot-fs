package com.tlog.dao;
import com.tlog.model.Customer;
import com.tlog.model.CustomerRegistrationRequest;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("list")
public class CustomerListDAO implements CustomerDAO{

    static List<Customer> customers;

    static{
        customers  = new ArrayList<>();
        Customer alex = new Customer(1,"Alex","alex@gmail.com",21);
        customers.add(alex);
        Customer jamila = new Customer(2, "Jamila", "jamila@gmail.com", 27);
        customers.add(jamila);
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customers.stream().
                filter(c -> c.getId().equals(id)).findFirst() ;
    }

    @Override
    public void createCustomer(Customer customer) {
        customers.add(customer);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        return customers.stream().anyMatch(c -> c.getEmail().equals(email));
    }

    @Override
    public boolean existsCustomerWithId(Integer id) {
        return customers.stream().anyMatch((c -> c.getId().equals(id)));
    }

    @Override
    public void deleteCustomerById(Integer id) {
        customers.stream().filter(c -> c.getId().equals(id)).findFirst().ifPresent(customers::remove); //method reference
    }

    @Override
    public void updateCustomerById(Customer updatedCustomer) {
        customers.add(updatedCustomer);
    }


}
