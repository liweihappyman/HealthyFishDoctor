package com.healthyfish.healthyfishdoctor.POJO;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：药典列表的数据bean
 * 作者：WKJ on 2017/7/15.
 * 邮箱：
 * 编辑：WKJ
 */

public class BeanPharmacopeia {
    private String title;//药典的大标题
    private List<String> list = new ArrayList<>();//药典大标题下面的具体子项

    public BeanPharmacopeia(String title, List<String> list) {
        this.title = title;
        this.list = list;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }
}
