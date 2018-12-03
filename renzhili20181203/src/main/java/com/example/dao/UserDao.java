package com.example.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.bean.UserBean;
import com.example.database.MySqlite;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private  static  UserDao insanner;
    private final SQLiteDatabase sb;

    public UserDao(Context context) {
        sb = new MySqlite(context).getWritableDatabase();
    }

    public static UserDao getInsanner(Context context) {
        if (insanner==null){
            insanner=new UserDao(context);
        }
        return insanner;
    }

    public void add(String s, String str) {
        ContentValues values=new ContentValues();
        values.put("uuid",s);
        values.put("name",str);
        sb.insert("users",null,values);
    }

    public List<UserBean> select() {
        List<UserBean> list = new ArrayList<>();
        Cursor users = sb.query("users", null, null, null, null, null, null);
        while (users.moveToNext()){
            String uuid = users.getString(users.getColumnIndex("uuid"));
            String name = users.getString(users.getColumnIndex("nmae"));
            list.add(new UserBean(uuid,name));
        }
        return list;
    }
}
