package com.bg.bassheadsbg.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class I18NConfig {

    private static final String LANG = "lang";
    private static final String CLASSPATH_I_18_N_MESSAGES = "classpath:i18n/messages";
    private static final String UTF_8 = "UTF-8";

    @Bean
    public LocaleResolver localeResolver() {
        return new CookieLocaleResolver(LANG);
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName(LANG);
        return localeChangeInterceptor;
    }


    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename(CLASSPATH_I_18_N_MESSAGES);
        messageSource.setDefaultEncoding(UTF_8);
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

}