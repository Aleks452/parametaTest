package com.parameta.parametaEmployees.services;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parameta.parametaEmployees.dtos.EmployeesDTO;
import com.parameta.parametaEmployees.endpoints.EmployeeEndpoint;
import com.parameta.parametaEmployees.entities.EmployeesEntity;
import com.parameta.parametaEmployees.exceptions.CriteriaIssuesException;
import com.parameta.parametaEmployees.exceptions.InternalErrorException;
import com.parameta.parametaEmployees.repositories.EmployeesRepository;
import com.parameta.parametaEmployees.soap.Employee;
import com.parameta.parametaEmployees.soap.PostEmployeeRequest;

@Service
public class EmployeesService {

	@Autowired
	EmployeesRepository employeesRepository;
	
	@Autowired
	EmployeeEndpoint employeeEndpoint;
	
		
	public EmployeesDTO saveEmployee(EmployeesEntity employeesEntity) {
		
		if (validateDays(employeesEntity.getEmploymentDate()) < 0) {
			throw new CriteriaIssuesException("{\"message\": \"The employeement date is major the current day. \",\"code\": \"CONDITION_FOUND\"}");
		}
		else if (validateYears(employeesEntity.getBirthDate()) < 18) {
			throw new CriteriaIssuesException("{\"message\": \"The employee is a minor, it is not allowed. \",\"code\": \"CONDITION_FOUND\"}");
		}
				
		else {
			
			Optional<EmployeesEntity> employees = employeesRepository.findByDocumentNumber(employeesEntity.getDocumentNumber());
			
			if (employees.isPresent()) {
				throw new CriteriaIssuesException("{\"message\": \"The employee is already registered. \",\"code\": \"INFO_FOUND\"}");
			}
			else {
				
				// Calling SOAP service
	            PostEmployeeRequest request = new PostEmployeeRequest();
	            
	            // Creating employee SOAP
	            Employee employee = new Employee();
	            
	            // Maping the values
	            employee.setDocumentNumber(employeesEntity.getDocumentNumber());
	            employee.setFirstName(employeesEntity.getFirstName());
	            employee.setLastName(employeesEntity.getLastName());
	            employee.setDocumentType(employeesEntity.getDocumentType());
	            employee.setBirthDate(convertDateToXMLGregorianCalendar(employeesEntity.getBirthDate()));
	            employee.setEmploymentDate(convertDateToXMLGregorianCalendar(employeesEntity.getEmploymentDate()));
	            employee.setPosition(employeesEntity.getPosition());
	            employee.setSalary(employeesEntity.getSalary());
	            
	            // Send the request
	            request.setEmployee(employee);
	            
	            //Execute SOAP service
	            employeeEndpoint.postEmployees(request);
	            
	            // Mapping to DTO, it could use to mapper but, is neccesary implement and get age and contract time
	            EmployeesDTO employeesDTO = new EmployeesDTO();
	            
	            employeesDTO.setDocumentNumber(employeesEntity.getDocumentNumber());
	            employeesDTO.setFirstName(employeesEntity.getFirstName());
	            employeesDTO.setLastName(employeesEntity.getLastName());
	            employeesDTO.setDocumentType(employeesEntity.getDocumentType());
	            employeesDTO.setBirthDate(employeesEntity.getBirthDate());
	            employeesDTO.setEmploymentDate(employeesEntity.getEmploymentDate());
	            employeesDTO.setPosition(employeesEntity.getPosition());
	            employeesDTO.setSalary(employeesEntity.getSalary());
	            employeesDTO.setCurrentAgeTime(getPeriod(employeesEntity.getBirthDate()));
	            employeesDTO.setCurrrentEmploymentTime(getPeriod(employeesEntity.getEmploymentDate()));
	                      
	            return employeesDTO;
			}			
		}
	}
	
	public int validateYears(Date date) {
        
		// Convert Date to LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.of("UTC")).toLocalDate();

        // Obtain the current date
        LocalDate currentDate = LocalDate.now();

        // get age in years
        Period period = Period.between(localDate, currentDate);
        int year = period.getYears();

        return year;
    }
	
	public int validateDays(Date date) {
        
		// Convert Date to LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.of("UTC")).toLocalDate();

        // Obtain the current date
        LocalDate currentDate = LocalDate.now();

        // get age in years
        Period period = Period.between(localDate, currentDate);
        int days = period.getDays();

        return days;
    }
	
	public String getPeriod(Date date) {
        
		// Convert Date to LocalDate
        LocalDate localDate = date.toInstant().atZone(ZoneId.of("UTC")).toLocalDate();

        // Obtain the current date
        LocalDate currentDate = LocalDate.now();

        // get period
        Period period = Period.between(localDate, currentDate);
        
        // convert data
        return String.format("%d years, %d months and %d days",
                period.getYears(),
                period.getMonths(),
                period.getDays());
    }
	
	private XMLGregorianCalendar convertDateToXMLGregorianCalendar(Date date) {
		try
		{
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
		}
        catch (DatatypeConfigurationException e) {
            // Internal error exception
        	throw new InternalErrorException("{\"message\": \"unexpected error has occurred . \",\"code\": \"INTERNAL_ERROR\"}");
        }
    }
		
}
