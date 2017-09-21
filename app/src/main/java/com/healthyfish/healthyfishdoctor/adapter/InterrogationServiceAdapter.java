package com.healthyfish.healthyfishdoctor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.healthyfish.healthyfishdoctor.R;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 描述：问诊服务页面的适配器
 * 作者：LYQ on 2017/7/5.
 * 邮箱：feifanman@qq.com
 * 编辑：LYQ
 */

public class InterrogationServiceAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater inflater;
    private List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private final int TYPE_PictureConsulting = 0;
    private final int TYPE_PrivateDoctor = 1;

    public InterrogationServiceAdapter(Context mContext, List<Map<String, Object>> list) {
        super();
        this.list = list;
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.size() > 0) {
            String type = list.get(position).get("type").toString();
            if (type.equals("pictureConsulting"))
                return TYPE_PictureConsulting;
            else
                return TYPE_PrivateDoctor;
        }
        return -1;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderGraphicConsultation holderGraphicConsultation = null;
        ViewHolderPrivateDoctor holderPrivateDoctor = null;
        if (list.size() > 0) {
            int type = getItemViewType(position);
            if (convertView == null) {
                switch (type) {
                    case TYPE_PictureConsulting:
                        convertView = inflater.inflate(R.layout.item_graphic_consultation, parent, false);
                        holderGraphicConsultation = new ViewHolderGraphicConsultation(convertView);
                        convertView.setTag(holderGraphicConsultation);
                        AutoUtils.autoSize(convertView);
                        break;
                    case TYPE_PrivateDoctor:
                        convertView = inflater.inflate(R.layout.item_private_doctor, parent, false);
                        holderPrivateDoctor = new ViewHolderPrivateDoctor(convertView);
                        convertView.setTag(holderPrivateDoctor);
                        AutoUtils.autoSize(convertView);
                        break;
                    default:
                        break;
                }
            } else {
                switch (type) {
                    case TYPE_PictureConsulting:
                        holderGraphicConsultation = (ViewHolderGraphicConsultation) convertView.getTag();
                        break;
                    case TYPE_PrivateDoctor:
                        holderPrivateDoctor = (ViewHolderPrivateDoctor) convertView.getTag();
                        break;
                    default:
                        break;
                }
            }
            switch (type) {
                case TYPE_PictureConsulting:
                    String flagPictureConsulting[] = new String[]{"name", "hospital", "message", "time", "portrait", "isNew", "isSender"};
                    Glide.with(mContext).load((String) list.get(position).get(flagPictureConsulting[4])).into(holderGraphicConsultation.civPeerGraphicConsultation);
                    holderGraphicConsultation.tvPeerNameGraphicConsultation.setText((String) list.get(position).get(flagPictureConsulting[0]));
                    holderGraphicConsultation.tvHospitalGraphicConsultation.setText((String) list.get(position).get(flagPictureConsulting[1]));
                    holderGraphicConsultation.tvMessageGraphicConsultation.setText((String) list.get(position).get(flagPictureConsulting[2]));
                    holderGraphicConsultation.tvReceiveTimeGraphicConsultation.setText((String) list.get(position).get(flagPictureConsulting[3]));
                    if ("false" == list.get(position).get(flagPictureConsulting[5]).toString()){
                        holderGraphicConsultation.ivNewMsgGraphicConsultation.setVisibility(View.GONE);
                    } else if ("true" == list.get(position).get(flagPictureConsulting[5]).toString()
                            && "false" == list.get(position).get(flagPictureConsulting[6]).toString()) {
                        holderGraphicConsultation.ivNewMsgGraphicConsultation.setVisibility(View.VISIBLE);
                    }

                    break;
                case TYPE_PrivateDoctor:
                    String flagPrivateDoctor[] = new String[]{"name", "hospital", "message", "time"};
                    holderPrivateDoctor.civDoctorPrivateDoctor.setImageResource(R.mipmap.logo_240);
                    holderPrivateDoctor.tvDoctorNamePrivateDoctor.setText((String) list.get(position).get(flagPrivateDoctor[0]));
                    holderPrivateDoctor.tvHospitalPrivateDoctor.setText((String) list.get(position).get(flagPrivateDoctor[1]));
                    holderPrivateDoctor.tvMessagePrivateDoctor.setText((String) list.get(position).get(flagPrivateDoctor[2]));
                    holderPrivateDoctor.tvReceiveTime.setText((String) list.get(position).get(flagPrivateDoctor[3]));
                    break;
                default:
                    break;
            }
        }
        return convertView;
    }

    static class ViewHolderGraphicConsultation {
        @BindView(R.id.civ_peer_graphic_consultation)
        CircleImageView civPeerGraphicConsultation;
        @BindView(R.id.tv_peerName_graphic_consultation)
        TextView tvPeerNameGraphicConsultation;
        @BindView(R.id.tv_hospital_graphic_consultation)
        TextView tvHospitalGraphicConsultation;
        @BindView(R.id.tv_message_graphic_consultation)
        TextView tvMessageGraphicConsultation;
        @BindView(R.id.tv_receive_time_graphic_consultation)
        TextView tvReceiveTimeGraphicConsultation;
        @BindView(R.id.iv_new_msg_graphic_consultation)
        ImageView ivNewMsgGraphicConsultation;
        ViewHolderGraphicConsultation(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderPrivateDoctor {
        @BindView(R.id.civ_doctor_private_doctor)
        CircleImageView civDoctorPrivateDoctor;
        @BindView(R.id.tv_doctorName_private_doctor)
        TextView tvDoctorNamePrivateDoctor;
        @BindView(R.id.tv_hospital_private_doctor)
        TextView tvHospitalPrivateDoctor;
        @BindView(R.id.tv_message_private_doctor)
        TextView tvMessagePrivateDoctor;
        @BindView(R.id.tv_receive_time)
        TextView tvReceiveTime;

        ViewHolderPrivateDoctor(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
