package com.healthyfish.healthyfishdoctor.POJO;

import java.util.ArrayList;
import java.util.List;

public class BeanSearchResp extends BeanBaseResp {
	private List<BeanSearchRespItem> resultList = new ArrayList<BeanSearchRespItem>();

	public List<BeanSearchRespItem> getResultList() {return resultList;}
	public void setResultList(List<BeanSearchRespItem> resultList) {this.resultList = resultList;}
}
