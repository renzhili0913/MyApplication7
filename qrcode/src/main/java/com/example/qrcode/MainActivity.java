package com.example.qrcode;

import android.Manifest;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import cn.bingoogolapple.qrcode.zxing.QRCodeEncoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class MainActivity extends AppCompatActivity {
    private Button scanning_code,generate_code;
    private ImageView imageView;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取资源id
        scanning_code=findViewById(R.id.scanning_code);
        generate_code=findViewById(R.id.generate_code);
        imageView=findViewById(R.id.image);
        editText=findViewById(R.id.edit_text);
        //扫描二维码的点击事件
        scanning_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断android版本号是否是6.0以上，（Build.VERSION_CODES.M：表示版本号是6.0）
                if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                    //checkSelfPermission判断当前有没有第二个参数所代表的权限，当前权限为相机权限
                    //如果与给定条件不同，这没有此权限。需要授权
                    if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){

                        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},100);

                    }  else{
                        //如果版本是6.0以上，且条件满足，授权，直接跳转
                        Intent intent = new Intent(MainActivity.this,CodeActivity.class);
                        startActivity(intent);
                    }
                }else{
                    //版本低于6.0，直接跳转，通过清单文件配置请求权限
                    Intent intent = new Intent(MainActivity.this,CodeActivity.class);
                    startActivity(intent);
                }

            }
        });
        //生成二维码的点击事件
        generate_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRTask qrTask = new QRTask(MainActivity.this,imageView);
                qrTask.execute(editText.getText().toString());
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==100&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
            startActivity(new Intent(MainActivity.this, CodeActivity.class));
        }
    }
    //创建静态内部类处理文本框获取到的字符串，并生成二维码赋值与imageview展示
    static class QRTask extends AsyncTask<String,Void,Bitmap>{
        //软引用类型
        private WeakReference<Context> mContent;
        private WeakReference<ImageView> mImageView;

        public QRTask(Context content,ImageView imageView ) {
            mContent=new WeakReference<>(content);
            mImageView=new WeakReference<>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String str = strings[0];
            if (TextUtils.isEmpty(str)){
                return null;
            }
            //软应用只能通过get()获取到相应的方法，返回要生成的二维码的尺寸大小
            int size = mContent.get().getResources().getDimensionPixelOffset(R.dimen.qr_code_size);
            //返回生成的二维码图片
            return QRCodeEncoder.syncEncodeQRCode(str,size);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            //生成的二维码不为空就直接赋值与imageview上
            if (bitmap!=null){
                mImageView.get().setImageBitmap(bitmap);
            }else{
                Toast.makeText(mContent.get(),"生成失败",Toast.LENGTH_SHORT).show();
            }
        }
    }


}
