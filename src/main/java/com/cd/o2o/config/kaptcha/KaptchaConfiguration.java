package com.cd.o2o.config.kaptcha;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletException;

@Configuration //标注@Configuration的类，相当于一个xml配置文件
public class KaptchaConfiguration {

    @Bean(name = "kaptchaServlet") //标注@Bean后表明返回对象会被当做一个Bean注册到Spring IoC容器
    public ServletRegistrationBean servletRegistrationBean() throws ServletException {
        //注册KaptchaServlet类到servlet容器中，并指定映射路径（访问该映射路径，就会去访问该servlet）
        ServletRegistrationBean servlet = new ServletRegistrationBean(new KaptchaServlet(), "/Kaptcha");
        /*配置Kaptcha相关初始化参数*/
        //是否有边框
        servlet.addInitParameter("kaptcha.border","no");
        //字体颜色
        servlet.addInitParameter("kaptcha.textproducer.font.color","blue");
        //字体大小
        servlet.addInitParameter("kaptcha.textproducer.font.size","43");
        //字体样式<!--Arial是宋体字-->
        servlet.addInitParameter("kaptcha.textproducer.font.names","cmr10");
        //使用哪些字符生成验证码
        servlet.addInitParameter("kaptcha.textproducer.char.string","QWERTASDFZXC23456");
        //图片验证码字符个数
        servlet.addInitParameter("kaptcha.textproducer.char.length","4");
        //图片宽度
        servlet.addInitParameter("kaptcha.image.width","135");
        //图片高度
        servlet.addInitParameter("kaptcha.image.height","50");
        //干扰线的颜色
        servlet.addInitParameter("kaptcha.noise.color","black");
        //图片样式
        servlet.addInitParameter("kaptcha.obscurificator.impl","com.google.code.kaptcha.impl.FishEyeGimpy");
        //背景颜色渐变，开始颜色
        servlet.addInitParameter("kaptcha.background.clear.from","white");
        //背景颜色渐变， 结束颜色
        servlet.addInitParameter("kaptcha.background.clear.to","white");
        return servlet;
    }

}
