package com.herbalife.is.mybatisencrypt.service.impl;

import com.herbalife.is.mybatisencrypt.entity.Student;
import com.herbalife.is.mybatisencrypt.mapper.StudentMapper;
import com.herbalife.is.mybatisencrypt.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentMapper studentMapper;

    @Autowired
    public StudentServiceImpl(StudentMapper studentMapper){
        this.studentMapper = studentMapper;
    }

    @Override
    public Student findStudentById(Integer id) {
        return studentMapper.findStudentById(id);
    }

    @Override
    public void insert(Student student) {
        studentMapper.insert(student);
    }
}
