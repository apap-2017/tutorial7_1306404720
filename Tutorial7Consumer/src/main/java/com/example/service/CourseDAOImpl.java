package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.dao.CourseDAO;
import com.example.model.CourseModel;

@Service
public class CourseDAOImpl implements CourseDAO{
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	   // Do any additional configuration here
	   return builder.build();
	}
	
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public CourseModel selectCourse(String id) {
		System.out.println(id);
		CourseModel course = restTemplate.getForObject("http://localhost:8080/rest/course/view/"+id, CourseModel.class);
		return course;
	}

	@Override
	public List<CourseModel> selectAllCourse() {
		List<CourseModel> course = restTemplate.getForObject("http://localhost:8080/rest/course/viewall/", List.class);
		return course;
	}
	
	
}
