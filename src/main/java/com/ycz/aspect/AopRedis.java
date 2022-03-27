package com.ycz.aspect;/*
 @author ycz
 @date 2021-12-06-15:38
*/

import com.alibaba.fastjson.JSON;
import com.ycz.annotation.RedisAnnotation;
import com.ycz.annotation.UpdateAnnotation;
import com.ycz.common.CodeMsg;
import com.ycz.common.Result;
import com.ycz.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;

@Slf4j
@Component
@Aspect
public class AopRedis {

    @Autowired
    private RedisUtil redisUtil;

//    @Pointcut("execution(* com.ycz.service.Impl.*.*(..))")//切点
//    public void webLog(){
//
//    }

    /**
     * 配置删除Redis的缓存的aop
     *
     */
    @AfterReturning(value = "@annotation(com.ycz.annotation.UpdateAnnotation)",returning = "returnValue")
    public void AfterDelRedis(JoinPoint joinPoint, Result returnValue) throws NoSuchMethodException {
        log.info("要走删除缓存了、。。。准备好朋友们");
        Signature signature=joinPoint.getSignature();
        //获取类名
        String className=joinPoint.getClass().getSimpleName();
        //方法名
        String methodName=signature.getName();

        Class[] parameterTypes = new Class[joinPoint.getArgs().length];

        Object[] args=joinPoint.getArgs();

        String params="";

        for(int i=0; i<args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile){
                System.out.println("属于？？？");
                return;
            }

            if(args[i] != null) {
                params += JSON.toJSONString(args[i]);
                parameterTypes[i] = args[i].getClass();
            }else {
                parameterTypes[i] = null;
            }
        }

        Method method = joinPoint.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
        UpdateAnnotation annotation = method.getAnnotation(UpdateAnnotation.class);
        //缓存过期时间
        //缓存名称
        String[] names = annotation.name();

        log.info("要删除缓存的"+ Arrays.toString(names));

        log.info("Result的Code："+returnValue);

        if (returnValue.getCode()!=200){
            log.info(methodName+"程序有错误");
            return;
        }

        log.info("开始删除Redis缓存");
        //这里得找哦
        //这里就可以删除多出的 redis缓存了
        for (String name : names) {
            Set<String> keys = redisUtil.getKeys(name+"*");
            System.out.println("模糊查找的："+keys);
            if (!CollectionUtils.isEmpty(keys)){
                //如果不为空
                for (String key : keys) {
                    redisUtil.del(key);//删除
                    log.info("删除了"+key);
                }
            }
        }

    }


    @Around("@annotation(com.ycz.annotation.RedisAnnotation)")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        try{
            Signature signature=proceedingJoinPoint.getSignature();
            //获取类名
            String className=proceedingJoinPoint.getClass().getSimpleName();
            //方法名
            String methodName=signature.getName();

            Class[] parameterTypes = new Class[proceedingJoinPoint.getArgs().length];

            Object[] args=proceedingJoinPoint.getArgs();

            String params="";

            for(int i=0; i<args.length; i++) {
                if(args[i] != null) {
                    params += JSON.toJSONString(args[i]);
                    parameterTypes[i] = args[i].getClass();
                }else {
                    parameterTypes[i] = null;
                }
            }

            // 主要是为了拿到注解，拿到方法再去拿到注解
            Method method = proceedingJoinPoint.getSignature().getDeclaringType().getMethod(methodName, parameterTypes);
            //获取Cache注解
            RedisAnnotation annotation = method.getAnnotation(RedisAnnotation.class);
            //缓存过期时间
            long expire = annotation.expire();
            //缓存名称
            String name = annotation.name();
            //先从redis获取 redisKey：注解名称+类名+方法名称+md5参数
            String redisKey = name + "::" + className+"::"+methodName+"::"+params;

            Object redisValue =  redisUtil.get(redisKey);
            if (redisValue!=null){
                log.info("走了缓存 {},{}==>{}",className,methodName,redisKey);
                return redisValue;
            }
            log.info("redisKey===>>>"+redisKey);

            Object proceed = proceedingJoinPoint.proceed();

            redisUtil.set(redisKey,proceed);
            redisUtil.expire(redisKey,expire);
            log.info("存入缓存~~~ {},{},{}",className,methodName,redisKey);
            return proceed;
        }catch (Exception e){
            e.printStackTrace();
        }
        return new Result(CodeMsg.ERROR);
    }




}
