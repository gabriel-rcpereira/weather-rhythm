package com.grcp.weatherrhythm.locationsong.config.message;

import org.springframework.boot.web.servlet.server.Encoding;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

@Configuration
public class MessageConfiguration {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:message/messages");
        messageSource.setDefaultEncoding(Encoding.DEFAULT_CHARSET.displayName());
        return messageSource;
    }
}
