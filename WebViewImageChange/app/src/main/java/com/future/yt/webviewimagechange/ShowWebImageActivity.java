package com.future.yt.webviewimagechange;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class ShowWebImageActivity extends Activity {
    private TextView imageTextView = null;
    private String imagePath = null;
    private ZoomableImageView imageView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_webimage);
        this.imagePath = getIntent().getStringExtra("image");

        this.imageTextView = (TextView) findViewById(R.id.show_webimage_imagepath_textview);
        imageTextView.setText(this.imagePath);
        imageView = (ZoomableImageView) findViewById(R.id.show_webimage_imageview);


    }

    public static Drawable loadImageFromUrl(String url) throws IOException {

        URL m = new URL(url);
        InputStream i = (InputStream) m.getContent();
        Drawable d = Drawable.createFromStream(i, "src");
        return d;
    }

    private Bitmap mBitMap;

    class DownloadTask extends AsyncTask<Integer, Integer, String> {
        //后面尖括号内分别是参数（例子里是线程休息时间），进度(publishProgress用到)，返回值 类型

        @Override
        protected void onPreExecute() {
            //第一个执行方法
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Integer... params) {
            //第二个执行方法,onPreExecute()执行完后执行
            try {
                mBitMap = ((BitmapDrawable) ShowWebImageActivity.loadImageFromUrl(imagePath)).getBitmap();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "执行完毕";
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            //这个函数在doInBackground调用publishProgress时触发，虽然调用时只有一个参数
            //但是这里取到的是一个数组,所以要用progesss[0]来取值
            //第n个参数就用progress[n]来取值
            super.onProgressUpdate(progress);
        }

        @Override
        protected void onPostExecute(String result) {
            //doInBackground返回时触发，换句话说，就是doInBackground执行完后触发
            //这里的result就是上面doInBackground执行后的返回值，所以这里是"执行完毕"
            super.onPostExecute(result);
            imageView.setImageBitmap(mBitMap);


        }

    }
}
