package com.brunoli.payroll;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;

/**
 * @author Greg Turnquist
 */
// tag::code[]
@PreAuthorize("hasRole('ROLE_MANAGER')")
public interface EmployeeRepository extends MongoRepository<Employee, String> {

	@Override
	@PreAuthorize("#employee?.manager == null or #employee?.manager?.name == authentication?.name")
	Employee save(@Param("employee") Employee employee);

	@Override
	@PreAuthorize("@employeeRepository.findOne(#id)?.manager?.name == authentication?.name")
	void delete(@Param("id") String id);

	@Override
	@PreAuthorize("#employee?.manager?.name == authentication?.name")
	void delete(@Param("employee") Employee employee);

}
// end::code[]