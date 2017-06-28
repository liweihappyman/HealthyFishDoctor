package com.healthyfish.healthyfishdoctor.POJO;

import java.util.ArrayList;
import java.util.List;

public class BeanHealthPlanListResp extends BeanBaseResp {
	
	private List<BeanHealthPlanListRespItem> healthPlanList = new ArrayList<BeanHealthPlanListRespItem>();

	public List<BeanHealthPlanListRespItem> getHealthPlanList() {return healthPlanList;}
	public void setHealthPlanList(List<BeanHealthPlanListRespItem> healthPlanList) {this.healthPlanList = healthPlanList;}
}
