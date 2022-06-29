package com.example.demo.document;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName="customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type=FieldType.Text)
    private String firstName;

    @Field(type = FieldType.Text)
    private String lastName;


    @Field(type =FieldType.Long)
    private Long age;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }



}