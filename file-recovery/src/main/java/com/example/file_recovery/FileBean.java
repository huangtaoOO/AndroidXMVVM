package com.example.file_recovery;

/**
 * @author: tao
 * @time: 2020/9/21
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */
public class FileBean {
    String prefix ;
    String path;

    public FileBean(String prefix, String path) {
        this.prefix = prefix;
        this.path = path;
    }

    @Override
    public String toString() {
        return "FileBean{" +
                "prefix='" + prefix + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
