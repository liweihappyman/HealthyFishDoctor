package com.healthyfish.healthyfishdoctor.ui.activity.pharmacopeia;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.healthyfish.healthyfishdoctor.POJO.BeanBaseKeyGetReq;
import com.healthyfish.healthyfishdoctor.POJO.BeanListReq;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;
import com.healthyfish.healthyfishdoctor.utils.OkHttpUtils;
import com.healthyfish.healthyfishdoctor.utils.RetrofitManagerUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * 描述：药物详情
 * 作者：WKJ on 2017/7/15.
 * 邮箱：
 * 编辑：WKJ
 */
public class DrugDetail extends BaseActivity {

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.name_of_drug)
    TextView nameOfDrug;
    @BindView(R.id.indications)
    TextView indications;
    @BindView(R.id.specifications)
    TextView specifications;
    @BindView(R.id.usage_and_dosage)
    TextView usageAndDosage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drug_detail);
        ButterKnife.bind(this);
        initToolBar(toolbar,toolbarTitle,"具体药名");
        testP();
    }

    private void testP() {
        BeanBaseKeyGetReq beanBaseKeyGetReq = new BeanBaseKeyGetReq();
        beanBaseKeyGetReq.setKey("feb_prec_07");


        BeanListReq beanListReq = new BeanListReq();
        beanListReq.setPrefix("feb_prec_");
        beanListReq.setFrom(0);
        beanListReq.setNum(-1);
        beanListReq.setTo(-1);
        RetrofitManagerUtils.getInstance(this,null).getHealthyInfoByRetrofit(OkHttpUtils.getRequestBody(beanBaseKeyGetReq), new Subscriber<ResponseBody>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ResponseBody responseBody) {
                String str = null;
                try {
                    str = responseBody.string();
                    Log.i("药典测试",""+str);


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });




    }


}
