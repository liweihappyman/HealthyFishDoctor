package com.healthyfish.healthyfishdoctor.ui.activity.medical_record;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.foamtrace.photopicker.ImageCaptureManager;
import com.foamtrace.photopicker.PhotoPickerActivity;
import com.foamtrace.photopicker.PhotoPreviewActivity;
import com.foamtrace.photopicker.SelectModel;
import com.foamtrace.photopicker.intent.PhotoPickerIntent;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;

import com.healthyfish.healthyfishdoctor.POJO.BeanCourseOfDisease;
import com.healthyfish.healthyfishdoctor.POJO.BeanMedRec;
import com.healthyfish.healthyfishdoctor.POJO.MessageToServise;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.CreateCourseGridAdapter;
import com.healthyfish.healthyfishdoctor.constant.constants;
import com.healthyfish.healthyfishdoctor.service.UploadImages;
import com.healthyfish.healthyfishdoctor.ui.widget.DatePickerDialog;
import com.healthyfish.healthyfishdoctor.utils.Utils1;
import com.zhy.autolayout.AutoLinearLayout;

import org.litepal.crud.DataSupport;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CreateCourse extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    public static final int CREATE_COURSE_RESULT_UPDATE_OR_DEL = 30;//病程更新返回标志
    public static final int CREATE_COURSE_RESULT_SAVE = 31;//病程保存返回标志
    private CreateCourseGridAdapter gridAdapter;//适配器
    private List<String> imagePaths = new ArrayList<>();//（后面用的）图片的路径list
    private ImageCaptureManager captureManager; // 相机拍照处理类
    public static final int REQUEST_CAMERA_CODE = 11;//摄像头拍照的标志
    public static final int REQUEST_PREVIEW_CODE = 12;//预览的标志
    private BeanCourseOfDisease courseOfDisease = new BeanCourseOfDisease();
    private BeanMedRec medRec = new BeanMedRec();
    private boolean loadNetworkImages = false;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.type_ly)
    AutoLinearLayout typeLy;
    @BindView(R.id.rec_patient_info)
    EditText recPatientInfo;
    @BindView(R.id.create_course_img_gridview)
    GridView createCourseImgGridview;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.save)
    TextView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_course);
        ButterKnife.bind(this);
        toolbar.setTitle("");
        toolbarTitle.setText("创建病程");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_icon);
        }
        initListener();
        if (constants.POSITION_COURSE == -1) {
            initData1();
        } else {
            initData2();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.med_rec, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.del:
                delete();
                break;
        }
        return true;
    }

    /**
     * 删除病程的操作,新建状态提示没有可删除的病程
     */
    private void delete() {
        if (constants.POSITION_COURSE != -1) {
            showDelDialog();// 删除提示对话框
        } else {
            Toast.makeText(this, "没有可删除的病程哦", Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 删除提示对话框
     */
    private void showDelDialog() {
        new AlertDialog.Builder(CreateCourse.this).setMessage("是否要删除此病程")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        courseOfDisease.delete(BeanCourseOfDisease.class, courseOfDisease.getId());
                        dialog.dismiss();
                        Intent intent = new Intent(CreateCourse.this, NewMedRec.class);
                        setResult(CREATE_COURSE_RESULT_UPDATE_OR_DEL, intent);
                        finish();

                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    //点击创建病程进来执行的初始化操作
    private void initData1() {
        date.setText(Utils1.getTime());
        refreshAdpater(imagePaths);
    }

    /**
     *  如果是点击item进来初始化数据，执行这个方法
     *  根据ID获取当前编辑的medRec的数据，并做相应的处理
     */
    private void initData2() {
        medRec = DataSupport.find(BeanMedRec.class, NewMedRec.ID, true);
        courseOfDisease = medRec.getListCourseOfDisease().get(constants.POSITION_COURSE);
        //Log.i("查看病程信息", "路径：" + courseOfDisease.getImgUrls().toString());
        type.setText(courseOfDisease.getType());
        date.setText(courseOfDisease.getDate());
        recPatientInfo.setText(courseOfDisease.getRecPatientInfo());
        List<String> paths = courseOfDisease.getImgPaths();
        for (String string : paths) {
            if (!new File(string).exists()) {
                loadNetworkImages = true;
                paths.clear();//如果图片在本地不全有，直接加载网络的
                paths.addAll(courseOfDisease.getImgUrls());
                break;
            }
        }
        refreshAdpater(paths);
    }


    /**
     * 初始化控件的监听
     */
    private void initListener() {
        typeLy.setOnClickListener(this);
        date.setOnClickListener(this);
        createCourseImgGridview.setOnItemClickListener(this);
        save.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date:
                selectTime();
                break;
            case R.id.type_ly:
                showOptions();
                break;
            case R.id.save:
                saveOrUpdata();
                break;
        }
    }

    /**
     * 执行保存或者更新操作，保存操作在NewMedRec活动里执行
     */
    private void saveOrUpdata() {
        getInfo();//获取控件的值
        if (constants.POSITION_COURSE != -1) {//每次更新必须重新关联
            courseOfDisease.setBeanMedRec(medRec);//关联，medRec必须是数据库已经存在的数据对象
            courseOfDisease.setImgPaths(imagePaths);
            String g = courseOfDisease.getImgPaths().toString();
            //Log.i("保存前路径","路径"+g);
            //更新操作
            //经测试，当图片路径从有到无的时候，更新失败，这里直接用保存了，
            // 因为courseOfDisease对象是从数据库读取的，所以执行save就是在原来的基础上更新
            //courseOfDisease.update(courseOfDisease.getId());
            courseOfDisease.save();
            //如果有图片则开启服务上传图片
            if (imagePaths.size() > 0) {
                Intent startUploadImages = new Intent(this, UploadImages.class);
                startUploadImages.putExtra("messageToService", new MessageToServise(medRec.getId(), courseOfDisease.getId(), imagePaths));
                startService(startUploadImages);
            }
            //测试查看保存后的路径
//            BeanMedRec beanMedRec= DataSupport.find(BeanMedRec.class, NewMedRec.ID, true);
//            BeanCourseOfDisease courseOfDisease = beanMedRec.getListCourseOfDisease().get(constants.POSITION_COURSE);
//            String string = courseOfDisease.getImgPaths().toString();
//            String s= courseOfDisease.getImgUrls().toString();
//            Log.i("保存后路径","路径"+string+"wang"+s);
            //设置intent数据，并跳转
            Intent intent = new Intent(CreateCourse.this, NewMedRec.class);
            intent.putExtra("updateCourse", courseOfDisease);
            setResult(CREATE_COURSE_RESULT_UPDATE_OR_DEL, intent);
            finish();
        } else {
            //将数据返回NewMedRec活动进行保存
            Intent intent = new Intent(CreateCourse.this, NewMedRec.class);
            intent.putExtra("saveCourse", courseOfDisease);
            setResult(CREATE_COURSE_RESULT_SAVE, intent);
            finish();
        }
    }
    /**
     *  获取控件的值
     */

    private void getInfo() {
        courseOfDisease.setType(type.getText().toString());
        courseOfDisease.setDate(date.getText().toString());
        courseOfDisease.setRecPatientInfo(recPatientInfo.getText().toString());
        courseOfDisease.setImgPaths(imagePaths);
    }


    /**
     *  用poppopupwindow+listView+ArrayAdapter实现弹窗选择菜单
     */
    private void showOptions() {
        View rootView;
        rootView = LayoutInflater.from(CreateCourse.this).inflate(R.layout.options,
                null);
        final PopupWindow mPopWindow = new PopupWindow(rootView);
        mPopWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopWindow.setTouchable(true);
        mPopWindow.setFocusable(true);
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);
        ListView optionns = (ListView) rootView.findViewById(R.id.options_listview);
        optionns.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type.setText(parent.getItemAtPosition(position).toString());
                mPopWindow.dismiss();
            }
        });
        mPopWindow.showAsDropDown(typeLy);
    }

    /**
     *  时间选择对话框
     */
    private void selectTime() {
        DatePickerDialog datePicker_dialog = new DatePickerDialog(this, new
                DatePickerDialog.MyListener() {
                    @Override
                    public void refreshUI(String string) {
                        date.setText(string);
                    }
                });
        datePicker_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        datePicker_dialog.show();
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position < 9) {//设置显示图片的数量
            if (imagePaths.size() == position) {
                selectImg();//选择图片
            } else {
                preview(position);//图片预览
            }
        }
    }

    /**
     * 设置图片预览的路径，本地或者网络
     * 如果图片在本地不全有，则直接加载网络的图片预览
     * @param position 当前图片的位置
     */
    private void preview(int position) {
        for (String string : imagePaths) {
            if (!new File(string).exists()) {
                loadNetworkImages = true;
                break;
            }
        }
        PhotoPreviewIntent intent = new PhotoPreviewIntent(this);
        if (loadNetworkImages) {
            imagePaths.clear();
            //如果图片路径在手机不全存在，则直接将路径设置为网络的路径
            imagePaths.addAll(courseOfDisease.getImgUrls());
        }
        intent.setPhotoPaths((ArrayList) imagePaths);
        intent.setCurrentItem(position);
        startActivityForResult(intent, REQUEST_PREVIEW_CODE);
    }


    /**
     * 设置选择图片的张数
     */
    protected void selectImg() {
        PhotoPickerIntent intent = new PhotoPickerIntent(this);
        intent.setSelectModel(SelectModel.MULTI);
        intent.setShowCarema(true); // 是否显示拍照， 默认false
        intent.setMaxTotal(8); // 最多选择照片数量，默认为8
        intent.setSelectedPaths((ArrayList) imagePaths); // 已选中的照片地址， 用于回显选中状态
        //intent.setImageConfig(config);
        startActivityForResult(intent, REQUEST_CAMERA_CODE);
    }
    /**
     * 适配器更新列表
     */
    private void refreshAdpater(List<String> paths) {
        // 处理返回照片地址
        if (imagePaths == null) {
            imagePaths = new ArrayList<>();
        }
        imagePaths.clear();
        imagePaths.addAll(paths);
        int i = imagePaths.size();
        if (gridAdapter == null) {
            gridAdapter = new CreateCourseGridAdapter(imagePaths, this);
            createCourseImgGridview.setAdapter(gridAdapter);
            gridAdapter.notifyDataSetChanged();
        } else {
            gridAdapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == CreateCourse.RESULT_OK) {
            switch (requestCode) {
                // 选择照片
                case REQUEST_CAMERA_CODE:
                    refreshAdpater(data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT));
                    break;
                // 预览
                case REQUEST_PREVIEW_CODE:
                    removeUrls(data);
                    refreshAdpater(data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT));
                    break;
                // 调用相机拍照
                case ImageCaptureManager.REQUEST_TAKE_PHOTO:
                    if (captureManager.getCurrentPhotoPath() != null) {
                        captureManager.galleryAddPic();
                        ArrayList<String> paths = new ArrayList<>();
                        paths.add(captureManager.getCurrentPhotoPath());
                        refreshAdpater(paths);
                    }
                    break;
            }
        }
    }
    /**
     * 将预览时删除的图片的路径从courseOfDisease.getImgUrls()中去掉
     * （即从存放网络路径的list中去掉，这一步很关键，前面这个路径要用到，所以必须同步）
     *
     * @param data 预览结束回传的数据
     */
    private void removeUrls(Intent data) {
        imagePaths.clear();
        imagePaths.addAll(data.getStringArrayListExtra(PhotoPreviewActivity.EXTRA_RESULT));
        List<String> temp = new ArrayList<>();
        for (int i = 0; i < imagePaths.size(); i++) {
            for (int k = 0; k < courseOfDisease.getImgUrls().size(); k++) {
                if (imagePaths.get(i).equals(courseOfDisease.getImgUrls().get(k))) {
                    temp.add(courseOfDisease.getImgUrls().get(k));
                }
            }
        }
        courseOfDisease.getImgUrls().clear();
        courseOfDisease.setImgUrls(temp);
    }
}
