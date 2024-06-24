package com.tlog.service;

import com.tlog.exception.DuplicateResourceException;
import com.tlog.exception.RequestValidationException;
import com.tlog.exception.ResourceNotFoundException;
import com.tlog.model.Customer;
import com.tlog.dao.CustomerDAO;
import com.tlog.model.CustomerRegistrationRequest;
import com.tlog.model.CustomerUpdateRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDAO customerDAO;

    public CustomerService(@Qualifier("jpa") CustomerDAO customerDAO){
        this.customerDAO = customerDAO;
    }

    public List<Customer> getAllCustomers(){
        return customerDAO.selectAllCustomers();
    }

    public Customer getCustomer(Integer id){
        return customerDAO.selectCustomerById(id).orElseThrow(
                () -> new ResourceNotFoundException("customer with id [%s] not found".formatted(id)));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest){
        /*Business Logic
        * Check if email exists , if not add the customer
        * */
        if(customerDAO.existsCustomerWithEmail(customerRegistrationRequest.email())){
            throw new DuplicateResourceException("email already exists");
        }
        //else add the new customer
        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age()
        );
        customerDAO.createCustomer(customer);
    }

    public void deleteCustomer(Integer id){
        /*Only if id exists else throw error */
        if(!customerDAO.existsCustomerWithId(id)){
            throw new ResourceNotFoundException("customer with id [%s] not found".formatted(id));
        }
        customerDAO.deleteCustomerById(id);
    }

    public void updateCustomer(Integer customerId, CustomerUpdateRequest customerUpdateRequest){
        boolean changed = false;
        if(!customerDAO.existsCustomerWithId(customerId)){
            throw new ResourceNotFoundException("customer with id [%s] not found".formatted(customerId));
        }
        Customer existingCustomerData = getCustomer(customerId);
        if(!existingCustomerData.getName().equals(customerUpdateRequest.name())
                && customerUpdateRequest.name()!= null){
            changed = true;
            existingCustomerData.setName(customerUpdateRequest.name());
        }
        if(!existingCustomerData.getAge().equals(customerUpdateRequest.age()) && customerUpdateRequest.age()!= null){
            changed = true;
            existingCustomerData.setAge(customerUpdateRequest.age());
        }
        if(!existingCustomerData.getEmail().equals(customerUpdateRequest.email())
                && customerUpdateRequest.email()!= null){
            changed = true;
            existingCustomerData.setEmail(customerUpdateRequest.email());
        }
        if(changed == true){
            customerDAO.updateCustomerById(existingCustomerData);
        }
        else{
            throw new RequestValidationException("no changes");
        }
    }
}
