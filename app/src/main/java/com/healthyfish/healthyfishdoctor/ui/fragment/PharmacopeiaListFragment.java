package com.healthyfish.healthyfishdoctor.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.healthyfish.healthyfishdoctor.POJO.BeanPharmacopeia;
import com.healthyfish.healthyfishdoctor.R;
import com.healthyfish.healthyfishdoctor.adapter.PharmacopeiaExplvAdapter;
import com.healthyfish.healthyfishdoctor.ui.activity.pharmacopeia.ListOfCertainPharmacopoeia;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class PharmacopeiaListFragment extends Fragment {
    @BindView(R.id.explv_pharmacopia)
    ExpandableListView explvPharmacopia;
    Unbinder unbinder;
    private List<BeanPharmacopeia> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pharmacopeia_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {

        List<String>  chils1 = new ArrayList<>();
        chils1.add("抗疟原虫");
        chils1.add("抗蠕虫");
        chils1.add("抗阿米巴及滴虫");
        BeanPharmacopeia b = new BeanPharmacopeia("抗感染",chils1);
        List<String>  chils2 = new ArrayList<>();
        chils2.add("抗疟原虫2");
        chils2.add("抗蠕虫2");
        chils2.add("抗阿米巴及滴虫2");
        BeanPharmacopeia b2 = new BeanPharmacopeia("神经系统",chils2);
        List<String>  chils3 = new ArrayList<>();
        chils3.add("抗疟原虫3");
        chils3.add("抗蠕虫3");
        chils3.add("抗阿米巴及滴虫3");
        BeanPharmacopeia b3 = new BeanPharmacopeia("神经系统",chils3);
        list.add(b);
        list.add(b2);
        list.add(b3);
        PharmacopeiaExplvAdapter adapter = new PharmacopeiaExplvAdapter(getActivity(),list);
        explvPharmacopia.setAdapter(adapter);
//        int intgroupCount = explvPharmacopia.getCount();
//        for (int i=0; i<intgroupCount; i++)
//        {
//            explvPharmacopia.expandGroup(i);
//        };


        explvPharmacopia.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0, count = explvPharmacopia.getExpandableListAdapter().getGroupCount(); i < count; i++) {
                    if (groupPosition != i) {// 关闭其他分组
                        explvPharmacopia.collapseGroup(i);
                    }
                }
            }
        });
        explvPharmacopia.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //Toast.makeText(getActivity(),list.get(groupPosition).getList().get(childPosition),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), ListOfCertainPharmacopoeia.class);
                startActivity(intent);
                return true;
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
