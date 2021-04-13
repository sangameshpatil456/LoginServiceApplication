package com.login.model.dao;

import java.util.List;

import com.login.model.bean.UserBean;

public interface UserDao {

	public List<UserBean> getUser();

	public UserBean getUserById(long userID);

	public UserBean saveUser(UserBean userBean);

	public UserBean updateUser(UserBean userBean);

	public UserBean deleteUserById(long userID);

	public UserBean getUserByEmail(String userEmail);

}