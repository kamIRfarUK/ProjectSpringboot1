package com.practice.Project1.customer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(path ="/api/v1/customers")
public class CustomerController {


    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping
    public List<Customer> getCustomer(){

        return customerService.getCustomer();
    }


    @PostMapping
    public void createCustomerRequest(@RequestBody CreateCustomerRequest createCustomerRequest){
        log.info("Recieved create new customer {}", createCustomerRequest);
        customerService.createCustomer(createCustomerRequest);
    }


    @PutMapping(path = "/{id}")
    public void updateCustomer( @PathVariable("id") Long id,
                                @RequestParam(required = false) String name,
                                @RequestParam(required = false) String email,
                                @RequestParam(required = false) String address){
        log.info("Recieved update request of id:  {} with name:  {} , email:  {}, address:  {} ",id,name,email,address);
        customerService.updateCustomer(id,name,email,address);
    }


    @DeleteMapping(path = "/{id}")
    public void deleteCustomer(@PathVariable("id") Long id){
        log.info("Recieved delete customer with id :  {}", id);
        customerService.deleteCustomer(id);
    }
}
