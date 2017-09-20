package com.healthyfish.healthyfishdoctor.POJO;


//获取到的数据：
//[{"DEPT_NAME":"疼痛科门诊","DEPT_CODE":"0201"},{"DEPT_NAME":"男科","DEPT_CODE":"0203"},{"DEPT_NAME":"中医美容科","DEPT_CODE":"0204"},{"DEPT_NAME":"内科门诊","DEPT_CODE":"0206"},{"DEPT_NAME":"推拿科门诊","DEPT_CODE":"0207"},{"DEPT_NAME":"乳房病科门诊","DEPT_CODE":"0208"},{"DEPT_NAME":"中医预防保健科","DEPT_CODE":"0212"},{"DEPT_NAME":"外一科(肝胆.胃肠)","DEPT_CODE":2200},{"DEPT_NAME":"外三科(脑脊髓病)","DEPT_CODE":2202},{"DEPT_NAME":"儿科门诊","DEPT_CODE":2203},{"DEPT_NAME":"妇科门诊","DEPT_CODE":2204},{"DEPT_NAME":"皮肤科门诊","DEPT_CODE":2205},{"DEPT_NAME":"小儿脑康科门诊","DEPT_CODE":2206},{"DEPT_NAME":"耳鼻喉科门诊","DEPT_CODE":2207},{"DEPT_NAME":"眼科门诊","DEPT_CODE":2208},{"DEPT_NAME":"肛肠科门诊","DEPT_CODE":2209},{"DEPT_NAME":"外二科(泌尿.前列腺)","DEPT_CODE":2210},{"DEPT_NAME":"骨一科门诊(四肢)","DEPT_CODE":2211},{"DEPT_NAME":"骨二科门诊(脊柱)","DEPT_CODE":2212},{"DEPT_NAME":"骨三科门诊(四肢)","DEPT_CODE":2213},{"DEPT_NAME":"肾病科门诊","DEPT_CODE":2215},{"DEPT_NAME":"心病科门诊","DEPT_CODE":2216},{"DEPT_NAME":"风湿病科门诊","DEPT_CODE":2217},{"DEPT_NAME":"肺病科门诊","DEPT_CODE":2218},{"DEPT_NAME":"内分泌病科门诊","DEPT_CODE":2219},{"DEPT_NAME":"针灸科门诊","DEPT_CODE":2220},{"DEPT_NAME":"脾胃病科门诊","DEPT_CODE":2221},{"DEPT_NAME":"肿瘤一科门诊","DEPT_CODE":2222},{"DEPT_NAME":"口腔科门诊","DEPT_CODE":22300},{"DEPT_NAME":"门诊骨科","DEPT_CODE":22400},{"DEPT_NAME":"脑病科门诊","DEPT_CODE":22800},{"DEPT_NAME":"肝病门诊","DEPT_CODE":23300},{"DEPT_NAME":"肿瘤二科门诊","DEPT_CODE":23500},{"DEPT_NAME":"针灸腹针室","DEPT_CODE":23600}]

public class BeanHospDeptListRespItem extends BeanBaseResp {
	private String DEPT_CODE; //部门代码
	private String DEPT_NAME; //名称
	
	public String getDEPT_CODE() {
		return DEPT_CODE;
	}
	public void setDEPT_CODE(String dEPT_CODE) {
		DEPT_CODE = dEPT_CODE;
	}
	public String getDEPT_NAME() {
		return DEPT_NAME;
	}
	public void setDEPT_NAME(String dEPT_NAME) {
		DEPT_NAME = dEPT_NAME;
	}

}
