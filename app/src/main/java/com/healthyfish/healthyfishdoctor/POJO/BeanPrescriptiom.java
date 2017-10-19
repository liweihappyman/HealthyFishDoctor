package com.healthyfish.healthyfishdoctor.POJO;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * 描述：电子处方bean
 * 作者：WKJ on 2017/7/21.
 * 邮箱：
 * 编辑：WKJ
 */

public class BeanPrescriptiom extends DataSupport{

    /**
     * PRESCRIBE_OPERATOR : 杨坚毅/142
     * DEPT_NAME : 心病科门诊
     * SEX : 男
     * ITEM_CLASS : 西药
     * APPLY_DEPT : 2216
     * SICK_NAME : 邹玉贵
     * DIAGNOSIS_NAME : 高血压(慢)
     * WRITE_TIME : 2016-12-15T09:00:35+08:00
     * presList : [{"LAY_PHYSIC_DAYS":"14","PRICE":"44.35","FREQ_DESCRIBE":"q.d.","LAY_PHYSIC_QUANTITY":"14","PACK_SPEC":"10mg*7","PHYSIC_DOSEAGE":"10","STATUS":"已完成","COST":"88.70","USAGE":"口服","SICK_ID":"0000281122","DOSE_UNIT":"mg","PHYSIC_NAME":"瑞舒伐他汀钙片","PRESCRIBE_NUMBER":"12953414","TAKE_MEDICINE_WAYS_CODE":"Y020","PHYSIC_UNIT":"片","MODIFY_TIME":"2016-12-15T09:00:36+08:00"}]
     * RESCRIBE_STATUS : 已执行
     * SICK_ID : 0000281122
     * PRESCRIPTION_NUMBER : 12953414
     * key : pres_null_lzzyy_12953414
     * AGE : 66
     */

    private String PRESCRIBE_OPERATOR;
    private String DEPT_NAME;
    private String SEX;
    private String ITEM_CLASS;//由于这个字段没有用到，暂时拿来存放List<PresListBean> presList的JsonString对象；
    private String APPLY_DEPT;
    private String SICK_NAME;
    private String DIAGNOSIS_NAME;
    private String WRITE_TIME;
    private String RESCRIBE_STATUS;
    private String SICK_ID;
    private String PRESCRIPTION_NUMBER;
    private String key;
    private String AGE;

    protected List<PresListBean> presList;//只有生明为private的对象才会被litepal创建为数据库的字段，这个用上面的ITEM_CLASS存放


    public String getPRESCRIBE_OPERATOR() {
        return PRESCRIBE_OPERATOR;
    }

    public void setPRESCRIBE_OPERATOR(String PRESCRIBE_OPERATOR) {
        this.PRESCRIBE_OPERATOR = PRESCRIBE_OPERATOR;
    }

    public String getDEPT_NAME() {
        return DEPT_NAME;
    }

    public void setDEPT_NAME(String DEPT_NAME) {
        this.DEPT_NAME = DEPT_NAME;
    }

    public String getSEX() {
        return SEX;
    }

    public void setSEX(String SEX) {
        this.SEX = SEX;
    }

    public String getITEM_CLASS() {
        return ITEM_CLASS;
    }

    public void setITEM_CLASS(String ITEM_CLASS) {
        this.ITEM_CLASS = ITEM_CLASS;
    }

    public String getAPPLY_DEPT() {
        return APPLY_DEPT;
    }

    public void setAPPLY_DEPT(String APPLY_DEPT) {
        this.APPLY_DEPT = APPLY_DEPT;
    }

    public String getSICK_NAME() {
        return SICK_NAME;
    }

    public void setSICK_NAME(String SICK_NAME) {
        this.SICK_NAME = SICK_NAME;
    }

    public String getDIAGNOSIS_NAME() {
        return DIAGNOSIS_NAME;
    }

    public void setDIAGNOSIS_NAME(String DIAGNOSIS_NAME) {
        this.DIAGNOSIS_NAME = DIAGNOSIS_NAME;
    }

    public String getWRITE_TIME() {
        return WRITE_TIME;
    }

    public void setWRITE_TIME(String WRITE_TIME) {
        this.WRITE_TIME = WRITE_TIME;
    }

    public String getRESCRIBE_STATUS() {
        return RESCRIBE_STATUS;
    }

    public void setRESCRIBE_STATUS(String RESCRIBE_STATUS) {
        this.RESCRIBE_STATUS = RESCRIBE_STATUS;
    }

    public String getSICK_ID() {
        return SICK_ID;
    }

    public void setSICK_ID(String SICK_ID) {
        this.SICK_ID = SICK_ID;
    }

    public String getPRESCRIPTION_NUMBER() {
        return PRESCRIPTION_NUMBER;
    }

    public void setPRESCRIPTION_NUMBER(String PRESCRIPTION_NUMBER) {
        this.PRESCRIPTION_NUMBER = PRESCRIPTION_NUMBER;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAGE() {
        return AGE;
    }

    public void setAGE(String AGE) {
        this.AGE = AGE;
    }

    public List<PresListBean> getPresList() {
        return presList;
    }

    public void setPresList(List<PresListBean> presList) {
        this.presList = presList;
    }

    public  class PresListBean {
        /**
         * LAY_PHYSIC_DAYS : 1
         * PRICE : 36.40
         * FREQ_DESCRIBE : q.d.
         * LAY_PHYSIC_QUANTITY : 2
         * PACK_SPEC : 69.56g*(A.B.C各一)
         * PHYSIC_DOSEAGE : 2
         * STATUS : 已完成
         * COST : 72.80
         * USAGE : 口服
         * SICK_ID : 0000281122
         * DOSE_UNIT : 盒
         * PHYSIC_NAME : 复方聚乙二醇电解质散(Ⅰ)
         * PRESCRIBE_NUMBER : 14222548
         * TAKE_MEDICINE_WAYS_CODE : Y020
         * PHYSIC_UNIT : 盒
         * MODIFY_TIME : 2017-04-25T09:29:33+08:00
         */

        protected String LAY_PHYSIC_DAYS;
        protected String PRICE;
        protected String FREQ_DESCRIBE;
        protected String LAY_PHYSIC_QUANTITY;
        protected String PACK_SPEC;
        protected String PHYSIC_DOSEAGE;
        protected String STATUS;
        protected String COST;
        protected String USAGE;
        protected String SICK_ID;
        protected String DOSE_UNIT;
        protected String PHYSIC_NAME;
        protected String PRESCRIBE_NUMBER;
        protected String TAKE_MEDICINE_WAYS_CODE;
        protected String PHYSIC_UNIT;
        protected String MODIFY_TIME;

        public String getLAY_PHYSIC_DAYS() {
            return LAY_PHYSIC_DAYS;
        }

        public void setLAY_PHYSIC_DAYS(String LAY_PHYSIC_DAYS) {
            this.LAY_PHYSIC_DAYS = LAY_PHYSIC_DAYS;
        }

        public String getPRICE() {
            return PRICE;
        }

        public void setPRICE(String PRICE) {
            this.PRICE = PRICE;
        }

        public String getFREQ_DESCRIBE() {
            return FREQ_DESCRIBE;
        }

        public void setFREQ_DESCRIBE(String FREQ_DESCRIBE) {
            this.FREQ_DESCRIBE = FREQ_DESCRIBE;
        }

        public String getLAY_PHYSIC_QUANTITY() {
            return LAY_PHYSIC_QUANTITY;
        }

        public void setLAY_PHYSIC_QUANTITY(String LAY_PHYSIC_QUANTITY) {
            this.LAY_PHYSIC_QUANTITY = LAY_PHYSIC_QUANTITY;
        }

        public String getPACK_SPEC() {
            return PACK_SPEC;
        }

        public void setPACK_SPEC(String PACK_SPEC) {
            this.PACK_SPEC = PACK_SPEC;
        }

        public String getPHYSIC_DOSEAGE() {
            return PHYSIC_DOSEAGE;
        }

        public void setPHYSIC_DOSEAGE(String PHYSIC_DOSEAGE) {
            this.PHYSIC_DOSEAGE = PHYSIC_DOSEAGE;
        }

        public String getSTATUS() {
            return STATUS;
        }

        public void setSTATUS(String STATUS) {
            this.STATUS = STATUS;
        }

        public String getCOST() {
            return COST;
        }

        public void setCOST(String COST) {
            this.COST = COST;
        }

        public String getUSAGE() {
            return USAGE;
        }

        public void setUSAGE(String USAGE) {
            this.USAGE = USAGE;
        }

        public String getSICK_ID() {
            return SICK_ID;
        }

        public void setSICK_ID(String SICK_ID) {
            this.SICK_ID = SICK_ID;
        }

        public String getDOSE_UNIT() {
            return DOSE_UNIT;
        }

        public void setDOSE_UNIT(String DOSE_UNIT) {
            this.DOSE_UNIT = DOSE_UNIT;
        }

        public String getPHYSIC_NAME() {
            return PHYSIC_NAME;
        }

        public void setPHYSIC_NAME(String PHYSIC_NAME) {
            this.PHYSIC_NAME = PHYSIC_NAME;
        }

        public String getPRESCRIBE_NUMBER() {
            return PRESCRIBE_NUMBER;
        }

        public void setPRESCRIBE_NUMBER(String PRESCRIBE_NUMBER) {
            this.PRESCRIBE_NUMBER = PRESCRIBE_NUMBER;
        }

        public String getTAKE_MEDICINE_WAYS_CODE() {
            return TAKE_MEDICINE_WAYS_CODE;
        }

        public void setTAKE_MEDICINE_WAYS_CODE(String TAKE_MEDICINE_WAYS_CODE) {
            this.TAKE_MEDICINE_WAYS_CODE = TAKE_MEDICINE_WAYS_CODE;
        }

        public String getPHYSIC_UNIT() {
            return PHYSIC_UNIT;
        }

        public void setPHYSIC_UNIT(String PHYSIC_UNIT) {
            this.PHYSIC_UNIT = PHYSIC_UNIT;
        }

        public String getMODIFY_TIME() {
            return MODIFY_TIME;
        }

        public void setMODIFY_TIME(String MODIFY_TIME) {
            this.MODIFY_TIME = MODIFY_TIME;
        }

        @Override
        public String toString() {
            return "PresListBean{" +
                    "LAY_PHYSIC_DAYS='" + LAY_PHYSIC_DAYS + '\'' +
                    ", PRICE='" + PRICE + '\'' +
                    ", FREQ_DESCRIBE='" + FREQ_DESCRIBE + '\'' +
                    ", LAY_PHYSIC_QUANTITY='" + LAY_PHYSIC_QUANTITY + '\'' +
                    ", PACK_SPEC='" + PACK_SPEC + '\'' +
                    ", PHYSIC_DOSEAGE='" + PHYSIC_DOSEAGE + '\'' +
                    ", STATUS='" + STATUS + '\'' +
                    ", COST='" + COST + '\'' +
                    ", USAGE='" + USAGE + '\'' +
                    ", SICK_ID='" + SICK_ID + '\'' +
                    ", DOSE_UNIT='" + DOSE_UNIT + '\'' +
                    ", PHYSIC_NAME='" + PHYSIC_NAME + '\'' +
                    ", PRESCRIBE_NUMBER='" + PRESCRIBE_NUMBER + '\'' +
                    ", TAKE_MEDICINE_WAYS_CODE='" + TAKE_MEDICINE_WAYS_CODE + '\'' +
                    ", PHYSIC_UNIT='" + PHYSIC_UNIT + '\'' +
                    ", MODIFY_TIME='" + MODIFY_TIME + '\'' +
                    '}';
        }
    }

}
