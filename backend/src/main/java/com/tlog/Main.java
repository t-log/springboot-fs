package com.tlog;

import com.github.javafaker.Faker;
import com.tlog.model.Customer;
import com.tlog.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import java.util.Random;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Main.class, args);
//        printBeans(run);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
        return args ->{
            Faker faker = new Faker();
            Random randomAge = new Random();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            customerRepository.save(new Customer(firstName+" "+lastName,
                    firstName.toLowerCase()+"."+lastName.toLowerCase()+"@tlog.com",
                    randomAge.nextInt(16,99)));
        };
    }


    public static void printBeans(ConfigurableApplicationContext ctx){
        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
    }
}
