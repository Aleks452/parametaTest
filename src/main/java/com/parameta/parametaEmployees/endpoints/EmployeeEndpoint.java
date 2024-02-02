package com.parameta.parametaEmployees.endpoints;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.parameta.parametaEmployees.converters.EmployeeConverter;
import com.parameta.parametaEmployees.entities.EmployeesEntity;
import com.parameta.parametaEmployees.repositories.EmployeesRepository;
import com.parameta.parametaEmployees.soap.Employee;
import com.parameta.parametaEmployees.soap.GetEmployeeByDocumentNumberRequest;
import com.parameta.parametaEmployees.soap.GetEmployeeByDocumentNumberResponse;
import com.parameta.parametaEmployees.soap.PostEmployeeRequest;
import com.parameta.parametaEmployees.soap.PostEmployeeResponse;
import com.parameta.parametaEmployees.utils.Constants;

@Endpoint
public class EmployeeEndpoint {

    
    @Autowired
    private EmployeesRepository employeesRepository;
    
    @Autowired
    private EmployeeConverter employeeConverter;

    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "getEmployeeByDocumentNumberRequest")
    @ResponsePayload
    public GetEmployeeByDocumentNumberResponse getEmployee(@RequestPayload GetEmployeeByDocumentNumberRequest request) {
        GetEmployeeByDocumentNumberResponse response = new GetEmployeeByDocumentNumberResponse();
        Optional<EmployeesEntity> employeesEntity = employeesRepository.findByDocumentNumber(request.getDocumentNumber());
        response.setEmployee(employeeConverter.convertEmployeeEntityToEmployee(employeesEntity.get())) ;
        return response;
    }
    
    
    @PayloadRoot(namespace = Constants.NAMESPACE_URI, localPart = "postEmployeeRequest")
    @ResponsePayload
    public PostEmployeeResponse postEmployees(@RequestPayload PostEmployeeRequest request) {
    	PostEmployeeResponse response = new PostEmployeeResponse();
        EmployeesEntity employeesEntity = employeeConverter.convertEmployeeToEmployeeEntity(request.getEmployee());
        Employee employee = employeeConverter.convertEmployeeEntityToEmployee(employeesRepository.save(employeesEntity));
        response.setEmployee(employee);
        return response;
    }
    
}
