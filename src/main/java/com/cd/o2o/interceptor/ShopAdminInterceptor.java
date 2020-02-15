package com.cd.o2o.interceptor;

import com.cd.o2o.entity.Person;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShopAdminInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取用户登陆信息
        Person person = (Person) request.getSession().getAttribute("person");
        //判断用户是否有权限进入商家管理后台系统
        if(person != null && person.getUserId() > 0
                && person.getEnableStatus() == 1 && person.getPersonType() == 2){
            //如果验证通过，则返回true，放行请求，即用户接下来的操作可以正常执行
            return true;
        }
        //如果不满足登陆验证，则跳转到登陆页面
        response.sendRedirect("/o2o/local/to_login");
        /*
        如果不满足登陆验证，则跳转到登陆页面
           PrintWriter out = response.getWriter();
           out.println("<html>");
           out.println("<script>");
           out.println("window.location.href = '/o2o/local/to_login'");
           out.println("</script>");
           out.println("</html>");
        */
        return false;
    }
}
