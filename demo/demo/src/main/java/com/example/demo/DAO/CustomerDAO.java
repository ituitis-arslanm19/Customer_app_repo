package com.example.demo.DAO;

import com.example.demo.DTO.CustomerDTO;
import com.example.demo.model.Customer;
import com.example.demo.model.Role;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service @Transactional
public class CustomerDAO {

    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;

    public CustomerDAO( CustomerRepository customerRepository, RoleRepository roleRepository) {

        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
    }

    public Optional<Customer> getCustomerbyId(int Id){
        Optional<Customer> customer=customerRepository.findById(Id);

        return customer;

    }
    public List<Customer> getCustomers(){

        return customerRepository.findAll();

    }
    public void addCustomer(Customer customer){




        customerRepository.save(customer);;

    }
    public void deleteById(int Id){

        customerRepository.deleteById(Id);

    }
    public void addRoleToCustomer(String username,String rolename){
        Customer customer=customerRepository.findByUsername(username);
        Role role= roleRepository.findByName(rolename);
        customer.getRoles().add(role);


    }
    public void addRole(Role role){


        roleRepository.save(role);

    }

}
