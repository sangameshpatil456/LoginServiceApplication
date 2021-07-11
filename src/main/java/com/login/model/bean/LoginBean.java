package com.login.model.bean;

public class LoginBean {

	private String userName;
	private String password;
	private String userEmail;

	public LoginBean() {
		super();
	}
    public LoginBean(String userName, String password, String userEmail) {
        this.userName = userName;
        this.password = password;
        this.userEmail = userEmail;
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserEmail() {
		return userEmail;
	}

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}