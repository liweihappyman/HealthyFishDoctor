package com.healthyfish.healthyfishdoctor.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import com.healthyfish.healthyfishdoctor.MainActivity;
import com.healthyfish.healthyfishdoctor.MyApplication;
import com.healthyfish.healthyfishdoctor.R;

/**
 * 描述：
 * 作者：Wayne on 2017/10/11 21:46
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

public class SendNotificationsUtils {
    /**
     * @param ContentTitle 通知标题
     * @param ContentText 通知具体内容
     * @param activity  页面活动
     */
    public static void sendNotifications(String ContentTitle, String ContentText, Class<MainActivity> activity) {
        //手机震动权限  <uses-permission android:name="android.permission.VIBRATE" />
        //获取系统的通知管理
        NotificationManager notificationManager = (NotificationManager) MyApplication.getContetxt().getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(MyApplication.getContetxt(), activity);//点击跳转的目标页面
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        //封装intent，意思是要满足某一条件才会触发，这里是只有点击通知才会触发跳转事件
        PendingIntent pt = PendingIntent.getActivity(MyApplication.getContetxt(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //构造通知消息
        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyApplication.getContetxt());
        //在状态栏消息提示文字
        builder.setTicker("健鱼消息提示");
        //状态栏显示的图标
        builder.setSmallIcon(R.mipmap.logo_healthyfish_24);
        //以下设置通知消息的显示标题、内容
        builder.setContentTitle(ContentTitle);
        builder.setContentText(ContentText);
        //设置下拉后通知显示的大图标
        builder.setLargeIcon(BitmapFactory.decodeResource(MyApplication.getContetxt().getResources(), R.mipmap.logo_healthyfish_48));
        //设置消息的数量
        //builder.setNumber(number);
        builder.setWhen(System.currentTimeMillis());
        //设置消息点击的跳转事件
        builder.setContentIntent(pt);
        //设置点击后自动取消在状态栏和消息栏的显示状态
        builder.setAutoCancel(true);
        //设置铃声、震动为当前手机正在使用的模式
        builder.setDefaults(Notification.DEFAULT_ALL);
        //设置闪烁灯
        builder.setLights(0xff00ff00, 3000, 1000);
        //设置优先级
        builder.setPriority(Notification.PRIORITY_MAX);
        //发起通知
        notificationManager.notify(1, builder.build());
    }
}
