package com.muzi.verticaltablayout;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * Created by muzi on 2018/4/13.
 * 727784430@qq.com
 */

public class IndicatorView extends View {

    private View oldView;

    public IndicatorView(Context context) {
        super(context);
    }

    public IndicatorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * 开启Indicator动画
     *
     * @param view
     */
    public void openAnimator(View view) {
        if (view == null || view.equals(oldView)) {
            return;
        }
        if (oldView == null) {
            oldView = view;
        }
        if (getVisibility() != View.VISIBLE) {
            setVisibility(View.VISIBLE);
        }
        float oldY = calculateViewCenter(oldView);
        float newY = calculateViewCenter(view);

        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "TranslationY", oldY, newY);
        animator.setDuration(500);
        animator.setInterpolator(new OvershootInterpolator());
        animator.start();

        oldView = view;
    }

    /**
     * 计算位移距离
     *
     * @param view
     * @return
     */
    private float calculateViewCenter(View view) {
        int tabHeight = getHeight();
        int viewHeight = view.getHeight();
        int viewTop = view.getTop();
        return 1f * (viewTop + viewHeight / 2 - tabHeight / 2);
    }

}
