package com.dingsheng.decent.conf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @luzhengxiang
 * @create 2020-04-06 02:30
 **/
@Configuration
@MapperScan(basePackages = "com.dingsheng.decent.*.mapper")
public class MybatisConfig {
}
