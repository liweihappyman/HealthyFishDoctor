package com.healthyfish.healthyfishdoctor.ui.activity.medical_record;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeyGetReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeyGetResp;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeyRemReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanCourseOfDisease;
import com.healthyfish.healthyfishdoctor.POJO.BeanMedRec;
import com.healthyfish.healthyfishdoctor.POJO.BeanMedRecUser;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserListReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserLoginReq;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.MedRecLvAdapter;
import com.healthyfish.healthyfishdoctor.constant.constants;
import com.healthyfish.healthyfishdoctor.utils.ComparatorDate;
import com.healthyfish.healthyfishdoctor.utils.MySharedPrefUtil;
import com.healthyfish.healthyfishdoctor.utils.OkHttpUtils;
import com.healthyfish.healthyfishdoctor.utils.RetrofitManagerUtils;
import com.zhy.autolayout.AutoLinearLayout;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Subscriber;

import static com.healthyfish.healthyfishdoctor.ui.activity.medical_record.NewMedRec.ALL_MED_REC_RESULT;


/**
 * 描述：电子病历
 * 作者：WKJ on 2017/6/30.
 * 邮箱：
 * 编辑：WKJ
 */


public class AllMedRec extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    public static final int TO_NEW_MED_REC = 38;//进入NewMedRec页面的请求标志
    private List<String> nullValueKey = new ArrayList<>();//存放value值为空的key；
    boolean hasNullValueKey = false;//标志空值key
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.med_rec_all)
    ListView medRecAll;
    @BindView(R.id.new_med_rec)
    AutoLinearLayout newMedRec;
    private boolean hasNewData = false;
    private List<BeanMedRec> listMecRec = new ArrayList<>();
    private int size;
    BeanMedRecUser beanMedRecUser = new BeanMedRecUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_rec_all);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        //toolbar.setOverflowIcon(getResources().getDrawable(R.drawable.three_points));
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_icon);
        }
        newMedRec.setOnClickListener(this);
        medRecAll.setOnItemClickListener(this);
        init();//先获取数据库的数据初始化列表
        reqForNetworkData();//然后加载网络数据更新列表
    }
    /**
     * 思路:先加载本地数据库的内容，异步获取网络的数据，通过对比key，如果没有则添加到本地数据库，最后更新列表
     */
    private void init() {
        listMecRec.clear();
        beanMedRecUser = DataSupport.find(BeanMedRecUser.class,constants.MED_REC_USER_ID,true);
        listMecRec = beanMedRecUser.getMedRecList();
        if (listMecRec.size() == 0) {
            initNullLV();
        }
        if (listMecRec.size() > 0) {
            //将日期按时间先后排序
            ComparatorDate c = new ComparatorDate();
            Collections.sort(listMecRec, c);
            //遍历出日期，格式为：       2017年10月
            List<String> listDate = new ArrayList<>();
            for (int i = 0; i < listMecRec.size(); i++) {
                String date = listMecRec.get(i).getClinicalTime();
                date = date.substring(0, date.indexOf("月") + 1);
                listDate.add(date);
            }
            MedRecLvAdapter medRecLvAdapter = new MedRecLvAdapter(this, listMecRec, listDate);
            medRecAll.setAdapter(medRecLvAdapter);
        }
    }


    /**
     * 访问网络数据
     */
    private void reqForNetworkData() {
        final List<String> keysOutDB = new ArrayList<>();//存放数据库没有的key
        //String userStr = MySharedPrefUtil.getValue("_user");
        //BeanUserLoginReq beanUserLogin = JSON.parseObject(userStr, BeanUserLoginReq.class);
        StringBuilder prefix = new StringBuilder("medRec_18576011826");
        //prefix.append(beanUserLogin.getMobileNo());//获取当前用户的手机号

        BeanUserListReq beanUserListReq = new BeanUserListReq();
        beanUserListReq.setPrefix(prefix.toString());
        beanUserListReq.setFrom(0);
        beanUserListReq.setNum(-1);
        beanUserListReq.setTo(-1);
//        BeanUserListValueReq userListValueReq = new BeanUserListValueReq();
//        userListValueReq.setPrefix(prefix.toString());
//        userListValueReq.setFrom(0);
//        userListValueReq.setNum(-1);
//        userListValueReq.setTo(-1);
        RetrofitManagerUtils.getInstance(this, null).getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanUserListReq), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(AllMedRec.this, "出错啦，请检查网络环境"+e.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String str = responseBody.string();
                    Log.i("所有病历", "数据" + str);
                    if (!TextUtils.isEmpty(str)) {
                        List<String> keys = JSONArray.parseObject(str, List.class);
                        if (keys.size() > 0) {
                            for (final String key : keys) {
                                //Log.i("medReckey", "key" + key);
                                if (DataSupport.select("key").where("key = ? ", key).find(BeanMedRec.class).isEmpty()) {
                                    keysOutDB.add(key);
                                    hasNewData = true;
                                }
                            }
                            if (hasNewData) {//如果 有新数据则通知更新列表
                                new Thread() {
                                    @Override
                                    public void run() {
                                        keyGet(keysOutDB);
                                    }
                                }.start();
                            }else {
                                Toast.makeText(AllMedRec.this,"已经是最新数据了",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    /**
     * ----------------------------------------------------------------------------------------------------------------------
     * 删除网络数据空值key，造成空值key的原因还不清楚，出现过
     */
    private void networkReqDelMedRec(String key) {
        //删除多个用
//        String userStr = MySharedPrefUtil.getValue("_user");
//        BeanUserLoginReq beanUserLogin = JSON.parseObject(userStr, BeanUserLoginReq.class);
//
//        final BeanBaseKeysRemReq beanBaseKeysRemReq = new BeanBaseKeysRemReq();
//        StringBuilder prefix = new StringBuilder("medRec_");
//        prefix.append(beanUserLogin.getMobileNo());//获取当前用户的手机号
//        //Log.i("电子病历","prefix:"+prefix.toString());
//        beanBaseKeysRemReq.setPrefix(prefix.toString());
//        beanBaseKeysRemReq.getKeyList().add(medRec.getKey());
//        Log.i("keyiiiii", "addkey" + beanBaseKeysRemReq.getKeyList().get(0));

        BeanBaseKeyRemReq baseKeyRemReq = new BeanBaseKeyRemReq();//删除单个
        baseKeyRemReq.setKey(key);
        RetrofitManagerUtils.getInstance(AllMedRec.this, null).getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(baseKeyRemReq), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(ResponseBody responseBody) {

            }
        });

    }

    /**
     * 根据返回的key逐个获取数据r
     */

    private void keyGet(final List<String> keysOutDB) {
        for (final String key : keysOutDB) {
            size++;
            final BeanBaseKeyGetReq beanBaseKeyGetReq = new BeanBaseKeyGetReq();
            beanBaseKeyGetReq.setKey(key);
            RetrofitManagerUtils.getInstance(AllMedRec.this, null).getMedRecByRetrofit(OkHttpUtils.getRequestBody(beanBaseKeyGetReq), new Subscriber<ResponseBody>() {
                @Override
                public void onCompleted() {
                    if (size == keysOutDB.size()) {
                        size = 0;
                        handler.sendEmptyMessage(0x11);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(AllMedRec.this, "出错啦", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onNext(ResponseBody responseBody) {
                    try {
                        String rspv = responseBody.string();
                        if (!TextUtils.isEmpty(rspv)) {
                            BeanBaseKeyGetResp object = JSON.parseObject(rspv, BeanBaseKeyGetResp.class);
                            if (object.getValue() != null) {
                                BeanMedRec beanMedRec = JSON.parseObject(object.getValue(), BeanMedRec.class);
                                beanMedRec.setKey(key);
                                beanMedRec.setBeanMedRecUser(beanMedRecUser);
                                beanMedRec.save();
                                List<BeanCourseOfDisease> courseOfDiseaseList = beanMedRec.getListCourseOfDisease();
                                for (BeanCourseOfDisease courseOfDisease : courseOfDiseaseList) {
                                    courseOfDisease.setBeanMedRec(beanMedRec);
                                    courseOfDisease.save();
                                }
                            } else {
                                nullValueKey.add(key);
                                hasNullValueKey = true;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
    /**
     * 初始化空的ListView,提示列表为空
     */

    private void initNullLV() {
        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setVisibility(View.GONE);
        ((ViewGroup) medRecAll.getParent()).addView(imageView);
        medRecAll.setEmptyView(imageView);
        imageView.setImageResource(R.mipmap.personal_center);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.share:

                break;
            case R.id.del:

                break;
            case R.id.test:
                reqForNetworkData();//获取网络数据
                break;
        }
        return true;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.med_rec, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.new_med_rec://新建病历
                constants.POSITION_MED_REC = -1;
                Intent intent = new Intent(this, NewMedRec.class);
                startActivityForResult(intent, TO_NEW_MED_REC);
                startActivity(intent);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        constants.POSITION_MED_REC = position;
        Intent intent = new Intent(AllMedRec.this, NewMedRec.class);
        //将选中的病历的id穿到NewMedRec活动
        intent.putExtra("id", listMecRec.get(position).getId());
        startActivityForResult(intent, TO_NEW_MED_REC);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case ALL_MED_REC_RESULT:
                listMecRec.clear();
                init();
                break;

        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x11) {
                init();//更新列表
                if (hasNullValueKey){
                    for (String key:nullValueKey)
                        networkReqDelMedRec(key);//删除空值key
                }
            }
        }
    };
}
