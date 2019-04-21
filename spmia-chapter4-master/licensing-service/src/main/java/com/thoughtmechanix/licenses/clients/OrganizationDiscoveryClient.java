package com.thoughtmechanix.licenses.clients;


import com.thoughtmechanix.licenses.model.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
/*
缺点
1. 没有利用Ribbon负载均，instances.get(0)指定实例
2. 开发人员做太多工作
3. RestTemplate 被实例化，没有用到@autowired注解，原因 @EnableDiscoveryClient启用了 DiscoveryClient客户端，
所有RestTemplat都将注入一个使用Ribbon的拦截器，这个拦截器将改变使用RestTemplate类创建URL的行为，直接实例化可以
改变这种情况，建议避免直接实例化。
*/
@Component
public class OrganizationDiscoveryClient {

    @Autowired
    private DiscoveryClient discoveryClient;

    public Organization getOrganization(String organizationId) {
        RestTemplate restTemplate = new RestTemplate();
        //检索通过Eureka注册的所有组织服务实例。
        //ServiceInstance 类用于保存服务的特定实例，包含主机名，端口和URI
        List<ServiceInstance> instances = discoveryClient.getInstances("organizationservice");

        if (instances.size()==0) return null;
        //url http://ip:port/v1/organizations/id_value
        String serviceUri = String.format("%s/v1/organizations/%s",instances.get(0).getUri().toString(), organizationId);
    
        ResponseEntity< Organization > restExchange =
                restTemplate.exchange(
                        serviceUri,
                        HttpMethod.GET,
                        null, Organization.class, organizationId);

        return restExchange.getBody();
    }
}
