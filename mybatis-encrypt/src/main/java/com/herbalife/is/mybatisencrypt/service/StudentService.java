package com.herbalife.is.mybatisencrypt.service;

import com.herbalife.is.mybatisencrypt.entity.Student;

public interface StudentService {
    Student findStudentById(Integer id);
    void insert(Student student);
}
