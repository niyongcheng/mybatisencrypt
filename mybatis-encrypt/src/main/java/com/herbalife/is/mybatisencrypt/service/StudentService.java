package com.herbalife.is.mybatisencrypt.service;

import com.herbalife.is.mybatisencrypt.request.StudentRequest;

public interface StudentService {
    StudentRequest findStudentById(Integer id);
    void insert(StudentRequest student);
}
