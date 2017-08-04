package com.healthyfish.healthyfishdoctor.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;

import com.healthyfish.healthyfishdoctor.POJO.AppFuncBean;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.healthy_chat.AppFuncAdapter;

import java.util.ArrayList;

public class SimpleAppsGridView extends RelativeLayout {

    protected View view;

    public SimpleAppsGridView(Context context) {
        this(context, null);
    }

    public SimpleAppsGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.view_apps, this);
        init();
    }

    protected void init(){
        GridView gv_apps = (GridView) view.findViewById(R.id.gv_apps);
        final ArrayList<AppFuncBean> mAppFuncBeanList = new ArrayList<>();
        mAppFuncBeanList.add(new AppFuncBean(R.mipmap.icon_photo, "图片", 1));
        mAppFuncBeanList.add(new AppFuncBean(R.mipmap.icon_camera, "拍照", 2));
        mAppFuncBeanList.add(new AppFuncBean(R.mipmap.icon_audio, "视频", 3));
        mAppFuncBeanList.add(new AppFuncBean(R.mipmap.icon_qzone, "空间", 4));
        mAppFuncBeanList.add(new AppFuncBean(R.mipmap.icon_contact, "联系人", 5));
        mAppFuncBeanList.add(new AppFuncBean(R.mipmap.icon_file, "文件", 6));
        mAppFuncBeanList.add(new AppFuncBean(R.mipmap.icon_loaction, "位置", 7));

        AppFuncAdapter adapter = new AppFuncAdapter(getContext(), mAppFuncBeanList);
        gv_apps.setAdapter(adapter);

        gv_apps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (mAppFuncBeanList.get(position).getId()) {
                    case 1:
                        Log.e("Wayne", "图片");
                        break;
                    case 2:
                        Log.e("Wayne", "照相");
                        break;
                    default:
                        break;
                }
            }
        });

    }


    private void clickToGetImage() {

    }





}
