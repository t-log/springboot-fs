package com.tlog.service;

import com.tlog.dao.CustomerDAO;
import com.tlog.mapper.CustomerRowMapper;
import com.tlog.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("jdbc")
public class CustomerJDBCDataAccessService implements CustomerDAO {
    private JdbcTemplate jdbcTemplate;
    private CustomerRowMapper customerRowMapper;

    public CustomerJDBCDataAccessService(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = customerRowMapper;
    }

    @Override
    public List<Customer> selectAllCustomers() {
        var sql = """
                SELECT id, name, age, email
                FROM customer
                """;
        return jdbcTemplate.query(sql, customerRowMapper);
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        var sql = """
                SELECT id, name, age, email
                FROM customer where  id = ?
                                """;

        return jdbcTemplate.
                query(sql, customerRowMapper, id).
                stream().
                findFirst();
    }

    @Override
    public void createCustomer(Customer customer) {
        var sql = """
                INSERT INTO customer(name, email, age) 
                VALUES(?, ? ,?) 
                """;
        int result = jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge());
        System.out.println("jdbc.Template update "+ result);
    }

    @Override
    public boolean existsCustomerWithEmail(String email) {
        var sql = """
                SELECT count(id) FROM customer
                WHERE email = ?
                """;
        Integer result = jdbcTemplate.queryForObject(sql,Integer.class,email);
        return result != null && result>0;
    }

    @Override
    public boolean existsCustomerWithId(Integer id) {
        var sql = """
                SELECT count(id) FROM customer
                WHERE id = ?
                """;
        Integer result = jdbcTemplate.queryForObject(sql,Integer.class,id);
        return result != null && result>0;

    }

    @Override
    public void deleteCustomerById(Integer id) {
        var sql = """
                DELETE FROM customer
                WHERE id = ?
                """;
        int result = jdbcTemplate.update(sql,id);
    }

    @Override
    public void updateCustomerById(Customer updatedCustomer) {
        if(updatedCustomer.getName()!=null){
            var sql = """
                UPDATE customer
                SET name = ? WHERE id = ?
                """;
            int result = jdbcTemplate.update(sql,updatedCustomer.getName(),updatedCustomer.getId());
        }
        if(updatedCustomer.getAge()!=null){
            var sql = """
                UPDATE customer
                SET age = ? WHERE id = ?
                """;
            int result = jdbcTemplate.update(sql,updatedCustomer.getAge(),updatedCustomer.getId());
        }
        if(updatedCustomer.getEmail()!=null){
            var sql = """
                UPDATE customer
                SET email = ? WHERE id = ?
                """;
            int result = jdbcTemplate.update(sql,updatedCustomer.getEmail(),updatedCustomer.getId());
        }


    }
}
