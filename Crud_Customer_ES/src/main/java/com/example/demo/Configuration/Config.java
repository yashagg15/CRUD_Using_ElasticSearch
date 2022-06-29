package com.example.demo.Configuration;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
public class Config {


    @Value("${spring.elasticsearch.url}")
    private String host;

    @Value("${spring.elasticsearch.rest.username}")
    private String username;

    @Value("${spring.elasticsearch.rest.password}")
    private String password;


    @Bean
    public RestHighLevelClient client() {

        ClientConfiguration clientConfiguration=ClientConfiguration.builder()
                .connectedTo("localhost:9200").withBasicAuth(username,password).build();

        return RestClients.create(clientConfiguration).rest();

/*        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "NEU=kTpOpTooy+fFyT_I"));
        RestHighLevelClient client=setCredentials(credentialsProvider);*/

    }

/*    public static RestHighLevelClient setCredentials(CredentialsProvider credentialsProvider){
        RestHighLevelClient client = new
                RestHighLevelClient(RestClient.builder(new HttpHost("localhost", 9200)).
                setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                        return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                }));
        return client;
    }*/


    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(client());
    }
}