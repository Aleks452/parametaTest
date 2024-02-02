package com.parameta.parametaEmployees.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parameta.parametaEmployees.dtos.EmployeesDTO;
import com.parameta.parametaEmployees.entities.EmployeesEntity;
import com.parameta.parametaEmployees.services.EmployeesService;
import com.parameta.parametaEmployees.validations.MandatoryFirstValidation;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

	@Autowired
	EmployeesService employeesService;
	
	@GetMapping("/save-employee")
	public ResponseEntity<EmployeesDTO> saveEmployee(@Validated({MandatoryFirstValidation.class}) @RequestBody  EmployeesEntity employeesEntity){
		return ResponseEntity.ok(employeesService.saveEmployee(employeesEntity));
	}
	
	
}
