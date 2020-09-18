// ITaoAidlInterface.aidl
package com.example.aidlcommon;

// Declare any non-default types here with import statements
import com.example.aidlcommon.AidlBean;

interface ITaoAidlInterface {

    int getInt();

    int getLong();

    boolean getBoolean();

    float getFloat();

    double getDouble();

    String getString();

    AidlBean getBean();

    void showLog();
}
