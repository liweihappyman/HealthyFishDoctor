package com.healthyfish.healthyfishdoctor.POJO;

import java.util.List;

/**
 * 描述：
 * 作者：WKJ on 2017/7/30.
 * 邮箱：
 * 编辑：WKJ
 */

public class BeanPrescription {

    /**
     * abbr : xsljzt
     * attending : 脾胃气虚，痰阻气滞证
     * comp : 人参、白术、茯苓、甘草、陈皮、半夏、砂仁、木香、生姜
     * effect : 益气化痰，行气温中
     * key : feb_pres_01_xsljzt_0038
     * name : 香砂六君子汤
     * ref : 《古今名医方论》
     * symptom : 呕吐痞闷，不思饮食，脘腹胀痛，消瘦倦怠，或气虚肿满呕吐痞闷，不思饮食，脘腹胀痛，消瘦倦怠，或气虚肿满
     * tactics : ["四君子汤中和义","参朮茯苓甘草比","益以夏陈名六君","健脾化痰又理气","除去半夏名异功","或加香砂胃寒祛"]
     * title : 方剂-补益-香砂六君子汤
     */

    private String abbr;
    private String attending;
    private String comp;
    private String effect;
    private String key;
    private String name;
    private String ref;
    private String symptom;
    private String title;
    private List<String> tactics;

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getAttending() {
        return attending;
    }

    public void setAttending(String attending) {
        this.attending = attending;
    }

    public String getComp() {
        return comp;
    }

    public void setComp(String comp) {
        this.comp = comp;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getTactics() {
        return tactics;
    }

    public void setTactics(List<String> tactics) {
        this.tactics = tactics;
    }
}
