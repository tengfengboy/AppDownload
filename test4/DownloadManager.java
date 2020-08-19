package com.example.xmlparse.test4;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DownloadManager {
    private String DEFAULT_FILE_DIR;//默认下载目录
    private Map<Integer, DownloadTask> mDownloadTasks;//文件下载任务索引，String为url,用来唯一区别并操作下载的文件
    private static DownloadManager mInstance;
    private static final String TAG = "DownloadManager";
    /**
     * 下载文件
     */
    public DownloadTask getDownloadTask(int positon){
        return mDownloadTasks.get(positon);
    }
    public void download(int... urls) {
        //单任务开启下载或多任务开启下载
        for (int i = 0, length = urls.length; i < length; i++) {
            int url = urls[i];
            if (mDownloadTasks.containsKey(url)) {
                mDownloadTasks.get(url).start();
            }
        }
    }


    // 获取下载文件的名称
    public String getFileName(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }

    /**
     * 暂停
     */
    public void pause(int... positions) {
        //单任务暂停或多任务暂停下载
        for (int i = 0, length = positions.length; i < length; i++) {
            int position = positions[i];
            if (mDownloadTasks.containsKey(position)) {
                mDownloadTasks.get(position).pause();

            }
        }
    }

    /**
     * 取消下载
     */
    public void cancel(int position,String... urls) {
        //单任务取消或多任务取消下载
        for (int i = 0, length = urls.length; i < length; i++) {
            String url = urls[i];
            Log.d(TAG, "cancel: 取消下载"+position);
            if (mDownloadTasks.containsKey(position)) {

                Log.e(TAG, "position"+position );
                Log.e(TAG, "cancel: "+mDownloadTasks.get(position));
                mDownloadTasks.get(position).cancel();
            }
        }
    }

    /**
     * 添加下载任务
     */
    public void add(int position,String url, String filePath, String fileName, DownloadListner l) {
        if (TextUtils.isEmpty(filePath)) {//没有指定下载目录,使用默认目录
            filePath = getDefaultDirectory();
        }
        if (TextUtils.isEmpty(fileName)) {
            fileName = getFileName(url);
        }
        mDownloadTasks.put(position, new DownloadTask(new FilePoint(url, filePath, fileName), l));
    }

    /**
     * 默认下载目录
     * @return
     */
    private String getDefaultDirectory() {
        if (TextUtils.isEmpty(DEFAULT_FILE_DIR)) {
            DEFAULT_FILE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath()
                   + File.separator + "Download" + File.separator;

            System.out.println(DEFAULT_FILE_DIR);
        }
        return DEFAULT_FILE_DIR;
    }

    public static DownloadManager getInstance() {//管理器初始化
        if (mInstance == null) {
            synchronized (DownloadManager.class) {
                if (mInstance == null) {
                    mInstance = new DownloadManager();
                }
            }
        }
        return mInstance;
    }

    public DownloadManager() {
        mDownloadTasks = new HashMap<>();
    }

    /**
     * 取消下载
     */
    public boolean isDownloading(int... positions) {
        //这里传一个url就是判断一个下载任务
        //多个url数组适合下载管理器判断是否作操作全部下载或全部取消下载
        boolean result = false;
        for (int i = 0, length = positions.length; i < length; i++) {
            int position = positions[i];
            if (mDownloadTasks.containsKey(position)) {
                result = mDownloadTasks.get(position).isDownloading();
            }
        }
        return result;
    }
}
