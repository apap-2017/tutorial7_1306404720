package com.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.StudentModel;
import com.example.service.StudentService;

@RestController
@RequestMapping("/rest")
public class StudentRestController{
	
	@Autowired
	StudentService studentService;
	
	@RequestMapping("/student/view/{npm}")
	public StudentModel view (@PathVariable(value = "npm") String npm) {
		StudentModel student = studentService.selectStudent (npm);
		return student;
	}
}
