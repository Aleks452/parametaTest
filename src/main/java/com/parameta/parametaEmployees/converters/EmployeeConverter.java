package com.parameta.parametaEmployees.converters;


import com.parameta.parametaEmployees.entities.EmployeesEntity;
import com.parameta.parametaEmployees.soap.Employee;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;


@Component
public class EmployeeConverter {

    public EmployeesEntity convertEmployeeToEmployeeEntity(Employee employee) {
        EmployeesEntity employeesEntity = new EmployeesEntity();
        employeesEntity.setDocumentNumber(employee.getDocumentNumber());
        employeesEntity.setFirstName(employee.getFirstName());
        employeesEntity.setLastName(employee.getLastName());
        employeesEntity.setDocumentType(employee.getDocumentType());
        employeesEntity.setBirthDate(employee.getBirthDate().toGregorianCalendar().getTime());
        employeesEntity.setEmploymentDate(employee.getEmploymentDate().toGregorianCalendar().getTime());
        employeesEntity.setPosition(employee.getPosition());
        employeesEntity.setSalary(employee.getSalary());
        return employeesEntity;
    }

    public Employee convertEmployeeEntityToEmployee(EmployeesEntity employeesEntity) {
    	
    	try {
    	
    	GregorianCalendar birthDate = new GregorianCalendar();
    	birthDate.setTime(employeesEntity.getBirthDate());
    	
    	GregorianCalendar employmentDate = new GregorianCalendar();
    	employmentDate.setTime(employeesEntity.getEmploymentDate());
    	
        Employee employee = new Employee();
        employee.setDocumentNumber(employeesEntity.getDocumentNumber());
        employee.setFirstName(employeesEntity.getFirstName());
        employee.setLastName(employeesEntity.getLastName());
        employee.setDocumentType(employeesEntity.getDocumentType());
        employee.setBirthDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(birthDate));
		employee.setEmploymentDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(employmentDate));
		employee.setPosition(employeesEntity.getPosition());
        employee.setSalary(employeesEntity.getSalary());
        return employee;
        
    	} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }

    public List<EmployeesEntity> convertEmployeesToEmployeeEntities(List<Employee> employees) {
        List<EmployeesEntity> employeesEntities = new ArrayList<EmployeesEntity>();
        for (Employee employee : employees) {
        	employeesEntities.add(convertEmployeeToEmployeeEntity(employee));
        }
        return employeesEntities;
    }

    public List<Employee> convertEmployeeEntitiesToEmployees(List<EmployeesEntity> employeesEntities) {
        List<Employee> employees = new ArrayList<Employee>();
        for (EmployeesEntity employeesEntity : employeesEntities) {
        	employees.add(convertEmployeeEntityToEmployee(employeesEntity));
        }
        return employees;
    }
}
