package com.cd.o2o.config.web;

import com.cd.o2o.interceptor.ShopAdminInterceptor;
import com.cd.o2o.interceptor.SuperAdminInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.*;

@Configuration //标注@Configuration的类，相当于一个xml配置文件
public class SpringMvcConfiguration implements WebMvcConfigurer {

    /**
     * 添加静态资源配置，配置虚拟路径访问本地图片
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //意思是：url中读取到/upload时，就会自动将/upload解析成D:/idea/java_workspace/image/upload/
        /**
         * Windows系统
         */
//        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:/idea/java_workspace/image/upload/");
        /**
         * Linux系统
         */
        registry.addResourceHandler("/upload/**").addResourceLocations("file:/home/image/o2o/upload/");
    }


    /**
     * 添加拦截器链配置
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器1，对商家管理系统进行权限验证
        InterceptorRegistration registration1 = registry.addInterceptor(new ShopAdminInterceptor());
        //指定拦截器1要拦截的请求(支持*通配符)
        registration1.addPathPatterns("/shop_admin/**");

        //注册拦截器2，对超级管理员系统进行权限验证
        InterceptorRegistration registration2 = registry.addInterceptor(new SuperAdminInterceptor());
        //指定拦截器2要拦截的请求(支持*通配符)
        registration2.addPathPatterns("/super_admin/**");
    }


    /**
     * 无业务逻辑页面跳转
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/shop_admin/product_buy_check").setViewName("shop/product_buy_check");
        registry.addViewController("/frontend/to_consuming_record").setViewName("/frontend/consuming_record");
    }


    /**
     * 文件上传解析器
     * @return
     */
    @Bean("multipartResolver")
    public CommonsMultipartResolver createMultipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8");
        // 1024*1024*20 = 20M
        multipartResolver.setMaxUploadSize(1024*1024*20);
        multipartResolver.setMaxInMemorySize(1024*1024*20);
        return multipartResolver;
    }

}