package com.healthyfish.healthyfishdoctor.POJO;
/**
 * Created by Administrator on 2017/3/24.
 */
public class BeanPharmSelf {
    private String key; //feb_phm_catId_name
    private String title; //标题
    private String name; //药名
    private String abbr;
	private String taste; //性味
    private String meridian; //归经
    private String features; //功能
    private String attending; //主治
    private String volume;
    private float min; //1〜3g
    private float max;

    private String collision; //不宜与...同用
    private String caution;//虚弱者慎用 /生品内服宜慎//孕妇禁用
    private String traits; //性状

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

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getMeridian() {
        return meridian;
    }

    public void setMeridian(String meridian) {
        this.meridian = meridian;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getAttending() {
        return attending;
    }

    public void setAttending(String attending) {
        this.attending = attending;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public String getCollision() {
        return collision;
    }

    public void setCollision(String collision) {
        this.collision = collision;
    }

    public String getCaution() {
        return caution;
    }

    public void setCaution(String caution) {
        this.caution = caution;
    }

    public String getTraits() {
        return traits;
    }

    public void setTraits(String traits) {
        this.traits = traits;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }
}
