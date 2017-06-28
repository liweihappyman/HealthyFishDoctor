package com.healthyfish.healthyfishdoctor.POJO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/20.
 */
/*
方名:朱砂安神丸
组成:朱砂（君）、黄连、炙甘草、生地、当归
功效:重镇安神，清心泻火
主治:心火亢盛，阴血不足证
症状:失眠多梦，怔忡惊悸，心神烦乱，舌红，脉细数
歌诀:朱砂安神东垣方，
归连甘草合地黄，
怔忡不寐心烦乱，
养阴清热可康复。
出处:《内伤伤辨惑论》
*/
public class BeanPrescription {
	private String key;
	private String title;

	private String name;
	private String abbr;
	private String comp;
    private String effect;
    private String attending;
    private String symptom;
    private List<String> tactics = new ArrayList<String>();
    private String ref;

    public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getAttending() {
        return attending;
    }

    public void setAttending(String attending) {
        this.attending = attending;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getComp() {
        return comp;
    }

    public void setComp(String comp) {
        this.comp = comp;
    }

    public List<String> getTactics() {
        return tactics;
    }

    public void setTactics(List<String> tactics) {
        this.tactics = tactics;
    }
}
