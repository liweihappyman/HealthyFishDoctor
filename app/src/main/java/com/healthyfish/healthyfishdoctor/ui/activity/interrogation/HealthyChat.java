package com.healthyfish.healthyfishdoctor.ui.activity.interrogation;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.healthyfish.healthyfishdoctor.MainActivity;
import com.healthyfish.healthyfishdoctor.MyApplication;
import com.healthyfish.healthyfishdoctor.POJO.AppFuncBean;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserChatInfo;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserLoginReq;
import com.healthyfish.healthyfishdoctor.POJO.ImMsgBean;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.healthy_chat.AppFuncAdapter;
import com.healthyfish.healthyfishdoctor.adapter.healthy_chat.ChattingListAdapter;
import com.healthyfish.healthyfishdoctor.broadcast.NetWorkChangeBroadcastReceiver;
import com.healthyfish.healthyfishdoctor.constant.Constants;
import com.healthyfish.healthyfishdoctor.eventbus.WeChatImageMessage;
import com.healthyfish.healthyfishdoctor.eventbus.WeChatReceiveMsg;
import com.healthyfish.healthyfishdoctor.service.WeChatUploadImage;
import com.healthyfish.healthyfishdoctor.ui.activity.BaseActivity;
import com.healthyfish.healthyfishdoctor.ui.widget.SessionChatKeyboardBase;
import com.healthyfish.healthyfishdoctor.utils.AutoLogin;
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

public class HealthyChat extends BaseActivity implements FuncLayout.OnFuncKeyBoardListener, AutoHeightLayout.OnMaxParentHeightChangeListener {
    private static final int REQUEST_CAMERA_CODE = 12;
    private static final int REQUEST_PREVIEW_CODE = 13;
    // 发送延时显示失败（秒数）
    private static final int AUTO_SET_FAILURE = 10;

    private static final String MQTT_SEND_IMG_TYPE = "i";
    public static final String MQTT_SEND_TXT_TYPE = "t";

    private Toolbar toolbar;
    private ListView lvChat;
    TextView tvTitle;
    private SessionChatKeyboardBase sessionChat;

    // 聊天列表
    List<ImMsgBean> beanList;
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
    // TODO: 2017/8/8 用户聊天信息
    // 获取用户聊天信息
    private BeanUserChatInfo beanUserChatInfo;
    // 获取对方手机信息
    private String topic;
    // 本机登录者
    private String sender;
    // 客户头像
    private String peerPortrait;
    // 服务类型
    private String serviceType;
    //private String imgUrl = null;

    // 网络状态广播
    private BroadcastReceiver netReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthy_chat);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lvChat = (ListView) findViewById(R.id.lv_chat);
        sessionChat = (SessionChatKeyboardBase) findViewById(R.id.session_chat);

        initChatInfo();

        initView();
        // 注册EventBus
        EventBus.getDefault().register(this);
    }

    // 初始化聊天信息
    private void initChatInfo() {
        beanUserChatInfo = (BeanUserChatInfo) getIntent().getSerializableExtra("BeanUserChatInfo");
        beanUserLoginReq = JSON.parseObject(MySharedPrefUtil.getValue("user"), BeanUserLoginReq.class);
        topic = "u" + beanUserChatInfo.getPhone();
        //topic = "d" + "13977211042";
        sender = "d" + beanUserLoginReq.getMobileNo();
        //medRECKey = "dmr" + beanDoctorChatInfo.getPhone() + beanUserLoginReq.getMobileNo();
        peerPortrait = beanUserChatInfo.getImgUrl();
        serviceType = beanUserChatInfo.getServiceType();

/*        topic = "u18077207818";
        sender = "d18977280163";*/
    }

    private void initView() {
        // 初始化toolBar
        initToolbar();
        // 初始化表情键盘
        initEmoticonsKeyBoardBar();
        // 初始化收到的信息列表
        initListView();
        // 初始化下拉刷新
        initSwipeToRefresh();
        // 初始化接收广播
        initReceiver();
    }

    private void initToolbar() {
        // 医生姓名
        toolbar.setTitle(beanUserChatInfo.getName());
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_icon);
        }
    }

    /**
     * 网络状态广播监听
     */
    private void initReceiver() {
        netReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
//                判断当前网络状态
                String user = MySharedPrefUtil.getValue("user");
                String sid = MySharedPrefUtil.getValue("sid");
                switch (intent.getIntExtra(NetWorkChangeBroadcastReceiver.NET_TYPE, 0)) {
                    case 0:
                        Toast.makeText(HealthyChat.this, "网络连接失败", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(HealthyChat.this, "wifi连接", Toast.LENGTH_SHORT).show();
                        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(sid)) {
                            AutoLogin.autoLogin();
                            MqttUtil.startAsync();
                        }
                        break;
                    case 2:
                        Toast.makeText(HealthyChat.this, "数据流量连接", Toast.LENGTH_SHORT).show();
                        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(sid)) {
                            AutoLogin.autoLogin();
                            MqttUtil.startAsync();
                        }
                        break;
                    default:
                        break;

                }
            }
        };
        IntentFilter filter = new IntentFilter(NetWorkChangeBroadcastReceiver.NET_CHANGE);
        registerReceiver(netReceiver, filter);
    }

    /**
     * 返回按钮的监听
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(HealthyChat.this, MainActivity.class));
                break;
            default:
                break;
        }
        return true;
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
                OnSendBtnClick(sessionChat.getEtChat().getText().toString(), MQTT_SEND_TXT_TYPE);
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
                if (actionType == Constants.EMOTICON_CLICK_BIGIMAGE) {
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
        beanList = DataSupport.where("topic = ? and name = ? or topic = ? and name = ?", topic, sender, sender, topic).find(ImMsgBean.class);
        for (ImMsgBean b : beanList) {
            b.setPortrait(peerPortrait);
            ContentValues values = new ContentValues();
            values.put("isNewMsg", "false");
            values.put("portrait", peerPortrait);
            b.updateAll(ImMsgBean.class, values, "time = ?", b.getTime() + "");
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

    // 点击发送按钮发送消息
    private void OnSendBtnClick(String msg, String mqttMsgType) {
        if (!TextUtils.isEmpty(msg)) {
            ImMsgBean bean = new ImMsgBean();
            bean.setName(sender);
            bean.setSender(true);// 是否是发送者
            bean.setTime(DateTimeUtil.getLongMs());// 发送时间
            bean.setLoading(true);// loading状态
            bean.setSuccess(true);// 开始去掉发送失败标识
            bean.setServiceType(serviceType);
            bean.setNewMsg(true);
            bean.setContent(msg);
            // 开启线程，AUTO_SET_FAILURE秒钟之后自动设置发送失败
            autoFailureAfterSeconds(bean);
            bean.setTopic(topic);
            switch (mqttMsgType) {
                case MQTT_SEND_TXT_TYPE:
                    bean.setType(mqttMsgType);// 类型：文字
                    // UI添加消息
                    chattingListAdapter.addData(bean, true, false);
                    // MQTT发送数据
                    MqttUtil.sendTxt(bean);
                    break;
                case MQTT_SEND_IMG_TYPE:
                    bean.setType(mqttMsgType);// 类型：图片
                    // UI添加消息
                    chattingListAdapter.addData(bean, true, false);
                    // 开启服务默默上传图片
                    Intent startUploadImage = new Intent(this, WeChatUploadImage.class);
                    startUploadImage.putExtra("WeChatImage", bean);
                    startService(startUploadImage);
                    break;
                default:
                    break;
            }

            // 跳转到底部
            scrollToBottom();
        }
    }

    // 更新发送成功或者失败状态
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateSendingStatus(ImMsgBean msg) {
        // 刷新列表状态
        chattingListAdapter = new ChattingListAdapter(this);
        beanList = DataSupport.where("topic = ? and name = ? or topic = ? and name = ?", topic, sender, sender, topic).find(ImMsgBean.class);
        /*for (ImMsgBean b : beanList) {
            b.setPortrait(doctorPortrait);
        }*/
        chattingListAdapter.addData(beanList);
        lvChat.setAdapter(chattingListAdapter);
    }

    // 接收到消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveMsg(WeChatReceiveMsg msg) {
        String time = msg.getTime() + "";
        ImMsgBean newMsg = DataSupport.where("time = ?", time).find(ImMsgBean.class).get(0);
        newMsg.setPortrait(peerPortrait);
        // 刷新列表状态
        chattingListAdapter.addData(newMsg, true, false);
        chattingListAdapter.notifyDataSetChanged();
        lvChat.setAdapter(chattingListAdapter);
    }

    // 返回已经上传图片在服务器的URL
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUploadImgUrl(final WeChatImageMessage msg) {
        String time = msg.getTime() + "";
        ImMsgBean bean = DataSupport.where("time = ?", time).find(ImMsgBean.class).get(0);
        // MQTT发送数据
        MqttUtil.sendImg(bean);
    }

    // 开启线程，几秒钟之后自动设置发送失败
    private void autoFailureAfterSeconds(final ImMsgBean bean) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    sleep(AUTO_SET_FAILURE * 1000);
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

    // 发送大图片
    private void OnSendImage(String iconUri) {
        if (!TextUtils.isEmpty(iconUri)) {
            OnSendBtnClick("[img]" + iconUri, MQTT_SEND_IMG_TYPE);//给大图片加标记
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
                            clickToLoadImage();
                            break;
                        case 2:
                            clickToTakePhoto();
                            break;
                        case 3:
                            Toast.makeText(HealthyChat.this, "程序员正在加班实现", Toast.LENGTH_SHORT).show();
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
            OnSendBtnClick(msg, MQTT_SEND_IMG_TYPE);
        }
        imagePaths.clear();
    }

    // 上传照片
    private void clickToLoadImage() {
        PhotoPickerIntent intent = new PhotoPickerIntent(HealthyChat.this);
        intent.setSelectModel(SelectModel.MULTI); //选择单张还是多张
        intent.setShowCarema(false); // 是否显示拍照
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
        unregisterReceiver(netReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initMqtt();
    }

    private void initMqtt() {
        String user = com.healthyfish.healthyfishdoctor.utils.MySharedPrefUtil.getValue("user");
        String sid = com.healthyfish.healthyfishdoctor.utils.MySharedPrefUtil.getValue("sid");
        if (!TextUtils.isEmpty(user) && !TextUtils.isEmpty(sid)) {
            AutoLogin.autoLogin();
            MqttUtil.startAsync();
        }
    }
}
