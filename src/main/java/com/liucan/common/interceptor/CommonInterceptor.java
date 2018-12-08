package com.liucan.common.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liucan
 * @date 2018/6/24
 * @brief 通用请求拦截器
 * 1.处理器拦截器会在浏览器特定的请求执行处理器Controller处理之前，之后拦截
 * 2.spring-mvc处理器拦截器在你需要为特定类型的请求应用一些功能时很有用，
 * 比如，检查用户身份,用户是否登录，记录日志等等。
 */
@Slf4j
public class CommonInterceptor extends HandlerInterceptorAdapter {
    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     * 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器
     * 如果返回true
     * 执行下一个拦截器,直到所有的拦截器都执行完毕
     * 再执行被拦截的Controller
     * 然后进入拦截器链,
     * 从最后一个拦截器往回执行所有的postHandle()
     * 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {
        //已经通过自定义注解+aop实现是否需要用户登录
        String url = request.getRequestURI().substring(request.getContextPath().length());
        log.info("[拦截器]拦截请求：{}，执行preHandle,开始执行拦截处理", url);
        return true;
//
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equalsIgnoreCase("uid")) {
//                    log.info("[拦截器]拦截请求：{}，执行preHandle,拦截处理完成，用户已经登录uid:{}", url, cookie.getValue());
//                    return true;
//                }
//            }
//        }
//
//        log.error("[拦截器]拦截" +
//                "请求：{},执行preHandle, 拦截处理完成，用户还未登录", url);
//        response.sendRedirect("login");
//        return false;
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作
     * 可在modelAndView中加入数据，比如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response,
                           Object handler,
                           ModelAndView modelAndView) throws Exception {
        String url = request.getRequestURI().substring(request.getContextPath().length());
        log.info("[拦截器]请求处理完成：{}，准备生成视图：，执行postHandle", url);
        if (modelAndView != null) {  //加入当前时间
            modelAndView.addObject("var", "测试postHandle");
        }
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response,
                                Object handler,
                                Exception ex) throws Exception {
        String url = request.getRequestURI().substring(request.getContextPath().length());
        log.info("[拦截器]请求全部处理完成：{}，执行afterCompletion", url);
    }

}