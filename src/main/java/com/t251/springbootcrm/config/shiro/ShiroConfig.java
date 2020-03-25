package com.t251.springbootcrm.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.t251.springbootcrm.entity.Right;
import com.t251.springbootcrm.service.IUserService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class ShiroConfig {
    @Resource
    private IUserService userService;

    //注入redis参数
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    /*  @Value("${spring.redis.password}")
      private String password;*/
    @Value("${spring.redis.timeout}")
    private int timeout;
    /**
     * 创建ShiroFilterFactoryBean
     * 9
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        System.out.println("ShiroConfiguration.shiroFilter()：配置权限控制规则");
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        //必须设置SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //如果不设置默认会自动寻找web工程根目录下的"/login.jsp"页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        //登录成功后要跳转的链接
        shiroFilterFactoryBean.setSuccessUrl("/main");
        //未授权界面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        //添加shiro内置过滤器,实现权限相关的url拦截
        /**
         * 常见的过滤器
         * anon:无需认证(登录)可以访问
         * authc:必须认证才可以访问
         * user:如果使用Remember Me的功能,可以直接访问
         * perms:该资源必须得到资源权限才可以访问
         * role:该资源必须得到角色权限才可以访问
         */
        Map<String,String>filterChainDefinitionMap=new LinkedHashMap<String,String>();
        //配置不会拦截的链接  顺序判断
        filterChainDefinitionMap.put("/css/**","anon");
        filterChainDefinitionMap.put("/fonts/**","anon");
        filterChainDefinitionMap.put("/images/**","anon");
        filterChainDefinitionMap.put("/js/**","anon");
        filterChainDefinitionMap.put("/localcss/**","anon");
        filterChainDefinitionMap.put("/dologin/**","anon");
       // filterChainDefinitionMap.put("/user/**","anon");
        //配置退出 过滤器，其中的具体的退出代码Shiro已经替我们实现了
        //与AuthorizationInterceptor拦截器冲突
        //  filterChainDefinitionMap.put("/logout","logout");

        //配置测试权限(真实权限应该从数据库中获得)
        //filterChainDefinitionMap.put("/user/list","perms[用户管理]");
        //filterChainDefinitionMap.put("/user/add","perms[用户管理]");
        //filterChainDefinitionMap.put("/role/list","perms[角色管理]");
        //从数据库中动态获得所有权限控制(控制URL访问)
        List<Right>rights=userService.findAllRights();  //获取所有权限控制
        for(Right right:rights){
            if (!right.getRightType().equals("Folder")&&!right.getRightType().equals("Button")){
                System.out.println("过滤器拦截的url："+right.getRightUrl()+"，以及对应需要访问的权限:"+right.getRightText());
                filterChainDefinitionMap.put(right.getRightUrl(),"perms["+right.getRightText()+"]");
            }
        }
        //过滤链定义 从上往下执行 一般将/**放在最下面  这是个坑！
        //authc：所有url都必须认证通过才可以访问；anon：所有url都可以匿名访问
        filterChainDefinitionMap.put("/**","authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }


    /**
     * 创建realm
     * 9
     */
    @Bean(name = "myshiroRealm")
    public MyShiroRealm myshiroRealm(){
        MyShiroRealm myshiroRealm=new MyShiroRealm();
        //告诉realm，使用credentialsMatcher加密算法类来验证密文
        myshiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        //启动缓存及设置缓存名称
        myshiroRealm.setCachingEnabled(true);
        //AuthorizationCachingEnabled 不是cation！！！
        myshiroRealm.setAuthorizationCachingEnabled(true);
        myshiroRealm.setAuthorizationCacheName("authorizationCache");
        return myshiroRealm;
    }

    /**
     * 创建DefaultWebSecurityManager
     * 9
     * @return
     */
    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();

        //自定义缓存实现，使用redis
           securityManager.setCacheManager(cacheManager());
        //自定义session管理 使用redis
          securityManager.setSessionManager(sessionManager());


        //设置realm
        securityManager.setRealm(myshiroRealm());
        return  securityManager;
    }

    /**
     * 9
     * @return
     */
    @Bean(name = "shiroDialect")
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        System.out.println("hashedCredentialsMatcher=======================================");
        HashedCredentialsMatcher hashedCredentialsMatcher=new HashedCredentialsMatcher();
        //使用md5算法进行加密
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //设置散列次数，意味加密几次
        hashedCredentialsMatcher.setHashIterations(1);
        return hashedCredentialsMatcher;
    }


    /* 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     * @return
    */

    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setHost(host);
        redisManager.setPort(port);
        //redisManager.setPassword(password);
        redisManager.setTimeout(timeout);
        return redisManager;
    }

    /*
     * cacheManager缓存redis实现
     * 使用的是shiro-redis开源插件
     * @return
     */

    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        //缓存名称
        redisCacheManager.setPrincipalIdFieldName("usrName");
        //缓存时间
        redisCacheManager.setExpire(1800);
        return redisCacheManager;
    }

    /*
     * RedisSessionDao shiro sessionDao层的实现 使用的是redis shiro开源插件
     * @return
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }
    /*
     * Session Manager
     * 使用的是shiro-redis开源插件
        */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        return sessionManager;
    }
}
