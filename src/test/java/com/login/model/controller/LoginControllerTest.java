package com.login.model.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.login.model.bean.LoginBean;
import com.login.model.bean.UserBean;
import com.login.model.entity.User;
import com.login.model.security.TokenGenerators;
import com.login.model.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {LoginController.class})
public class LoginControllerTest {

    @MockBean
    private UserService userService;

    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @MockBean
    private TokenGenerators jwtTokenGenerator;

    @Autowired
    LoginController loginController;

    private ObjectMapper mapper;
    private MockMvc mockMvc;
    private UserBean userBean;
    private User user;
    private LoginBean loginBean;

    public LoginControllerTest(){
        this.userBean = new UserBean();
        this.user = new User();
        this.mapper = new ObjectMapper();
        loginBean = new LoginBean("sangamesh","avc","sangamesh@gmail.com");
    }

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(loginController).build();
    }

    @Test
    public void testLoginUser() throws Exception {
        mockMvc.perform(post("/auth/login")
                .contentType("application/json")
                .content(mapper.writeValueAsString(loginBean)))
                .andExpect(status().isOk());
    }

    @AfterEach
    void testTearDown() {
    }

    @Test
    public void testGetPublicKey() throws Exception {
        mockMvc.perform(get("/auth/getPublicKey")
                .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void testEncrypt() {
    }

    @Test
    public void testDecrypt() {
    }

    @Test
    public void testGetVersion() {
    }
}
