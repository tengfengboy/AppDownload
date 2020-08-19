package com.example.xmlparse;

public class AppInfo {
    int rank;
    String name;
    String com;
    String clazz;
    String downloadurl;

    public AppInfo(int rank, String name, String com, String clazz, String downloadurl) {
        this.rank = rank;
        this.name = name;
        this.com = com;
        this.clazz = clazz;
        this.downloadurl = downloadurl;
    }

    public AppInfo() {
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "rank=" + rank +
                ", name='" + name + '\'' +
                ", com='" + com + '\'' +
                ", clazz='" + clazz + '\'' +
                ", downloadurl='" + downloadurl + '\'' +
                '}';
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCom() {
        return com;
    }

    public void setCom(String com) {
        this.com = com;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }
}
