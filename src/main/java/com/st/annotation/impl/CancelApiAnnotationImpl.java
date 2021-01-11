package com.st.annotation.impl;

import com.st.annotation.CancelApiAnnotation;
import com.st.controller.MessageController;
import com.st.dto.ResponseHeadDTO;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Aspect
@Component
public class CancelApiAnnotationImpl {

    @Pointcut(value = "execution(public * com.st.controller.*Controller.*(..)) && @annotation(com.st.annotation.CancelApiAnnotation)")
    public void addAdvice() {
    }

    @Around("addAdvice()")
    public Object Interceptor(ProceedingJoinPoint pjp) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Class clazz = pjp.getTarget().getClass();
        Method[] methods = clazz.getDeclaredMethods();
        Object o1 = null;
        for (Method method : methods) {
            if (method.isAnnotationPresent(CancelApiAnnotation.class)) {
                //获取注解中的value值
                CancelApiAnnotation cancelApiAnnotation = method.getAnnotation(CancelApiAnnotation.class);
                String value = cancelApiAnnotation.value();
                ResponseHeadDTO responseHeadDTO = new ResponseHeadDTO();
                responseHeadDTO.setAppMessage(value);
                String className = method.getReturnType().getName();
                Class theClass = Class.forName(className);
                o1 = theClass.newInstance();
                Method setMethod = o1.getClass().getMethod("setResponseHead", ResponseHeadDTO.class);
                setMethod.invoke(o1,responseHeadDTO);
            }
        }
        return o1;
    }
}
