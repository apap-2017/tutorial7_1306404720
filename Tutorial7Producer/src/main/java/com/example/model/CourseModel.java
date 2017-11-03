package com.example.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseModel {
	private String idCourse;
	private String name;
	private Integer credits;
	private List<StudentModel> students;
	
}
