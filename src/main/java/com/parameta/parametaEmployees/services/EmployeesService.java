package com.parameta.parametaEmployees.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.parameta.parametaEmployees.dtos.EmployeesDTO;
import com.parameta.parametaEmployees.endpoints.EmployeeEndpoint;
import com.parameta.parametaEmployees.entities.EmployeesEntity;
import com.parameta.parametaEmployees.exceptions.CriteriaIssuesException;
import com.parameta.parametaEmployees.exceptions.InternalErrorException;
import com.parameta.parametaEmployees.repositories.EmployeesRepository;
import com.parameta.parametaEmployees.soap.Employee;
import com.parameta.parametaEmployees.soap.PostEmployeeRequest;
import com.parameta.parametaEmployees.utils.Constants;
import com.parameta.parametaEmployees.utils.Utilities;

@Service
public class EmployeesService {

	@Autowired
	EmployeesRepository employeesRepository;

	@Autowired
	EmployeeEndpoint employeeEndpoint;

	private static final Logger logger = LoggerFactory.getLogger(EmployeesService.class);

	public EmployeesDTO saveEmployee(EmployeesEntity employeesEntity) {

		try
		{
			// Validate employment date
			if (Utilities.validateDays(employeesEntity.getEmploymentDate()) < 0) {
				logger.error(Constants.DATE_EMPLOYMENT_ERROR);
				throw new CriteriaIssuesException("{\"message\": \"" +  Constants.DATE_EMPLOYMENT_ERROR + "\",\"code\": \"CONDITION_FOUND\"}");
			}
			// validate birth date
			else if (Utilities.validateYears(employeesEntity.getBirthDate()) < 18) {
				logger.error(Constants.DATE_BIRTH_ERROR);
				throw new CriteriaIssuesException("{\"message\": \"" +  Constants.DATE_BIRTH_ERROR + "\",\"code\": \"CONDITION_FOUND\"}");
			}

			else {
				// Validate information on the table
				Optional<EmployeesEntity> employees = employeesRepository.findByDocumentNumber(employeesEntity.getDocumentNumber());
				// If the information exist on the table, it shows an error
				if (employees.isPresent()) {
					logger.info(Constants.INFO_FOUND);
					throw new CriteriaIssuesException("{\"message\": \"" + Constants.INFO_FOUND + "\",\"code\": \"INFO_FOUND\"}");
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
					employee.setBirthDate(Utilities.convertDateToXMLGregorianCalendar(employeesEntity.getBirthDate()));
					employee.setEmploymentDate(Utilities.convertDateToXMLGregorianCalendar(employeesEntity.getEmploymentDate()));
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
					employeesDTO.setCurrentAgeTime(Utilities.getPeriod(employeesEntity.getBirthDate()));
					employeesDTO.setCurrrentEmploymentTime(Utilities.getPeriod(employeesEntity.getEmploymentDate()));

					return employeesDTO;
				}			
			}
		}
		catch (DataAccessException ex) {
			logger.error(ex.getMessage());
			throw new InternalErrorException("{\"message\": \"" +  Constants.INTERNAL_ERROR + "\",\"code\": \"INTERNAL_ERROR\"}");
			
		}
	}


}
