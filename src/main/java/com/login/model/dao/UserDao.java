package com.login.model.dao;

import java.util.List;

import com.login.model.bean.UserBean;

public interface UserDao {

	List<UserBean> getUser();

	UserBean getUserById(long userID);

	UserBean saveUser(UserBean userBean);

	UserBean updateUser(UserBean userBean);

	UserBean deleteUserById(long userID);

	UserBean getUserByEmail(String userEmail);

}