package com.example.anelse.scaleview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


public class CustomScaleView extends ViewGroup {
    private float minScale = 0.0f;//最小刻度值
    private float maxScale = 0.0f;//最大刻度值
    private float currentScale = 0.0f;//当前刻度
    private int scaleImageW = 0;
    private boolean isFirstSet=true;

    public CustomScaleView(Context context) {
        super(context);
    }

    public CustomScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);

        measureChildren(widthMeasureSpec, heightMeasureSpec);

        int mWidth = 0;
        int mHeight = 0;
        int widthC1 = 0;
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            int cWidth = childView.getMeasuredWidth();
            int cHeight = childView.getMeasuredHeight();
            if (i == 0) {
                widthC1 = cWidth;
            } else {
                scaleImageW = cWidth;
            }
            mWidth = Math.max(mWidth, cWidth);
            mHeight = mHeight + cHeight;
        }

        mWidth = mWidth + widthC1;
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? sizeWidth
                : mWidth, (heightMode == MeasureSpec.EXACTLY) ? sizeHeight
                : mHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int startTop = 0;
        int startL = 0;
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            if (i == 0) {
                int left = 0;
                if (currentScale <= minScale) {
                    left = 0;
                } else if (currentScale >= maxScale) {
                    left = scaleImageW;
                } else {
                    left = (int) ((currentScale - minScale) / (maxScale - minScale) * scaleImageW);
                }
                childView.layout(left, 0, left + childView.getMeasuredWidth(), childView.getMeasuredHeight());
                startTop = childView.getMeasuredHeight();
                startL = childView.getMeasuredWidth() / 2;
            } else {
                childView.layout(startL, startTop, startL + childView.getMeasuredWidth(), startTop + childView.getMeasuredHeight());
                startTop = startTop + childView.getMeasuredHeight();
            }

        }
    }


    public float getMinScale() {
        return minScale;
    }

    public void setMinScale(float minScale) {
        this.minScale = minScale;
    }

    public float getMaxScale() {
        return maxScale;
    }

    public void setMaxScale(float maxScale) {
        this.maxScale = maxScale;
    }

    public float getCurrentScale() {
        return currentScale;
    }

    public void setCurrentScale(float currentScale) {
        this.currentScale = currentScale;
        if(isFirstSet){
            isFirstSet=false;
        }else{
            requestLayout();
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
