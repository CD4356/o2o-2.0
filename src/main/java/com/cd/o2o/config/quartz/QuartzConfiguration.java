package com.cd.o2o.config.quartz;

import com.cd.o2o.service.ProductSellDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration //标注@Configuration的类，相当于一个xml配置文件
public class QuartzConfiguration {

    @Autowired
    private ProductSellDailyService productSellDailyService;
    @Autowired
    private MethodInvokingJobDetailFactoryBean jobDetailFactory;
    @Autowired
    private CronTriggerFactoryBean cronTriggerFactory;

    /**
     * 配置jobDetail作业类
     * @return
     */
    @Bean(name = "jobDetailFactory") //标注@Bean后表明返回对象会被当做一个Bean注册到Spring IoC容器
    public MethodInvokingJobDetailFactoryBean createJobDetail(){
        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        //指定任务名称
        jobDetailFactoryBean.setName("product_sell_daily_job");
        //指定组名称
        jobDetailFactoryBean.setGroup("job_product_sell_daily_group");
        /*
          对于同一个JobDetail，当指定多个trigger时，很可能第一个job完成之前，第二个job就开始了
          指定concurrent属性为false，多个job就会串行执行，而不会并发执行，即第一个job完成前，第二个job不会开启
         */
        jobDetailFactoryBean.setConcurrent(false);
        //指定具体的job任务类
        jobDetailFactoryBean.setTargetObject(productSellDailyService);
        //指定运行任务的方法
        jobDetailFactoryBean.setTargetMethod("dailyCalculate");
        return jobDetailFactoryBean;
    }

    /**
     * 配置cronTrigger触发器（作业调度的方式）
     * @return
     */
    @Bean(name = "cronTriggerFactory")
    public CronTriggerFactoryBean createProductSellDailyTrigger(){
        CronTriggerFactoryBean triggerFactory = new CronTriggerFactoryBean();
        //指定trigger的名称
        triggerFactory.setName("product_sell_daily_trigger");
        //指定trigger的组名
        triggerFactory.setGroup("job_product_sell_daily_group");
        //指定trigger绑定的job
        triggerFactory.setJobDetail(jobDetailFactory.getObject());
        //设置cron表达式，每天凌晨定时运行（通过在线Cron表达式生成器来生成）
        triggerFactory.setCronExpression("0 0 0 * * ? *");
        return triggerFactory;
    }

    /**
     * 配置scheduler调度工厂
     * @return
     */
    @Bean
    public SchedulerFactoryBean createScheduler(){
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        //绑定cronTrigger
        schedulerFactory.setTriggers(cronTriggerFactory.getObject());
        return schedulerFactory;
    }

}
