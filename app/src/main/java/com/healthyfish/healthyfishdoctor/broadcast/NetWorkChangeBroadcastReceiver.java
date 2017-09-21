package com.healthyfish.healthyfishdoctor.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 描述：广播实现网络状态变化与WIFI移动网的监听
 * 作者：Wayne on 2017/8/6 21:04
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

public class NetWorkChangeBroadcastReceiver extends BroadcastReceiver {
    //标记当前网络状态，0为无可用网络状态，1表示有网络， 2表示手机没有处于wifi网络而是处于移动网络。
    public static final String NET_CHANGE = "net_change";
    public static final String NET_TYPE = "net_type";

    @Override
    public void onReceive(Context context, Intent intent) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //移动数据
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        //wifi网络
        NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
//            网络状态全部不可用
            Intent netIntent = new Intent(NET_CHANGE);
            netIntent.putExtra(NET_TYPE, 0);
            context.sendBroadcast(netIntent);
            return;
        }
        if (wifiNetInfo.isConnected()) {
            // 有wifi网络连接
            Intent netIntent = new Intent(NET_CHANGE);
            netIntent.putExtra(NET_TYPE, 1);
            context.sendBroadcast(netIntent);
            return;
        }

        if (mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
            // 只有移动数据，没有网络
            Intent netIntent = new Intent(NET_CHANGE);
            netIntent.putExtra(NET_TYPE, 2);
            context.sendBroadcast(netIntent);
            return;
        }

    }
}
