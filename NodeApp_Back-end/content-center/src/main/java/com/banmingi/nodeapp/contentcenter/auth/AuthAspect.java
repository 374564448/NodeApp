package com.banmingi.nodeapp.contentcenter.auth;

import com.banmingi.nodeapp.contentcenter.security.SecurityException;
import com.banmingi.nodeapp.contentcenter.util.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @auther 半命i 2020/4/22
 * @description
 */
@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthAspect {
    private final JwtOperator jwtOperator;

    /**
     * 检查登录状态
     * @param point
     * @return
     */
    @Around("@annotation(com.banmingi.nodeapp.contentcenter.auth.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint point) throws Throwable {

            this.checkToken();
            return point.proceed();

    }

    private void checkToken() {
        try {
        //1. 从header里面获取token
        HttpServletRequest request = getHttpServletRequest();
        String token = request.getHeader("X-Token");

        //2. 校验token是否合法或在有效期内,如果不合法或已过期,直接抛异常;如果合法或未过期,放行
        Boolean isValid = jwtOperator.validateToken(token);

        if (!isValid) {
            throw new SecurityException("token 不合法！");
        }
        //3. 如果校验成功,就将用户的信息设置到request的attribute里面
        Claims claims = jwtOperator.getClaimsFromToken(token);
        request.setAttribute("id",claims.get("id"));
        request.setAttribute("wxNickname",claims.get("wxNickname"));
        request.setAttribute("role",claims.get("role"));
        } catch (Throwable throwable) {
            throw new SecurityException("token 不合法！");
        }
    }

    /**
     * 获取request
     * @return
     */
    private HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes)requestAttributes;
        return attributes.getRequest();
    }

    /**
     * 权限验证
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.banmingi.nodeapp.contentcenter.auth.CheckAuthorization)")
    public Object checkAuthorization(ProceedingJoinPoint point) throws Throwable {
        try {
            //1. 验证token是否合法
            this.checkToken();
            //2. 验证用户角色是否匹配
            HttpServletRequest request = getHttpServletRequest();
            String role = (String) request.getAttribute("role");

            MethodSignature signature = (MethodSignature) point.getSignature();
            //拿到添加@CheckAuthorization注解的方法
            Method method = signature.getMethod();
            //拿到@CheckAuthorization注解
            CheckAuthorization annotation = method.getAnnotation(CheckAuthorization.class);

            String value = annotation.value();
            if (!Objects.equals(role,value)) {
                throw new SecurityException("用户无权访问！");
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            throw new SecurityException("用户无权访问！");
        }
        return point.proceed();
    }
}
