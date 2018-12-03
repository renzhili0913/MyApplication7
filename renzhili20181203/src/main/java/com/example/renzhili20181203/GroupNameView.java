package com.example.renzhili20181203;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

@SuppressLint("AppCompatCustomView")
public class GroupNameView extends TextView {
    public GroupNameView(Context context) {
        super(context);
    }

    public GroupNameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GroupNameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.TitleBarView);
        int color = typedArray.getColor(R.styleable.TitleBarView_textColor, Color.RED);
        int integer = typedArray.getInteger(R.styleable.TitleBarView_textSize, 20);
        setTextColor(color);
        setTextSize(integer);
        typedArray.recycle();

    }
}
