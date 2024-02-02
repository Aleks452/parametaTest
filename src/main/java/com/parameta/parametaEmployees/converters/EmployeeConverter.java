package com.parameta.parametaEmployees.converters;

import com.parameta.parametaEmployees.entities.EmployeesEntity;
import com.parameta.parametaEmployees.soap.Employee;
import com.parameta.parametaEmployees.utils.Utilities;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


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
    	
        Employee employee = new Employee();
        employee.setDocumentNumber(employeesEntity.getDocumentNumber());
        employee.setFirstName(employeesEntity.getFirstName());
        employee.setLastName(employeesEntity.getLastName());
        employee.setDocumentType(employeesEntity.getDocumentType());
        employee.setBirthDate(Utilities.convertDateToXMLGregorianCalendar(employeesEntity.getBirthDate()));
		employee.setEmploymentDate(Utilities.convertDateToXMLGregorianCalendar(employeesEntity.getEmploymentDate()));
		employee.setPosition(employeesEntity.getPosition());
        employee.setSalary(employeesEntity.getSalary());
        return employee;
        	
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
