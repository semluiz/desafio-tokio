package com.example.api.web.rest;

import java.util.ArrayList;

import com.example.api.model.CustomerAddress;
import com.example.api.repository.CustomerRepository;
//import com.example.api.service.AddressService;
import com.example.api.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.example.api.model.Customer;
import com.example.api.service.CustomerService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService service;

    private AddressService addressService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerService service) {
        this.service = service;
    }

    //Listando todos com paginação
    @GetMapping
    public Page<Customer> findAll(
            @PageableDefault(sort = "name", direction = Sort.Direction.ASC, page = 0, size = 10) Pageable pageable) {
        return service.findAll(pageable);
    }

    //procurando por id especifico
    @GetMapping("/{id}")
    public Customer findById(@PathVariable Long id) {
        return service.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer with id=" + id + " not found"));
    }


    //Salvando os Dados
    @PostMapping("/{id}/cep/{cep}")
    public ResponseEntity<Customer> insertEndereco(@PathVariable("id") Long id, @PathVariable("cep") String cep) {
        try {
            Customer customer = service.findById(id).get();
            if (customer == null) {
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found");
            }
            CustomerAddress address = addressService.buscaEnderecoPorCEP(cep);
            address.setCustomer(customer);
            address = addressService.salvar(address);
            if (customer.getEnderecos() == null) {
                customer.setEnderecos(new ArrayList<>());
            }
            customer.getEnderecos().add(address);
            customer = service.save(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(customer);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }

    //Atualizando os dados
    @PutMapping("/atualiza")
    public ResponseEntity<?> update(@RequestBody Customer customer) {
        try {
            service.update(customer);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

    }


    //removendo o customer
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remover(@RequestBody Customer customer) {
        try {
            service.delete(customer);

            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
