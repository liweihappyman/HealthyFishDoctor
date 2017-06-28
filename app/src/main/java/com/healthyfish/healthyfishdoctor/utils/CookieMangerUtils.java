package com.healthyfish.healthyfishdoctor.utils;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * 描述：构建Cookie管理者
 * 作者：Wayne on 2017/6/27 22:51
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 * 参考：Retrofit 2.0 超能实践（二），Okhttp完美同步持久Cookie实现免登录  http://www.jianshu.com/p/1a5f14b63f47
 */

public class CookieMangerUtils implements CookieJar{
    private static final String TAG = "NovateCookieManger";
    private static Context mContext;
    private static PersistentCookieStore cookieStore;

    /**
     * Mandatory constructor for the NovateCookieManger
     */
    public CookieMangerUtils(Context context) {
        mContext = context;
        if (cookieStore == null) {
            cookieStore = new PersistentCookieStore(mContext);
        }
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }
}
