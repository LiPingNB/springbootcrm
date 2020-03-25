package com.t251.springbootcrm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;


import java.util.Arrays;

public class TestAuthorization {
    //角色授权，资源授权测试
    @Test    //使用org.junit.jupiter.api.Test报错
    public void testAuthorizationCustomRealm(){
        //创建SecurityManager工厂
        Factory<SecurityManager>factory= new IniSecurityManagerFactory("classpath:shiro.ini");
        //创建SecurityManager
        SecurityManager securityManager=factory.getInstance();
        //将SecurityManager设置到系统运行环境，和spring后将SecurityManager配置spring容器中，一般单例管理
        SecurityUtils.setSecurityManager(securityManager);
        //创建subejct
        Subject subject=SecurityUtils.getSubject();
        //创建token令牌
        UsernamePasswordToken token=new UsernamePasswordToken("zhangsan","111111");
        //执行认证
        try{
            subject.login(token);
        }catch (AuthenticationException e){
        e.printStackTrace();
        }
        System.out.println("认证状态："+subject.isAuthenticated());
        //认证通过后执行授权
        //基于角色的授权
        //hasRole传入角色的标识
        boolean ishasRole=subject.hasRole("role1");
        System.out.println("单个角色判断："+ishasRole);
        //hasAllRole是否拥有多个角色
        boolean hasAllroles=subject.hasAllRoles(Arrays.asList("role1","role2"));
        System.out.println("多个角色判断："+hasAllroles);
        //使用check方法进行授权，如果授权进行不通过会抛出异常
        //subject.checkRole("role1")//有该角色不会抛出异常？没有该角色会抛出异常

        //基于资源的授权
        //isPermitted传入权限标识符
        boolean isPermitted=subject.isPermitted("user:L05");
        System.out.println("单个权限判断"+isPermitted);
        boolean isPermittedAll=subject.isPermittedAll("user:L05","user:L06","user:L0303");
        System.out.println("多个权限判断"+isPermittedAll);
        //使用check方法进行授权，如果授权不通过会抛出异常
        //subject.checkPermission("user:L05");//有该权限不会抛出异常
        //subject.checkPermission("user:L05");//没有该权限会抛出异常
    }
}
