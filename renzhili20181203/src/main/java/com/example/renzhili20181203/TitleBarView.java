package com.example.renzhili20181203;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class TitleBarView extends LinearLayout {
    private Context context;
    public TitleBarView(Context context) {
        super(context);
        this.context=context;
        init();
    }

    public TitleBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        init();
    }

    private void init() {
        //获取布局
        View view = View.inflate(context,R.layout.title,null);
        //获取资源id
        final EditText editText= view.findViewById(R.id.edit_text);
        //点击事件
        view.findViewById(R.id.image_button).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onButtonClickListener!=null){
                    String trim = editText.getText().toString().trim();
                    if (trim.equals("")){
                        return;
                    }else{
                        onButtonClickListener.onButtonClick(trim);
                    }
                }
            }
        });
        addView(view);
    }
    onButtonClickListener onButtonClickListener;
    public void setOnButtonClickListener(onButtonClickListener onButtonClickListener){
        this.onButtonClickListener=onButtonClickListener;
    }
    //创建接口
    public interface onButtonClickListener{
        void onButtonClick(String str);
    }

}
