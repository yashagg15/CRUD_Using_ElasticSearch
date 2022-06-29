package com.example.demo.Controller;

import com.example.demo.DTOs.ResponseDTO;
import com.example.demo.Service.CustomerService;
import com.example.demo.document.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    Logger logger= LoggerFactory.getLogger(CustomerController.class);

    @RequestMapping(value = "/api/customer",method = RequestMethod.POST)
    public ResponseEntity<ResponseDTO> saveCustomer(@RequestBody Customer customer){
        ResponseDTO responseDTO=new ResponseDTO();
        try {
            logger.info("Saving Customer Details");
            customerService.save(customer);
            logger.info("Customer details saved");
            responseDTO.setMessage("Customer details successfully saved");
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            responseDTO.setMessage(e.getMessage());
            return new ResponseEntity<>(responseDTO,HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }


    @RequestMapping(value = "/api/id/{cusId}",method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getCustomerWithId(@PathVariable String cusId){

        ResponseDTO responseDTO=new ResponseDTO();
        try {
            logger.info("Fetching Customer Detail");
            Customer customer=customerService.findById(cusId);
            logger.info("Customer details fetched successfully");
            responseDTO.setMessage("Customer details fetched successfully");
            responseDTO.setObject(customer);
        }
        catch (Exception e) {
            logger.error(e.getMessage());
            responseDTO.setMessage(e.getMessage());
            return new ResponseEntity<>(responseDTO,HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @RequestMapping(value ="/api/name/{cusname}",method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getCustomerName(@PathVariable String cusname){
        List<Customer> customer=null;
        ResponseDTO responseDTO=new ResponseDTO();
        try{
            logger.info("Fetching all Customer Detail with given name");
            customer=customerService.getCustomerWithName(cusname);
            logger.info("All customer details fetched successfully");
            responseDTO.setMessage("all customer details fetched successfully");
            responseDTO.setList(customer);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            responseDTO.setMessage(e.getMessage());
            return new ResponseEntity<>(responseDTO,HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @RequestMapping(value="/api/indices",method = RequestMethod.GET)
    public ResponseEntity<ResponseDTO> getAllIndices()  {
        ResponseDTO responseDTO=new ResponseDTO();
        try {
            logger.info("Fetching all indices/tables");
            responseDTO.setList(customerService.getAllIndices());
            responseDTO.setMessage("All indices fetched successfully");
            logger.info("Fetched indices");
        }
        catch (Exception e){
            logger.error(e.getMessage());
            responseDTO.setMessage(e.getMessage());
            return new ResponseEntity<>(responseDTO,HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @RequestMapping(value = "/api/searchAllCustomers",method =RequestMethod.GET)
    public  ResponseEntity<ResponseDTO> getAllDocsInAnIndex(){
        ResponseDTO responseDTO=new ResponseDTO();
        try {
            logger.info("Fetching all docs inside an index");
            responseDTO.setList(customerService.searchAllDocuments());
            responseDTO.setMessage("All docs fetched successfully");
            logger.info("Fetched documents");
        }
        catch (Exception e){
            logger.error(e.getMessage());
            responseDTO.setMessage(e.getMessage());
            return new ResponseEntity<>(responseDTO,HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }


    @RequestMapping(value = "/api/createCustomer",method =RequestMethod.POST)
    public ResponseEntity<ResponseDTO> createCustomerUsingRestClient(@RequestBody Customer customer){

        ResponseDTO responseDTO=new ResponseDTO();
        try {
            logger.info("Saving customer");
            customerService.createCustomerUsingClient(customer);
            responseDTO.setMessage("Customer with id "+customer.getId()+" has been saved succesfully");
            logger.info("Customer details saved");
        }
        catch (Exception e){
            logger.error(e.getMessage());
            responseDTO.setMessage(e.getMessage());
            return new ResponseEntity<>(responseDTO,HttpStatus.FORBIDDEN);
        }
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }


}
