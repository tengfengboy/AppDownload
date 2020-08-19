package com.example.xmlparse.test4;

import java.util.ArrayList;
import java.util.List;

public class DPosition {
    public static List<Integer> dPositions;
    public static List<Integer> dtasks;

    private DPosition(){};

    public static DPosition mInstance;

    public static DPosition getInstance() {//管理器初始化
        if (mInstance == null) {
            synchronized (DownloadManager.class) {
                if (mInstance == null) {
                    mInstance = new DPosition();
                    mInstance.dPositions = new ArrayList<>();
                    mInstance.dtasks = new ArrayList<>();
                }
            }
        }
        return mInstance;
    }


}
