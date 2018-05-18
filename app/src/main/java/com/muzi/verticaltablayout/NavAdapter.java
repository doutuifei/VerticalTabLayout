package com.muzi.verticaltablayout;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by muzi on 2018/5/9.
 * 727784430@qq.com
 */
public class NavAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public NavAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.tabText, item);
    }

}
