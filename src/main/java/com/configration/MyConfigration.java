package com.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.configration.aop.Mdm;
import com.configration.handler.MyHandler;
//如果是单独的一个工程 一定要 在 META-INF/spring.factories
//中配置 那样在引用他的工程 通过 @EnableAutoConfiguration 就可以把单独的插件引入到容器中管理了
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MyConfigration {
	
	@Bean
	public Flower createFlower(){
		return new Flower();
	}
	@Bean
	public Mdm createMdm(){
		return new Mdm();
	}
	
	@Bean
	public RestTemplate restTemplate(ClientHttpRequestFactory factory){
		RestTemplate rt=new RestTemplate(factory);
		
		rt.setUriTemplateHandler(new MyHandler());
		return rt;
	}
	
	 @Bean
	    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
	        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
	        factory.setReadTimeout(5000);//ms
	        factory.setConnectTimeout(15000);//ms
	        return factory;
	    }
}
