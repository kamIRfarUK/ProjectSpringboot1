package com.practice.Project1.customer;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {


    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public List<Customer> getCustomer(){

        return customerRepository.findAll();
    }

    public void createCustomer( CreateCustomerRequest createCustomerRequest){
        Optional<Customer> customerByEmail = customerRepository.findByEmail(createCustomerRequest.email());
        if(customerByEmail.isPresent()){
            throw new RuntimeException(" The Email "+ createCustomerRequest.email()+" unavailable ");
        }
        Customer customer = Customer.create(createCustomerRequest.name(),
                createCustomerRequest.email(),
                createCustomerRequest.address());

        customerRepository.save(customer);
    }

    public void updateCustomer(Long id, String name, String email, String address) {
         Customer customer = customerRepository.findById(id).orElseThrow(()->
                new RuntimeException("Customer with id "+ id+ " doesnot found.")
         );


        if(Objects.nonNull(name) && !Objects.equals(customer.getName(), name)){
            customer.setName(name);
        }

        if(Objects.nonNull(email) && !Objects.equals(customer.getEmail(), email)){

            Optional<Customer> customerByEmail = customerRepository.findByEmail(email);
            if(customerByEmail.isPresent()){
                throw new RuntimeException(" The Email "+ email +" unavailable ");
            }
            customer.setEmail(email);
        }

        if(Objects.nonNull(address) && !Objects.equals(customer.getAddress(),address)){
            customer.setAddress(address);
        }

        customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        boolean isExists = customerRepository.existsById(id);
        if (!isExists){
            throw new RuntimeException(String.format("Customer with id %s doesnot Exists.",id));

        }
        customerRepository.deleteById(id);
    }
}
