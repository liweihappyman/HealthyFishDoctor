package com.healthyfish.healthyfishdoctor.ui.activity.interrogation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.healthyfish.healthyfishdoctor.MyApplication;
import com.healthyfish.healthyfishdoctor.POJO.AppFuncBean;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserLoginReq;
import com.healthyfish.healthyfishdoctor.POJO.ImMsgBean;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.healthy_chat.AppFuncAdapter;
import com.healthyfish.healthyfishdoctor.adapter.healthy_chat.ChattingListAdapter;
import com.healthyfish.healthyfishdoctor.constant.constants;
import com.healthyfish.healthyfishdoctor.ui.widget.SessionChatKeyboardBase;
import com.healthyfish.healthyfishdoctor.utils.DateTimeUtil;
import com.healthyfish.healthyfishdoctor.utils.chat_utils.SimpleCommonUtils;
import com.healthyfish.healthyfishdoctor.utils.mqtt_utils.MqttUtil;
import com.healthyfish.healthyfishdoctor.utils.mqtt_utils.MySharedPrefUtil;
import com.sj.emoji.EmojiBean;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sj.keyboard.data.EmoticonEntity;
import sj.keyboard.interfaces.EmoticonClickListener;
import sj.keyboard.widget.AutoHeightLayout;
import sj.keyboard.widget.FuncLayout;

import static java.lang.Thread.sleep;

public class HealthyChat extends AppCompatActivity implements FuncLayout.OnFuncKeyBoardListener, AutoHeightLayout.OnMaxParentHeightChangeListener {
    private static final int REQUEST_CAMERA_CODE = 12;
    private static final int REQUEST_PREVIEW_CODE = 13;

    private Toolbar toolbar;
    private ListView lvChat;
    private SessionChatKeyboardBase sessionChat;

    // 聊天列表适配器
    private ChattingListAdapter chattingListAdapter;
    // photopicker的照相机参数
    private ImageCaptureManager captureManager;
    // 选择多张照片的路径
    private ArrayList<String> imagePaths;
    // 拍照照片的路径
    private String imagePath;
    // 获取全局登录信息
    private BeanUserLoginReq beanUserLoginReq;
    // 获取医生手机信息
    private String topic;
    // 本机登录者
    private String sender;
    // 医生头像
    private String doctorPortrait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy_chat);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvChat = (ListView) findViewById(R.id.lv_chat);
        sessionChat = (SessionChatKeyboardBase) findViewById(R.id.session_chat);

        //
//        BeanDoctorChatInfo beanDoctorChatInfo = (BeanDoctorChatInfo) getIntent().getSerializableExtra("BeanDoctorChatInfo");
//
//        // 医生姓名
//        toolbar.setTitle(beanDoctorChatInfo.getName());
//        setSupportActionBar(toolbar);
//
//        beanUserLoginReq = JSON.parseObject(MySharedPrefUtil.getValue("user"), BeanUserLoginReq.class);
//        //topic = "d" + beanDoctorChatInfo.getPhone();
//        topic = "u" + "18077207818";
//        sender = "d" + beanUserLoginReq.getMobileNo();
//        //medRECKey = "dmr" + beanDoctorChatInfo.getPhone() + beanUserLoginReq.getMobileNo();
//        doctorPortrait = beanDoctorChatInfo.getImgUrl();

        initView();
        // 注册EventBus
        EventBus.getDefault().register(this);
    }

    private void initView() {
        // 初始化表情键盘
        initEmoticonsKeyBoardBar();
        // 初始化收到的信息列表
        initListView();
        // 初始化下拉刷新
        initSwipeToRefresh();
    }

    /**
     * @description 初始化表情键盘
     * @author
     */
    private void initEmoticonsKeyBoardBar() {
        SimpleCommonUtils.initEmoticonsEditText(sessionChat.getEtChat());

        sessionChat.setAdapter(SimpleCommonUtils.getCommonAdapter(this, emoticonClickListener));
        sessionChat.addOnFuncKeyBoardListener(this);
        sessionChat.addFuncView(new SimpleAppsGridView(this));
        sessionChat.setOnMaxParentHeightChangeListener(this);


        // 点击发送按钮事件
        sessionChat.getBtnSend().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OnSendBtnClick(sessionChat.getEtChat().getText().toString());
                sessionChat.getEtChat().setText("");
            }
        });


        // 点击语音按钮事件
        sessionChat.getBtnVoice().setLongClickable(true);
/*        sessionChat.getBtnVoice().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                ToastUtil.showToast("功能未完善");
                // Log.e("Wayne", "Voice click");
                return false;
            }
        });*/
        sessionChat.getBtnVoice().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        sessionChat.getBtnVoice().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return false;
            }
        });
    }

    /**
     * @description 表情点击事件, 添加到sessionChat.setAdapter(SimpleCommonUtils
     * .getCommonAdapter(this, emoticonClickListener));
     * @author
     */
    EmoticonClickListener emoticonClickListener = new EmoticonClickListener() {
        @Override
        public void onEmoticonClick(Object o, int actionType, boolean isDelBtn) {

            if (isDelBtn) {
                SimpleCommonUtils.delClick(sessionChat.getEtChat());
            } else {
                if (o == null) {
                    return;
                }
                if (actionType == constants.EMOTICON_CLICK_BIGIMAGE) {
                    if (o instanceof EmoticonEntity) {
                        OnSendImage(((EmoticonEntity) o).getIconUri());
                    }
                } else {
                    String content = null;
                    if (o instanceof EmojiBean) {
                        content = ((EmojiBean) o).emoji;
                    } else if (o instanceof EmoticonEntity) {
                        content = ((EmoticonEntity) o).getContent();
                    }

                    if (TextUtils.isEmpty(content)) {
                        return;
                    }
                    int index = sessionChat.getEtChat().getSelectionStart();
                    Editable editable = sessionChat.getEtChat().getText();
                    editable.insert(index, content);
                }
            }
        }
    };

    // 初始化聊天队列 通过网络访问获取
    private void initListView() {
        chattingListAdapter = new ChattingListAdapter(this);
        // 从总的列表中选出一一对应的聊天关系，本机用户与选中的医生
        List<ImMsgBean> beanList = DataSupport.where("topic = ? and name = ? or topic = ? and name = ?", topic, sender, sender, topic).find(ImMsgBean.class);

        for (ImMsgBean b : beanList) {
            b.setPortrait(doctorPortrait);
        }

        chattingListAdapter.addData(beanList);
        lvChat.setAdapter(chattingListAdapter);
        lvChat.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_FLING:
                        break;
                    case SCROLL_STATE_IDLE:
                        break;
                    case SCROLL_STATE_TOUCH_SCROLL:
                        sessionChat.reset();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });
    }

    /**
     * @description 下拉刷新事件
     * @author
     */
    private void initSwipeToRefresh() {

    }

    // 更新发送成功或者失败状态
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateSendingStatus(ImMsgBean bean) {

        // 刷新列表状态
        chattingListAdapter = new ChattingListAdapter(this);
        List<ImMsgBean> beanList = DataSupport.where("topic = ? and name = ? or topic = ? and name = ?", topic, sender, sender, topic).find(ImMsgBean.class);

        for (ImMsgBean b : beanList) {
            b.setPortrait(doctorPortrait);
        }

        chattingListAdapter.addData(beanList);
        lvChat.setAdapter(chattingListAdapter);
    }

    // 点击发送按钮发送消息
    private void OnSendBtnClick(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            ImMsgBean bean = new ImMsgBean();
            bean.setName(sender);
            bean.setSender(true);// 是否是发送者
            bean.setType("t");// 类型：文字
            bean.setTime(DateTimeUtil.getLongMs());// 发送时间
            bean.setLoading(true);// loading状态
            bean.setSuccess(true);// 开始去掉发送失败标识
            bean.setContent(msg);

            // 开启线程，几秒钟之后自动设置发送失败
            autoFailureAfterSeconds(bean);

            bean.setTopic(topic);
            // UI添加消息
            chattingListAdapter.addData(bean, true, false);
            // MQTT发送数据
            MqttUtil.sendTxt(bean);

            // 跳转到底部
            scrollToBottom();
        }
    }

    // 开启线程，几秒钟之后自动设置发送失败
    private void autoFailureAfterSeconds(final ImMsgBean bean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(10 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                List<ImMsgBean> currentBean = DataSupport.where("time = ?", bean.getTime() + "").find(ImMsgBean.class);
                // 如果此时状态为loading状态，UI改为失败状态
                if (currentBean.get(0).isLoading()) {
                    bean.setToDefault("isSuccess");
                    bean.setToDefault("isLoading");
                    bean.updateAll("time = ?", bean.getTime() + "");
                    EventBus.getDefault().post(new ImMsgBean(bean.getTime()));
                }
            }
        }).start();
    }

    // 发送图标
    private void OnSendImage(String iconUri) {
        if (!TextUtils.isEmpty(iconUri)) {
            OnSendBtnClick("[img]" + iconUri);//给大图片加标记
        }
    }

    // 添加信息后滑动到底部
    private void scrollToBottom() {
        lvChat.post(new Runnable() {
            @Override
            public void run() {
                lvChat.setSelection(lvChat.getBottom());
            }
        });
    }


    @Override
    public void onMaxParentHeightChange(int i) {
        scrollToBottom();
    }

    @Override
    public void OnFuncPop(int i) {
        scrollToBottom();
    }

    @Override
    public void OnFuncClose() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sessionChat.reset();
    }

    // 处理返回的照片方式
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    imagePaths = new ArrayList<>();
                    imagePaths.addAll(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                    refreshAdpater(imagePaths);
                    break;
                case REQUEST_PREVIEW_CODE:
                    refreshAdpater(data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT));
                    break;
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
                    if (captureManager.getCurrentPhotoPath() != null) {
                        captureManager.galleryAddPic();
                        // 照片地址
                        imagePaths = new ArrayList<>();
                        imagePaths.add(captureManager.getCurrentPhotoPath());
                        refreshAdpater(imagePaths);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    // 初始化 更多功能 区域--包含上传相片，拍照，发送病历等按钮
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

        protected void init() {
            GridView gv_apps = (GridView) view.findViewById(R.id.gv_apps);
            final ArrayList<AppFuncBean> mAppFuncBeanList = new ArrayList<>();
            mAppFuncBeanList.add(new AppFuncBean(R.mipmap.icon_photo, "图片", 1));
            mAppFuncBeanList.add(new AppFuncBean(R.mipmap.icon_camera, "拍照", 2));
            mAppFuncBeanList.add(new AppFuncBean(R.mipmap.icon_send_medrec, "发送病历", 3));
/*            mAppFuncBeanList.add(new AppFuncBean(R.mipmap.icon_qzone, "空间", 4));
             mAppFuncBeanList.add(new AppFuncBean(R.mipmap.icon_contact, "联系人", 5));
            mAppFuncBeanList.add(new AppFuncBean(R.mipmap.icon_file, "文件", 6));
            mAppFuncBeanList.add(new AppFuncBean(R.mipmap.icon_loaction, "位置", 7));*/
            AppFuncAdapter adapter = new AppFuncAdapter(getContext(), mAppFuncBeanList);
            gv_apps.setAdapter(adapter);

            gv_apps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (mAppFuncBeanList.get(position).getId()) {
                        case 1:
                            // clickToLoadImage();
                            Toast.makeText(MyApplication.getContetxt(), "程序员正在加班实现", Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            //clickToTakePhoto();
                            Toast.makeText(MyApplication.getContetxt(), "程序员正在加班实现", Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            Toast.makeText(MyApplication.getContetxt(), "程序员正在加班实现", Toast.LENGTH_SHORT).show();
                        default:
                            break;
                    }
                }
            });
        }
    }

    // 刷新照片选择器
    private void refreshAdpater(ArrayList<String> paths) {
        // 处理返回照片地址
        if (imagePaths == null) {
            imagePaths = new ArrayList<>();
        }
        /*imagePaths.addAll(paths);*/

        for (int i = 0; i < imagePaths.size(); i++) {
            String msg = "[img]" + Uri.fromFile(new File(imagePaths.get(i)));
            OnSendBtnClick(msg);
        }
        imagePaths.clear();
    }

    // 上传照片
    private void clickToLoadImage() {
        PhotoPickerIntent intent = new PhotoPickerIntent(HealthyChat.this);
        intent.setSelectModel(SelectModel.MULTI); //选择单张还是多张
        intent.setShowCarema(true); // 是否显示拍照
        intent.setMaxTotal(9); // 最多选择照片数量，默认为6
        intent.setSelectedPaths(imagePaths); // 已选中的照片地址， 用于回显选中状态
        startActivityForResult(intent, REQUEST_CAMERA_CODE);
    }

    // 照相
    private void clickToTakePhoto() {
        try {
            if (captureManager == null) {
                captureManager = new ImageCaptureManager(HealthyChat.this);
            }
            Intent intent = captureManager.dispatchTakePictureIntent();
            startActivityForResult(intent, ImageCaptureManager.REQUEST_TAKE_PHOTO);
        } catch (IOException e) {
            Toast.makeText(HealthyChat.this, R.string.msg_no_camera, Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
