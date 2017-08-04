package com.healthyfish.healthyfishdoctor.POJO;
/**
 * Created by Administrator on 2016/11/10.
 */
public class BeanBaseKeyAddResp extends BeanBaseResp {
	private String url; //可以用于浏览器直接访问
	private String beanKey; //后续使用BeanBaseKeyGetReq与服务器交互
	private String timestamp;

	public String getTimestamp() {return timestamp;}
	public void setTimestamp(String timestamp) {this.timestamp = timestamp;}
	public String getUrl() {return url;}
	public void setUrl(String url) {this.url = url;}
	public String getBeanKey() {return beanKey;}
	public void setBeanKey(String beanKey) {this.beanKey = beanKey;}
}
