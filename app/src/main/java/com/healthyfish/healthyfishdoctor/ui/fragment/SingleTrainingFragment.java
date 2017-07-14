package com.healthyfish.healthyfishdoctor.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthyfish.healthyfishdoctor.POJO.BeanTrainingVideo;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.TrainingAdapter;

import java.util.List;

@SuppressLint("ValidFragment")
public class SingleTrainingFragment extends Fragment {
    Context mContext;
    private RecyclerView trainingRecyclerview;
    private List<BeanTrainingVideo> listTrainingVideo;

    public SingleTrainingFragment(Context mContext, List<BeanTrainingVideo> list) {
        this.mContext = mContext;
        this.listTrainingVideo = list;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_single_training, null);
        trainingRecyclerview = (RecyclerView) v.findViewById(R.id.training_recyclerview);

        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 1);
        trainingRecyclerview.setLayoutManager(layoutManager);
        TrainingAdapter adapter = new TrainingAdapter(mContext, listTrainingVideo);
        trainingRecyclerview.setAdapter(adapter);

        return v;
    }
}