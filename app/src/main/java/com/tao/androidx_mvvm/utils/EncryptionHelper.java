package com.tao.androidx_mvvm.utils;

import android.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * @author: tao
 * @time: 2019/1/9
 * @e-mail: 1462320178@qq.com
 * @version: 1.0
 * @exception: 无
 * @explain: 加密工具类
 */
public class EncryptionHelper {

    public static final int ENCODE = 0;
    public static final int DECODE = 1;

    /**
     * MODULUS：两个大素数的乘积
     */
    private static final String MODULUS = "100631058000714094813874361191853577129731636346684218206605779824931626830750623070803100189781211343851763275329364056640619755337779928985272486091431384128027213365372009648233171894708338213168824861061809490615593530405056055952622249066180336803996949444124622212096805545953751253607916170340397933039";
    /**
     * 公钥
     */
    private static final String PUB_KEY = "65537";
    /**
     * 私钥
     */
    private static final String PRI_KEY = "26900155715313643087786516528374548998821559381075740707715132776187148793016466508650068087107695523642202737697714709374658856733792614490943874205956727606674634563665154616758939576547663715234643273055658829482813503959459653708062875625210008961239643775661357655599312857249418610810177817213648575161";

    /**
     * @param content 要加密的文本
     * @param key     密钥
     * @param mode    加密还是解密
     * @return
     */
    public static String des(String content, String key, int mode) {
        String charset = "UTF-8";
        try {
            //1.将key转为byte数组
            byte[] keyBytes = key.getBytes(charset);
            //2.定义一个变量用来存储key
            byte[] temp = new byte[8];
            //3.数组拷贝，
            //1.原数组
            //2.原数组起始位置
            //3.目标数组
            //4.目标数组起始位置
            //5.拷贝的数组长度
            System.arraycopy(keyBytes, 0, temp, 0, Math.min(keyBytes.length, temp.length));
            //4.定义一个密钥对象
            //第一个参数表示密钥数组
            //第二个参数表示密钥名称
            SecretKey secretKey = new SecretKeySpec(temp, "des");
            //5.构造一个密文生成器，参数为算法名称
            Cipher cipher = Cipher.getInstance("des");
            //加密
            if (mode == ENCODE) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] bytes = cipher.doFinal(content.getBytes(charset));
                String string = Base64.encodeToString(bytes, Base64.DEFAULT);
                return string;
            } else {
                //解密
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                byte[] bytes = cipher.doFinal(Base64.decode(content, Base64.DEFAULT));
                return new String(bytes, 0, bytes.length);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param content 要加密的文本
     * @param key     密钥
     * @param mode    加密还是解密
     * @return
     */
    public static String des3(String content, String key, int mode) {
        String charset = "UTF-8";
        try {
            //1.将key转为byte数组
            byte[] keyBytes = key.getBytes(charset);
            //2.定义一个变量用来存储key
            byte[] temp = new byte[24];
            //3.数组拷贝，
            //1.原数组
            //2.原数组起始位置
            //3.目标数组
            //4.目标数组起始位置
            //5.拷贝的数组长度
            System.arraycopy(keyBytes, 0, temp, 0, Math.min(keyBytes.length, temp.length));
            //4.定义一个密钥对象
            //第一个参数表示密钥数组
            //第二个参数表示密钥名称
            SecretKey secretKey = new SecretKeySpec(temp, "desede");
            //5.构造一个密文生成器，参数为算法名称
            Cipher cipher = Cipher.getInstance("desede");
            //加密
            if (mode == ENCODE) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                byte[] bytes = cipher.doFinal(content.getBytes(charset));
                String string = Base64.encodeToString(bytes, Base64.DEFAULT);
                return string;
            } else {
                //解密
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                byte[] bytes = cipher.doFinal(Base64.decode(content, Base64.DEFAULT));
                return new String(bytes, 0, bytes.length);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param content 要加密的文本
     * @param key     密钥
     * @param mode    加密还是解密
     * @return
     */
    private static final String AES = "AES";
    /**
     * AES_TRANSFORMATION：AES是加密方式 CBC是工作模式 PKCS5Padding是填充模式
     */
    private static final String AES_TRANSFORMATION = "AES/CBC/PKCS5Padding";
    private static final String AES_KEY = "DDStudy";
    public static String aes(String content, int mode) {
        String charset = "UTF-8";
        try {
            //1.将key转为byte数组
            byte[] keyBytes = AES_KEY.getBytes(charset);
            //2.定义一个变量用来存储key
            byte[] temp = new byte[32];
            //3.数组拷贝，
            //1.原数组
            //2.原数组起始位置
            //3.目标数组
            //4.目标数组起始位置
            //5.拷贝的数组长度
            System.arraycopy(keyBytes, 0, temp, 0, Math.min(keyBytes.length, temp.length));
            //4.定义一个密钥对象
            //第一个参数表示密钥数组
            //第二个参数表示密钥名称
            SecretKey secretKey = new SecretKeySpec(temp, AES);
            //5.构造一个密文生成器，参数为算法名称
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            //加密
            if (mode == ENCODE) {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey,new IvParameterSpec(new byte[cipher.getBlockSize()]));
                byte[] bytes = cipher.doFinal(content.getBytes(charset));
                return Base64.encodeToString(bytes, Base64.DEFAULT);
            } else {
                //解密
                cipher.init(Cipher.DECRYPT_MODE, secretKey,new IvParameterSpec(new byte[cipher.getBlockSize()]));
                byte[] bytes = cipher.doFinal(Base64.decode(content, Base64.DEFAULT));
                return new String(bytes, 0, bytes.length);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String rsaEncode(String content) {
        try {
            //1.获取一个密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance("rsa");
            //2.获取公钥对应的KeySpec对象
            //第一个参数表示两个大素数的乘积
            //第二个参数表示公钥
            RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec(new BigInteger(MODULUS), new BigInteger(PUB_KEY));
            //3.生成一个公钥
            PublicKey publicKey = keyFactory.generatePublic(rsaPublicKeySpec);
            Cipher cipher = Cipher.getInstance("rsa");
            cipher.init(Cipher.ENCRYPT_MODE,publicKey);
            byte[] bytes = cipher.doFinal(content.getBytes("utf-8"));
            return Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String rsaDecode(String content) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("rsa");
            RSAPrivateKeySpec rsaPrivateKeySpec = new RSAPrivateKeySpec(new BigInteger(MODULUS), new BigInteger(PRI_KEY));
            PrivateKey privateKey = keyFactory.generatePrivate(rsaPrivateKeySpec);
            Cipher cipher = Cipher.getInstance("rsa");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] bytes = cipher.doFinal(Base64.decode(content, Base64.DEFAULT));
            return new String(bytes, 0, bytes.length);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String sha1(String... strs){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            String str = "";
            for (String s:strs) {
                str += s;
            }
            md.update(str.getBytes());
            return bytes2HexString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String md5(String... strs) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            String str = "";
            for (String s:strs) {
                str += s;
            }
            md.update(str.getBytes());
            return bytes2HexString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getFileMD5(File file) {
        if (file == null || !file.exists() || !file.isFile()) {
            return null;
        }
        String hash = null;
        byte[] buffer = new byte[4096];
        BufferedInputStream in = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            in = new BufferedInputStream(new FileInputStream(file));
            int numRead = 0;
            while ((numRead = in.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            in.close();
            return bytes2HexString(md5.digest());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String bytes2HexString(byte[] bytes){
        StringBuilder hexValue = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            int val = ((int) bytes[i]) & 0xff;
            if (val < 16){
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
    }
}
