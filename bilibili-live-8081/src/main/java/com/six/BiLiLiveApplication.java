package com.six;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author ZhouJinDong
 * @Date 2021/1/4
 */
@SpringBootApplication
@EnableDiscoveryClient
public class BiLiLiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiLiLiveApplication.class);
    }
}
