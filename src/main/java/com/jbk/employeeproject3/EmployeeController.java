package com.jbk.employeeproject3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController {
	@Autowired
	EmployeeRepository employeeRepository;
	HashMap<Integer, Employee> cache = new HashMap<Integer, Employee>();

	@GetMapping(value = "/msg")
	public String msg() {
		return "hello world";
	}

	@PostMapping(value = "/saveEmp")
	public Employee saveEmp(@RequestBody Employee employee) {
		cache.put(employee.employeeId, employee);
        employeeRepository.save(employee);
		return cache.get(employee.employeeId);

	}

	@GetMapping(value = "/getEmp/{empID}")
	public Optional<Employee> getEmp(@PathVariable int empID) {
		
		return employeeRepository.findById(empID);
		//return cache.get(empID);

	}

	@PutMapping(value = "/updateEmp")
	public Employee updateEmp(@RequestBody Employee employee) {
		Employee oldEmployee = cache.get(employee.employeeId);
		if(oldEmployee!=null) {
		oldEmployee.setEmployeeName(employee.getEmployeeName());
		oldEmployee.setEmployeeSalary(employee.getEmployeeSalary());
		oldEmployee.setEmployeeAddress(employee.getEmployeeAddress());
		cache.put(oldEmployee.getEmployeeId(), oldEmployee);
		}
		
		Employee tempEmp=employeeRepository.findById(employee.employeeId).get();
		tempEmp.setEmployeeName(employee.getEmployeeName());
		tempEmp.setEmployeeAddress(employee.getEmployeeAddress());
		tempEmp.setEmployeeSalary(employee.getEmployeeSalary());
		employeeRepository.save(tempEmp);
		return employeeRepository.save(tempEmp);

	}

	@DeleteMapping(value = "/deleteEmp/{empID}")
	public String deleteEmp(@PathVariable int empID) {
     Optional<Employee> employee=employeeRepository.findById(empID);
     if(employee.isPresent()) {
    	 employeeRepository.deleteById(empID);
    	 return "Deleted Succesfully";
     }else {
    	 return "Employee Not found";
     }
		

	}

	@GetMapping(value = "/getAllEmp")
	public List<Employee> getAllEmp() {

		List<Employee> list = new ArrayList<Employee>();

		//return list = cache.values().stream().collect(Collectors.toList());
		return list=employeeRepository.findAll();
	}

	/*
	 * @GetMapping(value = "/getEmp50k") public List<Employee>
	 * getEmployeeSalary50K() {
	 * 
	 * List<Employee> list = new ArrayList<Employee>(); List<Employee> list1 =
	 * list.stream().filter(employee -> employee.getEmployeeSalary() > 50000)
	 * .collect(Collectors.toList());
	 * 
	 * List<Employee> listEmp = new ArrayList<Employee>(); for (Employee employee :
	 * list) { if (employee.getEmployeeSalary() > 50000) { listEmp.add(employee); }
	 * }
	 * 
	 * 
	 * return list1;
	 * 
	 * }
	 */
		

}




