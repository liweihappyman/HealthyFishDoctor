package com.healthyfish.healthyfishdoctor.POJO;

import java.util.ArrayList;
import java.util.List;

public class BeanItemHealthPlan {
	private String key;

	private String title; //标题
	private String description; //简介
	private List<BeanItemHealthPlanSubItem> todoList = new ArrayList<BeanItemHealthPlanSubItem>();

	public String getKey() {return key;}
	public void setKey(String key) {this.key = key;}
	public String getTitle() {return title;	}
	public void setTitle(String title) {this.title = title;	}
	public String getDescription() {return description;}
	public void setDescription(String description) {this.description = description;}
	public List<BeanItemHealthPlanSubItem> getTodoList() {return todoList;}
	public void setTodoList(List<BeanItemHealthPlanSubItem> todoList) {this.todoList = todoList;}

}
