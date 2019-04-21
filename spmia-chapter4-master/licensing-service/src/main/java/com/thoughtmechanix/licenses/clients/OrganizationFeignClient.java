package com.thoughtmechanix.licenses.clients;

import org.springframework.cloud.openfeign.FeignClient;
import com.thoughtmechanix.licenses.model.Organization;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
/*
1. 通过@FeignClient 会将接口自动实现成一个类
2. @FeignClient("organizationservice") 参数是 应用服务在eureka注册的服务器ID
3. @使用@RequestMapping注解来定义端点的路径和动作,使用@PathVariable来定义传入端点参数

*/
@FeignClient("organizationservice")
public interface OrganizationFeignClient {
    @RequestMapping(
            method= RequestMethod.GET,
            value="/v1/organizations/{organizationId}",
            consumes="application/json")
    Organization getOrganization(@PathVariable("organizationId") String organizationId);
}
