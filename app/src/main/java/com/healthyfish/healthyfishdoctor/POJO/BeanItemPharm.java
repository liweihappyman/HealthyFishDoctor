package com.healthyfish.healthyfishdoctor.POJO;

import java.util.ArrayList;
import java.util.List;

public class BeanItemPharm {
	private String key;
	private String title; //标题
	private List<BeanItemPharmFields> fieldList = new ArrayList<BeanItemPharmFields>();
	
	public String getKey() {return key;}
	public void setKey(String key) {this.key = key;}
	public String getTitle() {return title;	}
	public void setTitle(String title) {this.title = title;	}
	public List<BeanItemPharmFields> getFieldList() {return fieldList;}
	public void setFieldList(List<BeanItemPharmFields> fieldList) {this.fieldList = fieldList;}
}
