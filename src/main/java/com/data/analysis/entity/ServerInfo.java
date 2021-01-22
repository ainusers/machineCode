package com.data.analysis.entity;

import java.io.Serializable;
import java.util.List;

/*
 * @Author: tianyong
 * @Date: 2021/1/15 9:25
 * @Description: 配置信息
 */
public class ServerInfo implements Serializable {

    // 计算机名称
    private String name;
    // 允许的IP
    private List<String> ips;
    // 允许的MAC
    private List<String> macs;
    // 允许的CPU
    private String cpus;
    // 允许的主板
    private String Boards;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getIps() {
        return ips;
    }

    public void setIps(List<String> ips) {
        this.ips = ips;
    }

    public List<String> getMacs() {
        return macs;
    }

    public void setMacs(List<String> macs) {
        this.macs = macs;
    }

    public String getCpus() {
        return cpus;
    }

    public void setCpus(String cpus) {
        this.cpus = cpus;
    }

    public String getBoards() {
        return Boards;
    }

    public void setBoards(String boards) {
        Boards = boards;
    }

    @Override
    public String toString() {
        return "ServerInfo{" +
                "name='" + name + '\'' +
                ", ips=" + ips +
                ", macs=" + macs +
                ", cpus='" + cpus + '\'' +
                ", Boards='" + Boards + '\'' +
                '}';
    }
}
