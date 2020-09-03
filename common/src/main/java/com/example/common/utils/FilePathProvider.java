package com.example.common.utils;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author: tao
 * @time: 2019/9/11
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 说明
 */

public class FilePathProvider extends FileProvider {

    /** 用于保存一般文件 */
    public static final String DIR_FILE = "file";
    /** 用于保存下载的附件 */
    public static final String DIR_ENCLOSURE = "enclosure";
    /** 用于保存视频 */
    public static final String DIR_VIDEO = "video";
    /** 用于保存拍照图片 */
    public static final String DIR_PHOTO = "photo";
    /** 用于保存压缩图片 */
    public static final String DIR_COMPRESS = "compress";
    /** 用于保存缓存 */
    public static final String DIR_CACHE = "cache";

    /**
     * 获取外部存储文件夹
     *
     * @param context 上下文
     */
    public static File getPublicDir(Context context) {
        File filePath = new File(Environment.getExternalStorageDirectory()
                + File.separator + context.getApplicationContext().getPackageName());
        if (!filePath.exists())
            //noinspection ResultOfMethodCallIgnored
            filePath.mkdirs();
        return filePath;
    }

    /**
     * 获取外部存储文件夹--指定文件夹
     *
     * @param context 上下文
     * @param dirName 文件夹名称
     * @return 文件夹的Uri
     */
    public static File getPublicDir(Context context, String dirName) {
        File filePath = new File(Environment.getExternalStorageDirectory()
                + File.separator + context.getApplicationContext().getPackageName(), dirName);
        if (!filePath.exists())
            //noinspection ResultOfMethodCallIgnored
            filePath.mkdirs();
        return filePath;
    }

    /**
     * 在外部存储生成一个文件
     *
     * @param context  上下文
     * @param dirName  文件夹名称
     * @param fileName 文件名-需要包含后缀名
     * @return 文件的Uri
     */
    public static File createPublicFile(Context context, String dirName, String fileName) {
        File filePath = getPublicDir(context, dirName);
        return new File(filePath, fileName);
    }

    /**
     * 在外部存储生成一个文件的Uri
     *
     * @param context 上下文
     * @param file    文件
     * @return 文件的Uri
     */
    public static Uri createPublicFileUri(Context context, File file) {
        Uri result;
        // API 7.0 以上写法
        if (CommonUtils.hasN()) {
            result = getUriForFile(context, getCurrentAuthorities(context), file);
        }
        // API 7.0 以下写法
        else {
            result = Uri.fromFile(file);
        }
        return result;
    }

    /**
     * 获取文件Md5值
     */
    public static String calculateFileMd5(String filePath) {
        BigInteger bigInt = new BigInteger(1, calculateMd5(filePath));
        return bigInt.toString(16);
    }

    /**
     * Base64
     */
    private static String toBase64String(byte[] binaryData) {
        return new String(Base64.encode(binaryData, Base64.DEFAULT));
    }

    /**
     * 获取文件Md5
     */
    private static byte[] calculateMd5(String filePath) {
        byte[] md5;
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] buffer = new byte[10 * 1024];
            FileInputStream is = new FileInputStream(new File(filePath));
            int len;
            while ((len = is.read(buffer)) != -1) {
                digest.update(buffer, 0, len);
            }
            is.close();
            md5 = digest.digest();
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException("MD5 algorithm not found.");
        }
        return md5;
    }

    /**获取当前拥有者 */
    private static String getCurrentAuthorities(Context context) {
        return context.getPackageName() + ".fileprovider";
    }

}

