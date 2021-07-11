package com.login.model.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.login.model.bean.UserBean;
import com.login.model.entity.User;
import com.login.model.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserServiceImpl.class})
public  class UserServiceImplTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserServiceImpl userServiceImpl;

    private UserBean userBean;
    private final User user;

    public UserServiceImplTest() throws IOException {
        user = new User();
        userBean = new UserBean();
        File jsonFile = new File("src/test/data/LoginData.json");
        userBean = new ObjectMapper().readValue(jsonFile, UserBean.class);
    }
    @Before
    public void setUp() {
        when(userRepository.findByIsActive( true))
                .thenReturn(new ArrayList<>());
        when(userRepository.findByUserIdAndIsActive(1L,true))
                .thenReturn(user);
        when(userRepository.findByUserEmailAndIsActive("sangamesh@gmail.com",true))
                .thenReturn(user);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void getUser() {
        userServiceImpl.getUser();
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void getUserById() {
        userServiceImpl.getUserById(1L);
    }

    @Test(expected = NullPointerException.class)
    public void getUserById_WithUserIBydNull_ThenException() {
        userBean.setUserId(null);
        userServiceImpl.getUserById(userBean.getUserId());
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void saveUser() {
        userServiceImpl.saveUser(userBean);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void updateUser() {
        userServiceImpl.updateUser(userBean);
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void deleteUserById() {
        userServiceImpl.deleteUserById(1L);
    }

    @Test(expected = NullPointerException.class)
    public void deleteUserById_WithUserByIdNull_ThenException() throws Exception {
        userBean.setUserId(null);
        userServiceImpl.deleteUserById(userBean.getUserId());
    }

    @Test(expected = Test.None.class /* no exception expected */)
    public void getUserByEmail() {
        userServiceImpl.getUserByEmail(userBean.getUserEmail());
    }

    @Test(expected = ResponseStatusException.class)
    public void getUserByEmail_WithUserByEmailNull_ThenException() {
        userServiceImpl.getUserByEmail(null);
    }
}