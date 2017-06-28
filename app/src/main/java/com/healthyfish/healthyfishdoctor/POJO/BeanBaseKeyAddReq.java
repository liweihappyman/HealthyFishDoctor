package com.healthyfish.healthyfishdoctor.POJO;

//与BeanBaseSetKeyReq的区别是：upload是重新分配key，而setKey是更新原key
//如果上传的数据与用户相关，请App把用户的id放在prefix中，例如prefix="blog_XXXXX(uid)_"
public class BeanBaseKeyAddReq extends BeanBaseReq {
	//本class设计上不提供数据的修改
	//有要更新的数据时，另外新建一份，原来的不再访问即可（或者使用BeanBaseSetKeyReq）
//	private String key; //key为空（""）表示新增的数据，否则为修改的数据
//	public String getKey() {return key;}
//	public void setKey(String key) {this.key = key;}
	
	//prefix: 
	//"blog_(uid)_" 博文
	//"medRec_" 电子病历
	//"mrList_" 电子病历列表
	//"user_" 用户信息
	//"hpc_" health plan customize, use BeanHealthPlanCustomizeReq instead
	//"set_" 集合
	//"news_" 新闻
	private String prefix;
	private String jsonString;
	
	public BeanBaseKeyAddReq(){super(BeanBaseKeyAddReq.class.getSimpleName());}
	
	public String getPrefix() {return prefix;}
	public void setPrefix(String prefix) {this.prefix = prefix;}
	public String getJsonString() {return jsonString;}
	public void setJsonString(String jsonString) {this.jsonString = jsonString;}
}
