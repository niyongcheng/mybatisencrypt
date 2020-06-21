package com.herbalife.is.mybatisencrypt.controller.api.v1;

import com.herbalife.is.mybatisencrypt.entity.Student;
import com.herbalife.is.mybatisencrypt.request.StudentRequest;
import com.herbalife.is.mybatisencrypt.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1")
@RestController
public class StudentController {

    private StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService = studentService;
    }

    @RequestMapping(value = "/student/{id}", method = RequestMethod.GET)
    public StudentRequest getStudent(@PathVariable("id") Integer id){
        return studentService.findStudentById(id);
    }

    @RequestMapping(value = "/student", method = RequestMethod.POST)
    public void insert(@RequestBody StudentRequest student){
        studentService.insert(student);
    }
}
