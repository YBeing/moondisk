package com.lying.moondisk;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@MapperScan(basePackages = {"com.lying.moondisk.mapper"})
@EnableRetry
public class MoonDiskApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoonDiskApplication.class, args);
	}

}
