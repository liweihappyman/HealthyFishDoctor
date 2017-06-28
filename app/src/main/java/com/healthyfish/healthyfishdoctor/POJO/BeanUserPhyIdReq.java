package com.healthyfish.healthyfishdoctor.POJO;

import java.util.ArrayList;
import java.util.List;

public class BeanUserPhyIdReq extends BeanBaseReq {
	private List<Integer>	phyList = new ArrayList<Integer>(); //体质类型
	private List<Integer>	ansList = new ArrayList<Integer>(); //用户问卷的答案

	BeanUserPhyIdReq(){super(BeanUserPhyIdReq.class.getSimpleName());}

	public List<Integer> getAnsList() {
		return ansList;
	}

	public void setAnsList(List<Integer> ansList) {
		this.ansList = ansList;
	}

	public List<Integer> getPhyList() {
		return phyList;
	}

	public void setPhyList(List<Integer> phyList) {
		this.phyList = phyList;
	}

}
