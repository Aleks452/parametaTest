package com.parameta.parametaEmployees.dtos;

import java.util.Date;

import lombok.Data;

@Data
public class EmployeesDTO {

	private String documentNumber;
	private String firstName;
	private String lastName;
	private String documentType;
	private Date birthDate;
	private Date employmentDate;
	private String position;
	private Double salary;
	private String currentAgeTime;
	private String currrentEmploymentTime; 
}
