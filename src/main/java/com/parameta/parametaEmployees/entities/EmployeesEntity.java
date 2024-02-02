package com.parameta.parametaEmployees.entities;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.parameta.parametaEmployees.validations.MandatoryFirstValidation;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Entity
@Table(name = "EMPLOYEE")
@Data
public class EmployeesEntity implements Serializable { 

	private static final long serialVersionUID = 1L;

	/** this entity are using a validation implemented with interface, this permits use
	several criteria with different varaibles
	
	*/
	
	@Id
	@NotNull(groups = {MandatoryFirstValidation.class})
	@NotBlank(groups = {MandatoryFirstValidation.class})
	@Column(name = "document_number", length = 20)
	private String documentNumber;
	
	@NotNull(groups = {MandatoryFirstValidation.class})
	@NotBlank(groups = {MandatoryFirstValidation.class})
	@Column(name = "first_name", length = 255)
	private String firstName;
	
	@NotNull(groups = {MandatoryFirstValidation.class})
	@NotBlank(groups = {MandatoryFirstValidation.class})
	@Column(name = "last_name", length = 255)
	private String lastName;
	
	@NotNull(groups = {MandatoryFirstValidation.class})
	@NotBlank(groups = {MandatoryFirstValidation.class})
	@Column(name = "document_type", length = 50)
	private String documentType;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(groups = {MandatoryFirstValidation.class})
	@Column(name = "birth_date")
	private Date birthDate;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@NotNull(groups = {MandatoryFirstValidation.class})
	@Column(name = "employment_date")
	private Date employmentDate;
	
	@NotNull(groups = {MandatoryFirstValidation.class})
	@NotBlank(groups = {MandatoryFirstValidation.class})
	@Column(name = "position", length = 255)
	private String position;
	
	@NotNull(groups = {MandatoryFirstValidation.class})
	@Positive(groups = {MandatoryFirstValidation.class})
	@Column(name = "salary", length = 255)
	private Double salary;

}
