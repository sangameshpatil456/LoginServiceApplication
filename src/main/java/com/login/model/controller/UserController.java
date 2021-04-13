package com.login.model.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.model.bean.LoginResponse;
import com.login.model.bean.UserBean;
import com.login.model.service.UserService;
import com.login.model.utils.Model;

@RestController
@RequestMapping("/user/")
@Component
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@GetMapping
	public ResponseEntity<LoginResponse<Model>> getUser() {
		Model model = new Model();
		List<UserBean> userBean = userService.getUser();
		model.addAttribute("userBean", userBean);
		return new ResponseEntity<>(new LoginResponse<>(model), HttpStatus.OK);
	}

	@GetMapping("{userID}")
	public ResponseEntity<LoginResponse<Model>> getUserById(@PathVariable(name = "userID") long userID) {
		Model model = new Model();
		UserBean userBean = userService.getUserById(userID);
		model.addAttribute("userBean", userBean);
		return new ResponseEntity<>(new LoginResponse<>(model), HttpStatus.OK);
	}

	@PostMapping(value = "/save")
	public ResponseEntity<LoginResponse<Model>> saveUser(@RequestBody UserBean userBean) {
		String encodedPassword = bCryptPasswordEncoder.encode(userBean.getPassword());
		userBean.setPassword(encodedPassword);
		userService.saveUser(userBean);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PutMapping
	public ResponseEntity<LoginResponse<Model>> updateUser(@RequestBody UserBean userBean) {
		userService.updateUser(userBean);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("{userID}")
	public ResponseEntity<LoginResponse<Model>> deleteUserById(@PathVariable(name = "userID") long userID) {
		Model model = new Model();
		UserBean userBean = userService.deleteUserById(userID);
		model.addAttribute("userBean", userBean);
		return new ResponseEntity<>(new LoginResponse<>(model), HttpStatus.OK);
	}
}