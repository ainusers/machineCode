package com.data.analysis.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Enumeration;
import java.util.List;

/*
 * @Author: tianyong
 * @Date: 2021/1/14 16:53
 * @Description: 工具类
 */
public class Utils {

    /* 成员变量区域 */
    private static final String AES = "AES";
    private static final String CHARSET = "utf-8";
    private static final String CUSTOM_KEY = "hongyi2021";
    private static final Base64.Encoder ENCODER = Base64.getEncoder();
    private static final Logger log = LogManager.getLogger(Utils.class);


    /*
     * @Author: tianyong
     * @Date: 2021/1/14 17:47
     * @Description: 加密：AES + Base64
     */
    public static String encodeBase64(String content){
        // AES加密
        String encode = encode(CUSTOM_KEY, content);
        if(encode == null) return null;  //加密失败返回空
        try {
            // Base64加密
            return ENCODER.encodeToString(encode.getBytes(CHARSET));
        } catch (UnsupportedEncodingException e) {
            log.error("encode UnsupportedEncode exception!",e);
        }
        return null;
    }


    /*
     * @Author: tianyong
     * @Date: 2021/1/14 18:03
     * @Description: AES加密
     */
    public static String encode(String encodeRules, String content){
        try {
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance(AES);
            // 2.根据ecnodeRules规则初始化密钥生成器
            // 生成一个128位的随机源,根据传入的字节数组
            // keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(encodeRules.getBytes());
            // 256的jdk可能不支持，需要替换文件
            keygen.init(128, random);
            // 3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            // 4.获得原始对称密钥的字节数组
            byte [] raw = original_key.getEncoded();
            // 5.根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, AES);
            // 6.根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(AES);
            // 7.初始化密码器，第一个参数为加密(Encrypt_mode)或者解密解密(Decrypt_mode)操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 8.获取加密内容的字节数组(这里要设置为utf-8)不然内容中如果有中文和英文混合中文就会解密为乱码
            byte [] byte_encode = content.getBytes(CHARSET);
            // 9.根据密码器的初始化方式 - 加密：将数据加密
            byte [] byte_AES = cipher.doFinal(byte_encode);
            // 10.将加密后的数据转换为字符串
            return new String(new BASE64Encoder().encode(byte_AES));
        } catch (Exception e) {
            log.error("encode occurred error!",e);
        }
        return null;
    }


    /*
     * @Author: tianyong
     * @Date: 2021/1/15 9:12
     * @Description: 获取当前操作系统 (window / linux)
     */
    public static String getPlateform(){
        return System.getProperty("os.name").toLowerCase();
    }


    /*
     * @Author: tianyong
     * @Date: 2021/1/15 9:34
     * @Description: 获取当前服务器所有符合条件的InetAddress
     */
    public static List<InetAddress> getLocalAllInetAddress(){
        List<InetAddress> result = new ArrayList<>(4);
        try {
            // 遍历所有的网络接口
            for (Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements();) {
                NetworkInterface ni = (NetworkInterface) networkInterfaces.nextElement();
                // 在所有的接口下再遍历IP
                for (Enumeration addresses = ni.getInetAddresses(); addresses.hasMoreElements(); ) {
                    InetAddress address = (InetAddress) addresses.nextElement();
                    //排除LoopbackAddress、SiteLocalAddress、LinkLocalAddress、MulticastAddress类型的IP地址
                    if(!address.isLoopbackAddress() && !address.isLinkLocalAddress() && !address.isMulticastAddress()){
                        result.add(address);
                    }
                }
            }
        } catch (SocketException e) {
            log.error("get server inetaddress serial number fail!",e);
        }
        return result;
    }


    /*
     * @Author: tianyong
     * @Date: 2021/1/15 9:34
     * @Description: 获取某个网络地址对应的Mac地址
     */
    public static String getMacByInetAddress(InetAddress inetAddr){
        try {
            byte[] mac = NetworkInterface.getByInetAddress(inetAddr).getHardwareAddress();
            StringBuffer sb = new StringBuffer();
            for(int i=0;i<mac.length;i++){
                if(i != 0) {
                    sb.append("-");
                }
                //将十六进制byte转化为字符串
                String temp = Integer.toHexString(mac[i] & 0xff);
                if(temp.length() == 1){
                    sb.append("0" + temp);
                }else{
                    sb.append(temp);
                }
            }
            return sb.toString().toUpperCase();
        } catch (SocketException e) {
            log.error("get server mac serial number fail!",e);
        }
        return null;
    }

}
