package com.data.analysis.operation;

import com.data.analysis.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.stream.Collectors;

/*
 * @Author: tianyong
 * @Date: 2020/7/13 18:11
 * @Description: linux平台参数信息
 */
public class LinuxInfoAbstract extends ServerInfoAbstract {


    /* 变量 */
    private static Logger log = LogManager.getLogger(LinuxInfoAbstract.class);


    // 获取linux平台机器名
    @Override
    protected String Name() {
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
            return addr.getHostName().toString();
        } catch (UnknownHostException e) {
            log.error("get linux name serial number fail!",e);
        }
        return null;
    }


    // 获取linux平台IP
    @Override
    protected List<String> Ip() {
        List<String> result = null;
        List<InetAddress> inetAddresses = Utils.getLocalAllInetAddress();
        if(inetAddresses != null && inetAddresses.size() > 0){
            result = inetAddresses.stream().map(InetAddress::getHostAddress).distinct().map(String::toLowerCase).collect(Collectors.toList());
        }
        return result;
    }


    // 获取linux平台MAC
    @Override
    protected List<String> Mac() {
        List<String> result = null;
        // 1. 获取所有网络接口
        List<InetAddress> inetAddresses = Utils.getLocalAllInetAddress();
        if(inetAddresses != null && inetAddresses.size() > 0){
            // 2. 获取所有网络接口的Mac地址
            result = inetAddresses.stream().map(n->Utils.getMacByInetAddress(n)).distinct().collect(Collectors.toList());
        }
        return result;
    }


    // 使用dmidecode命令获取linux平台CPU序列号
    @Override
    protected String Cpu() {
        String serialNumber = "";
        BufferedReader reader = null;
        String[] shell = {"/bin/bash","-c","dmidecode -t processor | grep 'ID' | awk -F ':' '{print $2}' | head -n 1"};
        try {
            Process process = Runtime.getRuntime().exec(shell);
            process.getOutputStream().close();
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine().trim();
            if(StringUtils.isNotBlank(line)){
                serialNumber = line;
            }
            reader.close();
        } catch (Exception e) {
            log.error("get linux cpu serial number fail!",e);
        }
        return serialNumber;
    }


    // 使用dmidecode命令获取linux平台MAINBOARD序列号
    @Override
    protected String MainBoard() {
        String serialNumber = "";
        Process process = null;
        String[] shell = {"/bin/bash","-c","dmidecode | grep 'Serial Number' | awk -F ':' '{print $2}' | head -n 1"};
        try {
            process = Runtime.getRuntime().exec(shell);
            process.getOutputStream().close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine().trim();
            if(StringUtils.isNotBlank(line)){
                serialNumber = line;
            }
            reader.close();
        } catch (IOException e) {
            log.error("get linux MAINBOARD serial number fail!",e);
        }
        return serialNumber;
    }
}
