package com.data.analysis.operation;

import com.data.analysis.util.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/*
 * @Author: tianyong
 * @Date: 2021/1/15 9:27
 * @Description: window平台参数信息
 */
public class WindowInfoAbstract extends ServerInfoAbstract{


    /* 变量 */
    private static Logger log = LogManager.getLogger(WindowInfoAbstract.class);


    // 获取window平台机器名
    @Override
    protected String Name() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
            return addr.getHostName().toString();
        } catch (UnknownHostException e) {
            log.error("get window name serial number fail!",e);
        }
        return null;
    }

    // 获取window平台IP
    @Override
    protected List<String> Ip() {
        List<String> result = null;
        List<InetAddress> inetAddresses = Utils.getLocalAllInetAddress();
        if(inetAddresses != null && inetAddresses.size() > 0){
            result = inetAddresses.stream().map(InetAddress::getHostAddress).distinct().map(String::toLowerCase).collect(Collectors.toList());
        }
        return result;
    }


    // 获取window平台MAC
    @Override
    protected List<String> Mac() {
        List<String> result = null;
        //1. 获取所有网络接口
        List<InetAddress> inetAddresses = Utils.getLocalAllInetAddress();
        if(inetAddresses != null && inetAddresses.size() > 0){
            //2. 获取所有网络接口的Mac地址
            result = inetAddresses.stream().map(n-> Utils.getMacByInetAddress(n)).distinct().collect(Collectors.toList());
        }
        return result;
    }


    // 使用WMIC获取window平台CPU序列号
    @Override
    protected String Cpu() {
        String serialNumber = "";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("wmic cpu get processorid");
            process.getOutputStream().close();
            Scanner scanner = new Scanner(process.getInputStream());
            if(scanner.hasNext()){
                scanner.next();
            }
            if(scanner.hasNext()){
                serialNumber = scanner.next().trim();
            }
            scanner.close();
        } catch (IOException e) {
            log.error("get window cpu serial number fail!",e);
        }
        return serialNumber;
    }


    // 使用WMIC获取window平台MAINBOARD序列号
    @Override
    protected String MainBoard() {
        String serialNumber = "";
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("wmic baseboard get serialnumber");
            process.getOutputStream().close();
            Scanner scanner = new Scanner(process.getInputStream());
            if(scanner.hasNext()){
                scanner.next();
            }
            if(scanner.hasNext()){
                serialNumber = scanner.next().trim();
            }
            scanner.close();
        } catch (IOException e) {
            log.error("get window MAINBOARD serial number fail!",e);
        }
        return serialNumber;
    }
}
