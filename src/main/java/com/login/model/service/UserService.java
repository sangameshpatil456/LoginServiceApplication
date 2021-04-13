package com.login.model.service;

import java.util.List;

import com.login.model.bean.UserBean;

public interface UserService {

	public List<UserBean> getUser();

	public UserBean getUserById(long userID);

	public UserBean saveUser(UserBean userBean);

	public UserBean updateUser(UserBean userBean);

	public UserBean deleteUserById(long userID);

	public UserBean getUserByEmail(String userEmail);

}