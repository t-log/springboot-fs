package com.tlog;

import com.tlog.model.Customer;
import com.tlog.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Main.class, args);
//        printBeans(run);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
        return args ->{
            Customer alex = new Customer(1,"Alex","alex@gmail.com",21);
            Customer jamila = new Customer(2, "Jamila", "jamila@gmail.com", 27);
            List<Customer> customers = List.of(alex, jamila);
            customerRepository.saveAll(customers);
        };
    }


    public static void printBeans(ConfigurableApplicationContext ctx){
        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
