package com.healthyfish.healthyfishdoctor.POJO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/18.
 */
public class BeanFebrileTreatment {
	private String key;

	private String title;
	private String abbr;

	private String treatment;
    private List<String> descList = new ArrayList<String>();
    private List<String> disKeyList = new ArrayList<String>();

    public BeanFebrileTreatment(){}
    public BeanFebrileTreatment(String t, String tr){
        this.title = t;
        this.treatment = tr;
    }
    public BeanFebrileTreatment(String t, String tr, String ...d){
        this.title = t;
        this.treatment = tr;
        for(int i=0; i<d.length; i++)
        this.descList.add(d[i]);
    }
    public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
    public List<String> getDescList() {
        return descList;
    }

    public void setDescList(List<String> list) {
        this.descList = list;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbbr() {
		return abbr;
	}
	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public List<String> getDisKeyList() {
        return disKeyList;
    }

    public void setDisKeyList(List<String> disKeyList) {
        this.disKeyList = disKeyList;
    }
}
