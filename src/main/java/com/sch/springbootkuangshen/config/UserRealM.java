package com.sch.springbootkuangshen.config;
/*
 *  @Author SunChanHui
 *  @Create Time 2020-04-27
 * @PackName com.sch.springbootkuangshen.config
 * @Project springboot-kuangshen
 */

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

/**
 * @author sch
 * 自动以RealM     shiro
 */
public class UserRealM extends AuthorizingRealm {
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo info =new SimpleAuthorizationInfo();
        info.addStringPermission("user:add");
        return info;
    }
    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username="root";
        String password="123456";
        //获取用户的令牌信息进行认证
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
        //用户名验证
        if (!token.getUsername().equals(username)) {
            return null;  //自动抛出UnknownAccountException
        }
        //密码无需验证,由shiro自动验证
        return new SimpleAuthenticationInfo("",password,"");
    }
}
