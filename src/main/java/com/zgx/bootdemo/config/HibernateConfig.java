package com.zgx.bootdemo.config;

import com.zgx.bootdemo.service.impl.BankSerivceImpl;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import javax.persistence.EntityManagerFactory;
import java.util.HashMap;


/**
 * @author zhouguixing
 * @date 2019/4/16 18:24
 * @description 注入SessionFactory
 */
@Configuration
public class HibernateConfig{
    //    <bean name="bankSerivceImpl" class="com.zgx.bootdemo.service.impl.BankSerivceImpl"/>
//    <bean id="bankSerivce" class="org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter">
//        <property name="service" ref="bankSerivceImpl"/>
//        <property name="serviceInterface" value="com.zgx.bootdemo.service.BankSerivce"/>
//    </bean>
//    <bean class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
//        <!--必须配置order 优先使用SimpleUrlHandler-->
//        <property name="order" value="0"/>
//        <property name="urlMap">
//            <map>
//                <entry key="bankSerivce" value-ref="bankSerivce"/>
//            </map>
//        </property>
//    </bean>
    @Autowired
    private BankSerivceImpl bankSerivceImpl;

    @Bean
    public HttpInvokerServiceExporter httpInvokerServiceExporter() {
        HttpInvokerServiceExporter httpInvokerServiceExporter = new HttpInvokerServiceExporter();
        httpInvokerServiceExporter.setService(bankSerivceImpl);
        httpInvokerServiceExporter.setServiceInterface(bankSerivceImpl.getClass().getInterfaces()[0]);
        httpInvokerServiceExporter.afterPropertiesSet();
        return httpInvokerServiceExporter;
    }

    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping(){
        SimpleUrlHandlerMapping simpleUrlHandlerMapping = new SimpleUrlHandlerMapping();
        simpleUrlHandlerMapping.setOrder(0);

        HashMap<String, HttpInvokerServiceExporter> map = new HashMap<>();
        map.put("bankService",httpInvokerServiceExporter() );
        simpleUrlHandlerMapping.setUrlMap(map);
        return simpleUrlHandlerMapping;
    }


    @Bean
    public SessionFactory sesssionFactory(EntityManagerFactory entityManagerFactory){
        if (entityManagerFactory.unwrap(SessionFactory.class) == null) {
            throw new NullPointerException("factory is not a hibernate factory");
        }else{
            return entityManagerFactory.unwrap(SessionFactory.class);
        }
    }


}