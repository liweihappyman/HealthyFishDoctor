package com.healthyfish.healthyfishdoctor.POJO;

import java.util.ArrayList;
import java.util.List;

public class BeanUserMqttStatusCheck extends BeanBaseReq {
	private List<String> userList = new ArrayList<String>();

	BeanUserMqttStatusCheck(){super(BeanUserMqttStatusCheck.class.getSimpleName());}
	
	public List<String> getUserList() {
		return userList;
	}

	public void setUserList(List<String> userList) {
		this.userList = userList;
	}

}
