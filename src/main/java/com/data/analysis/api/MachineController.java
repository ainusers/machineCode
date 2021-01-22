package com.data.analysis.api;

import com.data.analysis.service.OrchestrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/*
 * @Author: tianyong
 * @Date: 2021/1/14 16:55
 * @Description: 机器API接口
 */
@Controller
@RequestMapping("/service/machine")
public class MachineController {


    @Autowired
    private OrchestrationService orchestrationService;


    /*
     * @Author: tianyong
     * @Date: 2021/1/14 16:56
     * @Description: 生成机器码
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/make/machinecode")
    public String makeMachineCode(){
        return orchestrationService.encryptionConfig();
    }


    /*
     * @Author: tianyong
     * @Date: 2021/1/15 11:09
     * @Description: 测试获取硬件信息
     */
    @CrossOrigin
    @ResponseBody
    @RequestMapping("/machine/info")
    public String getMachineInfo(){
        return orchestrationService.makeServerConfig();
    }


}
