package com.example.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.model.CourseModel;
import com.example.model.StudentModel;
import com.example.service.StudentService;


@Controller
public class StudentController
{
    @Autowired
    StudentService studentDAO;


    @RequestMapping("/")
    public String index ()
    {
        return "index";
    }


    @RequestMapping("/student/add")
    public String add ()
    {
        return "form-add";
    }


    @RequestMapping("/student/add/submit")
    public String addSubmit (
            @RequestParam(value = "npm", required = false) String npm,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "gpa", required = false) double gpa)
    {
        StudentModel student = new StudentModel (npm, name, gpa, null);
        studentDAO.addStudent (student);

        return "success-add";
    }


    @RequestMapping("/student/view")
    public String view (Model model,
            @RequestParam(value = "npm", required = false) String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/view/{npm}")
    public String viewPath (Model model,
            @PathVariable(value = "npm") String npm)
    {
        StudentModel student = studentDAO.selectStudent (npm);

        if (student != null) {
            model.addAttribute ("student", student);
            return "view";
        } else {
            model.addAttribute ("npm", npm);
            return "not-found";
        }
    }


    @RequestMapping("/student/viewall")
    public String view (Model model)
    {
        List<StudentModel> students = studentDAO.selectAllStudents ();
        model.addAttribute ("students", students);

        return "viewall";
    }


    @RequestMapping("/student/delete/{npm}")
    public String delete (Model model, @PathVariable(value = "npm") String npm)
    {
    	StudentModel found = studentDAO.selectStudent(npm);
    	if (!found.equals(null)) {
    		studentDAO.deleteStudent (npm);
    		return "delete";
    	}else {
    		return "not-found";
    	}
    }

    @RequestMapping("/student/update/{npm}")
    public String update (Model model, @PathVariable(value = "npm") String npm) {
    	StudentModel found = studentDAO.selectStudent(npm);
    	if(found.equals(null)) {
    		return "not-found";
    	}else {
    		model.addAttribute("student", found);
    		return "form-update";
    	}
    }
    
    @RequestMapping(value = "student/update/submit", method = {RequestMethod.POST, RequestMethod.GET})
    public String updateSubmit(Model model, @ModelAttribute StudentModel student) {

    	StudentModel found = studentDAO.selectStudent(student.getNpm());
    	System.out.println(found.toString());
    	if (found.equals(null)) {
			return "not-found";
		}else {			
			found.setGpa(student.getGpa());
			found.setName(student.getName());
			studentDAO.updateStudent(found);
			return "success-update";
		}
    	  
    }
    
//  GET dan POST dalam satu request mapping dan satu method saja.
//  Request mapping dari kedua method, yaitu update dan updateSubmit dimatikan, agar 
//  requestnya hanya menjalankan method ini :D
    
//    @RequestMapping(value = {"student/update/{npm}", "student/update/submit"}, method = {RequestMethod.POST, RequestMethod.GET})
    public String updateAndSubmit(@PathVariable Optional<String> npm,
    		Model model, @ModelAttribute StudentModel student, 
    		HttpServletRequest request) {
    	
    	if(request.getMethod().equals("GET")) {
//    		ini isi dari Method update :D 
    		StudentModel found = studentDAO.selectStudent(npm.get());
        	if(found.equals(null)) {
        		return "not-found";
        	}else {
        		model.addAttribute("student", found);
        		return "form-update";
        	}
    	}else {
//    		ini isi dari method updateSubmit :D
    		StudentModel found = studentDAO.selectStudent(student.getNpm());
        	System.out.println(found.toString());
        	if (found.equals(null)) {
    			return "not-found";
    		}else {			
    			found.setGpa(student.getGpa());
    			found.setName(student.getName());
    			studentDAO.updateStudent(found);
//    			sebenarnya lebih baik bila return method untuk membalikkan laman get, tapi ini cukup sama dengan method diatas :D  
    			return "success-update";
    		}
    	}
    }
    
    @RequestMapping("/course/view/{id}")
    public String viewcourse (Model model, @PathVariable(value = "id") String id){
        CourseModel course= studentDAO.selectCourse(id);
        if (course != null) {
        	model.addAttribute ("course", course);
            return "viewCourse";
        } else {
            model.addAttribute ("id", id);
            return "not-foundCourse";
        }
    }
    
}
