package com.healthyfish.healthyfishdoctor.POJO;/*
package com.healthyfish.healthyfish.POJO;

import com.kangfish.utils.PharmUtils;

public class BeanItemPharmAbstract {
	private String url;
	private int idOffset; //for remove
	private String title; //标题
	private String category;
	private float min; //1〜3g
	private float max;
	private float minSwallow; //【用法与用量】3〜9g，另煎兑服；也可研粉吞服，一次2g，一日2 次。
	private float maxSwallow; //【用法与用量】3〜9g; 研粉吞服，一次1〜3g。外用适量。
	
	private String collision; //不宜与...同用
	private String taste; //性味
	private String meridian; //归经
	private String caution;//虚弱者慎用
	private String forbidden; //孕妇禁用
	

	public BeanItemPharmAbstract(){}
	public BeanItemPharmAbstract(BeanItemPharm bean){
		this.title = bean.getTitle();
	}
	public BeanItemPharmAbstract(BeanItemPharmAllInOne bean){
		this.title = bean.getTitle();
		this.category = bean.getCategory();
		min = -1;
		max = -1;
		minSwallow = -1;
		maxSwallow = -1;
		PharmUtils.extract(this, bean.getContent());
	}
	
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	public int getIdOffset() {return idOffset;}
	public void setIdOffset(int idOffset) {this.idOffset = idOffset;}
	public String getTitle() {return title;	}
	public void setTitle(String title) {this.title = title;	}
	public String getCategory() {return category;}
	public void setCategory(String category) {this.category = category;}
	public float getMin() {return min;}
	public void setMin(float min) {this.min = min;}
	public float getMax() {return max;}
	public void setMax(float max) {this.max = max;}
	public float getMinSwallow() {return minSwallow;}
	public void setMinSwallow(float minSwallow) {this.minSwallow = minSwallow;}
	public float getMaxSwallow() {return maxSwallow;}
	public void setMaxSwallow(float maxSwallow) {this.maxSwallow = maxSwallow;}
	public String getCollision() {return collision;}
	public void setCollision(String collision) {this.collision = collision;}
	public String getTaste() {return taste;}
	public void setTaste(String taste) {this.taste = taste;}
	public String getMeridian() {return meridian;}
	public void setMeridian(String meridian) {this.meridian = meridian;}
	public String getCaution() {return caution;}
	public void setCaution(String caution) {this.caution = caution;}
	public String getForbidden() {return forbidden;}
	public void setForbidden(String forbidden) {this.forbidden = forbidden;}
}
*/
