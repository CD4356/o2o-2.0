package com.cd.o2o.config.dao;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration //类上标注了@Configuration注解，意味着它是一个IoC容器配置类
public class DataSourceConfiguration {

    @Bean(name = "dataSource") //标注@Bean后表明返回对象会被当做一个Bean注册到Spring IoC容器
    @Primary //primary将当前数据库连接池作为默认数据库连接池
    @ConfigurationProperties(prefix = "spring.datasource") //将yml属性文件中指定前缀的属性绑定进bean实例对应的属性中
    public DataSource dataSource(){
        return new ComboPooledDataSource();
        //return DataSourceBuilder.create().type(return DataSourceBuilder.create().type(com.mchange.v2.c3p0.ComboPooledDataSource.class).build();.class).build();
    }

}
