package com.example.xmlparse;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PULLService {

    public List<AppInfo> getAppInfos(InputStream inputStream) throws Exception {
        XmlPullParser parser = Xml.newPullParser();
        List<AppInfo> appInfos = null;
        AppInfo appInfo = null;
        parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
        parser.setInput(inputStream, null);
        //现在parser处于 XmlPullParser.START_DOCUMENT
        if (parser.getEventType() == XmlPullParser.START_DOCUMENT) {
            System.out.println("====Start Document====");
        }
        String name = "";
        while ( parser.next() != XmlPullParser.END_DOCUMENT) {
            switch (parser.getEventType()) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if(name.equals("topapps")){
                        appInfos = new ArrayList<>();
                    }else if (name.equals("appinfo")){
                        appInfo = new AppInfo();
                    }else if(name.equals("rank")){
                        appInfo.setRank(Integer.parseInt(parser.nextText()));
                    }else if(name.equals("name")){
                        appInfo.setName(parser.nextText());
                    }else if(name.equals("package")){
                        appInfo.setCom(parser.nextText());
                    }else if(name.equals("clazz")){
                        appInfo.setClazz(parser.nextText());
                    }
                    else if(name.equals("downloadurl")){
                        appInfo.setDownloadurl(parser.nextText());
                    }
                    break;

                case XmlPullParser.END_TAG:
                    if (parser.getName().equals("appinfo")){
                        appInfos.add(appInfo);
                    }

                    break;
                default:
                    break;
            }
        }
        //现在parser处于 XmlPullParser.END_DOCUMENT
        if (parser.getEventType() == XmlPullParser.END_DOCUMENT) {
            System.out.println("====End Document====");
        }

        parser.next();//到  END_DOCUMENT后就一直是END_DOCUMENT

        return appInfos;
    }
}