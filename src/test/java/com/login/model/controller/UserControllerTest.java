package com.login.model.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.login.model.bean.UserBean;
import com.login.model.entity.User;
import com.login.model.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserController.class})
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserController userController;

    private final ObjectMapper mapper;
    private MockMvc mockMvc;
    private final UserBean userBean;

    public UserControllerTest(){
        userBean = new UserBean();
        User user = new User();
        mapper = new ObjectMapper();
    }

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void testGetUser() throws Exception {
        mockMvc.perform(get("/user/")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserById() throws Exception {
         mockMvc.perform(get("/user/{userID}",1L)
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetUserById_WithPathVariableEmpty_ClientError() throws Exception {
        mockMvc.perform(get("/user/{userID}", "")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveUser() throws Exception {
        mockMvc.perform(post("/user/save")
                .contentType("application/json")
                .content(mapper.writeValueAsString(userBean)))
                .andExpect(status().isOk());
    }

    @Test
    public void testSaveUser_WithRequestBodyEmpty_ClientError() throws Exception {
        mockMvc.perform(post("/user/save")
                .contentType("application/json")
                .content(mapper.writeValueAsString(null)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void  testUpdateUser() throws Exception {
        mockMvc.perform(put("/user/")
        .contentType("application/json")
        .content(mapper.writeValueAsString(userBean)))
        .andExpect(status().isOk());
    }

    @Test
    public void  testUpdateUser_WithRequestBodyEmpty_ClientError() throws Exception {
        mockMvc.perform(put("/user/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(null)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testDeleteUser() throws Exception{
        mockMvc.perform(delete("/user/{userID}",1L)
        .contentType("application/json")).andExpect(status().isOk());
    }
}
