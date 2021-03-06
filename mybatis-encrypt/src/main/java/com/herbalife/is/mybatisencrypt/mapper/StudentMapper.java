package com.herbalife.is.mybatisencrypt.mapper;

import com.herbalife.is.mybatisencrypt.entity.Student;
import com.herbalife.is.mybatisencrypt.request.StudentRequest;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentMapper {
    Student findStudentById(Integer id);
    void insert(Student student);
}
