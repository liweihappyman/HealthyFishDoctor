package com.healthyfish.healthyfishdoctor.utils.mqtt_utils;

import android.os.Handler;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.healthyfish.healthyfishdoctor.MyApplication;
import com.healthyfish.healthyfishdoctor.POJO.BeanUserLoginReq;
import com.healthyfish.healthyfishdoctor.POJO.ImMsgBean;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.eventbus.WeChatReceiveMsg;
import com.healthyfish.healthyfishdoctor.utils.DateTimeUtil;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.healthyfish.healthyfishdoctor.utils.mqtt_utils.MqttUtil.userName;


/**
 * 描述：
 * 作者：Wayne on 2017/7/22 16:41
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 * <p>
 * <p>
 * 一、登录
 * 1. clientId填用户在App中注册的Id，例如手机号，再加上用户类型：u,d,s
 * 2. username填用户的名称/昵称
 * 3. password填用户从http头部中获得的sessionId
 * <p>
 * 二、主题
 * 1.系统主题，以$开头，主要用于系统界别的消息，包括新闻
 * 2.普通用户，以u开头，用于给普通用户发送消息
 * 3.群组，以g开头，用于群发消息
 * 4.群组的管理，以m开头，用于群组的申请加入、退出
 * 5.医生，以d开头
 * <p>
 * 三、消息
 * 消息格式为：
 * |uid_len|uid|msg
 * 其中，uid_len为1Byte，表示后面的uid的长度(0-255)
 * uid为该消息的发出者标识，对应于clientId，用于接收者显示消息的来源
 * msg为发出的消息，包括普通文本、图片、表情等，格式为
 * |type|data|
 * 其中，type表示消息的类型，1Byte
 * t:text
 * i:image
 * v:video
 * a:audio
 * m:电子病历medical-record
 * $:管理类消息：建立会话、管理会话
 * 表情嵌入在文本中，由<.../>包含，不作为单独的类型。
 * <p>
 * 例如，如下的消息
 * “t测试一下<smile/>这个表情”
 * 中间的<smile/>在显示时用笑脸表情替换
 * 如果</>包含的字符串不是默认的表情，则原样显示字符串
 * 若用户输入了<smile/>，则自动替换为<$smile/>【多插入一个$符号】
 * <p>
 * image的格式为
 * i|len|<src="...">|img_bytes
 * 其中，src用于指明该图的原始图片在服务器上的地址，len表明src的长度，最后的img_bytes是直接发给对方的图片缩略图
 * 例如，i27/demo/user/img/20170305.png988023...
 */

public class MqttUtil {
    public static final int MSG_WHAT_MQTT_BASE = 0;

    // 初始化MQTT连接与否标志位
    public static boolean connFlag = false;
    public static final String HOST = "tcp://219.159.248.209:1883";
    private static String userType;
    private static String localUser;
    private static String localTopic;
    private static int keepAliveInterval = 60;
    private static boolean keepAliveFlag = false;

    public static final byte FLAG_ACK = (byte) 0x80;
    public static final byte FLAG_NAK = (byte) 0x40;
    public static final byte MASK_MSG = (byte) 0x3f;
    public static final byte MASK_ACK = (byte) 0xc0;
    public static final byte MSG_SYS_CreateChannel = 1; //加好友
    public static final byte MSG_SYS_DropChannel = 2; //删除会话
    public static final byte MSG_USER_SYS_OFFLINE = 3; //用户下线
    public static final byte MSG_USER_SYS_ONLINE = 4; //用户下线

    // 获取登录账号和密码
    private static String userJson = MySharedPrefUtil.getValue("user");
    public static BeanUserLoginReq beanUserLoginReq = JSON.parseObject(userJson, BeanUserLoginReq.class);
    public static String userName = beanUserLoginReq.getMobileNo();
    public static String userPwd = beanUserLoginReq.getPwdSHA256();

    static Handler pingHandler = new Handler();
    static Runnable pingRunnable = new Runnable() {
        @Override
        public void run() {
            keepAliveFlag = false;
            if (connFlag) {
                sendPing();
                Log.e("MqttUtil", "sendPing - " + DateTimeUtil.getTime());
            } else {
                startAsync();
                Log.e("MqttUtil", "startAsync - " + DateTimeUtil.getTime());
            }
            pingHandler.postDelayed(this, keepAliveInterval * 1000);
        }
    };

    // 判断用户端口
    static {
        if ("doct".equalsIgnoreCase(MyApplication.getContetxt().getResources().getString(R.string.type))) {
            userType = "d"; //医生端
        } else {
            userType = "u";
        }
        localUser = userName;
        localTopic = userType + localUser;
        pingHandler.postDelayed(pingRunnable, keepAliveInterval * 1000);
    }

    private static MqttAsyncClient mqttAsyncClient;

    public static void startAsync() {
        if (!connFlag) {
            connect();
        }
//        listen();
    }

    // 连接服务器
    private static boolean connectingFlag = false;

    public static void connect() {
        if (connectingFlag)
            return;
        final String user = userName;
        if (user == null || "".equalsIgnoreCase(user)) {
            return;
        }
        final String pwd = userPwd;
        if (pwd == null || "".equalsIgnoreCase(pwd)) {
            return;
        }
        String name = MySharedPrefUtil.getValue("name");
        if (name == null || "".equalsIgnoreCase(name)) {
            name = user;
        }
        connectingFlag = true;
        // sid_397C5B4390424970D2DEDD490DFC2181
        String passwd = MySharedPrefUtil.getValue("sid").substring(4);
        // String passwd = "E7FF9D647A8FB76D0E0F00A1F48F9132";
        try {
            String clientId = "" + userType + user;
            Log.i("MqttUtil", "connect: uid=" + clientId);
            mqttAsyncClient = new MqttAsyncClient(HOST, userType + user, new MemoryPersistence());
            // MQTT的连接设置
            MqttConnectOptions options = new MqttConnectOptions();
            // 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
            options.setCleanSession(false);
            // 设置连接的用户名
            options.setUserName(user);
            // 设置连接的密码
            options.setPassword(passwd.toCharArray());
            // 设置超时时间 单位为秒
            options.setConnectionTimeout(10);
            // 设置会话心跳时间 单位为秒 服务器会每隔1.5*20秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
            options.setKeepAliveInterval(keepAliveInterval);
//            options.setWill();
            mqttAsyncClient.connect(options, null, new IMqttActionListener() {
                public void onSuccess(IMqttToken arg0) {
                    connectingFlag = false;
                    connFlag = true;
                    localUser = userName;
                    localTopic = userType + localUser;

                    Log.e("MQTT", "连接服务器成功.");
                    listen();

                    // 从返回的CONNACK消息中，查看session_present标志位
                    if (arg0.getSessionPresent()) {
                        Log.e("MQTT", "服务器仍保持连接");
                    } else {
                        Log.e("MQTT", "服务器未保持连接，需要重建");
                        subscribe(userType + user);
                        subscribe("$news");
                    }

                }

                public void onFailure(IMqttToken arg0, Throwable arg1) {
                    Log.e("MQTT", "连接服务器失败: " + arg1.getLocalizedMessage());
                    connectingFlag = false;
                    connFlag = false;
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }


    public static void sendPing() {
        try {
            mqttAsyncClient.checkPing(null, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
//                    Log.i("MqttUtil","sendPing - onSuccess");
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
//                    Log.i("MqttUtil","sendPing - onFailure");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // 发布主题
    public static void publish(String topic, String msg) {
        try {
            mqttAsyncClient.publish(topic, msg.getBytes(), 1, false, null, new IMqttActionListener() {

                public void onFailure(IMqttToken arg0, Throwable arg1) {
                    Log.e("MQTT", "消息返回失败");
                }

                public void onSuccess(IMqttToken arg0) {
                    Log.e("MQTT", "消息返回成功");
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // 订阅主题
    public static void subscribe(String topic) {
        try {
            mqttAsyncClient.subscribe(topic, 1, null, new IMqttActionListener() {
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.i("MQTT", "成功监听主题: " + asyncActionToken.getTopics().toString());
                }

                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    Log.i("MQTT", "监听失败");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendMsg(final ImMsgBean bean) throws JSONException {

        try {
            String localUser = userType + userName;
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            bs.write((byte) localUser.length());
            bs.write(localUser.getBytes());
            // 判断是否是文字还是图片
            switch (bean.getType()) {
                case "t":
                    bs.write((bean.getType() + bean.getContent()).getBytes());
                    break;
                case "i":
                    bs.write((bean.getType() + bean.getImgUrl()).getBytes());
                    break;
            }
            if (mqttAsyncClient == null) {
                connect();
                /*callHandler(obj.getString("method"), "failed: 请先登录");*/
                return;
            }

            mqttAsyncClient.publish(bean.getTopic(), bs.toByteArray(), 1, false, null, new IMqttActionListener() {
                public void onFailure(IMqttToken arg0, Throwable arg1) {
                    if (!connFlag) {
                        connectingFlag = false;
                        connect();
                    }
                    Log.e("todo发布消息的方法和状态", "发布失败");

                    bean.setToDefault("isLoading");
                    bean.updateAll("time = ?", bean.getTime() + "");
                    // 异步传送发送失败状态
                    EventBus.getDefault().post(new ImMsgBean(bean.getTime()));
                }

                public void onSuccess(IMqttToken token) {
                    Log.e("MQTT", "publish onSuccess---------" + token.getMessageId());
                    Log.e("todo发布消息的方法和状态", "发布成功");

                    bean.setSuccess(true);
                    // 在使用updateAll()方法时，不能使用set方法来将字段设置为默认值
                    bean.setToDefault("isLoading");
                    bean.updateAll("time = ?", bean.getTime() + "");

                    // 异步传送发送成功状态
                    EventBus.getDefault().post(new ImMsgBean(bean.getTime()));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    // 发送文本
    public static void sendTxt(final ImMsgBean bean) {

        bean.save();

        try {
            sendMsg(bean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*try {
            String localUser = userType + userName;
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            bs.write((byte) localUser.length());
            bs.write(localUser.getBytes());
            bs.write((bean.getType() + bean.getContent()).getBytes());
            if (mqttAsyncClient == null) {
                connect();
                return;
            }

            mqttAsyncClient.publish(bean.getTopic(), bs.toByteArray(), 1, false, null, new IMqttActionListener() {
                public void onFailure(IMqttToken arg0, Throwable arg1) {
                    if (!connFlag) {
                        connectingFlag = false;
                        connect();
                    }
                    Log.e("todo发布消息的方法和状态", "发布失败");

                    bean.setToDefault("isLoading");
                    bean.updateAll("time = ?", bean.getTime() + "");
                    // 异步传送发送失败状态
                    EventBus.getDefault().post(new ImMsgBean(bean.getTime()));
                }

                public void onSuccess(IMqttToken token) {
                    Log.e("MQTT", "publish onSuccess---------" + token.getMessageId());
                    Log.e("todo发布消息的方法和状态", "发布成功");

                    bean.setSuccess(true);
                    // 在使用updateAll()方法时，不能使用set方法来将字段设置为默认值
                    bean.setToDefault("isLoading");
                    bean.updateAll("time = ?", bean.getTime() + "");

                    // 异步传送发送成功状态
                    EventBus.getDefault().post(new ImMsgBean(bean.getTime()));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }*/
    }

    // 发送图片
    public static void sendImg(final ImMsgBean bean) {

        // TODO: 2017/8/6 解决图片发送问题
        bean.save();

        try {
            sendMsg(bean);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*try {
            String localUser = userType + userName;
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            bs.write((byte) localUser.length());
            bs.write(localUser.getBytes());
            bs.write((bean.getType() + bean.getImgUrl()).getBytes());
            if (mqttAsyncClient == null) {
                connect();
                return;
            }

            mqttAsyncClient.publish(bean.getTopic(), bs.toByteArray(), 1, false, null, new IMqttActionListener() {
                public void onFailure(IMqttToken arg0, Throwable arg1) {
                    if (!connFlag) {
                        connectingFlag = false;
                        connect();
                    }
                    Log.e("todo发布消息的方法和状态", "发布失败");

                    bean.setToDefault("isLoading");
                    bean.updateAll("time = ?", bean.getTime() + "");
                    // 异步传送发送失败状态
                    EventBus.getDefault().post(new ImMsgBean(bean.getTime()));
                }

                public void onSuccess(IMqttToken token) {
                    Log.e("MQTT", "publish onSuccess---------" + token.getMessageId());
                    Log.e("todo发布消息的方法和状态", "发布成功");

                    bean.setSuccess(true);
                    // 在使用updateAll()方法时，不能使用set方法来将字段设置为默认值
                    bean.setToDefault("isLoading");
                    bean.updateAll("time = ?", bean.getTime() + "");

                    // 异步传送发送成功状态
                    EventBus.getDefault().post(new ImMsgBean(bean.getTime()));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (MqttPersistenceException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }*/
    }

    // 监听消息
    private static void listen() {
        // 设置回调
        mqttAsyncClient.setCallback(new PushCallback());
    }

}

class PushCallback implements MqttCallback {

    public void connectionLost(Throwable cause) {
        // 连接丢失后，一般在这里面进行重连
        MqttUtil.connFlag = false;
        System.out.println("连接断开，可以做重连");
        MqttUtil.startAsync();
    }

    //先于onSuccess调用
    public void deliveryComplete(IMqttDeliveryToken token) {
        Log.e("MQTT", token.getMessageId() + " : deliveryComplete---------" + token.isComplete());
    }

    public void messageArrived(String topic, MqttMessage message) throws Exception {

        Log.e("messageArrived", "[" + DateTimeUtil.getTime() + "]arrive: " + new String(message.getPayload()));
        Log.e("topicArrived", "[" + DateTimeUtil.getTime() + "]arrive: " + topic);

        ImMsgBean bean = new ImMsgBean();

        // subscribe后得到的消息会执行到这里面
        if (topic.charAt(0) == '$') {//系统主题
            //news
            String dest = topic.substring(1);
            if (dest.equalsIgnoreCase("news")) {
                // TODO: 2017/7/25 保存msg
                    /*MqttDao.saveMsg(topic, "sys", message.getPayload().toString(), "s");*/
            }
        } else {
            String dest = topic.substring(1);
            if (!dest.equalsIgnoreCase(userName)) {
                Log.w("MQTT", "drop a msg of topic: " + topic);
                return; //非本用户接收的主题
            }
            byte[] payload = message.getPayload();
            byte uid_len = payload[0];
            if (uid_len == '$') { //Mqtt系统自动发送的系统消息

            } else {
                byte[] uid_array = new byte[uid_len];
                System.arraycopy(payload, 1, uid_array, 0, uid_len);
                String peer = new String(uid_array);

                byte type = payload[1 + uid_len];
                // TODO: 2017/7/25 判断授权
                   /* if (type != '$' && !MqttDao.isValid(peer)) { //未授权的用户
                        return;
                    }*/
                switch (type) {
                    case '$': {//用户级系统消息，建立会话，下线
                        byte sysMsg = payload[2 + uid_len];
                        int msg_len = payload.length - uid_len - 3;
                        byte[] msg_array = new byte[msg_len];
                        System.arraycopy(payload, 3 + uid_len, msg_array, 0, msg_len);

                        byte msgCmd = (byte) (sysMsg & MqttUtil.MASK_MSG);
//                        byte msgType =(byte) (sysMsg & MqttUtil.MASK_ACK);
                        switch (msgCmd) {
                            case MqttUtil.MSG_SYS_CreateChannel:
                                // TODO: 2017/7/25 添加好友
                                //MqttMsgCreateChannel.process(sysMsg, peer, new String(msg_array, "utf-8"));
                                break;
                            case MqttUtil.MSG_SYS_DropChannel:
                                // TODO: 2017/7/25 删除好友
                                // MqttMsgDropChannel.process(sysMsg, peer);
                                break;
                            case MqttUtil.MSG_USER_SYS_OFFLINE:
                            case MqttUtil.MSG_USER_SYS_ONLINE:
                                // 状态改变
                                // MqttUserStatusChange.process(peer, msgCmd);
                                break;
                        }
                        break;
                    }

                    case 't': {//文本信息
                        int msg_len = payload.length - uid_len - 2;
                        byte[] msg_array = new byte[msg_len];
                        System.arraycopy(payload, 2 + uid_len, msg_array, 0, msg_len);
                        String content = new String(msg_array, "utf-8");
                        // TODO: 2017/7/27 保存msg
                        MqttMsgText.process(bean, peer, content, topic);
                        break;
                    }

                    case 'm': {//电子病历
                        int msg_len = payload.length - uid_len - 2;
                        byte[] msg_array = new byte[msg_len];
                        System.arraycopy(payload, 2 + uid_len, msg_array, 0, msg_len);
                        String content = new String(msg_array, "utf-8");
                        // TODO: 2017/7/27 保存msg
                        MqttMsgText.process(bean, peer, content, topic);
                        break;
                    }
                    // TODO: 2017/7/25 发送收到图片处理
                    case 'i': {//image
                        int msg_len = payload.length - uid_len - 2;
                        byte[] msg_array = new byte[msg_len];
                        System.arraycopy(payload, 2 + uid_len, msg_array, 0, msg_len);
                        String url = new String(msg_array, "utf-8");
                        MqttMsgImage.process(bean, peer, url, topic);
                        break;
                    }
                    case 'v': //video
                        break;
                    case 'a': //audio
                        break;
                }
            }
        }

    }
}

class MqttMsgText {
/*    public static void process(String topic, String peer, String content, String type) {
//        long ts = MqttDao.saveMsg(topic, peer, content, "t");
        long ts = DateTimeUtil.getLongMs();
        Log.e("process: ", ts + "");
        BeanMqttMsgItem bean = new BeanMqttMsgItem(ts, topic, peer, content, type, false);
        //需要根据peer去找到主题，更新content
    }*/

    // 发送文本
    public static void process(ImMsgBean bean, String peer, String content, String topic) {
        // 要显示的内容
        bean.setContent(content);
        bean.setToDefault("isSender");
        bean.setName(peer);

        bean.setTime(DateTimeUtil.getLongMs());
        bean.setType("t");
        bean.setTopic(topic);
        bean.setNewMsg(true);
        bean.save();
        EventBus.getDefault().post(new WeChatReceiveMsg(bean.getTime()));

    }
}

//i|len|<src="...">|img_bytes
class MqttMsgImage {
    public static void process(ImMsgBean bean, String peer, String url, String topic) {
        if ("failure".equals(url)) {
            bean.setContent("接收图片失败");
            bean.setType("t");
        } else {
            bean.setContent("[img]");
            bean.setType("i");
        }
        bean.setImgUrl(url);
        bean.setToDefault("isSender");
        bean.setName(peer);
        bean.setTime(DateTimeUtil.getLongMs());
        bean.setTopic(topic);
        bean.setNewMsg(true);
        bean.save();
        EventBus.getDefault().post(new WeChatReceiveMsg(bean.getTime()));
    }
}