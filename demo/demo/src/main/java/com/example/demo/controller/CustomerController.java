package com.example.demo.controller;

import com.example.demo.DTO.CustomerDTO;
import com.example.demo.model.CustomerRequest;
import com.example.demo.model.ReturnModel;
import com.example.demo.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")

public class CustomerController {
   private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<ReturnModel> getAllCustomers(){
        log.debug("getAllCustomers is working");
        String message="Ok";
        List<CustomerDTO>customers = null;
        try {
            customers=  customerService.getCustomers();
        }
        catch(Exception e){
            log.error("Error: {}",e.getMessage());
            message=e.getMessage();

        }

        ReturnModel returnModel= ReturnModel.builder()
                .code("202")
                .message(message)
                .result(customers)
                .successful(true)
                .build();
            ;
        return ResponseEntity.ok(returnModel);

}
    @PostMapping("/getCustomerById")
    public ResponseEntity<ReturnModel> getCustomerById(@RequestBody CustomerRequest customerRequest){
        log.debug("getCustomer is working");
        boolean successful=true;
        String message="Müşteri bulundu.";
        CustomerDTO customerDTO=null ;
        try{customerDTO= customerService.getCustomerById(customerRequest.getId());
            }
        catch(Exception e){
            log.error("Error: {}",e.getMessage());
            successful=false;
            message=e.getMessage();
            }

        ReturnModel returnModel= ReturnModel.builder()
                .code("202")
                .message(message)
                .result(customerDTO)
                .successful(successful)
                .build();

        return ResponseEntity.ok(returnModel);

    }
    @PostMapping(value="/addCustomer")

    public ResponseEntity <ReturnModel> addCustomer(@RequestBody CustomerDTO customerDTO){

        log.debug("addCustomer is working");
        String message="Ok";
        boolean successful=true;
        try{
            successful= customerService.addCustomer(customerDTO);

        }

        catch(Exception e){
            log.error("Error: {}",e.getMessage());
            successful=false;
            message=e.getMessage();
        }

        ReturnModel returnModel= ReturnModel.builder()
                .code("202")
                .message(message)
                .result(successful)
                .successful(successful)
                .build();

        return ResponseEntity.ok(returnModel);

    }
    @PostMapping(value="/deleteCustomerById")
    public ResponseEntity <ReturnModel> deleteCustomerById(@RequestBody CustomerRequest customerRequest){
        log.debug("deleteById is working");
        String message="Ok";
        boolean successful=true;
        try{
            successful= customerService.deleteCustomerById(customerRequest.getId());
            }

        catch(Exception e){
            log.error("Error: {}",e.getMessage());
            successful=false;
            message=e.getMessage();
        }

        ReturnModel returnModel= ReturnModel.builder()
                .code("202")
                .message(message)
                .result(successful)
                .successful(successful)
                .build();

        return ResponseEntity.ok(returnModel);

    }




}
