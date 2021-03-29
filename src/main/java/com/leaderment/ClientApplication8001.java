package com.leaderment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Springboot 启动类
 * 保修登记
 *
 */
@SpringBootApplication
@MapperScan({"com.leaderment.*"})  
public class ClientApplication8001 
{
    public static void main( String[] args ){
    	SpringApplication.run(ClientApplication8001.class, args);
    }
    
}
