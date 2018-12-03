package com.example.renzhili20181203;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

public class FlowLayout extends LinearLayout {
    //最高的孩子
    private int mChildMaxHeight;
    //左右间距
    private int mHSpace=20;
    private int mVSpace=20;

    public FlowLayout(Context context) {
        super(context);

    }
    public FlowLayout(Context context,AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //父元素的框
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        //测量孩子的大小
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //寻找最高的孩子
        findMaxHeightChild();
        //初始化
        int left =0;
        int top=0;
        //得到所有孩子并循环
        int childCount = getChildCount();
        for (int i=0;i<childCount;i++){
            View view = getChildAt(i);
            if (left!=0){
                if ((left+view.getMeasuredWidth())>widthSize){
                    top+=view.getMeasuredHeight()+mVSpace;
                    left=0;
                }
            }
            left+=view.getMeasuredWidth()+mHSpace;
        }
        setMeasuredDimension(widthSize,(top+mChildMaxHeight)>heightSize?heightSize:top+mChildMaxHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //寻找最高的孩子
        findMaxHeightChild();
        //初始化
        int left =0;
        int top=0;
        //得到所有孩子并循环
        int childCount = getChildCount();
        for (int i=0;i<childCount;i++){
            View view = getChildAt(i);
            if (left!=0){
                if ((left+view.getMeasuredWidth())>getWidth()){
                    top+=view.getMeasuredHeight()+mVSpace;
                    left=0;
                }
            }
            view.layout(left,top,left+view.getMeasuredWidth(),top+mChildMaxHeight);
            left+=view.getMeasuredWidth()+mHSpace;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    private void findMaxHeightChild() {
        mChildMaxHeight=0;
        int childCount = getChildCount();
        for (int i = 0 ; i <childCount; i++){
            View view = getChildAt(i);
            if (view.getMeasuredHeight()>mChildMaxHeight){
                mChildMaxHeight=view.getMeasuredHeight();
            }
        }
    }
}
