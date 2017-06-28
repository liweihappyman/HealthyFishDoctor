package com.healthyfish.healthyfishdoctor.POJO;

import java.util.ArrayList;
import java.util.List;

/*
待完成的健康计划，包括体质、慢病：进度计划、当天计划内容
移动监测：与标准数据的比对结果
*/
public class BeanHealthPlanResp extends BeanBaseResp {
	
	private List<BeanHealthPlanRespItem> healthPlan = new ArrayList<BeanHealthPlanRespItem>();

	public List<BeanHealthPlanRespItem> getHealthPlan() {return healthPlan;}
	public void setHealthPlan(List<BeanHealthPlanRespItem> healthPlan) {this.healthPlan = healthPlan;}

}
