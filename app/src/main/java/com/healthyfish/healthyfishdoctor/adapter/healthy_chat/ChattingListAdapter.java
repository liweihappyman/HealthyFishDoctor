package com.healthyfish.healthyfishdoctor.adapter.healthy_chat;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.foamtrace.photopicker.intent.PhotoPreviewIntent;
import com.healthyfish.healthyfishdoctor.MyApplication;
import com.healthyfish.healthyfishdoctor.POJO.BeanMedRec;
import com.healthyfish.healthyfishdoctor.POJO.BeanPersonalInformation;
import com.healthyfish.healthyfishdoctor.POJO.ImMsgBean;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.ui.activity.medical_record.NewMedRec;
import com.healthyfish.healthyfishdoctor.utils.DateTimeUtil;
import com.healthyfish.healthyfishdoctor.utils.chat_utils.ImageLoadUtils;
import com.healthyfish.healthyfishdoctor.utils.chat_utils.SimpleCommonUtils;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import sj.keyboard.utils.imageloader.ImageBase;

import static com.healthyfish.healthyfishdoctor.MyApplication.uid;
import static com.healthyfish.healthyfishdoctor.constant.Constants.HttpHealthyFishyUrl;

// 聊天界面、对话框 -- 适配器
public class ChattingListAdapter extends BaseAdapter {

    private final int VIEW_TYPE_COUNT = 8;
    private final int VIEW_TYPE_LEFT_TEXT = 0;
    private final int VIEW_TYPE_LEFT_IMAGE = 1;
    private final int VIEW_TYPE_RIGHT_TEXT = 2;
    private final int VIEW_TYPE_RIGHT_IMAGE = 3;
    private final int VIEW_TYPE_LEFT_MDR = 4;
    private final int VIEW_TYPE_RIGHT_MDR = 5;

    private Activity mActivity;
    private LayoutInflater mInflater;

    private List<ImMsgBean> mData;

    public ChattingListAdapter(Activity activity) {
        this.mActivity = activity;
        mInflater = LayoutInflater.from(activity);
    }

    public void addData(List<ImMsgBean> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        if (mData == null) {
            mData = new ArrayList<>();
        }
        for (ImMsgBean bean : data) {
            addData(bean, false, false);
        }
        this.notifyDataSetChanged();
    }

    public void addData(ImMsgBean bean, boolean isNotifyDataSetChanged, boolean isFromHead) {
        if (bean == null) {
            return;
        }
        if (mData == null) {
            mData = new ArrayList<>();
        }

        if (bean.getMsgType() >= 0) {
            String content = bean.getContent();
            if (content != null) {
                if (bean.isSender() == true) {
                    // 图片
                    if (content.indexOf("[img]") >= 0) {
                        bean.setImage(content.replace("[img]", ""));
                        bean.setMsgType(ImMsgBean.CHAT_MSGTYPE_IMG_SENDER);
                    }
                    // 病历
                    else if (content.indexOf("[mdr]") >= 0) {
                        bean.setMdrKey(content.replace("[mdr]", ""));
                        bean.setMsgType(ImMsgBean.CHAT_MSGTYPE_MDR_SENDER);
                    } else {
                        bean.setMsgType(ImMsgBean.CHAT_MSGTYPE_TEXT_SENDER);
                    }
                } else if (bean.isSender() == false) {
                    // 图片
                    if (content.indexOf("[img]") >= 0) {
                        bean.setImage(content.replace("[img]", ""));
                        bean.setMsgType(ImMsgBean.CHAT_MSGTYPE_IMG_RECEIVER);
                    }
                    // 病历
                    else if (content.indexOf("[mdr]") >= 0) {
                        bean.setMdrKey(content.replace("[mdr]", ""));
                        bean.setMsgType(ImMsgBean.CHAT_MSGTYPE_MDR_RECEIVER);
                    } else {
                        bean.setMsgType(ImMsgBean.CHAT_MSGTYPE_TEXT_RECEIVER);
                    }
                }
            }
        }

        if (isFromHead) {
            mData.add(0, bean);
        } else {
            mData.add(bean);
        }

        if (isNotifyDataSetChanged) {
            this.notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // 获取消息Item的类型
    @Override
    public int getItemViewType(int position) {
        if (mData.get(position) == null) {
            return -1;
        }
        // 判断消息类型是发送还是接收，以及文字、病历或者图片
        switch (mData.get(position).getMsgType()) {
            case ImMsgBean.CHAT_MSGTYPE_TEXT_SENDER:
                return VIEW_TYPE_RIGHT_TEXT;
            case ImMsgBean.CHAT_MSGTYPE_IMG_SENDER:
                return VIEW_TYPE_RIGHT_IMAGE;
            case ImMsgBean.CHAT_MSGTYPE_MDR_SENDER:
                return VIEW_TYPE_RIGHT_MDR;
            case ImMsgBean.CHAT_MSGTYPE_TEXT_RECEIVER:
                return VIEW_TYPE_LEFT_TEXT;
            case ImMsgBean.CHAT_MSGTYPE_IMG_RECEIVER:
                return VIEW_TYPE_LEFT_IMAGE;
            case ImMsgBean.CHAT_MSGTYPE_MDR_RECEIVER:
                return VIEW_TYPE_LEFT_MDR;
            default:
                return -1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    // 将布局填充到视图中
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ImMsgBean bean = mData.get(position);

        int type = getItemViewType(position);
        View holderView = null;
        switch (type) {
            case VIEW_TYPE_RIGHT_TEXT:
                ViewHolder rightTextHolder;
                if (convertView == null) {
                    rightTextHolder = new ViewHolder();
                    holderView = mInflater.inflate(R.layout.listitem_chat_right_text, null);
                    holderView.setFocusable(true);
                    rightTextHolder.iv_portrait = (ImageView) holderView.findViewById(R.id.iv_portrait);
                    rightTextHolder.tv_content = (TextView) holderView.findViewById(R.id.tv_content);
                    rightTextHolder.sendtime = (TextView) holderView.findViewById(R.id.sendtime);
                    rightTextHolder.iv_loading = (ImageView) holderView.findViewById(R.id.iv_loading);
                    rightTextHolder.iv_failure_send = (ImageView) holderView.findViewById(R.id.iv_failure_send);
                    holderView.setTag(rightTextHolder);
                    convertView = holderView;
                } else {
                    rightTextHolder = (ViewHolder) convertView.getTag();
                }
                disPlayRightTextView(position, convertView, rightTextHolder, bean);
                break;

            case VIEW_TYPE_LEFT_TEXT:
                ViewHolder leftTextHolder;
                if (convertView == null) {
                    leftTextHolder = new ViewHolder();
                    holderView = mInflater.inflate(R.layout.listitem_chat_left_text, null);
                    holderView.setFocusable(true);
                    leftTextHolder.iv_portrait = (ImageView) holderView.findViewById(R.id.iv_portrait);
                    leftTextHolder.tv_content = (TextView) holderView.findViewById(R.id.tv_content);
                    leftTextHolder.sendtime = (TextView) holderView.findViewById(R.id.sendtime);
                    holderView.setTag(leftTextHolder);
                    convertView = holderView;
                } else {
                    leftTextHolder = (ViewHolder) convertView.getTag();
                }
                disPlayLeftTextView(position, convertView, leftTextHolder, bean);
                break;

            case VIEW_TYPE_RIGHT_IMAGE:
                final ViewHolder rightImageHolder;
                if (convertView == null) {
                    rightImageHolder = new ViewHolder();
                    holderView = mInflater.inflate(R.layout.listitem_chat_right_image, null);
                    holderView.setFocusable(true);
                    rightImageHolder.iv_portrait = (ImageView) holderView.findViewById(R.id.iv_portrait);
                    rightImageHolder.iv_image = (ImageView) holderView.findViewById(R.id.iv_image);
                    rightImageHolder.sendtime = (TextView) holderView.findViewById(R.id.sendtime);
                    rightImageHolder.iv_loading = (ImageView) holderView.findViewById(R.id.iv_loading);
                    rightImageHolder.iv_failure_send = (ImageView) holderView.findViewById(R.id.iv_failure_send);
                    holderView.setTag(rightImageHolder);
                    convertView = holderView;
                } else {
                    rightImageHolder = (ViewHolder) convertView.getTag();
                }
                disPlayRightImageView(position, convertView, rightImageHolder, bean);

                // 点击放大预览图片
                rightImageHolder.iv_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        preview(bean.getImgUrl());
                    }
                });
                break;

            case VIEW_TYPE_LEFT_IMAGE:
                final ViewHolder leftImageHolder;
                if (convertView == null) {
                    leftImageHolder = new ViewHolder();
                    holderView = mInflater.inflate(R.layout.listitem_chat_left_image, null);
                    holderView.setFocusable(true);
                    leftImageHolder.iv_portrait = (ImageView) holderView.findViewById(R.id.iv_portrait);
                    leftImageHolder.iv_image = (ImageView) holderView.findViewById(R.id.iv_image);
                    leftImageHolder.sendtime = (TextView) holderView.findViewById(R.id.sendtime);
                    holderView.setTag(leftImageHolder);
                    convertView = holderView;
                } else {
                    leftImageHolder = (ViewHolder) convertView.getTag();
                }
                disPlayLeftImageView(position, convertView, leftImageHolder, bean);
                // 点击放大预览图片
                leftImageHolder.iv_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        preview(bean.getImgUrl());
                    }
                });
                break;

            case VIEW_TYPE_RIGHT_MDR:
                final ViewHolder rightMdrHolder;
                if (convertView == null) {
                    rightMdrHolder = new ViewHolder();
                    holderView = mInflater.inflate(R.layout.listitem_chat_right_text, null);
                    holderView.setFocusable(true);
                    rightMdrHolder.iv_portrait = (ImageView) holderView.findViewById(R.id.iv_portrait);
                    rightMdrHolder.tv_content = (TextView) holderView.findViewById(R.id.tv_content);
                    rightMdrHolder.sendtime = (TextView) holderView.findViewById(R.id.sendtime);
                    rightMdrHolder.iv_loading = (ImageView) holderView.findViewById(R.id.iv_loading);
                    rightMdrHolder.iv_failure_send = (ImageView) holderView.findViewById(R.id.iv_failure_send);
                    holderView.setTag(rightMdrHolder);
                    convertView = holderView;
                }else {
                    rightMdrHolder = (ViewHolder) convertView.getTag();
                }
                disPlayRightMdrView(position, convertView, rightMdrHolder, bean);
                // 点击病历内容跳转到病历
                rightMdrHolder.tv_content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToMedRec(bean.getMdrKey().substring(5));
                    }
                });
                break;

            case VIEW_TYPE_LEFT_MDR:
                final ViewHolder leftMdrHolder;
                if (convertView == null) {
                    leftMdrHolder = new ViewHolder();
                    holderView = mInflater.inflate(R.layout.listitem_chat_right_text, null);
                    holderView.setFocusable(true);
                    leftMdrHolder.iv_portrait = (ImageView) holderView.findViewById(R.id.iv_portrait);
                    leftMdrHolder.tv_content = (TextView) holderView.findViewById(R.id.tv_content);
                    leftMdrHolder.sendtime = (TextView) holderView.findViewById(R.id.sendtime);
                    leftMdrHolder.iv_loading = (ImageView) holderView.findViewById(R.id.iv_loading);
                    leftMdrHolder.iv_failure_send = (ImageView) holderView.findViewById(R.id.iv_failure_send);
                    holderView.setTag(leftMdrHolder);
                    convertView = holderView;
                }else {
                    leftMdrHolder = (ViewHolder) convertView.getTag();
                }
                disPlayLeftMdrView(position, convertView, leftMdrHolder, bean);
                // 点击病历内容跳转到病历
                leftMdrHolder.tv_content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToMedRec(bean.getMdrKey().substring(5));
                    }
                });
                break;
            default:
                convertView = new View(mActivity);
                break;
        }
        return convertView;
    }

    public void disPlayLeftTextView(int position, View view, ViewHolder holder, ImMsgBean bean) {
        setContent(holder.tv_content, bean.getContent());
        holder.sendtime.setText(DateTimeUtil.getTime(bean.getTime()));
        Glide.with(holder.iv_portrait.getContext()).load(bean.getPortrait()).into(holder.iv_portrait);
    }

    public void disPlayLeftImageView(int position, View view, ViewHolder holder, ImMsgBean bean) {
        Glide.with(holder.iv_portrait.getContext()).load(bean.getPortrait()).into(holder.iv_portrait);
        if (ImageBase.Scheme.FILE == ImageBase.Scheme.ofUri(bean.getImage())) {
            String filePath = ImageBase.Scheme.FILE.crop(bean.getImage());
            Glide.with(holder.iv_image.getContext())
                    .load(filePath)
                    .into(holder.iv_image);
        } else {
            Glide.with(holder.iv_image.getContext())
                    .load(bean.getImgUrl())
                    .into(holder.iv_image);
        }
        holder.sendtime.setText(DateTimeUtil.getTime(bean.getTime()));
    }

    public void disPlayRightTextView(int position, View view, ViewHolder holder, ImMsgBean bean) {
        setContent(holder.tv_content, bean.getContent());
        holder.sendtime.setText(DateTimeUtil.getTime(bean.getTime()));
        Glide.with(holder.iv_portrait.getContext()).load(getLocalUserImg()).into(holder.iv_portrait);
        // 动态修改发送状态（加载、失败、成功）
        statusOfLoadingOrFailureOrSuccess(holder, bean);

    }

    public void disPlayRightImageView(int position, View view, ViewHolder holder, ImMsgBean bean) {
        try {
            Glide.with(holder.iv_portrait.getContext()).load(getLocalUserImg()).into(holder.iv_portrait);
            if (ImageBase.Scheme.FILE == ImageBase.Scheme.ofUri(bean.getImage())) {
                String filePath = ImageBase.Scheme.FILE.crop(bean.getImage());
                Glide.with(holder.iv_image.getContext())
                        .load(filePath)
                        .into(holder.iv_image);
            } else {
                ImageLoadUtils.getInstance(mActivity).displayImage(bean.getImage(), holder.iv_image);
            }
            holder.sendtime.setText(DateTimeUtil.getTime(bean.getTime()));
            // 动态修改发送状态（加载、失败、成功）
            statusOfLoadingOrFailureOrSuccess(holder, bean);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disPlayRightMdrView(int position, View convertView, ViewHolder holder, ImMsgBean bean) {
        // 查看病历详情
        String mdrDetail = getMDRKey(bean.getContent().substring(5));
        setContent(holder.tv_content, "病历发送成功\n" + "病历详情: " + mdrDetail);
        holder.sendtime.setText(DateTimeUtil.getTime(bean.getTime()));
        Glide.with(holder.iv_portrait.getContext()).load(getLocalUserImg()).into(holder.iv_portrait);
        // 动态修改发送状态（加载、失败、成功）
        statusOfLoadingOrFailureOrSuccess(holder, bean);
    }

    private void disPlayLeftMdrView(int position, View convertView, ViewHolder holder, ImMsgBean bean) {
        // 查看病历详情
        String mdrDetail = getMDRKey(bean.getContent().substring(5));
        setContent(holder.tv_content, "病历接收成功\n" + "病历详情: " + mdrDetail);
        holder.sendtime.setText(DateTimeUtil.getTime(bean.getTime()));
        Glide.with(holder.iv_portrait.getContext()).load(getLocalUserImg()).into(holder.iv_portrait);
        // 动态修改发送状态（加载、失败、成功）
        statusOfLoadingOrFailureOrSuccess(holder, bean);
    }

    public void setContent(TextView tv_content, String content) {
        SimpleCommonUtils.spannableEmoticonFilter(tv_content, content);
    }

    private final class ViewHolder {
        private ImageView iv_portrait;
        private TextView tv_content;
        private ImageView iv_image;
        private TextView sendtime;
        private ImageView iv_loading;
        private ImageView iv_failure_send;
    }

    // 获取本机用户头像
    public String getLocalUserImg() {
        String key = "info_" + uid;
        List<BeanPersonalInformation> personalInformationList = DataSupport.where("key = ?", key).find(BeanPersonalInformation.class);
        if (personalInformationList != null) {
            return HttpHealthyFishyUrl + personalInformationList.get(0).getImgUrl();
        }
        return String.valueOf(R.mipmap.logo_240);
    }

    // 根据病历的key获取病历详情
    private String getMDRKey(String mdrKey) {
        List<BeanMedRec> mdeRecList = DataSupport.where("key = ?", mdrKey).find(BeanMedRec.class);
        //Log.e("返回病历信息 ", mdeRecList.get(0).getDiseaseInfo());
        return mdeRecList.get(0).getDiseaseInfo();
        //return null;
    }

    // 点击病历内容跳转到病历
    private void goToMedRec(String mdrKey) {
        Intent intent = new Intent(MyApplication.getContetxt(), NewMedRec.class);
        intent.putExtra("MdrKey", mdrKey);
        intent.putExtra("Lable", "HealthyChat");
        this.mActivity.startActivity(intent);
    }

    /**
     * 动态修改发送状态（加载、失败、成功）
     *
     * @param holder
     * @param bean
     */
    private void statusOfLoadingOrFailureOrSuccess(ViewHolder holder, ImMsgBean bean) {
        holder.iv_failure_send.setVisibility(View.INVISIBLE);
        holder.iv_loading.setVisibility(View.INVISIBLE);
        holder.iv_loading.clearAnimation();

        if (!bean.isSuccess()) {
            holder.iv_failure_send.setVisibility(View.VISIBLE);
        }

        holder.iv_loading.setVisibility(View.INVISIBLE);
        if (bean.isLoading()) {
            holder.iv_loading.setVisibility(View.VISIBLE);
            Animation rotate = AnimationUtils.loadAnimation(mActivity, R.anim.rotate_anim);
            if (rotate != null) {
                holder.iv_loading.startAnimation(rotate);
            } else {
                holder.iv_loading.setAnimation(rotate);
                holder.iv_loading.startAnimation(rotate);
            }
        } else {
            holder.iv_loading.setVisibility(View.INVISIBLE);
            holder.iv_loading.clearAnimation();
        }
    }

    /**
     * 设置图片预览的路径，本地或者网络
     * 如果图片在本地不全有，则直接加载网络的图片预览
     *
     * @param imagePath
     */
    private void preview(String imagePath) {
        PhotoPreviewIntent intent = new PhotoPreviewIntent(mActivity);
        List<String> imagePaths = new ArrayList<>();
        imagePaths.add(imagePath);
        intent.setPhotoPaths((ArrayList) imagePaths);
        intent.setCurrentItem(0);
        mActivity.startActivity(intent);
    }
}