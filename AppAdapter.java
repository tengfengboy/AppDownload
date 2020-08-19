package com.example.xmlparse;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.xmlparse.test4.DPosition;
import com.example.xmlparse.test4.DownloadListner;
import com.example.xmlparse.test4.DownloadManager;


import java.util.List;


public class AppAdapter extends ArrayAdapter {

    DownloadManager mDownloadManager;
    private int resouceId;
    Context context;
    String TAG = "getView";
    List<Integer> positions = DPosition.getInstance().dPositions;
    ListView listView ;
    float tem_progress;
    public AppAdapter(@NonNull Context context, int textViewResourceId, List<AppInfo> list) {
        super(context, textViewResourceId, list);
        resouceId = textViewResourceId;
        this.context = context;
    }

    class ViewHolder{
        TextView tv_progress1;
        ProgressBar pb_progress1;
        TextView appName;
        Button btn_cancel;
        Button btn_download1;
        boolean isDownLoad;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final AppInfo appInfo = (AppInfo) getItem(position);
        View view;
        listView = (ListView)parent;
        mDownloadManager = DownloadManager.getInstance();
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resouceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.appName = view.findViewById(R.id.appName);
            viewHolder.tv_progress1 = view.findViewById(R.id.tv_progress1);
            viewHolder.pb_progress1 = view.findViewById(R.id.pb_progress1);
            viewHolder.btn_download1 = view.findViewById(R.id.btn_download1);
            viewHolder.btn_cancel = view.findViewById(R.id.btn_cancel1);
            view.setTag(viewHolder);
        }else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();

            //如果在下载队列中存在下载，就显示出来
//            if (positions!=null){
//                if (!positions.contains(position)){
//                    viewHolder.pb_progress1.setVisibility(View.INVISIBLE);
//                    viewHolder.tv_progress1.setVisibility(view.INVISIBLE);
//                }else {
//                    viewHolder.pb_progress1.setVisibility(View.VISIBLE);
//                    viewHolder.tv_progress1.setVisibility(view.VISIBLE);
//                }
//            }

//            if (!mDownloadManager.isDownloading(appInfo.getDownloadurl())){
//
//            }
        }







        if (tem_progress>0){
            System.out.println(position+viewHolder.toString());
            pauseInit(position,viewHolder);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btn_download1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initDownloads(position,appInfo.getDownloadurl(),finalViewHolder,appInfo.getName());
                if(!DPosition.dtasks.contains(position)){
                    DPosition.dtasks.add(position);
                }
                if (!mDownloadManager.isDownloading(position)) {
                    mDownloadManager.download(position);
                    finalViewHolder.btn_download1.setText("暂停");

                } else {
                    finalViewHolder.btn_download1.setText("下载");
                    mDownloadManager.pause(position);
                }
            }
        });

        viewHolder.btn_cancel.setOnClickListener(new View.OnClickListener() {
            
            @Override
            public void onClick(View view) {

                Log.d(TAG, "onClick: 取消下载");
                mDownloadManager.cancel(position,appInfo.getDownloadurl());
                DPosition.dtasks.remove(position-1);

            }
        });
        viewHolder.appName.setText(appInfo.getName());
        return view;

    }



    private  void pauseInit(int position, ViewHolder viewHolder){
        System.out.println(listView.getFirstVisiblePosition()+"---"+listView.getLastVisiblePosition()+"---"+position);
        System.out.println(tem_progress);

        for (int i:DPosition.dtasks){
            System.out.println(i);
            if (i>=(listView.getFirstVisiblePosition())&&i<=(listView.getLastVisiblePosition())){
                mDownloadManager.getDownloadTask(i).getmListner().onProgress(tem_progress);

            }else{
                viewHolder.pb_progress1.setProgress(0);
                viewHolder.tv_progress1.setText(0 +"%");
            }

        }
       // mDownloadManager.getDownloadTask(position).getmListner().onProgress(tem_progress);
//        if (position>=(listView.getFirstVisiblePosition())&&position<=(listView.getLastVisiblePosition())){
//            System.out.println(tem_progress);
//            viewHolder.pb_progress1.setProgress((int) (tem_progress * 100));
//            viewHolder.tv_progress1.setText(String.format("%.2f", tem_progress * 100) + "%");
//
//
//
//        }else{
//            viewHolder.pb_progress1.setProgress(0);
//            viewHolder.tv_progress1.setText(0 +"%");
//        }
    }



    private void initDownloads(final int position, String url, final ViewHolder viewHolder, String name) {
        if (DPosition.dPositions!=null){
            if(DPosition.dPositions.contains(position)){
                return;
            }else {
                DPosition.dPositions.add(position);
                mDownloadManager.add(position,url,null,name,new DownloadListner() {
                    @Override
                    public void onFinished() {
                        Toast.makeText(context, "下载完成!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProgress(float progress) {


                        System.out.println(listView.getFirstVisiblePosition()+"---"+listView.getLastVisiblePosition()+"---"+position);
                        if (position>=listView.getFirstVisiblePosition()&&position<=listView.getLastVisiblePosition()){
                            System.out.println(viewHolder);
                            viewHolder.pb_progress1.setProgress((int) (progress * 100));
                            viewHolder.tv_progress1.setText(String.format("%.2f", progress * 100) + "%");
                            tem_progress = progress;
                        }else{
                            viewHolder.pb_progress1.setProgress(0);
                            viewHolder.tv_progress1.setText(0 +"%");
                        }

                    }

                    @Override
                    public void onPause() {

                        Toast.makeText(context, "暂停了!", Toast.LENGTH_SHORT).show();
                    }


                    @Override
                    public void onCancel() {
                        viewHolder.tv_progress1.setText("0%");
                        viewHolder.pb_progress1.setProgress(0);
                        viewHolder.btn_download1.setText("下载");
                        Toast.makeText(context, "下载已取消!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }



    }




}



