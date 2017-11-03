package com.example.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Many;

import com.example.model.CourseModel;
import com.example.model.StudentModel;

@Mapper
public interface StudentMapper
{
    @Select("select npm, name, gpa from student where npm = #{npm}")
    @Results(value= {
    		@Result(property="npm", column="npm"),
    		@Result(property="name", column="name"),
    		@Result(property="gpa", column="gpa"),
    		@Result(property="courses", column="npm",javaType = List.class, many=@Many(select="selectCourses"))
    })
    StudentModel selectStudent (@Param("npm") String npm);

    @Select("select npm, name, gpa from student")
    @Results(value= {    		
    		@Result(property="npm", column="npm"),
    		@Result(property="name", column="name"),
    		@Result(property="gpa", column="gpa"),
    		@Result(property="courses", column="npm",javaType = List.class, many=@Many(select="selectCourses"))
    })
    List<StudentModel> selectAllStudents ();

    @Insert("INSERT INTO student (npm, name, gpa) VALUES (#{npm}, #{name}, #{gpa})")
    void addStudent (StudentModel student);
    
    @Delete("DELETE FROM student where student.npm = #{npm};")
    void deleteStudent(String npm);
    
    @Update("UPDATE student SET student.name = #{name}, student.gpa = #{gpa} WHERE student.npm = #{npm};")
    void updateStudent(StudentModel student);
    
    @Select("select course.id_course, name, credits " +
    		"from studentcourse join course " +
    		"on studentcourse.id_course = course.id_course " +
    		"where studentcourse.npm = #{npm}")
    @Results(value= {    		
    		@Result(property="idCourse", column="id_course"),
    })
    List<CourseModel> selectCourses(@Param("npm") String npm);
    
    @Select("select student.npm, name, gpa " +
    		"from studentcourse join student " +
    		"on studentcourse.npm = student.npm " +
    		"where studentcourse.id_course = #{idCourse}")
    List<StudentModel> selectStudentsCourse(@Param("idCourse") String idCourse);
    
    @Select("select * from course where id_course = #{id}")
    @Results(value= {
    		@Result(property="idCourse", column="id_course"),
    		@Result(property="name", column="name"),
    		@Result(property="credit", column="credit"),
    		@Result(property="students", column="id_course",javaType = List.class, many=@Many(select="selectStudentsCourse"))
    })
    CourseModel selectCourse (@Param("id") String id);
    
    
    
}
