package com.login.model.service;

import java.util.List;

import com.login.model.bean.UserBean;

public interface UserService {

	List<UserBean> getUser();

	UserBean getUserById(long userID);

	void saveUser(UserBean userBean);

	void updateUser(UserBean userBean);

	UserBean deleteUserById(long userID);

	UserBean getUserByEmail(String userEmail);

}