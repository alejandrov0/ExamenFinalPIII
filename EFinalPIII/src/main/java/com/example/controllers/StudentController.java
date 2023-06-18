package com.example.controllers;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.daos.CourseDao;
import com.example.daos.GradeDao;
import com.example.daos.StudentDao;
import com.example.entities.Course;
import com.example.entities.GradeRequest;
import com.example.entities.Student;
import com.example.entities.Grade;

import com.example.services.StudentService;


@RestController
@RequestMapping("/students")
public class StudentController {
	
	private final StudentDao studentDao;
    private final CourseDao courseDao;
    private final GradeDao gradeDao;

	
	@Autowired
	private StudentService studentService;
	
	
	@GetMapping
    public List<Student> findAll(){
		return studentService.findAll();
    }
	
	@GetMapping( value = "/{id}")
    public Student findById( @PathVariable Long id){
		return studentService.findById(id);
    }

	@PostMapping()
    public Student create( @RequestBody Student student){
		return studentService.create(student);
    }

	@PutMapping(value = "/{id}")
    public Student modify( @PathVariable Long id, @RequestBody Student student){
		return studentService.modify(id,student);
    }

	@DeleteMapping(value = "/{id}")
	public void delete(@PathVariable Long id) {
		studentService.delete(id);
	}
	
	public StudentController(StudentDao studentDao, CourseDao courseDao, GradeDao gradeDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
		this.gradeDao =  gradeDao;
    }

    @PostMapping("/{studentId}/courses/{courseId}")
    public ResponseEntity<?> addCourseToStudent(@PathVariable Long studentId, @PathVariable Long courseId) {
        Student student = studentDao.findById(studentId).orElse(null);
        Course course = courseDao.findById(courseId).orElse(null);

        if (student == null || course == null) {
            return ResponseEntity.notFound().build();
        }

        student.getCourses().add(course);
        studentDao.save(student);

        return ResponseEntity.ok().build();
    }
    

}
