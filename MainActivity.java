package com.example.xmlparse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PULLService pullService = new PULLService();
        try {
            InputStream inputStream = getAssets().open("info.xml");
            List<AppInfo> list = pullService.getAppInfos(inputStream);
            ArrayAdapter adapter = new AppAdapter(MainActivity.this,R.layout.app_item,list);
            ListView listView = (ListView)findViewById(R.id.list_view);
            listView.setAdapter(adapter);

        }catch (Exception e){

        }

    }



}