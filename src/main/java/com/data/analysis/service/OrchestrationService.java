package com.data.analysis.service;

import com.data.analysis.entity.ServerInfo;
import com.data.analysis.operation.LinuxInfoAbstract;
import com.data.analysis.operation.WindowInfoAbstract;
import com.data.analysis.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * @Author: tianyong
 * @Date: 2021/1/15 9:12
 * @Description: 编排服务层
 */
@Service
public class OrchestrationService {


    /*
     * @Author: tianyong
     * @Date: 2021/1/15 9:13
     * @Description: 编排硬件配置信息
     */
    public String OrchestrationConfigInfo() {
        ServerInfo serverInfo = Utils.getPlateform().startsWith("windows") ? new WindowInfoAbstract().getServerInfo() : new LinuxInfoAbstract().getServerInfo();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        // 编排配置
        StringBuilder sb = new StringBuilder();
        if(!StringUtils.isEmpty(serverInfo.getName()) && serverInfo.getIps().size()!=0 && serverInfo.getMacs().size()!=0 && !StringUtils.isEmpty(serverInfo.getCpus()) && !StringUtils.isEmpty(serverInfo.getBoards())){
            sb.append(serverInfo.getName()).append("#").append(serverInfo.getIps()).append("#").append(serverInfo.getMacs()).append("#").append(serverInfo.getCpus())
                    .append("#").append(serverInfo.getBoards()).append("#").append(sdf.format(new Date()));
            return String.valueOf(sb);
        }
        return "";
    }


    /*
     * @Author: tianyong
     * @Date: 2021/1/15 9:13
     * @Description: 加密硬件信息
     */
    public String encryptionConfig(){
        String content = OrchestrationConfigInfo();
        if(content.isEmpty()) return "orchestration server config info fail!";
        return Utils.encodeBase64(Utils.encodeBase64(content));
    }


    /*
     * @Author: tianyong
     * @Date: 2021/1/15 11:10
     * @Description: 服务信息组装
     */
    public String makeServerConfig() {
        ServerInfo serverInfo = Utils.getPlateform().startsWith("windows") ? new WindowInfoAbstract().getServerInfo() : new LinuxInfoAbstract().getServerInfo();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        // 服务信息组装
        StringBuilder sb = new StringBuilder();
            sb.append(serverInfo.getName()).append("#").append(serverInfo.getIps()).append("#").append(serverInfo.getMacs()).append("#").append(serverInfo.getCpus())
                    .append("#").append(serverInfo.getBoards()).append("#").append(sdf.format(new Date()));
        return String.valueOf(sb);
    }

}
