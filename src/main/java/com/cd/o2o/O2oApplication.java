package com.cd.o2o;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @SpringBootApplication组合注解 = @SpringBootConfiguration + @EnableAutoConfiguration + @ComponentScan
 *
 * @EnableAutoConfiguration： springboot应用把所有符合条件的@Configuration配置类都加载到当前springboot创建的IoC容器中
 * @SpringBootConfiguration： 标注当前类是一个IoC容器配置类，功能与@Configuration一致
 * @ComponentScan： 默认扫描当前package下所有标注了@Component、@Repository、@Service、@Controller注解的类到IoC容器中，
 *                    可以通过basePackages属性（👉@ComponentScan(basePackages = "com.cd.o2o")）指定@ComponentScan的自动扫描范围
 *                    如果不指定，则默认从声明@ComponentScan类所在的package进行扫描。
 *                 所以，声明了@SpringBootApplication的启动类一般是放在root package下，因为默认不指定basePackages，否则无法扫描到其它注解类
 */
@SpringBootApplication
@MapperScan("com.cd.o2o.dao") //批量扫描所有的mapper/dao接口生成对应的MapperFactoryBean，并注册到容器中，相当于在每个mapper/dao接口上标注@Mapper注解
@EnableTransactionManagement //在启动器上标注了@EnableTransactionManagement注解来开启事务支持，然后在方法中使用@Transactional注解即可进行事务开发
public class O2oApplication {

    public static void main(String[] args) {
        SpringApplication.run(O2oApplication.class, args);
        System.out.println("项目启动成功！");
    }

}