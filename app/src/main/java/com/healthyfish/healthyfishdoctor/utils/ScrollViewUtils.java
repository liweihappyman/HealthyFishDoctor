package com.healthyfish.healthyfishdoctor.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 描述：用于解决ScrollView中嵌套ListView出现的问题
 * 作者：LYQ on 2017/7/16.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class ScrollViewUtils {

    /**
     * 计算ListView的高度以解决ListView嵌套在ScrollView中出现的高度问题
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
