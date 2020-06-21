package com.herbalife.is.mybatisencrypt.service.impl;

import com.herbalife.is.mybatisencrypt.entity.Student;
import com.herbalife.is.mybatisencrypt.mapper.StudentMapper;
import com.herbalife.is.mybatisencrypt.request.StudentRequest;
import com.herbalife.is.mybatisencrypt.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

@Service
public class StudentServiceImpl implements StudentService {
    private StudentMapper studentMapper;
    private final Charset charset = StandardCharsets.UTF_8;

    @Autowired
    public StudentServiceImpl(StudentMapper studentMapper){
        this.studentMapper = studentMapper;
    }

    @Override
    public StudentRequest findStudentById(Integer id) {
        Student student = studentMapper.findStudentById(id);
        return StudentRequest.builder()
                .id(student.getId())
                .age(student.getAge())
                .name(student.getName())
                .cellphone(new String(student.getCellphone(),this.charset))
                .build();
    }

    @Override
    public void insert(StudentRequest student) {
        Student studentObj = Student.builder()
                .age(100)
                .name(student.getName())
                .cellphone(student.getCellphone().getBytes(charset))
                .build();
        studentMapper.insert(studentObj);
    }
}
