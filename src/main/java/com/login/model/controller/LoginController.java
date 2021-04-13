package com.login.model.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.login.model.bean.LoginBean;
import com.login.model.bean.LoginResponse;
import com.login.model.bean.UserBean;
import com.login.model.security.TokenGenerators;
import com.login.model.service.UserService;
import com.login.model.utils.Model;

@RestController
@RequestMapping("/auth/")
public class LoginController {

	@Autowired
	UserService userService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private TokenGenerators jwtTokenGenerator;

	@PostMapping(value="/login")
	public ResponseEntity<LoginResponse<Model>> loginUser(@RequestBody LoginBean loginBean) {
		Model model = new Model();
		if (loginBean != null) {
			UserBean userBean = userService.getUserByEmail(loginBean.getUserEmail());
			if (userBean.getUserId() == null) {
				model.addAttribute("error", "User Not Found");
				return new ResponseEntity<>(new LoginResponse<>(model), HttpStatus.NOT_FOUND);
			}
			String token = jwtTokenGenerator.getJWTToken(userBean.getUserId(), userBean.getUserName(),userBean.getUserEmail());
			if (bCryptPasswordEncoder.matches(loginBean.getPassword(), userBean.getPassword())) {
				model.addAttribute("userId", userBean.getUserId());
				model.addAttribute("userName", userBean.getUserName());
				model.addAttribute("userEmail", userBean.getUserEmail());
				model.addAttribute("isActive", userBean.getIsActive());
				model.addAttribute("token", token);
			} else {
				model.addAttribute("error", "Wrong Username and Password");
				return new ResponseEntity<>(new LoginResponse<>(model), HttpStatus.BAD_REQUEST);
			}
		} else {
			model.addAttribute("error", "Invalid user information");
			return new ResponseEntity<>(new LoginResponse<>(model), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(new LoginResponse<>(model), HttpStatus.OK);
	}
}