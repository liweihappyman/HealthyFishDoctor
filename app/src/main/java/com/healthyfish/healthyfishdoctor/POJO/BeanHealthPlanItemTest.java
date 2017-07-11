package com.healthyfish.healthyfishdoctor.POJO;

public class BeanHealthPlanItemTest {
	private String title;//标题
	private String progress; //进度计划
	private String todo; //当天计划内容
	private boolean done = false;
	//添加的
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getProgress() {return progress;}
	public void setProgress(String progress) {this.progress = progress;}
	public String getTodo() {return todo;}
	public void setTodo(String todo) {this.todo = todo;}
	public boolean isDone() {return done;}
	public void setDone(boolean done) {this.done = done;}
}
