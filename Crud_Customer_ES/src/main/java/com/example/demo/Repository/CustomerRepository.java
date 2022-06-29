package com.example.demo.Repository;

import com.example.demo.document.Customer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Service;


@Service
public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {


}
