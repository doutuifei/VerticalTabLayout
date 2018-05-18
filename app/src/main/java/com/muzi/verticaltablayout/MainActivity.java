package com.muzi.verticaltablayout;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.indicatorView)
    IndicatorView indicatorView;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private NavAdapter adapter;
    private LinearLayoutManager manager;
    private List<String> list = new ArrayList<>();

    private int currPosition = 0;
    private SubjectFragment fragment;
    private SubjectFragment[] fragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initDate();

        adapter = new NavAdapter(R.layout.item_nav_class, list);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (currPosition != position) {
                    //切换fragment
                    changeFragment(position);
                    currPosition = position;
                }
                indicatorView.openAnimator(view);
            }
        });

        //默认加载第一个fragment
        fragment = SubjectFragment.newInstance(list.get(0));
        changeFragment(currPosition);

        //设置indicatorView初始位置
        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        View child = manager.findViewByPosition(0);
                        if (child != null) {
                            indicatorView.openAnimator(child);
                        }
                    }
                });
            }
        });
    }

    /**
     * 加载fragment
     *
     * @param position
     */
    private void changeFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragments[currPosition] != null) {
            transaction.hide(fragments[currPosition]);
        }
        fragment = fragments[position];
        if (fragment == null) {
            fragment = SubjectFragment.newInstance(list.get(position));
            fragments[position] = fragment;
            transaction.add(R.id.fragmentContainer, fragment);
        } else {
            transaction.show(fragment);
        }
        transaction.commitAllowingStateLoss();
    }


    private void initDate() {
        list.add("数学");
        list.add("语文");
        list.add("英语");
        list.add("化学");
        list.add("物理");
        list.add("生物");
        list.add("低劣");
        fragments = new SubjectFragment[list.size()];
    }
}
