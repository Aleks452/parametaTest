package com.parameta.parametaEmployees.repositories;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.parameta.parametaEmployees.entities.EmployeesEntity;

@Repository
public interface EmployeesRepository extends PagingAndSortingRepository<EmployeesEntity, String> {

	// Find by document number, it could getting one result
	Optional<EmployeesEntity> findByDocumentNumber(String documentNumber);

	EmployeesEntity save(EmployeesEntity employeesEntity);
	
	
}
