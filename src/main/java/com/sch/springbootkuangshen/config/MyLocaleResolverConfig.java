package com.sch.springbootkuangshen.config;
/*
 *  @Author SunChanHui
 *  @Create Time 2020-04-15
 * @PackName com.sch.springbootkuangshen.config
 * @Project springboot-kuangshen
 */
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author sch
 * 自定义国际化解析器
 */
public class MyLocaleResolverConfig implements LocaleResolver {
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        //获取请求参数，确定是何种语言
        String language=request.getParameter("l");
        //获取默认国际化参数
        Locale defaultLocale = Locale.getDefault();
        //如果请求带有国际化参数
        if(!StringUtils.isEmpty(language)){
            String[] s = language.split("_");
            //Locale(String language, String country)
            defaultLocale=new Locale(s[0],s[1]);
        }
        return defaultLocale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

    }
}
