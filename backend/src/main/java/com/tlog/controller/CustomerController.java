package com.tlog.controller;

import com.tlog.model.Customer;
import com.tlog.model.CustomerRegistrationRequest;
import com.tlog.model.CustomerUpdateRequest;
import com.tlog.service.CustomerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("api/v1/customers") // this is now specified as root so further mappings need not include this
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController (CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @GetMapping("{customerId}") //path variable
    public Customer getCustomer(@PathVariable("customerId") Integer customerId){
        return customerService.getCustomer(customerId);
    }

    @PostMapping
    public void registerCustomer(@RequestBody CustomerRegistrationRequest customerRegistrationRequest){
        customerService.addCustomer(customerRegistrationRequest);
    }

    @DeleteMapping("{customerId}")
    public void deleteCustomerById(@PathVariable("customerId") Integer customerId){
        customerService.deleteCustomer(customerId);
    }

    @PutMapping("{customerId}")
    public void updateCustomerById(@PathVariable("customerId") Integer customerId,
                                   @RequestBody CustomerUpdateRequest customerUpdateRequest){
        customerService.updateCustomer(customerId, customerUpdateRequest);
    }
}
