package com.example.renzhili20181203;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bean.UserBean;
import com.example.dao.UserDao;

import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private FlowLayout fl_search,fl_hot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        //获取资源id
        fl_search = findViewById(R.id.fl_search);
        fl_hot = findViewById(R.id.fl_hot);
        TitleBarView titleBarView = findViewById(R.id.title);

       List<UserBean> select= UserDao.getInsanner(MainActivity.this).select();
       if (select.size()>0) {
           for (UserBean bean : select
                   ) {
               TextView textView = new TextView(MainActivity.this);
               textView.setText(bean.getName());
               textView.setBackgroundResource(R.drawable.shape);
               fl_search.addView(textView);
               init(textView,bean.getUuid());
           }
       }

        titleBarView.setOnButtonClickListener(new TitleBarView.onButtonClickListener() {
            @Override
            public void onButtonClick(String str) {
                TextView textView = new TextView(MainActivity.this);
                UUID  uuid = UUID.randomUUID();
                textView.setTag(uuid);
                String s = String.valueOf(textView.getTag());
                UserDao.getInsanner(MainActivity.this).add(s,str);
                textView.setText(str);
                textView.setBackgroundResource(R.drawable.shape);
                fl_search.addView(textView);
                init(textView,s);
            }
        });
        String[] meuns = {"考拉山周年人气","电动牙刷","由你哇","豆豆先","沐浴露","日东红茶","榴莲"};
        for (int i=0;i<meuns.length;i++){
            TextView textView = new TextView(MainActivity.this);
            UUID  uuid = UUID.randomUUID();
            textView.setTag(uuid);
            String s = String.valueOf(textView.getTag());
            textView.setText(meuns[i]);
            textView.setBackgroundResource(R.drawable.shape);
            fl_hot.addView(textView);
            init(textView,s);
        }
    }

    private void init(final TextView textView, String s) {
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,textView.getText(),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
