package com.healthyfish.healthyfishdoctor.POJO;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：检查报告（化验单）
 * 作者：WKJ on 2017/8/1.
 * 邮箱：
 * 编辑：WKJ
 */

public class BeanInspectionReport extends DataSupport{

    /**
     * ID_NO :
     * testList : [{"ABNORMAL_INDICATOR":"H","RESULT_DATE_TIME":"2016-12-01T11:04:11","HOSPITAL_MARK":"","TEST_NO":"1608640012_6775493","RESULT":84.51,"UNITS":"mg/L","ITEM_NAME":"尿微量白蛋白","REFERENCE_RANGE":"0.00-20.0"}]
     * RESULT_STATUS : 4
     * PATIENT_NAME : 邹玉贵、
     * PATIENT_ID : 0000281122
     * SPECIMEN :
     * PHONE_NUMBER :
     * HOSPITAL_MARK :
     * TEST_NO : 1608640012_6775493
     * key : rept_13977211042_lzzyy_1608640012_6775493
     * REQUESTED_DATE : 2016-12-01T08:59:27、
     * ITEM_NAME : 尿微量白蛋白测定、
     */

    private String ID_NO;
    private int RESULT_STATUS;
    private String PATIENT_NAME;
    private String PATIENT_ID;
    private String SPECIMEN;//没有用到这个字段，用来存放List<TestListBean>  的JsonStr
    private String PHONE_NUMBER;
    private String HOSPITAL_MARK;
    private String TEST_NO;
    private String key;
    private String REQUESTED_DATE;
    private String ITEM_NAME;
    protected List<TestListBean> testList = new ArrayList<>();

    public String getID_NO() {
        return ID_NO;
    }

    public void setID_NO(String ID_NO) {
        this.ID_NO = ID_NO;
    }

    public int getRESULT_STATUS() {
        return RESULT_STATUS;
    }

    public void setRESULT_STATUS(int RESULT_STATUS) {
        this.RESULT_STATUS = RESULT_STATUS;
    }

    public String getPATIENT_NAME() {
        return PATIENT_NAME;
    }

    public void setPATIENT_NAME(String PATIENT_NAME) {
        this.PATIENT_NAME = PATIENT_NAME;
    }

    public String getPATIENT_ID() {
        return PATIENT_ID;
    }

    public void setPATIENT_ID(String PATIENT_ID) {
        this.PATIENT_ID = PATIENT_ID;
    }

    public String getSPECIMEN() {
        return SPECIMEN;
    }

    public void setSPECIMEN(String SPECIMEN) {
        this.SPECIMEN = SPECIMEN;
    }

    public String getPHONE_NUMBER() {
        return PHONE_NUMBER;
    }

    public void setPHONE_NUMBER(String PHONE_NUMBER) {
        this.PHONE_NUMBER = PHONE_NUMBER;
    }

    public String getHOSPITAL_MARK() {
        return HOSPITAL_MARK;
    }

    public void setHOSPITAL_MARK(String HOSPITAL_MARK) {
        this.HOSPITAL_MARK = HOSPITAL_MARK;
    }

    public String getTEST_NO() {
        return TEST_NO;
    }

    public void setTEST_NO(String TEST_NO) {
        this.TEST_NO = TEST_NO;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getREQUESTED_DATE() {
        return REQUESTED_DATE;
    }

    public void setREQUESTED_DATE(String REQUESTED_DATE) {
        this.REQUESTED_DATE = REQUESTED_DATE;
    }

    public String getITEM_NAME() {
        return ITEM_NAME;
    }

    public void setITEM_NAME(String ITEM_NAME) {
        this.ITEM_NAME = ITEM_NAME;
    }

    public List<TestListBean> getTestList() {
        return testList;
    }

    public void setTestList(List<TestListBean> testList) {
        this.testList = testList;
    }

    public  class TestListBean {
        /**
         * ABNORMAL_INDICATOR : H
         * RESULT_DATE_TIME : 2016-12-01T11:04:11
         * HOSPITAL_MARK :
         * TEST_NO : 1608640012_6775493
         * RESULT : 84.51
         * UNITS : mg/L
         * ITEM_NAME : 尿微量白蛋白
         * REFERENCE_RANGE : 0.00-20.0
         */

        protected String ABNORMAL_INDICATOR;
        protected String RESULT_DATE_TIME;
        protected String HOSPITAL_MARK;
        protected String TEST_NO;
        protected String RESULT;
        protected String UNITS;
        protected String ITEM_NAME;
        protected String REFERENCE_RANGE;

        public String getABNORMAL_INDICATOR() {
            return ABNORMAL_INDICATOR;
        }

        public void setABNORMAL_INDICATOR(String ABNORMAL_INDICATOR) {
            this.ABNORMAL_INDICATOR = ABNORMAL_INDICATOR;
        }

        public String getRESULT_DATE_TIME() {
            return RESULT_DATE_TIME;
        }

        public void setRESULT_DATE_TIME(String RESULT_DATE_TIME) {
            this.RESULT_DATE_TIME = RESULT_DATE_TIME;
        }

        public String getHOSPITAL_MARK() {
            return HOSPITAL_MARK;
        }

        public void setHOSPITAL_MARK(String HOSPITAL_MARK) {
            this.HOSPITAL_MARK = HOSPITAL_MARK;
        }

        public String getTEST_NO() {
            return TEST_NO;
        }

        public void setTEST_NO(String TEST_NO) {
            this.TEST_NO = TEST_NO;
        }

        public String getRESULT() {
            return RESULT;
        }

        public void setRESULT(String RESULT) {
            this.RESULT = RESULT;
        }

        public String getUNITS() {
            return UNITS;
        }

        public void setUNITS(String UNITS) {
            this.UNITS = UNITS;
        }

        public String getITEM_NAME() {
            return ITEM_NAME;
        }

        public void setITEM_NAME(String ITEM_NAME) {
            this.ITEM_NAME = ITEM_NAME;
        }

        public String getREFERENCE_RANGE() {
            return REFERENCE_RANGE;
        }

        public void setREFERENCE_RANGE(String REFERENCE_RANGE) {
            this.REFERENCE_RANGE = REFERENCE_RANGE;
        }
    }
}
