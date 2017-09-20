package com.healthyfish.healthyfishdoctor.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

/**
 * Created by a302 on 2016/12/25.
 */

public class WkjNumberPicker extends NumberPicker {

        public WkjNumberPicker(Context context) {
            super(context);
        }

        public WkjNumberPicker(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public WkjNumberPicker(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        public void addView(View child) {
            super.addView(child);
            updateView(child);
        }

        @Override
        public void addView(View child, int index,
                            android.view.ViewGroup.LayoutParams params) {
            super.addView(child, index, params);
            updateView(child);
        }

        @Override
        public void addView(View child, android.view.ViewGroup.LayoutParams params) {
            super.addView(child, params);
            updateView(child);
        }

        public void updateView(View view) {
            if (view instanceof EditText) {
                //这里修改字体的属性
                ((EditText) view).setTextColor(Color.parseColor("#FF019b79"));
//            ((EditText) view).setTextSize();
            }
        }

    }
    //设置分割线的颜色
//    private void setNumberPickerDividerColor(NumberPicker numberPicker) {
//        NumberPicker picker = numberPicker;
//        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
//        for (Field pf : pickerFields) {
//            if (pf.getName().equals("mSelectionDivider")) {
//                pf.setAccessible(true);
//                try {
//                    //设置分割线的颜色值
//                    pf.set(picker, new ColorDrawable(this.getResources().getColor(R.color.green)));
//                } catch (IllegalArgumentException e) {
//                    e.printStackTrace();
//                } catch (Resources.NotFoundException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//                break;
//            }
//        }
//    }
//}
