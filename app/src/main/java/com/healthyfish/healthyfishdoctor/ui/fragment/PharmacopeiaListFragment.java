package com.healthyfish.healthyfishdoctor.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeyGetReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeyGetResp;
import com.healthyfish.healthyfishdoctor.POJO.BeanListReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanPharmacopeia;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.PharmacopeiaExplvAdapter;
import com.healthyfish.healthyfishdoctor.ui.activity.pharmacopeia.ListOfCertainPharmacopoeia;
import com.healthyfish.healthyfishdoctor.utils.OkHttpUtils;
import com.healthyfish.healthyfishdoctor.utils.RetrofitManagerUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class PharmacopeiaListFragment extends Fragment {
    @BindView(R.id.explv_pharmacopia)
    ExpandableListView explvPharmacopia;
    Unbinder unbinder;
    private List<BeanPharmacopeia> list = new ArrayList<>();
    private List<String> listPresCategeryKey = new ArrayList<>();
    private List<String> listPresCategeryName = new ArrayList<>();
    private List<String> listCertainPresKey = new ArrayList<>();
    private List<String> listCertainPresDescibe = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pharmacopeia_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        //getPrescriptionCategeryKey();
        getCertainPrescriptionKey();
        initData();
        return view;
    }



//获取方剂种类key,再根据种类获取value（中文名）
    private void getPrescriptionCategeryKey() {
        listPresCategeryKey.clear();
        BeanListReq beanListReq = new BeanListReq();
        beanListReq.setPrefix("feb_prec_");
        beanListReq.setFrom(0);
        beanListReq.setNum(-1);
        beanListReq.setTo(-1);
        RetrofitManagerUtils.getInstance(getActivity(),null).getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanListReq), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.i("药典测试","");
                if (listPresCategeryKey.size()>0){
                    listPresCategeryName.clear();
                    final BeanBaseKeyGetReq beanBaseKeyGetReq = new BeanBaseKeyGetReq();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = listPresCategeryKey.size() - 1; i >= 0; i--) {
                                keyGetPrescriptionCategeryName(beanBaseKeyGetReq, listPresCategeryKey.get(i));
                            }
                        }
                    }).start();
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.i("药典测试","");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                String str = null;
                try {
                    str = responseBody.string();
                    Log.i("药典测试","类型key"+str);
                    listPresCategeryKey = JSONArray.parseObject(str, List.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    private void keyGetPrescriptionCategeryName(BeanBaseKeyGetReq beanBaseKeyGetReq,String key) {
        beanBaseKeyGetReq.setKey(key);
        RetrofitManagerUtils.getInstance(getActivity(),null).getPharmacopoeiaByRetrofit(OkHttpUtils.getRequestBody(beanBaseKeyGetReq), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.i("药典测试","");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("药典测试","");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                String str = null;
                try {
                    str = responseBody.string();
                    if (str!=null) {
                        BeanBaseKeyGetResp beanBaseKeyGetResp = JSON.parseObject(str,BeanBaseKeyGetResp.class);
                        listPresCategeryName.add(beanBaseKeyGetResp.getValue());
                        Log.i("药典测试","类型key获取的中文名字:"+beanBaseKeyGetResp.getValue());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //---------------------------------------------------------------------------------------------------------------


    //获取具体某类方剂key,再根据种类获取value（中文名）
    private void getCertainPrescriptionKey() {
        listCertainPresKey.clear();
        BeanListReq beanListReq = new BeanListReq();
        beanListReq.setPrefix("feb_pres_");
        beanListReq.setFrom(0);
        beanListReq.setNum(-1);
        beanListReq.setTo(-1);
        RetrofitManagerUtils.getInstance(getActivity(),null).getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanListReq), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.i("药典测试","");
                if (listCertainPresKey.size()>0){
                    listPresCategeryName.clear();
                    final BeanBaseKeyGetReq beanBaseKeyGetReq = new BeanBaseKeyGetReq();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = listCertainPresKey.size()-1 ; i>=0;i--){
                                keyGetCertainPrescriptionDescribe(beanBaseKeyGetReq,listCertainPresKey.get(i));
                            }
                        }
                    }).start();

                }
            }

            @Override
            public void onError(Throwable e) {
                Log.i("药典测试","");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                String str = null;
                try {
                    str = responseBody.string();
                    Log.i("药典测试","key"+str);
                    listCertainPresKey = JSONArray.parseObject(str, List.class);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }




    private void keyGetCertainPrescriptionDescribe(BeanBaseKeyGetReq beanBaseKeyGetReq,String key) {
        beanBaseKeyGetReq.setKey(key);
        RetrofitManagerUtils.getInstance(getActivity(),null).getPharmacopoeiaByRetrofit(OkHttpUtils.getRequestBody(beanBaseKeyGetReq), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {
                Log.i("药典测试","");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("药典测试","");
            }

            @Override
            public void onNext(ResponseBody responseBody) {
                String str = null;
                try {
                    str = responseBody.string();
                    if (str!=null) {
                        BeanBaseKeyGetResp beanBaseKeyGetResp = JSON.parseObject(str,BeanBaseKeyGetResp.class);
                        listPresCategeryName.add(beanBaseKeyGetResp.getValue());
                        Log.i("药典测试","描述:"+beanBaseKeyGetResp.getValue());
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }








    private void initData() {

        List<String>  chils1 = new ArrayList<>();
        chils1.add("抗疟原虫");
        chils1.add("抗蠕虫");
        chils1.add("抗阿米巴及滴虫");
        BeanPharmacopeia b = new BeanPharmacopeia("抗感染",chils1);
        List<String>  chils2 = new ArrayList<>();
        chils2.add("抗疟原虫2");
        chils2.add("抗蠕虫2");
        chils2.add("抗阿米巴及滴虫2");
        BeanPharmacopeia b2 = new BeanPharmacopeia("神经系统",chils2);
        List<String>  chils3 = new ArrayList<>();
        chils3.add("抗疟原虫3");
        chils3.add("抗蠕虫3");
        chils3.add("抗阿米巴及滴虫3");
        BeanPharmacopeia b3 = new BeanPharmacopeia("神经系统",chils3);
        list.add(b);
        list.add(b2);
        list.add(b3);
        PharmacopeiaExplvAdapter adapter = new PharmacopeiaExplvAdapter(getActivity(),list);
        explvPharmacopia.setAdapter(adapter);
//        int intgroupCount = explvPharmacopia.getCount();
//        for (int i=0; i<intgroupCount; i++)
//        {
//            explvPharmacopia.expandGroup(i);
//        };


        explvPharmacopia.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0, count = explvPharmacopia.getExpandableListAdapter().getGroupCount(); i < count; i++) {
                    if (groupPosition != i) {// 关闭其他分组
                        explvPharmacopia.collapseGroup(i);
                    }
                }
            }
        });
        explvPharmacopia.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //Toast.makeText(getActivity(),list.get(groupPosition).getList().get(childPosition),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ListOfCertainPharmacopoeia.class);
                startActivity(intent);
                return true;
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
