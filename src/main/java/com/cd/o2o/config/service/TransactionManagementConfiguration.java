package com.cd.o2o.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

//标注@Configuration的类，相当于一个xml配置文件
@Configuration
//@EnableTransactionManagement注解用于开启事务支持后，即可在Service方法中使用@Transactional注解进行事务管理
@EnableTransactionManagement
public class TransactionManagementConfiguration implements TransactionManagementConfigurer {

    @Autowired
    private DataSource dataSource;


    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
