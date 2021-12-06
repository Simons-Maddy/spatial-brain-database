package com.spatial.spatialbrain.config;

import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.thetransactioncompany.cors.CORSFilter;

    /**
     * 使用配置方式开发Filter,否则其中的自动注入无效
     *
     * @author Chunfu Simons Xiao
     */

    @Configuration
    public class HttpFilterConfig {

        /**
         * com.thetransactioncompany cors-filter
         * @return
         */

        @Bean
        public FilterRegistrationBean<Filter> corsFilter() {
            FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();

            registration.setFilter(new CORSFilter());

            //cors.supportsCredentials {true|false} defaults to true.
            //registration.addInitParameter("cors.supportsCredentials", "true");

            registration.addInitParameter("cors.allowOrigin", "http://localhost:8080,https://cdn.highcharts.com.cn/");
            //不符合时，报错：Cross-Origin Resource Sharing (CORS) Filter: CORS origin denied

            //cors.supportedMethods {method-list} defaults to "GET, POST, HEAD, OPTIONS".
            registration.addInitParameter("cors.supportedMethods", "GET,POST");
            //不符合时，报错：Cross-Origin Resource Sharing (CORS) Filter: Unsupported HTTP method

            //cors.supportedHeaders {"*"|header-list} defaults to *.
            //registration.addInitParameter("cors.supportedHeaders", "*");

            //cors.exposedHeaders {header-list} defaults to empty list.
            //registration.addInitParameter("cors.exposedHeaders", "");

            //cors.maxAge {int} defaults to -1 (unspecified).3600表示一个小时
            registration.addInitParameter("cors.maxAge", "3600");

            //cors.allowSubdomains {true|false} defaults to false.
            //cors.allowGenericHttpRequests {true|false} defaults to true.
            //cors.tagRequests {true|false} defaults to false (no tagging).

            registration.setName("CORSFilter"); //过滤器名称
            registration.addUrlPatterns("/*");//过滤路径
            registration.setOrder(1); //设置顺序
            return registration;
        }
    }
