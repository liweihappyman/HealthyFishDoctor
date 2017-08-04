package com.healthyfish.healthyfishdoctor.POJO;


/**
 *
 feb_t_: treat[方剂]
 feb_d_: disease[原文的疾病描述]
 feb_c_: 伤寒论中的章节名

 feb_pres_: 方剂prescription[方剂]
 feb_prec_:方剂的种类 category[归类]

 feb_phm_: 药材pharm
 feb_phc_：药性
 */





public class BeanListReq extends BeanBaseReq {
	private String prefix;
	int	from;
	int num;
	int to;

	public BeanListReq(){super(BeanListReq.class.getSimpleName());}
	
	public String getPrefix() {return prefix;}
	public void setPrefix(String prefix) {this.prefix = prefix;}
	public int getFrom() {return from;}
	public void setFrom(int from) {this.from = from;}
	public int getNum() {return num;}
	public void setNum(int num) {this.num = num;}
	public int getTo() {return to;}
	public void setTo(int to) {this.to = to;}
}
