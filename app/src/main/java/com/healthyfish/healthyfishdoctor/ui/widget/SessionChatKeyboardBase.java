package com.healthyfish.healthyfishdoctor.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.healthyfish.healthyfishdoctor.R;

import sj.keyboard.XhsEmoticonsKeyBoard;
import sj.keyboard.utils.EmoticonsKeyboardUtils;

/**
 * 描述：重写更多功能GridView的高度
 * 作者：Wayne on 2017/7/7 08:57
 * 邮箱：liwei_happyman@qq.com
 * 编辑：
 */

public class SessionChatKeyboardBase extends XhsEmoticonsKeyBoard {

    public final int APPS_HEIGHT = 120;

    public SessionChatKeyboardBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void OnSoftClose() {
        super.OnSoftClose();
        if (mLyKvml.getCurrentFuncKey() == FUNC_TYPE_APPPS) {
            setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == com.keyboard.view.R.id.btn_voice_or_text) {
            if (mEtChat.isShown()) {
                mBtnVoiceOrText.setImageResource(R.mipmap.chatting_softkeyboard);
                showVoice();
            } else {
                showText();
                mBtnVoiceOrText.setImageResource(R.mipmap.chatting_vodie);
                EmoticonsKeyboardUtils.openSoftKeyboard(mEtChat);
            }
        } else if (i == com.keyboard.view.R.id.btn_face) {
            toggleFuncView(FUNC_TYPE_EMOTION);
        } else if (i == com.keyboard.view.R.id.btn_multimedia) {
            toggleFuncView(FUNC_TYPE_APPPS);
            setFuncViewHeight(EmoticonsKeyboardUtils.dip2px(getContext(), APPS_HEIGHT));
        }
    }
}
