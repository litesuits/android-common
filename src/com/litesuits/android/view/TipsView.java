package com.litesuits.android.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * @author MaTianyu
 * @date 2014-08-15
 */
public class TipsView extends LinearLayout {
    protected View realView;
    protected ViewGroup parentView;

    protected Context context;
    protected boolean isThisInLayout = false;

    public TipsView(Context context) {
        this(context, (AttributeSet) null);
    }

    public TipsView(Context context, AttributeSet attrs) {
        this(context, attrs, null, null);
    }

    public TipsView(Context context, View realView, View tipView) {
        this(context, null, realView, tipView);
    }

    public TipsView(Context context, View realView) {
        this(context, null, realView, null);
    }

    public TipsView(Context context, AttributeSet attrs, View realView, View tipView) {
        super(context, attrs);
        this.context = context;
        initSelf();
        setRealView(realView);
        setTipView(tipView);
    }

    public void initSelf() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
    }

    public TipsView setTipView(View tipView) {
        if (tipView != null) {
            removeAllViews();
            addView(tipView);
        }
        return this;
    }

    public TipsView setTipView(int resID) {
        removeAllViews();
        LayoutInflater.from(context).inflate(resID, this, true);
        return this;
    }


    public View getRealView() {
        return realView;
    }


    public TipsView setRealView(View realView) {
        if (realView != null) {
            showRealView();
            this.realView = realView;
            parentView = (ViewGroup) realView.getParent();
            setLayoutParams(realView.getLayoutParams());
        }
        return this;
    }


    public void showRealView() {
        if (realView != null && parentView != null) {
            if (isThisInLayout) {
                for (int i = 0, size = parentView.getChildCount(); i < size; i++) {
                    if (parentView.getChildAt(i) == this) {
                        parentView.removeView(this);
                        //realView.setId(getId());
                        //setId(0);
                        parentView.addView(realView, i);
                        isThisInLayout = false;
                        break;
                    }
                }
            }
        }
    }

    public void showTipsView() {
        if (realView != null && parentView != null) {
            if (!isThisInLayout) {
                for (int i = 0, size = parentView.getChildCount(); i < size; i++) {
                    if (parentView.getChildAt(i) == realView) {
                        parentView.removeView(realView);
                        //setId(realView.getId());
                        //realView.setId(0);
                        parentView.addView(this, i);
                        isThisInLayout = true;
                        break;
                    }
                }
            }
        }
    }

    public void gone() {
        setVisibility(GONE);
    }

    public void visible() {
        setVisibility(VISIBLE);
    }

    public void inVisible() {
        setVisibility(INVISIBLE);
    }
}
