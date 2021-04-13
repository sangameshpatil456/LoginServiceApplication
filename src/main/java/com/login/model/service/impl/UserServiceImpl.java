package com.login.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.login.model.bean.UserBean;
import com.login.model.dao.UserDao;
import com.login.model.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public List<UserBean> getUser() {
		return (List<UserBean>) userDao.getUser();
	}

	@Override
	public UserBean getUserById(long userID) {
		return userDao.getUserById(userID);
	}

	@Override
	public UserBean saveUser(UserBean userBean) {
		return userDao.saveUser(userBean);
	}

	@Override
	public UserBean updateUser(UserBean userBean) {
		return userDao.updateUser(userBean);
	}

	@Override
	public UserBean deleteUserById(long userID) {
		return userDao.deleteUserById(userID);
	}

	@Override
	public UserBean getUserByEmail(String userEmail) {
		return userDao.getUserByEmail(userEmail);
	}

}