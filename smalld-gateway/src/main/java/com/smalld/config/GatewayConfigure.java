
package com.smalld.config;

import cn.hutool.extra.spring.SpringUtil;
import feign.codec.Decoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : yesk
 * @classname : GatewayConfig
 * @description : 网关配置
 * @date : 2022/11/16 15:32
 */
@Import({SpringUtil.class})
@Configuration
public class GatewayConfigure {

    /**
     * 无需登录的接口地址集合
     */
    private static final String[] NO_LOGIN_PATH_ARR = {
            /* 主入口 */
            "/",
            /* 静态资源 */
            "/favicon.ico",
            "/doc.html",
            "/webjars/**",
            "/swagger-resources/**",
            "/v2/api-docs",
            "/v2/api-docs-ext",
            "/configuration/ui",
            "/configuration/security",
            "/ureport/**",
            "/druid/**",
            /* 认证相关 */
            "/api/webapp/auth/c/getPicCaptcha",
            "/api/webapp/auth/c/getPhoneValidCode",
            "/api/webapp/auth/c/doLogin",
            "/api/webapp/auth/c/doLoginByPhone",
            "/api/webapp/auth/b/getPicCaptcha",
            "/api/webapp/auth/b/getPhoneValidCode",
            "/api/webapp/auth/b/doLogin",
            "/api/webapp/auth/b/doLoginByPhone",
            /* 三方登录相关 */
            "/api/webapp/auth/third/render",
            "/api/webapp/auth/third/callback",
            /* 系统基础配置 */
            "/api/webapp/dev/config/sysBaseList",
            /* 系统字典树 */
            "/api/webapp/dev/dict/tree",
            /* 文件下载 */
            "/api/webapp/dev/file/download",
    };

    /**
     * 仅超管使用的接口地址集合
     */
    private static final String[] SUPER_PERMISSION_PATH_ARR = {
            "/api/webapp/auth/session/**",
            "/api/webapp/auth/third/page",
            "/api/webapp/client/user/**",
            "/api/webapp/sys/org/**",
            "/api/webapp/sys/position/**",
            "/api/webapp/sys/button/**",
            "/api/webapp/sys/menu/**",
            "/api/webapp/sys/module/**",
            "/api/webapp/sys/spa/**",
            "/api/webapp/sys/role/**",
            "/api/webapp/sys/user/**",
            "/api/webapp/dev/config/**",
            "/api/webapp/dev/dict/**",
            "/api/webapp/dev/email/page",
            "/api/webapp/dev/email/delete",
            "/api/webapp/dev/email/detail",
            "/api/webapp/dev/file/page",
            "/api/webapp/dev/file/list",
            "/api/webapp/dev/file/delete",
            "/api/webapp/dev/file/detail",
            "/api/webapp/dev/job/**",
            "/api/webapp/dev/log/**",
            "/api/webapp/dev/message/page",
            "/api/webapp/dev/message/delete",
            "/api/webapp/dev/message/detail",
            "/api/webapp/dev/monitor/**",
            "/api/webapp/dev/sms/page",
            "/api/webapp/dev/sms/delete",
            "/api/webapp/dev/sms/detail",
            "/api/flwapp/flw/model/**",
            "/api/flwapp/flw/templatePrint/**",
            "/api/flwapp/flw/templateSn/**",
            "/api/bizapp/pay/**",
            "/api/bizapp/urp/**",
            "/api/tenapp/dbs/**",
            "/api/tenapp/ten/"
    };

    /**
     * B端要排除的，相当于C端要认证的
     */
    private static final String[] CLIENT_USER_PERMISSION_PATH_ARR = {
            "/auth/c/**",
            "/client/c/**"
    };


    /**
     * @description Feign解码器
     * @author yesk
     * @date 2022/11/16 1:45
     * @return Decoder
     **/
    @Bean
    public Decoder feignDecoder() {
        return new ResponseEntityDecoder(new SpringDecoder(feignHttpMessageConverter()));
    }

    /**
     * @description Feign http 消息转换器
     * @author yesk
     * @date 2022/11/16 1:46
     * @return
     **/
    public ObjectFactory<HttpMessageConverters> feignHttpMessageConverter() {
        final HttpMessageConverters httpMessageConverters = new HttpMessageConverters(new GateWayMappingJackson2HttpMessageConverter());
        return new ObjectFactory<HttpMessageConverters>() {
            @Override
            public HttpMessageConverters getObject() throws BeansException {
                return httpMessageConverters;
            }
        };
    }

    /**
     * @description 消息转换器
     * @author yesk
     * @date 2022/11/16 1:47
     * @return
     **/
    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

    public class GateWayMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {
        GateWayMappingJackson2HttpMessageConverter(){
            List<MediaType> mediaTypes = new ArrayList<>();
            mediaTypes.add(MediaType.valueOf(MediaType.TEXT_HTML_VALUE + ";charset=UTF-8"));
            setSupportedMediaTypes(mediaTypes);
        }
    }


    /**
     * RedisTemplate序列化
     *
     * @author yesk
     * @date 2022/6/21 17:01
     **/
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        Jackson2JsonRedisSerializer<?> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
