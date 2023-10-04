package com.bci.tareas.dto;

import com.bci.tareas.helper.ErrorResp;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class RespuestaDTO {

private String msg;
private String uuid ;
private String name;
private String password;
private String email;
private String created;
private  ErrorResp errorResp;
private String lastLogin;
private String token;
private String  isActive;
private PhoneDto phone;

public String getMsg() {
	return msg;
}
public void setMsg(String msg) {
	this.msg = msg;
}
public String getUuid() {
	return uuid;
}
public void setUuid(String uuid) {
	this.uuid = uuid;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getCreated() {
	return created;
}
public void setCreated(String created) {
	this.created = created;
}
public String getLastLogin() {
	return lastLogin;
}
public void setLastLogin(String lastLogin) {
	this.lastLogin = lastLogin;
}
public ErrorResp getErrorResp() {
	return errorResp;
}
public void setErrorResp(ErrorResp errorResp) {
	this.errorResp = errorResp;
}
public String getToken() {
	return token;
}
public void setToken(String token) {
	this.token = token;
}


public PhoneDto getPhone() {
	return phone;
}
public void setPhone(PhoneDto phone) {
	this.phone = phone;
}
public String getIsActive() {
	return isActive;
}
public void setIsActive(String isActive) {
	this.isActive = isActive;
}



}