package com.healthyfish.healthyfishdoctor.POJO;

public class BeanListReq extends BeanBaseReq {
	private String prefix;
	int	from;
	int num;
	int to;

	BeanListReq(){super(BeanListReq.class.getSimpleName());}
	
	public String getPrefix() {return prefix;}
	public void setPrefix(String prefix) {this.prefix = prefix;}
	public int getFrom() {return from;}
	public void setFrom(int from) {this.from = from;}
	public int getNum() {return num;}
	public void setNum(int num) {this.num = num;}
	public int getTo() {return to;}
	public void setTo(int to) {this.to = to;}
}
