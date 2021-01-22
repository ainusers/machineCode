package com.data.analysis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.net.UnknownHostException;


@SpringBootApplication
public class AnalysisApplication {

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication.run(AnalysisApplication.class, args);
        System.out.println("machine code generate url : http://IP:9393/service/machine/make/machinecode");
    }

}
