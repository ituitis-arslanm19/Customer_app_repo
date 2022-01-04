package com.example.demo.service;

import com.example.demo.DAO.CustomerDAO;
import com.example.demo.DTO.CustomerDTO;
import com.example.demo.model.Customer;
import com.example.demo.model.Role;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Transactional
@Slf4j
public class CustomerService implements UserDetailsService {
    private final CustomerDAO customerDAO;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private  final ModelMapper modelMapper;

    //Güvenlik konfigürasyonu için gerekli olan kullanıcı adına göre kullanıcı detaylarını gönderen ekstra metodum.
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByname is working.");
        Customer user= customerRepository.findByUsername(username);
        Collection<SimpleGrantedAuthority> authorities=new ArrayList<>();

        if (user==null){
            throw new UsernameNotFoundException("Customer not found");


        }
        user.getRoles().forEach(role -> {authorities.add(new SimpleGrantedAuthority(role.getName()));});
        return new User(user.getUsername(),user.getPassword(),authorities);
    }

    public CustomerService(CustomerDAO customerDAO, CustomerRepository customerRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.customerDAO = customerDAO;
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    //Optional<Customer>'ı CustomerDTO'ya model mapper ile dönüştüremediğim için burada dönüştürme işlemini değerler üzerinden kendim yaptım.
    public CustomerDTO getCustomerById(int Id){
        log.debug("getCustomerById is working.");
        Optional<Customer> customer=customerDAO.getCustomerbyId(Id);
        ArrayList<Role> roles= new ArrayList<>();

        for (Role role:customer.get().getRoles()) {
            roles.add(role);
        }
        CustomerDTO customerDTO=new CustomerDTO(customer.get().getId(),customer.get().getName(),customer.get().getUsername(),customer.get().getPassword(),roles);

            return customerDTO;





    }
    public List<CustomerDTO> getCustomers(){
        log.debug("getCustomers is working.");

        return  customerDAO.getCustomers().stream().map(customer -> modelMapper.map(customer,CustomerDTO.class)).collect(Collectors.toList());
    }
    public boolean addCustomer(CustomerDTO customerDTO){
        log.debug("addCustomer is working.");
        boolean successful=true;
        Customer customer=modelMapper.map(customerDTO,Customer.class);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        try{
            customerDAO.addCustomer(customer);
        }

        catch(Exception e){
            successful=false;
            log.error("Error: {}",e.getMessage());
        }


        return successful;
    }
    public boolean deleteCustomerById(int Id){
        log.debug("deleteCustomerById is working.");
        boolean successful=true;
        try{
            customerDAO.deleteById(Id);
        }
        catch(Exception e){
            successful=false;
            log.error("Error: {}",e.getMessage());
        }


        return successful;
    }
    //Rolü müşteriye eklemek için kullandığım ekstra metod.
    public void addRoleToCustomer(String username,String rolename){
        log.debug("addRoleToCustomer is working.");
        customerDAO.addRoleToCustomer(username,rolename);


    }
    //Rolleri database'ime eklemek için kullandığım ekstra metod.
    public void addRole(Role role){
        log.debug("addRole is working.");
        customerDAO.addRole(role);

    }

}
