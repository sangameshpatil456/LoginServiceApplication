package com.login.model.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.login.model.bean.UserBean;
import com.login.model.dao.UserDao;
import com.login.model.entity.User;
import com.login.model.repository.UserRepository;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	UserRepository userRepository;

	@Override
	public List<UserBean> getUser() {
		List<User> users = userRepository.findByIsActive(true);
		return entityListToBeanList(users);
	}

	@Override
	public UserBean getUserById(long userID) {
		User user = userRepository.findByUserIdAndIsActive(userID, true);
		return entityToBean(user);
	}

	public List<UserBean> entityListToBeanList(List<User> users) {
		List<UserBean> userBeans = new ArrayList<>();
		for (User user : users) {
			userBeans.add(entityToBean(user));
		}
		return userBeans;
	}

	private UserBean entityToBean(User user) {
		UserBean userBean = new UserBean();
		try {
			BeanUtils.copyProperties(userBean, user);
		} catch (Exception e) {

		}
		return userBean;
	}

	private User BeanToEntity(UserBean userBean) {
		User user = new User();
		try {
			BeanUtils.copyProperties(user, userBean);
		} catch (Exception e) {

		}
		return user;
	}

	@Override
	public UserBean saveUser(UserBean userBean) {
		User user = new User();
		userBean.setIsActive(user.getIsActive());
		userBean.setCreatedBy(user.getCreatedBy());
		userBean.setCreatedDateTime(new Date());
		userBean.setUpdatedBy(user.getUpdatedBy());
		userBean.setUpdatedDateTime(new Date());
		user = userRepository.save(BeanToEntity(userBean));
		return entityToBean(user);
	}

	@Override
	public UserBean updateUser(UserBean userBean) {
		userBean.setUpdatedDateTime(new Date());
		User user = BeanToEntity(userBean);
		userRepository.save(user);
		return entityToBean(user);
	}

	@Override
	public UserBean deleteUserById(long userID) {
		User user = userRepository.findById(userID).get();
		userRepository.delete(user);
		return null;
	}

	@Override
	public UserBean getUserByEmail(String userEmail) {
		User user = userRepository.findByUserEmailAndIsActive(userEmail, true);
		return entityToBean(user);
	}

}