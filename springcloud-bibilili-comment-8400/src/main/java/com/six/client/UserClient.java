package com.six.client;

import com.six.common.BaseRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "bibilili-userssss")
public interface UserClient {
    @RequestMapping(value = "/user/findByuid", method = RequestMethod.POST)
    public BaseRes findByuid(@RequestBody Map map);
}
