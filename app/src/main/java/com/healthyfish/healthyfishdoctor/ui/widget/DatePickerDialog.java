package com.healthyfish.healthyfishdoctor.ui.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.healthyfish.healthyfishdoctor.R;

import java.util.Calendar;


/**
 * Created by Administrator on 2016/11/5.
 * 功能:实现日期的选择
 * 继承了Dialog,Dialog里面填充布局好的datepickerdialog布局,并做监听
 * 用了三个numberPicker来实现数据的滚动选择
 *
 *  //去掉Dialog的标题栏
 *Dialog d = new Dialog(context);
 *d.requestWindowFeature(Window.FEATURE_NO_TITLE);
 *
 * DatePicker_Dialog dialog = new DatePicker_Dialog(datePickerActivity.this);
 *dialog.setTitle("选择开始的时间");//在Activity中给dialog设置标题内容
 *dialog.show();
*/
public class DatePickerDialog extends Dialog {
    private MyListener mylistener;
    private Context context;
    private int style;

    private NumberPicker np1,np2,np3;
    private static String str1 = "2016";//设置选择器的起始数据(后面重新赋值)
    private static String str2 = "12";
    private static String str3 = "29";

    /**
     * 自定义Dialog监听器
     */
    public interface MyListener {
        /**
         * 回调函数，用于在Dialog的监听事件触发后刷新Activity的UI显示
         */
        public void refreshUI(String string);
    }


    //构造函数,获取content,实现对该类的操作
    public DatePickerDialog(Context context) {
        super(context);
        this.context = context;
    }


    //构造函数2,获取content,实现对该类的操作,接口返回数据
    public DatePickerDialog(Context context, MyListener myListener) {
        super(context);
        this.context = context;
        this.mylistener=myListener;
    }

    //构造函数3,获取content和style风格选择(这里没用到),实现对该类的操作
    public DatePickerDialog(Context context, int style) {
        super(context);
        this.context = context;
        this.style = style;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.date_pickerdialog);//填充布局好的datepickerdialog布局
//对numberPicker:找到相应控件并做好约束条件和监听---------------
        np1 = (NumberPicker) findViewById(R.id.np1);
        np2 = (NumberPicker) findViewById(R.id.np2);
        np3 = (NumberPicker) findViewById(R.id.np3);

        //初始化时间，开始显示的都是当天的时间
        //===========================================
        Calendar c = Calendar.getInstance();

        String year = String.valueOf(c.get(Calendar.YEAR));
        str1 = year;
        String month = String.valueOf(c.get(Calendar.MONTH)+1);
        str2 = month;
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
        str3 = day;

        //=====================================================




        np1.setMaxValue(2022);//设置数字选择器的最大值,最小值,以及当前值
        np1.setMinValue(2015);
        np1.setValue(Integer.parseInt(str1));
        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
                // TODO Auto-generated method stub
                str1 = np1.getValue() + "";
                if (Integer.parseInt(str1) % 4 == 0
                        && Integer.parseInt(str1) % 100 != 0
                        || Integer.parseInt(str1) % 400 == 0) {
                    if(str2.equals("1")||str2.equals("3")||str2.equals("5")||str2.equals("7")||str2.equals("8")||str2.equals("10")||str2.equals("12")){
                        np3.setMaxValue(31);
                        np3.setMinValue(1);
                    }else if(str2.equals("4")||str2.equals("6")||str2.equals("9")||str2.equals("11")){
                        np3.setMaxValue(30);
                        np3.setMinValue(1);
                    }else{
                        np3.setMaxValue(29);
                        np3.setMinValue(1);
                    }

                } else {
                    if(str2.equals("1")||str2.equals("3")||str2.equals("5")||str2.equals("7")||str2.equals("8")||str2.equals("10")||str2.equals("12")){
                        np3.setMaxValue(31);
                        np3.setMinValue(1);
                    }else if(str2.equals("4")||str2.equals("6")||str2.equals("9")||str2.equals("11")){
                        np3.setMaxValue(30);
                        np3.setMinValue(1);
                    }else{
                        np3.setMaxValue(28);
                        np3.setMinValue(1);
                    }
                }

            }
        });
        np2.setMaxValue(12);
        np2.setMinValue(1);
        np2.setValue(Integer.parseInt(str2));
        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
                // TODO Auto-generated method stub
                str2 = np2.getValue()+"";
                if(str2.equals("1")||str2.equals("3")||str2.equals("5")||str2.equals("7")||str2.equals("8")||str2.equals("10")||str2.equals("12")){
                    np3.setMaxValue(31);
                    np3.setMinValue(1);
                }else if(str2.equals("4")||str2.equals("6")||str2.equals("9")||str2.equals("11")){
                    np3.setMaxValue(30);
                    np3.setMinValue(1);
                }else{
                    if (Integer.parseInt(str1) % 4 == 0
                            && Integer.parseInt(str1) % 100 != 0
                            || Integer.parseInt(str1) % 400 == 0) {
                        np3.setMaxValue(29);
                        np3.setMinValue(1);
                    } else {
                        np3.setMaxValue(28);
                        np3.setMinValue(1);
                    }
                }
            }
        });
        np3.setMaxValue(31);
        np3.setMinValue(1);
        np3.setValue(Integer.parseInt(str3));
        np3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker arg0, int arg1, int arg2) {
                // TODO Auto-generated method stub
                str3 = np3.getValue()+"";
            }
        });
//---------------------------------------------------------------------------------------------
        // 设置返回按钮事件
        Button bckButton = ((Button)findViewById(R.id.dialog_back));
        bckButton.setText("取消");
        bckButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //new DatePicker_Dialog((getContext()), DialogInterface.BUTTON_NEGATIVE);
                dismiss();
            }
        } );
        // 设置确定按钮事件
        Button cfmButton = ((Button)findViewById(R.id.dialog_confirm));
        cfmButton.setText("确定");
        cfmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //new DatePicker_Dialog((getContext()),  DialogInterface.BUTTON_NEGATIVE);
                mylistener.refreshUI(getDate());
                dismiss();
                //用来测试选择结果
                Toast.makeText(context,getDate(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //
    public static String getDate(){
        return str1+"年"+str2+"月"+str3+"日";
    }
    @Override
    public void show() {
        // TODO Auto-generated method stub
        super.show();
    }


}













