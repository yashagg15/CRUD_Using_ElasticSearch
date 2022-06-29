package com.example.demo.Service;

import com.example.demo.Repository.CustomerRepository;
import com.example.demo.document.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CustomerService {

    Logger logger= LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RestHighLevelClient client;
    @Autowired
    ObjectMapper objectMapper;
    private List<QueryBuilder> must;


    public void save(Customer customer){
        try {
            Customer customer1 = customerRepository.save(customer);
        }
        catch (Exception e){
            logger.error(e.getMessage());
        }
    }
    public Customer findById(String id){
        return customerRepository.findById(id).orElse(null);
    }

    public List<String> getAllIndices() throws IOException {

        GetIndexRequest request = new GetIndexRequest("*");
        GetIndexResponse response = client.indices().get(request, RequestOptions.DEFAULT);
        List<String> allIndices=Arrays.asList(response.getIndices());
        return allIndices;
    }

    public List<Customer> getCustomerWithName(String cusname) throws IOException {

        SearchSourceBuilder sb=new SearchSourceBuilder();
        BoolQueryBuilder bqb=QueryBuilders.boolQuery();
//        bqb.must(QueryBuilders.queryStringQuery("*"+cusname+"*").analyzeWildcard(true).field("firstName"));


        bqb.should(QueryBuilders.termQuery
                ("lastName","agarwal"));
        System.out.println(bqb);
        SearchRequest sr=new SearchRequest();
        sr.indices("customer");
        sr.source(sb);

        SearchResponse searchResponse=client.search(sr,RequestOptions.DEFAULT);
        SearchHit[] searchHits=searchResponse.getHits().getHits();
        List<Customer> customer=new ArrayList<>();
        if(searchHits.length>0){
            Arrays.stream(searchHits).
                    forEach(e->customer.add(objectMapper.convertValue(e.getSourceAsMap(),Customer.class)));
        }
        return customer;

    }

    public List<Customer> searchAllDocuments(){

        SearchSourceBuilder sb=new SearchSourceBuilder();
        sb.query(QueryBuilders.matchAllQuery());
        SearchRequest request=new SearchRequest().indices("customer").source(sb);
        SearchResponse response=null;
        try{
            response=client.search(request,RequestOptions.DEFAULT);
        }
        catch(Exception e){
            logger.error(e.getMessage());
        }
        SearchHit[] hits=response.getHits().getHits();
        List<Customer> customers=new ArrayList<>();
        if(hits.length>0)
            Arrays.stream(hits).forEach(hit->
                    customers.add(objectMapper.convertValue(hit.getSourceAsMap(),Customer.class)));

        return customers;

    }

    public Customer createCustomerUsingClient(Customer customer) {
        IndexRequest indexRequest=new IndexRequest("customer");
        indexRequest.id(customer.getId());
        indexRequest.source(customer);
        try{
            IndexResponse indexResponse=client.index(indexRequest,RequestOptions.DEFAULT);
            if(indexResponse.status()== RestStatus.ACCEPTED){
                return customer;
            }

        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return null;

    }

}
