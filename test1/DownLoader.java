package com.example.xmlparse.test1;

import android.app.DownloadManager;
import android.net.Uri;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class DownLoader extends AppCompatActivity {


    public DownloadManager downloadManager;
    long id;

    public void downLoadApk(String url,String appName) {
        //创建request对象
        DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
        if (request!=null){
            Log.d("request", request.toString());
            //设置什么网络情况下可以下载
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE);
            //设置通知栏的标题
            request.setTitle("下载");
            //设置通知栏的message
            request.setDescription(appName+"正在下载.....");
            //设置漫游状态下是否可以下载
            request.setAllowedOverRoaming(true);
            //设置文件存放目录
            request.setDestinationInExternalPublicDir("Download", "mobileqq_android.apk");
            //获取系统服务

            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
            request.setVisibleInDownloadsUi(true);
            //进行下载
            id = downloadManager.enqueue(request);
        }else {
            System.out.println("request 为空");
        }

    }

}
