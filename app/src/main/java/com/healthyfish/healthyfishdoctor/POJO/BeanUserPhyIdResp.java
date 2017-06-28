package com.healthyfish.healthyfishdoctor.POJO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/10.
 */
public class BeanUserPhyIdResp extends BeanBaseResp {
	private List<BeanUserPhysical> phyList = new ArrayList<BeanUserPhysical>();

	public List<BeanUserPhysical> getPhyList() {
		return phyList;
	}

	public void setPhyList(List<BeanUserPhysical> phyList) {
		this.phyList = phyList;
	}
}
