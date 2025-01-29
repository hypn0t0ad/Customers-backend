package com.example.customers_backend.controller;

import com.example.customers_backend.entity.Customer;
import com.example.customers_backend.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping
    public Page<Customer> getCustomers(@PageableDefault(size = 5) Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerRepository.save(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("{/id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable long id) {
        return ResponseEntity.ok(customerRepository.findById(id)
                .orElseThrow(RuntimeException::new));
    }

    @PostMapping("{/id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable long id,
                                                @RequestBody Customer customer) {
        Customer customerToModify = customerRepository.getReferenceById(id);
        customerToModify.setName(customer.getName());
        customerToModify.setEmail(customer.getEmail());
        customerToModify.setAddress(customer.getAddress());
        customerToModify.setPhonenumber(customer.getPhonenumber());
        customerRepository.save(customerToModify);
        return ResponseEntity.ok(customerToModify);
    }
}