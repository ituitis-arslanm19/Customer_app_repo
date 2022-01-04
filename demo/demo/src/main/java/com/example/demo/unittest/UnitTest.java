package com.example.demo.unittest;

import com.example.demo.DAO.CustomerDAO;
import com.example.demo.DTO.CustomerDTO;
import com.example.demo.controller.CustomerController;
import com.example.demo.model.Customer;
import com.example.demo.model.CustomerRequest;
import com.example.demo.model.ReturnModel;
import com.example.demo.model.Role;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;

import org.junit.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UnitTest {

    private  CustomerService customerService;
    private  CustomerDAO customerDAO;
    private  CustomerRepository customerRepository;
    private  RoleRepository roleRepository;
    private  PasswordEncoder passwordEncoder;
    private  ModelMapper modelMapper;


    @Before
    public void setUp() throws  Exception{
        customerRepository=Mockito.mock(CustomerRepository.class);
        customerDAO=Mockito.mock(CustomerDAO.class);
        roleRepository=Mockito.mock(RoleRepository.class);
        passwordEncoder=Mockito.mock(PasswordEncoder.class);
        modelMapper=Mockito.mock(ModelMapper.class);


        customerService= new CustomerService( customerDAO, customerRepository, roleRepository,  passwordEncoder,  modelMapper);


    }
        //Örnek olması için bir adet unit test kodladım.
    @Test public void whenGettingCustomerByValidRequest_itShouldReturnValidCustomerDTO(){
                ArrayList<Role> roles=new ArrayList<>();
                roles.add(new Role(1L,"ROLE_USER"));
                Optional<Customer> customer= Optional.of(new Customer(4, "ahmet", "ahmet11", "ahmet111", roles));
                CustomerDTO customerDTO=new CustomerDTO(4,"ahmet","ahmet11","ahmet111",roles);
                Mockito.when(customerDAO.getCustomerbyId(4)).thenReturn(customer);
                CustomerDTO result=customerService.getCustomerById(4);
                Assert.assertEquals(customerDTO,result);
                Mockito.verify(customerDAO).getCustomerbyId(4);
    }




}
