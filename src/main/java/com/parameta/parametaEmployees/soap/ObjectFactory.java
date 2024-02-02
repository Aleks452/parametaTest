//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2024.02.01 at 10:15:37 PM COT 
//


package com.parameta.parametaEmployees.soap;

import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.parameta.parametaEmployees.soap package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.parameta.parametaEmployees.soap
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetEmployeeByDocumentNumberRequest }
     * 
     */
    public GetEmployeeByDocumentNumberRequest createGetEmployeeByDocumentNumberRequest() {
        return new GetEmployeeByDocumentNumberRequest();
    }

    /**
     * Create an instance of {@link GetEmployeeByDocumentNumberResponse }
     * 
     */
    public GetEmployeeByDocumentNumberResponse createGetEmployeeByDocumentNumberResponse() {
        return new GetEmployeeByDocumentNumberResponse();
    }

    /**
     * Create an instance of {@link Employee }
     * 
     */
    public Employee createEmployee() {
        return new Employee();
    }

    /**
     * Create an instance of {@link PostEmployeeRequest }
     * 
     */
    public PostEmployeeRequest createPostEmployeeRequest() {
        return new PostEmployeeRequest();
    }

    /**
     * Create an instance of {@link PostEmployeeResponse }
     * 
     */
    public PostEmployeeResponse createPostEmployeeResponse() {
        return new PostEmployeeResponse();
    }

}