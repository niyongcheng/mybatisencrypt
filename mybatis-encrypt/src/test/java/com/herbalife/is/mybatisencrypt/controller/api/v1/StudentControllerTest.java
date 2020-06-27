package com.herbalife.is.mybatisencrypt.controller.api.v1;

import com.herbalife.is.mybatisencrypt.MybatisEncryptApplication;
import com.herbalife.is.mybatisencrypt.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = MybatisEncryptApplication.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class StudentControllerTest {

    private final String findStudentById = "/api/v1/student/1";

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    void getStudent() throws Exception {
        doReturn(MockStudent.findStudentById())
                .when(studentService)
                .findStudentById(any());

        MvcResult mvcResult = this.mockMvc.perform(get(findStudentById).contentType(MediaType.ALL_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void insert() {
    }
}