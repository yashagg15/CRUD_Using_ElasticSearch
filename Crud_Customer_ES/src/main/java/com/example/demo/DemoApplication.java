package com.example.demo;

import com.example.demo.Repository.CustomerRepository;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Autowired
	CustomerRepository cusRepository;
	
//	@SuppressWarnings("deprecation")
//	@Bean
//	public boolean createTestIndex(RestHighLevelClient restHighLevelClient) throws Exception {
//		try {
//			DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("hello-world");
//			restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT); // 1
//		} catch (Exception ignored) {
//		}
//
//		CreateIndexRequest createIndexRequest = new CreateIndexRequest("hello-world");
//		createIndexRequest.settings(
//				Settings.builder().put("index.number_of_shards", 1)
//						.put("index.number_of_replicas", 0));
//		restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT); // 2
//
//		return true;
//	}

}
