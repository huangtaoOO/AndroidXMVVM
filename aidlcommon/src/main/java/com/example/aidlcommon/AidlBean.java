package com.example.aidlcommon;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author: tao
 * @time: 2020/9/18
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class AidlBean implements Parcelable {

    private String string ;

    private List<String> list ;

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    public List<String> getList() {
        return list;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        String lis ;
        if (list!=null){
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for (String s:list) {
                sb.append(s);
                sb.append(",");
            }
            sb.append("]");
            lis = sb.toString();
        }else {
            lis = "[ null ]";
        }

        return "AidlBean{" +
                "string='" + string + '\'' +
                ", list=" + lis +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.string);
        dest.writeStringList(this.list);
    }

    public AidlBean() {
    }

    protected AidlBean(Parcel in) {
        this.string = in.readString();
        this.list = in.createStringArrayList();
    }

    public static final Parcelable.Creator<AidlBean> CREATOR = new Parcelable.Creator<AidlBean>() {
        @Override
        public AidlBean createFromParcel(Parcel source) {
            return new AidlBean(source);
        }

        @Override
        public AidlBean[] newArray(int size) {
            return new AidlBean[size];
        }
    };
}
